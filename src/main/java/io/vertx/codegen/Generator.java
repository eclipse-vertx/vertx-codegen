package io.vertx.codegen;

/*
 * Copyright 2014 Red Hat, Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Apache License v2.0 which accompanies this distribution.
 *
 * The Eclipse Public License is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * The Apache License v2.0 is available at
 * http://www.opensource.org/licenses/apache2.0.php
 *
 * You may elect to redistribute this code under either of these licenses.
 */

import io.vertx.codegen.annotations.VertxGen;
import org.mvel2.templates.TemplateRuntime;

import javax.annotation.processing.Completion;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.Writer;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.function.Function;
import java.util.logging.Logger;

/**
 *
 * @author <a href="http://tfox.org">Tim Fox</a>
 *
 */
public class Generator {

  private static final Logger log = Logger.getLogger(Generator.class.getName());

  public static final String VERTX_ASYNC_RESULT = "io.vertx.core.AsyncResult";
  public static final String VERTX_HANDLER = "io.vertx.core.Handler";
  public static final String JSON_OBJECT = "io.vertx.core.json.JsonObject";
  public static final String JSON_ARRAY = "io.vertx.core.json.JsonArray";
  public static final String VERTX = "io.vertx.core.Vertx";

  public void validatePackage(String packageName, Function<String, Boolean> packageMatcher) throws Exception {
    genAndApply(packageName, packageMatcher, null, null, false);
  }

  public void genAndApply(String packageName, Function<String, Boolean> packageMatcher,
                          Function<Class, String> outputFileFunction, String templateFileName) throws Exception {
    genAndApply(packageName, packageMatcher, outputFileFunction, templateFileName, true);
  }

  public void genAndApply(Class clazz, Function<Class, String> outputFileFunction, String templateName) throws Exception {
    Generator gen = new Generator();
    Source source = gen.generateModel(clazz);
    source.applyTemplate(outputFileFunction.apply(clazz), templateName);
  }

  private void genAndApply(String packageName, Function<String, Boolean> packageMatcher,
                           Function<Class, String> outputFileFunction, String templateFileName,
                           boolean apply) throws Exception {

    List<Class<?>> classes = ClassEnumerator.getClassesForPackage(packageName, packageMatcher);

    List<Class<?>> generableClasses = new ArrayList<>();
    for (Class<?> clazz: classes) {
      if (clazz.isInterface() && clazz.getAnnotation(VertxGen.class) != null) {
        generableClasses.add(clazz);
      }
    }
    for (Class<?> clazz: generableClasses) {
      Generator gen = new Generator();
      Source source = gen.generateModel(clazz);
      if (apply) {
        source.applyTemplate(outputFileFunction.apply(clazz), templateFileName);
      }
    }
  }

  public Source generateModel(Class c) throws Exception {
    log.info("Generating model for class " + c);
    String className = c.getCanonicalName();
    String fileName = className.replace(".", "/") + ".java";
    InputStream is = getClass().getClassLoader().getResourceAsStream(fileName);
    if (is == null) {
      throw new IllegalStateException("Can't find source on classpath: " + fileName);
    }
    // Load the source
    String source;
    try (Scanner scanner = new Scanner(is, "UTF-8").useDelimiter("\\A")) {
      source = scanner.next();
    }
    // Now copy it to a file (this is clunky but not sure how to get around it)
    String tmpFileName = System.getProperty("java.io.tmpdir") + "/" + fileName;
    File f = new File(tmpFileName);
    File parent = f.getParentFile();
    parent.mkdirs();
    try (PrintStream out = new PrintStream(new FileOutputStream(tmpFileName))) {
      out.print(source);
    }
    return generateModel(tmpFileName);
  }

  private Source generateModel(String sourceFileName) throws Exception {
    JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
    DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
    StandardJavaFileManager fm = compiler.getStandardFileManager(diagnostics, null, null);
    Iterable<? extends JavaFileObject> iter = fm.getJavaFileObjects(sourceFileName);
    JavaFileObject fo = iter.iterator().next();
    Writer out = new NullWriter();
    List<JavaFileObject> fileObjects = Collections.singletonList(fo);
    JavaCompiler.CompilationTask task = compiler.getTask(out, fm, diagnostics, null, null, fileObjects);
    MyProcessor processor = new MyProcessor();
    List<Processor> processors = Collections.<Processor>singletonList(processor);
    task.setProcessors(processors);
    try {
      task.call();
    } catch (RuntimeException e) {
      if (e.getCause() != null && e.getCause() instanceof RuntimeException) {
        throw (RuntimeException)e.getCause();
      } else {
        throw e;
      }
    }
    if (processor.processed == null) {
      throw new IllegalArgumentException(sourceFileName + " not processed. Does it have the VertxGen annotation?");
    }
    return processor.processed;
  }

  private void dumpClasspath(ClassLoader cl) {
    if (cl instanceof URLClassLoader) {
      URLClassLoader urlc = (URLClassLoader)cl;
      URL[] urls = urlc.getURLs();
      System.out.println("Dumping urls:");
      for (URL url: urls) {
        System.out.println(url);
      }
    } else {
      System.out.println("Not URLClassloader!");
    }
  }


  private void sortMethodMap(Map<String, List<MethodInfo>> map) {
    for (List<MethodInfo> list: map.values()) {
      list.sort((meth1, meth2) -> meth1.params.size() - meth2.params.size());
    }
  }

  private static class NullWriter extends Writer {
    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
    }

    @Override
    public void flush() throws IOException {
    }

    @Override
    public void close() throws IOException {
    }
  }

  private class MyProcessor implements Processor {

    @Override
    public Set<String> getSupportedOptions() {
      return Collections.emptySet();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
      HashSet<String> set = new HashSet<>();
      set.add(VertxGen.class.getCanonicalName());
      return set;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
      return SourceVersion.RELEASE_8;
    }

    private ProcessingEnvironment env;
    private Elements elementUtils;
    private Types typeUtils;
    private Source processed;

    @Override
    public void init(ProcessingEnvironment processingEnv) {
      this.env = processingEnv;
      elementUtils = env.getElementUtils();
      typeUtils = env.getTypeUtils();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
      if (!roundEnv.processingOver()) {
        processed = roundEnv.getRootElements().
            stream().
            map(elem -> new Source(elem).traverse(elementUtils, typeUtils)).
            findFirst().
            get();
      }
      return true;
    }

    @Override
    public Iterable<? extends Completion> getCompletions(Element element, AnnotationMirror annotation, ExecutableElement member, String userText) {
      return Collections.emptyList();
    }
  }
}
