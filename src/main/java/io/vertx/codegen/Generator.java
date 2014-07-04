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

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.gen.Fluent;
import io.vertx.core.gen.GenIgnore;
import io.vertx.core.gen.IndexGetter;
import io.vertx.core.gen.IndexSetter;
import io.vertx.core.gen.VertxGen;
import org.mvel2.templates.TemplateRuntime;

import javax.annotation.processing.Completion;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
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
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;


/**
 *
 * TODO tests -
 *
 * test with some test interfaces
 * test with interfaces that fail validation:
 *
 *   1. illegal types
 *   2. not interfaces
 *   3. overloaded methods with params in different sequence
 *   4. inner interface
 *   5. default methods
 *   6. static methods
 *
 * @author <a href="http://tfox.org">Tim Fox</a>
 *
 */
public class Generator {

  public void processFromClasspath(Class c, String outputFileName, String templateName) throws Exception {
    System.out.println("Processing class " + c);
    String className = c.getCanonicalName();
    String fileName = className.replace(".", "/") + ".java";
    InputStream is = getClass().getClassLoader().getResourceAsStream(fileName);
    if (is == null) {
      throw new IllegalStateException("Can't find file on classpath: " + fileName);
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
    boolean ok = parent.mkdirs();
    try (PrintStream out = new PrintStream(new FileOutputStream(tmpFileName))) {
      out.print(source);
    }
    process(tmpFileName, outputFileName, templateName);
  }

  public void process(String sourceFileName, String outputFileName, String templateName) throws Exception {
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
    task.call();

    if (!processor.processed) {
      throw new IllegalStateException("Interface not processed. Does it have the VertxGen annotation?");
    }
    if (processor.ifaceSimpleName == null) {
      throw new IllegalStateException("@VertxGen should only be used with interfaces");
    }
    String template = new String(Files.readAllBytes(Paths.get(templateName)));

    // MVEL preserves all whitespace therefore, so we can have readable templates we remove all line breaks
    // and replace all occurrences of "\n" with a line break
    // "\n" signifies we want an actual line break in the output
    // We use actual tab characters in the template so we can see indentation, but we strip these out
    // before parsing - so much sure you configure your IDE to use tabs as spaces for .templ files
    template = template.replace("\n", "").replace("\\n", "\n").replace("\t", "");

    // TODO - make sure there are no overloaded methods with same number of parameters or constructors

    Map<String, Object> vars = new HashMap<>();
    vars.put("ifaceSimpleName", processor.ifaceSimpleName);
    vars.put("ifaceFQCN", processor.ifaceFQCN);
    vars.put("ifaceComment", processor.ifaceComment);
    vars.put("helper", new Helper());
    vars.put("methods", processor.methods);
    processor.referencedTypes.addAll(processor.superTypes);
    vars.put("referencedTypes", processor.referencedTypes);
    vars.put("superTypes", processor.superTypes);
    vars.put("squashedMethods", processor.squashedMethods.values());
    sortMethodMap(processor.methodMap);
    vars.put("methodsByName", processor.methodMap);

    String output = (String)TemplateRuntime.eval(template, vars);
    File outFile = new File(outputFileName);
    if (!outFile.getParentFile().exists()) {
      outFile.getParentFile().mkdirs();
    }
    try (PrintStream outStream = new PrintStream(new FileOutputStream(outFile))) {
      outStream.print(output);
    }
  }

  private void sortMethodMap(Map<String, List<MethodInfo>> map) {
    for (List<MethodInfo> list: map.values()) {
      list.sort((meth1, meth2) -> meth1.params.size() - meth2.params.size());
    }
  }


  private void checkType(String type) {

    if (Helper.isBasicType(type)) {
      return;
    }

    String nonGenericType = Helper.getNonGenericType(type);
    String genericType = Helper.getGenericType(type);

    if ((nonGenericType.equals("java.util.List") || nonGenericType.equals("java.util.Set")) && Helper.isBasicType(genericType)) {
      return;
    }

    if (type.startsWith("java.") || type.startsWith("javax.")) {
      throw new IllegalStateException("Invalid type " + type + " in return type or parameter of API method");
    }

    // TODO proper type checking

  }

  class NullWriter extends Writer {
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

  class MyProcessor implements Processor {

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

    ProcessingEnvironment env;
    Elements elementUtils;
    Types typeUtils;
    boolean processed;
    List<MethodInfo> methods = new ArrayList<>();
    List<MethodInfo> staticMethods = new ArrayList<>();
    Set<String> referencedTypes = new HashSet<>();
    String ifaceSimpleName;
    String ifaceFQCN;
    String ifaceComment;
    List<String> superTypes = new ArrayList<>();
    // The methods, grouped by name
    Map<String, List<MethodInfo>> methodMap = new HashMap<>();

    // Methods where all overloaded methods with same name are squashed into a single method with all parameters
    Map<String, MethodInfo> squashedMethods = new HashMap();

    @Override
    public void init(ProcessingEnvironment processingEnv) {
      this.env = processingEnv;
      elementUtils = env.getElementUtils();
      typeUtils = env.getTypeUtils();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
      roundEnv.getRootElements().forEach(this::traverseElem);
      processed = true;
      return true;
    }

    private void traverseElem(Element elem) {
      switch (elem.getKind()) {
        case INTERFACE:
        case CLASS:
        {
          if (ifaceFQCN != null) {
            throw new IllegalStateException("Can only have one interface or class per file");
          }
          ifaceFQCN = elem.asType().toString();
          ifaceSimpleName = elem.getSimpleName().toString();
          ifaceComment = elementUtils.getDocComment(elem);
          TypeMirror tm = elem.asType();
          List<? extends TypeMirror> st = typeUtils.directSupertypes(tm);
          for (TypeMirror tmSuper: st) {
            Element superElement = typeUtils.asElement(tmSuper);
            if (superElement.getAnnotation(VertxGen.class) != null) {
              superTypes.add(Helper.getNonGenericType(tmSuper.toString()));
            }
          }
          break;
        }
        case METHOD:
        {
          ExecutableElement execElem = (ExecutableElement)elem;
          boolean isIgnore = execElem.getAnnotation(GenIgnore.class) != null;
          if (isIgnore) {
            break;
          }
          Set<Modifier> mods = execElem.getModifiers();
          if (!mods.contains(Modifier.PUBLIC)) {
            break;
          }
          boolean isStatic = mods.contains(Modifier.STATIC);
          boolean isFluent = execElem.getAnnotation(Fluent.class) != null;
          boolean isIndexGetter = execElem.getAnnotation(IndexGetter.class) != null;
          boolean isIndexSetter = execElem.getAnnotation(IndexSetter.class) != null;
          List<ParamInfo> mParams = getParams(execElem);
          String returnType = execElem.getReturnType().toString();
          checkType(returnType);
          if (!Helper.getNonGenericType(returnType).equals(Handler.class.getName())) {
            checkAddReferencedType(Helper.getNonGenericType(returnType));
          }
          String methodName = execElem.getSimpleName().toString();
          List<MethodInfo> meths = methodMap.get(methodName);
          if (meths == null) {
            meths = new ArrayList<>();
            methodMap.put(methodName, meths);
          } else {
            // Overloaded methods must have same parameter at each position in the param list
            for (MethodInfo meth: meths) {
              int pos = 0;
              for (ParamInfo param: meth.params) {
                if (pos < mParams.size()) {
                  if (!mParams.get(pos).equals(param)) {
                    throw new IllegalStateException("Overloaded method " + methodName + " has versions with different sequences of parameters");
                  }
                } else {
                  break;
                }
                pos++;
              }
            }
          }
          MethodInfo methodInfo = new MethodInfo(methodName, returnType,
                 isFluent, isIndexGetter, isIndexSetter, mParams, elementUtils.getDocComment(execElem), isStatic);
          meths.add(methodInfo);
          methods.add(methodInfo);
          MethodInfo squashed = squashedMethods.get(methodName);
          if (squashed == null) {
            squashed = new MethodInfo(methodName, returnType,
              isFluent, isIndexGetter, isIndexSetter, mParams, elementUtils.getDocComment(execElem), isStatic);
            squashedMethods.put(methodName, squashed);
          } else {
            squashed.addParams(mParams);
          }
          break;
        }
      }

      elem.getEnclosedElements().forEach(this::traverseElem);
    }

    private List<ParamInfo> getParams(ExecutableElement execElem) {
      List<? extends VariableElement> params = execElem.getParameters();
      List<ParamInfo> mParams = new ArrayList<>();
      for (VariableElement param: params) {
        String paramType = param.asType().toString();
        checkType(paramType);
        String nonGenericType = Helper.getNonGenericType(paramType);
        if (nonGenericType.equals(Handler.class.getCanonicalName())) {
          String genericType = Helper.getGenericType(paramType);
          if (genericType != null) {
            if (Helper.getNonGenericType(genericType).equals(AsyncResult.class.getCanonicalName())) {
              genericType = Helper.getGenericType(genericType);
              if (genericType != null) {
                checkAddReferencedType(genericType);
              }
            } else {
              checkAddReferencedType(genericType);
            }
          }
        }

        boolean option = param.asType().toString().endsWith("Options");
        System.out.println("param is " + param.getSimpleName() + " class is " + param.asType() + " option? " + option);

        ParamInfo mParam = new ParamInfo(param.getSimpleName().toString(), param.asType().toString(), option);
        mParams.add(mParam);
      }
      return mParams;
    }

    private void checkAddReferencedType(String type) {
      if (type != null && !type.startsWith("java.") && !type.equals(ifaceFQCN) && type.contains(".")) {
        referencedTypes.add(type);
        if (type.equals("io.vertx.core.Handler")) {
          new Exception().printStackTrace();
        }
      }
    }

    @Override
    public Iterable<? extends Completion> getCompletions(Element element, AnnotationMirror annotation, ExecutableElement member, String userText) {
      return Collections.emptyList();
    }

  }
}
