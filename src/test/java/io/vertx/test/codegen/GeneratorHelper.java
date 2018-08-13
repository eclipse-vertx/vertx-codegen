package io.vertx.test.codegen;

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

import io.vertx.codegen.*;
import io.vertx.codegen.Compiler;
import io.vertx.codegen.annotations.ModuleGen;
import io.vertx.codegen.annotations.DataObject;
import io.vertx.codegen.annotations.ProxyGen;
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
import javax.tools.JavaFileObject;
import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 *
 * @author <a href="http://tfox.org">Tim Fox</a>
 *
 */
public class GeneratorHelper {

  DiagnosticCollector<JavaFileObject> collector = new DiagnosticCollector<>();

  public PackageModel generatePackage(Class clazz, Set<Class> otherSupportedAnnotations) throws Exception {
    URL url = clazz.getClassLoader().getResource(clazz.getName().replace('.', '/') + ".java");
    File f = new File(url.toURI());
    MyProcessor<PackageModel> processor = new MyProcessor<>(codegen -> codegen.getPackageModel(clazz.getPackage().getName()), otherSupportedAnnotations.stream().map(Class::getCanonicalName).collect(Collectors.toSet()));
    Compiler compiler = new Compiler(processor, collector);
    compiler.compile(f);
    return processor.result;
  }

  public PackageModel generatePackage(Class clazz) throws Exception {
    return this.generatePackage(clazz, new HashSet<>());
  }

  public ModuleModel generateModule(ClassLoader loader, String packageFqn, Set<Class> otherSupportedAnnotations) throws Exception {
    URL url = loader.getResource(packageFqn.replace('.', '/') + "/package-info.java");
    File info = new File(url.toURI());
    File[] files = Files.walk(info.getParentFile().toPath()).filter(Files::isRegularFile).map(Path::toFile).toArray(File[]::new);
    MyProcessor<ModuleModel> processor = new MyProcessor<>(codegen -> codegen.getModuleModel(packageFqn), otherSupportedAnnotations.stream().map(Class::getCanonicalName).collect(Collectors.toSet()));
    Compiler compiler = new Compiler(processor, collector);
    compiler.compile(files);
    return processor.result;
  }

  public ModuleModel generateModule(ClassLoader loader, String packageFqn) throws Exception {
    return this.generateModule(loader, packageFqn, new HashSet<>());
  }

  public DataObjectModel generateDataObject(Class c, Class... rest) throws Exception {
    return generateClass(codegen -> codegen.getDataObjectModel(c.getCanonicalName()), c, rest);
  }

  public ClassModel generateClass(Class c, Class... rest) throws Exception {
    return generateClass(codegen -> codegen.getClassModel(c.getCanonicalName()), c, rest);
  }

  public EnumModel generateEnum(Class c, Class... rest) throws Exception {
    return generateClass(codegen -> codegen.getEnumModel(c.getCanonicalName()), c, rest);
  }

  public <M> M generateClass(Function<CodeGen, M> f, Set<Class> otherSupportedAnnotations, Class c, Class... rest) throws Exception {
    ArrayList<Class> types = new ArrayList<>();
    types.add(c);
    Collections.addAll(types, rest);
    String className = c.getCanonicalName();
    MyProcessor<M> processor = new MyProcessor<>(f, otherSupportedAnnotations.stream().map(Class::getCanonicalName).collect(Collectors.toSet()));
    Compiler compiler = new Compiler(processor, collector);
    compiler.compile(types);
    if (processor.result == null) {
      throw new IllegalArgumentException(className + " not processed.");
    }
    return processor.result;
  }

  public <M> M generateClass(Function<CodeGen, M> f, Class annotation, Class c, Class... rest) throws Exception {
    Set<Class> s = new HashSet<>();
    s.add(annotation);
    return this.generateClass(f, s, c, rest);
  }

  public <M> M generateClass(Function<CodeGen, M> f, Class c, Class... rest) throws Exception {
    return this.generateClass(f, new HashSet<>(), c, rest);
  }

  private class MyProcessor<R> implements Processor {

    private ProcessingEnvironment env;
    private final Function<CodeGen, R> f;
    private R result;
    private Set<String> supportedAnnotations;

    private MyProcessor(Function<CodeGen, R> f, Set<String> otherSupportedAnnotations) {
      this.f = f;
      this.supportedAnnotations = new HashSet<>();
      this.supportedAnnotations.add(ProxyGen.class.getCanonicalName());
      this.supportedAnnotations.add(VertxGen.class.getCanonicalName());
      this.supportedAnnotations.add(DataObject.class.getCanonicalName());
      this.supportedAnnotations.add(ModuleGen.class.getCanonicalName());
      this.supportedAnnotations.addAll(otherSupportedAnnotations);
    }

    @Override
    public Set<String> getSupportedOptions() {
      return Collections.emptySet();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
      return supportedAnnotations;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
      return SourceVersion.RELEASE_8;
    }

    @Override
    public void init(ProcessingEnvironment processingEnv) {
      this.env = processingEnv;
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
      if (!roundEnv.processingOver()) {
        CodeGen codegen = new CodeGen(env, roundEnv, Thread.currentThread().getContextClassLoader());
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
