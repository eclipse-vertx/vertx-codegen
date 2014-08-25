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

import io.vertx.codegen.annotations.GenModule;
import io.vertx.codegen.annotations.Options;
import io.vertx.codegen.annotations.VertxGen;

import javax.annotation.processing.Completion;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
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
import java.util.HashMap;
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

  Template template; // Global trivial compiled template cache
  HashMap<String, String> options = new HashMap<>();

  public void setOption(String name, String value) {
    options.put(name, value);
  }

  public void validatePackage(String packageName, Function<String, Boolean> packageMatcher) throws Exception {
    genAndApply(packageName, packageMatcher, null, null, false);
  }

  public void genAndApply(String packageName, Function<String, Boolean> packageMatcher,
                          Function<Class, String> outputFileFunction, String templateFileName) throws Exception {
    genAndApply(packageName, packageMatcher, outputFileFunction, templateFileName, true);
  }

  public void genAndApply(Class clazz, Function<Class, String> outputFileFunction, String templateName) throws Exception {
    ClassModel model = generateModel(clazz);
    applyTemplate(model, outputFileFunction.apply(clazz), templateName);
  }

  private void applyTemplate(ClassModel model, String outputFileName, String templateName) throws Exception {
    if (template != null && !templateName.equals(template.getName())) {
      template = null;
    }
    if (template == null) {
      template = new Template(templateName);
      template.setOptions(options);
    }
    template.apply(model, outputFileName);
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
      ClassModel model = gen.generateModel(clazz, generableClasses.toArray(new Class[generableClasses.size()]));
      if (apply) {
        applyTemplate(model, outputFileFunction.apply(clazz), templateFileName);
      }
    }
  }

  public ModuleModel generateModule(ClassLoader loader, String packageFqn) throws Exception {
    URL url = loader.getResource(packageFqn.replace('.', '/') + "/package-info.java");
    File f = new File(url.toURI());
    MyProcessor<ModuleModel> processor = new MyProcessor<>(codegen -> codegen.getModuleModel(packageFqn));
    processor.run(f);
    return processor.result;
  }

  public void validateOption(Class option) throws Exception {
    MyProcessor<Exception> processor = new MyProcessor<>(codegen -> {
      try {
        codegen.validateOption(option.getName());
        return null;
      } catch (Exception e) {
        return e;
      }
    });
    processor.run(Collections.singletonList(option));
    if (processor.result != null) {
      throw processor.result;
    }
  }

  public ClassModel generateModel(Class c, Class... rest) throws Exception {
    log.info("Generating model for class " + c);
    ArrayList<Class> types = new ArrayList<>();
    types.add(c);
    Collections.addAll(types, rest);
    String className = c.getCanonicalName();
    MyProcessor<ClassModel> processor = new MyProcessor<>(codegen -> codegen.getClassModel(className));
    processor.run(types);
    if (processor.result == null) {
      throw new IllegalArgumentException(className + " not processed. Does it have the VertxGen annotation?");
    }
    return processor.result;
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

  private class MyProcessor<R> implements Processor {

    @Override
    public Set<String> getSupportedOptions() {
      return Collections.emptySet();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
      HashSet<String> set = new HashSet<>();
      set.add(VertxGen.class.getCanonicalName());
      set.add(Options.class.getCanonicalName());
      set.add(GenModule.class.getCanonicalName());
      return set;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
      return SourceVersion.RELEASE_8;
    }

    private ProcessingEnvironment env;
    private final Function<CodeGen, R> f;
    private R result;

    private MyProcessor(Function<CodeGen, R> f) {
      this.f = f;
    }

    @Override
    public void init(ProcessingEnvironment processingEnv) {
      this.env = processingEnv;
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
      if (!roundEnv.processingOver()) {
        CodeGen codegen = new CodeGen(env, roundEnv);
        result = f.apply(codegen);
      }
      return true;
    }

    @Override
    public Iterable<? extends Completion> getCompletions(Element element, AnnotationMirror annotation, ExecutableElement member, String userText) {
      return Collections.emptyList();
    }

    public void run(List<Class> types) throws Exception {
      ArrayList<File> tmpFiles = new ArrayList<>();
      for (Class type : types) {
        String className = type.getCanonicalName();
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
        tmpFiles.add(f);
      }
      run(tmpFiles.toArray(new File[tmpFiles.size()]));
    }

    public void run(File... sourceFiles) throws Exception {
      JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
      DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
      StandardJavaFileManager fm = compiler.getStandardFileManager(diagnostics, null, null);
      Iterable<? extends JavaFileObject> fileObjects = fm.getJavaFileObjects(sourceFiles);
      Writer out = new NullWriter();
      JavaCompiler.CompilationTask task = compiler.getTask(out, fm, diagnostics, null, null, fileObjects);
      List<Processor> processors = Collections.<Processor>singletonList(this);
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
    }
  }
}
