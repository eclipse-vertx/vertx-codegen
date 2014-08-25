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

import io.vertx.codegen.annotations.Options;
import io.vertx.codegen.annotations.VertxGen;

import javax.annotation.processing.Completion;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
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

  HashMap<String, TypeElement> sources = new HashMap<>();
  HashMap<String, String> options = new HashMap<>();
  Template template; // Global trivial compiled template cache


  void addSources(Iterable<? extends Element> elements) {
    elements.forEach(element -> sources.put(Helper.getNonGenericType(element.asType().toString()), (TypeElement) element));
  }

  Model resolve(Elements elementUtils, Types typeUtils, String fqcn) {

    TypeElement element = sources.get(fqcn);
    if (element == null) {
      throw new IllegalArgumentException("Source for " + fqcn + " not found");
    } else {
      Model model = new Model(this, elementUtils, typeUtils, element);
      model.process();
      return model;
    }
  }

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
    Model model = generateModel(clazz);
    applyTemplate(model, outputFileFunction.apply(clazz), templateName);
  }

  private void applyTemplate(Model model, String outputFileName, String templateName) throws Exception {
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
      Model model = gen.generateModel(clazz, generableClasses.toArray(new Class[generableClasses.size()]));
      if (apply) {
        applyTemplate(model, outputFileFunction.apply(clazz), templateFileName);
      }
    }
  }

  public void checkOptions(Class option) throws Exception {
    new MyProcessor().run(Collections.singletonList(option));
  }

  public Model generateModel(Class c, Class... rest) throws Exception {
    log.info("Generating model for class " + c);
    ArrayList<Class> types = new ArrayList<>();
    types.add(c);
    Collections.addAll(types, rest);
    String className = c.getCanonicalName();
    MyProcessor processor = new MyProcessor(className);
    processor.run(types);
    if (processor.model == null) {
      throw new IllegalArgumentException(className + " not processed. Does it have the VertxGen annotation?");
    }
    return processor.model;
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
      set.add(Options.class.getCanonicalName());
      return set;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
      return SourceVersion.RELEASE_8;
    }

    private String type;
    private ProcessingEnvironment env;
    private Elements elementUtils;
    private Types typeUtils;
    private Model model;

    private MyProcessor(String type) {
      this.type = type;
    }

    private MyProcessor() {
    }

    @Override
    public void init(ProcessingEnvironment processingEnv) {
      this.env = processingEnv;
      elementUtils = env.getElementUtils();
      typeUtils = env.getTypeUtils();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
      if (!roundEnv.processingOver()) {
        // Check options
        roundEnv.getElementsAnnotatedWith(Options.class).forEach(element -> {
          checkOption(elementUtils, element);
        });
        addSources(roundEnv.getElementsAnnotatedWith(VertxGen.class));
        if (type != null) {
          model = Generator.this.resolve(elementUtils, typeUtils, type);
        }
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

  public void checkOption(Elements elementUtils, Element optionElt) {
    if (optionElt.getKind() == ElementKind.INTERFACE) {
      for (Element memberElt : elementUtils.getAllMembers((TypeElement) optionElt)) {
        if (memberElt.getKind() == ElementKind.METHOD) {
          if (memberElt.getSimpleName().toString().equals("optionsFromJson")) {
            if (memberElt.getModifiers().contains(Modifier.STATIC)) {
              // TODO should probably also test that the method returns the right options type and
              // takes JsonObject as a parameter
              return;
            }
          }
        }
      }
      throw new GenException(optionElt, "Options " + optionElt + " class does not have a static factory method called optionsFromJson");
    } else {
      throw new GenException(optionElt, "Options " + optionElt + " must be an interface not a class");
    }
  }
}
