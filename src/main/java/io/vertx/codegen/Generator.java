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
import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaFileObject;
import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
  DiagnosticCollector<JavaFileObject> collector = new DiagnosticCollector<>();

  public List<Diagnostic<? extends JavaFileObject>> getDiagnostics() {
    return collector.getDiagnostics();
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

  public PackageModel generatePackage(Class clazz) throws Exception {
    URL url = clazz.getClassLoader().getResource(clazz.getName().replace('.', '/') + ".java");
    File f = new File(url.toURI());
    MyProcessor<PackageModel> processor = new MyProcessor<>(codegen -> codegen.getPackageModel(clazz.getPackage().getName()));
    Compiler compiler = new Compiler(processor, collector);
    compiler.compile(f);
    return processor.result;
  }

  public ModuleModel generateModule(ClassLoader loader, String packageFqn) throws Exception {
    URL url = loader.getResource(packageFqn.replace('.', '/') + "/package-info.java");
    File f = new File(url.toURI());
    MyProcessor<ModuleModel> processor = new MyProcessor<>(codegen -> codegen.getModuleModel(packageFqn));
    Compiler compiler = new Compiler(processor, collector);
    compiler.compile(f);
    return processor.result;
  }

  public OptionsModel generateOptions(Class option) throws Exception {
    MyProcessor<OptionsModel> processor = new MyProcessor<>(codegen -> codegen.getOptionsModel(option.getName()));
    Compiler compiler = new Compiler(processor, collector);
    compiler.compile(Collections.singletonList(option));
    return processor.result;
  }

  public ClassModel generateModel(Class c, Class... rest) throws Exception {
    log.info("Generating model for class " + c);
    ArrayList<Class> types = new ArrayList<>();
    types.add(c);
    Collections.addAll(types, rest);
    String className = c.getCanonicalName();
    MyProcessor<ClassModel> processor = new MyProcessor<>(codegen -> codegen.getClassModel(className));
    Compiler compiler = new Compiler(processor, collector);
    compiler.compile(types);
    if (processor.result == null) {
      throw new IllegalArgumentException(className + " not processed. Does it have the VertxGen annotation?");
    }
    return processor.result;
  }

  public ProxyModel generateProxyModel(Class c, Class... rest) throws Exception {
    log.info("Generating proxy model for class " + c);
    ArrayList<Class> types = new ArrayList<>();
    types.add(c);
    Collections.addAll(types, rest);
    String className = c.getCanonicalName();
    MyProcessor<ProxyModel> processor = new MyProcessor<>(codegen -> codegen.getProxyModel(className));
    Compiler compiler = new Compiler(processor, collector);
    compiler.compile(types);
    if (processor.result == null) {
      throw new IllegalArgumentException(className + " not processed. Does it have the ProxyGen annotation?");
    }
    return processor.result;
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
  }
}
