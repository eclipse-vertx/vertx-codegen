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

import io.vertx.codegen.annotations.CacheReturn;
import io.vertx.codegen.annotations.Fluent;
import io.vertx.codegen.annotations.GenIgnore;
import io.vertx.codegen.annotations.IndexGetter;
import io.vertx.codegen.annotations.IndexSetter;
import io.vertx.codegen.annotations.Options;
import io.vertx.codegen.annotations.VertxGen;
import org.mvel2.templates.TemplateRuntime;

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
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
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

  private static final String VERTX_ASYNC_RESULT = "io.vertx.core.AsyncResult";
  private static final String VERTX_HANDLER = "io.vertx.core.Handler";
  private static final String JSON_OBJECT = "io.vertx.core.json.JsonObject";
  private static final String JSON_ARRAY = "io.vertx.core.json.JsonArray";
  private static final String VERTX = "io.vertx.core.Vertx";

  private MyProcessor processor = new MyProcessor();
  private List<MethodInfo> methods = new ArrayList<>();
  private Set<String> referencedTypes = new HashSet<>();
  private String ifaceSimpleName;
  private String ifaceFQCN;
  private String ifaceComment;
  private List<String> superTypes = new ArrayList<>();
  // The methods, grouped by name
  private Map<String, List<MethodInfo>> methodMap = new LinkedHashMap<>();
  private Set<String> referencedOptionsTypes = new HashSet<>();

  // Methods where all overloaded methods with same name are squashed into a single method with all parameters
  private Map<String, MethodInfo> squashedMethods = new LinkedHashMap<>();
  private boolean processed;

  public void validatePackage(String packageName, Function<String, Boolean> packageMatcher) throws Exception {
    genAndApply(packageName, packageMatcher, null, null, false);
  }

  public void genAndApply(String packageName, Function<String, Boolean> packageMatcher,
                          Function<Class, String> outputFileFunction, String templateFileName) throws Exception {
    genAndApply(packageName, packageMatcher, outputFileFunction, templateFileName, true);
  }

  public void genAndApply(Class clazz, Function<Class, String> outputFileFunction, String templateName) throws Exception {
    Generator gen = new Generator();
    gen.generateModel(clazz);
    gen.applyTemplate(outputFileFunction.apply(clazz), templateName);
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
      gen.generateModel(clazz);
      if (apply) {
        gen.applyTemplate(outputFileFunction.apply(clazz), templateFileName);
      }
    }
  }

  public void generateModel(Class c) throws Exception {
    if (processed) {
      throw new IllegalStateException("Already processed");
    }
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
    generateModel(tmpFileName);
  }

  private void generateModel(String sourceFileName) throws Exception {
    JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
    DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
    StandardJavaFileManager fm = compiler.getStandardFileManager(diagnostics, null, null);
    Iterable<? extends JavaFileObject> iter = fm.getJavaFileObjects(sourceFileName);
    JavaFileObject fo = iter.iterator().next();
    Writer out = new NullWriter();
    List<JavaFileObject> fileObjects = Collections.singletonList(fo);
    JavaCompiler.CompilationTask task = compiler.getTask(out, fm, diagnostics, null, null, fileObjects);
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
    if (!processed) {
      throw new IllegalArgumentException(sourceFileName + " not processed. Does it have the VertxGen annotation?");
    }
  }

  public void applyTemplate(String outputFileName, String templateName) throws Exception {
    // Read the template file from the classpath
    InputStream is = getClass().getClassLoader().getResourceAsStream(templateName);
    if (is == null) {
      throw new IllegalStateException("Can't find template file on classpath: " + templateName);
    }
    // Load the template
    String template;
    try (Scanner scanner = new Scanner(is, "UTF-8").useDelimiter("\\A")) {
      template = scanner.next();
    }
    // MVEL preserves all whitespace therefore, so we can have readable templates we remove all line breaks
    // and replace all occurrences of "\n" with a line break
    // "\n" signifies we want an actual line break in the output
    // We use actual tab characters in the template so we can see indentation, but we strip these out
    // before parsing.
    // So use tabs for indentation that YOU want to see in the template but won't be in the final output
    // And use spaces for indentation that WILL be in the final output
    template = template.replace("\n", "").replace("\\n", "\n").replace("\t", "");

    Map<String, Object> vars = new HashMap<>();
    vars.put("ifaceSimpleName", ifaceSimpleName);
    vars.put("ifaceFQCN", ifaceFQCN);
    vars.put("ifaceComment", ifaceComment);
    vars.put("helper", new Helper());
    vars.put("methods", methods);
    vars.put("referencedTypes", referencedTypes);
    vars.put("superTypes", superTypes);
    vars.put("squashedMethods", squashedMethods.values());
    vars.put("methodsByName", methodMap);
    vars.put("referencedOptionsTypes", referencedOptionsTypes);

    ClassLoader now = Thread.currentThread().getContextClassLoader();
    String output;
    try {
      // Be sure to have mvel classloader as parent during evaluation as it will need mvel classes
      // when generating code
      Thread.currentThread().setContextClassLoader(TemplateRuntime.class.getClassLoader());
      output = (String) TemplateRuntime.eval(template, vars);
    } finally {
      Thread.currentThread().setContextClassLoader(now);
    }
    File outFile = new File(outputFileName);
    if (!outFile.getParentFile().exists()) {
      outFile.getParentFile().mkdirs();
    }
    try (PrintStream outStream = new PrintStream(new FileOutputStream(outFile))) {
      outStream.print(output);
      outStream.flush();
    }
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

  private void checkParamType(Elements elementUtils, Element elem, String type) {

    // Basic types, int, long, String etc
    if (Helper.isBasicType(type)) {
      return;
    }
    // JsonObject or JsonArray
    if (isJsonType(type)) {
      return;
    }
    // Also can use Object as a param type (e.g. for EventBus)
    if (Object.class.getName().equals(type)) {
      return;
    }
    // Can also have a Handler<T> legally as param if T = basic type or VertxGen type
    String nonGenericType = Helper.getNonGenericType(type);
    String genericType = Helper.getGenericType(type);
    if (isLegalHandlerType(elementUtils, nonGenericType, genericType)) {
      return;
    }
    // Can also have a Handler<AsyncResult<T>> legally as param, if T = basic type or VertxGen type
    if (isLegalHandlerAsyncResultType(elementUtils, nonGenericType, genericType)) {
      return;
    }
    // Another user defined interface with the @VertxGen annotation is OK
    if (isVertxGenInterface(elementUtils, type)) {
      return;
    }
    // Can also specify option classes (which aren't VertxGen)
    if (isOptionType(elementUtils, elem, type)) {
      return;
    }
    throw new GenException(elem, "type " + type + " is not legal for use for a parameter in code generation");
  }

  private void checkReturnType(Elements elementUtils, Element elem, String type) {

    //System.out.println("Checking return type " + type);

    // Basic types, int, long, String etc
    if (Helper.isBasicType(type)) {
      return;
    }

    // JsonObject or JsonArray
    if (isJsonType(type)) {
      return;
    }

    // List<T> and Set<T> are also legal for returns if T = basic type
    String nonGenericType = Helper.getNonGenericType(type);
    String genericType = Helper.getGenericType(type);
    if (isLegalListOrSet(elementUtils, nonGenericType, genericType)) {
      return;
    }

    // Another user defined interface with the @VertxGen annotation is OK
    if (isVertxGenInterface(elementUtils, type)) {
      return;
    }
    throw new GenException(elem, "type " + type + " is not legal for use for a return type in code generation");
  }

  private boolean isOptionType(Elements elementUtils, Element elem, String type) {
    if (Helper.isBasicType(type)) {
      return false;
    }
    try {
      TypeElement clazz = elementUtils.getTypeElement(Helper.getNonGenericType(type));
      boolean isOption = clazz.getAnnotation(Options.class) != null;
      if (isOption) {
        referencedOptionsTypes.add(type);
      }
      return isOption;
    } catch (Exception e) {
      GenException genEx = new GenException(elem, e.getMessage());
      genEx.initCause(e);
      throw genEx;
    }
  }

  private boolean isLegalListOrSet(Elements elementUtils, String nonGenericType, String genericType) {
    return ((nonGenericType.startsWith(List.class.getName()) || nonGenericType.startsWith(Set.class.getName())) &&
                                                  (Helper.isBasicType(genericType) || isVertxGenInterface(elementUtils, genericType)));
  }

  private boolean isVertxGenInterface(Elements elementUtils, String type) {
    if (Helper.isBasicType(type)) {
      return false;
    }
    try {
      TypeElement clazz = elementUtils.getTypeElement(Helper.getNonGenericType(type));
      boolean isVertxGen = clazz.getAnnotation(VertxGen.class) != null;
      if (isVertxGen && !type.equals(VERTX)) {
        referencedTypes.add(Helper.getNonGenericType(type));
      }
      return isVertxGen;
    } catch (Exception e) {
      return false;
    }
  }

  private boolean isJsonType(String type) {
    return (type.equals(JSON_OBJECT) || type.equals(JSON_ARRAY));
  }

  private boolean isLegalHandlerType(Elements elementUtils, String nonGenericType, String genericType) {
    if (nonGenericType.equals(VERTX_HANDLER) &&
                              (Helper.isBasicType(genericType) || isVertxGenInterface(elementUtils, genericType) ||
                               isJsonType(genericType)
                               || isLegalListOrSet(elementUtils, genericType, Helper.getGenericType(genericType))
                               || genericType.equals(Void.class.getName())
                               || genericType.equals(Throwable.class.getName()))) {
      return true;
    }
    return false;
  }

  private boolean isLegalHandlerAsyncResultType(Elements elementUtils, String nonGenericType, String genericType) {
    if (nonGenericType.equals(VERTX_HANDLER) && genericType.startsWith(VERTX_ASYNC_RESULT)) {
      String genericType2 = Helper.getGenericType(genericType);
      if (Helper.isBasicType(genericType2) || isVertxGenInterface(elementUtils, genericType2) || isLegalListOrSet(elementUtils, genericType2, Helper.getGenericType(genericType2))
          || genericType2.equals(Void.class.getName()) || isJsonType(genericType2)) {
        return true;
      }
    }
    return false;
  }

  public List<MethodInfo> getMethods() {
    return methods;
  }

  public Set<String> getReferencedTypes() {
    return referencedTypes;
  }

  public String getIfaceSimpleName() {
    return ifaceSimpleName;
  }

  public String getIfaceFQCN() {
    return ifaceFQCN;
  }

  public String getIfaceComment() {
    return ifaceComment;
  }

  public List<String> getSuperTypes() {
    return superTypes;
  }

  public Map<String, MethodInfo> getSquashedMethods() {
    return squashedMethods;
  }

  public Map<String, List<MethodInfo>> getMethodMap() {
    return methodMap;
  }

  public Set<String> getReferencedOptionsTypes() {
    return referencedOptionsTypes;
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

    @Override
    public void init(ProcessingEnvironment processingEnv) {
      this.env = processingEnv;
      elementUtils = env.getElementUtils();
      typeUtils = env.getTypeUtils();
    }

    void traverseElem(Element elem) {
      Generator.this.traverseElem(elementUtils, typeUtils, elem);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
      roundEnv.getRootElements().forEach(this::traverseElem);
      processed = true;
      return true;
    }

    @Override
    public Iterable<? extends Completion> getCompletions(Element element, AnnotationMirror annotation, ExecutableElement member, String userText) {
      return Collections.emptyList();
    }
  }

  void traverseElem(Elements elementUtils, Types typeUtils, Element elem) {
    switch (elem.getKind()) {
      case CLASS: {
        throw new GenException(elem, "@VertxGen can only be used with interfaces in " + elem.asType().toString());
      }
      case INTERFACE: {
        if (ifaceFQCN != null) {
          throw new GenException(elem, "Can only have one interface per file");
        }
        ifaceFQCN = elem.asType().toString();
        ifaceSimpleName = elem.getSimpleName().toString();
        ifaceComment = elementUtils.getDocComment(elem);
        TypeMirror tm = elem.asType();
        List<? extends TypeMirror> st = typeUtils.directSupertypes(tm);
        for (TypeMirror tmSuper: st) {
          Element superElement = typeUtils.asElement(tmSuper);
          if (!tmSuper.toString().equals(Object.class.getName())) {
            if (superElement.getAnnotation(VertxGen.class) != null) {
              referencedTypes.add(Helper.getNonGenericType(tmSuper.toString()));
            }
            superTypes.add(tmSuper.toString());
          }
        }
        break;
      }
      case METHOD: {
        ExecutableElement execElem = (ExecutableElement)elem;
        boolean isIgnore = execElem.getAnnotation(GenIgnore.class) != null;
        if (isIgnore) {
          break;
        }
        Set<Modifier> mods = execElem.getModifiers();
        if (!mods.contains(Modifier.PUBLIC)) {
          break;
        }
        if (mods.contains(Modifier.DEFAULT)) {
          break;
        }
        boolean isStatic = mods.contains(Modifier.STATIC);
        boolean isFluent = execElem.getAnnotation(Fluent.class) != null;
        boolean isCacheReturn = execElem.getAnnotation(CacheReturn.class) != null;
        boolean isIndexGetter = execElem.getAnnotation(IndexGetter.class) != null;
        boolean isIndexSetter = execElem.getAnnotation(IndexSetter.class) != null;
        List<ParamInfo> mParams = getParams(elementUtils, execElem);
        String returnType = execElem.getReturnType().toString();
        if (returnType.equals("void")) {
          if (isCacheReturn) {
            throw new GenException(elem, "void method can't be marked with @CacheReturn");
          }
          if (isFluent) {
            throw new GenException(elem, "Methods marked with @Fluent must return a value");
          }
        }
        String methodName = execElem.getSimpleName().toString();
        // Only check the return type if not fluent, because generated code won't look it at anyway
        if (!isFluent) {
          // If it's the generic type of the interface (currently we only support a single generic type)
          // then don't validate it, as we don't know what the real type is
          if (!returnType.equals(Helper.getGenericType(ifaceFQCN))) {
            checkReturnType(elementUtils, elem, returnType);
          }
        }
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
                  throw new GenException(elem, "Overloaded method " + methodName + " has versions with different sequences of parameters");
                }
              } else {
                break;
              }
              pos++;
            }
          }
        }
        MethodInfo methodInfo = new MethodInfo(methodName, returnType,
            isFluent, isIndexGetter, isIndexSetter, isCacheReturn, mParams, elementUtils.getDocComment(execElem), isStatic);
        meths.add(methodInfo);
        methods.add(methodInfo);
        MethodInfo squashed = squashedMethods.get(methodName);
        if (squashed == null) {
          squashed = new MethodInfo(methodName, returnType,
              isFluent, isIndexGetter, isIndexSetter, isCacheReturn, mParams, elementUtils.getDocComment(execElem), isStatic);
          squashedMethods.put(methodName, squashed);
        } else {
          squashed.addParams(mParams);
        }
        break;
      }
    }

    for (Element enclosedElt : elem.getEnclosedElements()) {
      traverseElem(elementUtils, typeUtils, enclosedElt);
    }
    if (elem.getKind() == ElementKind.INTERFACE) {
      // We're done
      if (methods.isEmpty() && superTypes.isEmpty()) {
        throw new IllegalArgumentException("Interface " + ifaceFQCN + " does not contain any methods for generation");
      }
      referencedTypes.remove(Helper.getNonGenericType(ifaceFQCN)); // don't reference yourself
      sortMethodMap(methodMap);
    }
  }

  private List<ParamInfo> getParams(Elements elementUtils, ExecutableElement execElem) {
    List<? extends VariableElement> params = execElem.getParameters();
    List<ParamInfo> mParams = new ArrayList<>();
    for (VariableElement param: params) {
      String paramType = param.asType().toString();
      checkParamType(elementUtils, execElem, paramType);
      boolean option = isOptionType(elementUtils, execElem, paramType);
      ParamInfo mParam = new ParamInfo(param.getSimpleName().toString(), param.asType().toString(), option);
      mParams.add(mParam);
    }
    return mParams;
  }
}
