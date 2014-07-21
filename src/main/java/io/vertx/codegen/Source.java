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

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVariable;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 * A processed source.
 *
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
public class Source {

  public static final String VERTX_ASYNC_RESULT = "io.vertx.core.AsyncResult";
  public static final String VERTX_HANDLER = "io.vertx.core.Handler";
  public static final String JSON_OBJECT = "io.vertx.core.json.JsonObject";
  public static final String JSON_ARRAY = "io.vertx.core.json.JsonArray";
  public static final String VERTX = "io.vertx.core.Vertx";

  final Generator generator;
  final TypeElement modelElt;
  boolean processed = false;
  List<MethodInfo> methods = new ArrayList<>();
  HashSet<TypeInfo.Class> importedTypes = new HashSet<>();
  Set<String> referencedTypes = new HashSet<>();
  boolean concrete;
  String ifaceSimpleName;
  String ifaceFQCN;
  String ifaceComment;
  List<TypeInfo> superTypes = new ArrayList<>();
  List<TypeInfo> concreteSuperTypes = new ArrayList<>();
  List<TypeInfo> abstractSuperTypes = new ArrayList<>();
  // The methods, grouped by name
  Map<String, List<MethodInfo>> methodMap = new LinkedHashMap<>();
  Set<String> referencedOptionsTypes = new HashSet<>();

  // Methods where all overloaded methods with same name are squashed into a single method with all parameters
  Map<String, MethodInfo> squashedMethods = new LinkedHashMap<>();

  public Source(Generator generator, TypeElement modelElt) {
    this.generator = generator;
    this.modelElt = modelElt;
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

  public List<TypeInfo> getSuperTypes() {
    return superTypes;
  }

  public List<TypeInfo> getConcreteSuperTypes() {
    return concreteSuperTypes;
  }

  public List<TypeInfo> getAbstractSuperTypes() {
    return abstractSuperTypes;
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
    vars.put("importedTypes", importedTypes);
    vars.put("concrete", concrete);
    vars.put("ifaceSimpleName", ifaceSimpleName);
    vars.put("ifaceFQCN", ifaceFQCN);
    vars.put("ifaceComment", ifaceComment);
    vars.put("helper", new Helper());
    vars.put("methods", methods);
    vars.put("referencedTypes", referencedTypes);
    vars.put("superTypes", superTypes);
    vars.put("concreteSuperTypes", concreteSuperTypes);
    vars.put("abstractSuperTypes", abstractSuperTypes);
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

  Source process(Elements elementUtils, Types typeUtils) {
    if (!processed) {
      traverseElem(elementUtils, typeUtils, modelElt);
      processed = true;
    }
    return this;
  }

  private void traverseElem(Elements elementUtils, Types typeUtils, Element elem) {
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
        concrete = elem.getAnnotation(VertxGen.class).concrete();
        DeclaredType tm = (DeclaredType) elem.asType();
        List<? extends TypeMirror> typeArgs = tm.getTypeArguments();
        for (TypeMirror typeArg : typeArgs) {
          TypeVariable varTypeArg = (TypeVariable) typeArg;
          if (!isObjectBound(varTypeArg.getUpperBound())) {
            throw new GenException(elem, "Type variable bounds not supported " + varTypeArg.getUpperBound());
          }
        }
        List<? extends TypeMirror> st = typeUtils.directSupertypes(tm);
        for (TypeMirror tmSuper: st) {
          Element superElement = typeUtils.asElement(tmSuper);
          VertxGen superGen = superElement.getAnnotation(VertxGen.class);
          if (!tmSuper.toString().equals(Object.class.getName())) {
            if (superElement.getAnnotation(VertxGen.class) != null) {
              referencedTypes.add(Helper.getNonGenericType(tmSuper.toString()));
            }
            try {
              TypeInfo superTypeInfo = TypeInfo.create(typeUtils, null, (DeclaredType) tmSuper);
              superTypeInfo.collectImports(importedTypes);
              if (superGen != null) {
                (superGen.concrete() ? concreteSuperTypes : abstractSuperTypes).add(superTypeInfo);
                superTypes.add(superTypeInfo);
              }
            } catch (IllegalArgumentException e) {
              throw new GenException(elem, e.getMessage());
            }
          }
        }
        if (concrete && concreteSuperTypes.size() > 1) {
          throw new GenException(elem, "A concrete interface cannot extend more than two concrete interfaces");
        }
        if (!concrete && concreteSuperTypes.size() > 0) {
          throw new GenException(elem, "A abstract interface cannot extend more a concrete interface");
        }
        for (Iterator<TypeInfo.Class> i = importedTypes.iterator();i.hasNext();) {
          TypeInfo.Class type = i.next();
          if (Helper.getPackageName(type.toString()).equals(Helper.getPackageName(ifaceFQCN))) {
            i.remove();
          }
        }
        break;
      }
    }

    // Traverse nested elements that are not methods (like nested interfaces)
    for (Element enclosedElt : elem.getEnclosedElements()) {
      if (enclosedElt.getKind() != ElementKind.METHOD) {
        traverseElem(elementUtils, typeUtils, enclosedElt);
      }
    }

    if (elem.getKind() == ElementKind.INTERFACE) {

      // Collect methods
      collectMethods(elementUtils, typeUtils, null, elem, new ArrayList<>());

      // We're done
      if (methods.isEmpty() && superTypes.isEmpty()) {
        throw new GenException(elem, "Interface " + ifaceFQCN + " does not contain any methods for generation");
      }
      referencedTypes.remove(Helper.getNonGenericType(ifaceFQCN)); // don't reference yourself
      sortMethodMap(methodMap);
    }
  }

  private void collectMethods(Elements elementUtils, Types typeUtils, DeclaredType declaredType,
                              Element currentElt, ArrayList<ExecutableElement> methodElts) {
    out:
    for (Element currentEnclosedElt : currentElt.getEnclosedElements()) {
      if (currentEnclosedElt.getKind() == ElementKind.METHOD) {
        ExecutableElement currentMethodElt = (ExecutableElement) currentEnclosedElt;
        for (ExecutableElement methodElt : methodElts) {
          if (elementUtils.overrides(methodElt, currentMethodElt, (TypeElement) currentElt)) {
            break out;
          }
        }
        methodElts.add(currentMethodElt);
        addMethod(elementUtils, typeUtils, declaredType, currentMethodElt);
      }
    }
    LinkedList<DeclaredType> resolvedTypes = new LinkedList<>();
    resolveAbstractSuperTypes(elementUtils, typeUtils, currentElt.asType(), new HashSet<>(), resolvedTypes);
    for (DeclaredType superType : resolvedTypes) {
      TypeElement superTypeElt = (TypeElement) superType.asElement();
      Element otherElt = generator.sources.get(superTypeElt.getQualifiedName().toString());
      collectMethods(elementUtils, typeUtils, superType, otherElt, methodElts);
    }
  }

  private static void resolveAbstractSuperTypes(Elements elementUtils, Types typeUtils,
                                                TypeMirror type, HashSet<String> knownTypes, LinkedList<DeclaredType> resolvedTypes) {
    for (TypeMirror superType : typeUtils.directSupertypes(type)) {
      DeclaredType declaredSuperType = (DeclaredType) superType;
      String superTypeFQCN = declaredSuperType.toString();
      if (!knownTypes.contains(superTypeFQCN)) {
        Element superTypeElement = declaredSuperType.asElement();
        VertxGen gen = superTypeElement.getAnnotation(VertxGen.class);
        if (gen != null && !gen.concrete()) {
          knownTypes.add(superTypeFQCN);
          resolvedTypes.add(declaredSuperType);
        }
        resolveAbstractSuperTypes(elementUtils, typeUtils, superType, knownTypes, resolvedTypes);
      }
    }
  }

  private void addMethod(Elements elementUtils,  Types typeUtils,
                         DeclaredType containing, ExecutableElement execElem) {
    boolean isIgnore = execElem.getAnnotation(GenIgnore.class) != null;
    if (isIgnore) {
      return;
    }
    Set<Modifier> mods = execElem.getModifiers();
    if (!mods.contains(Modifier.PUBLIC)) {
      return;
    }
    if (mods.contains(Modifier.DEFAULT)) {
      return;
    }
    boolean isStatic = mods.contains(Modifier.STATIC);
    boolean isFluent = execElem.getAnnotation(Fluent.class) != null;
    boolean isCacheReturn = execElem.getAnnotation(CacheReturn.class) != null;
    boolean isIndexGetter = execElem.getAnnotation(IndexGetter.class) != null;
    boolean isIndexSetter = execElem.getAnnotation(IndexSetter.class) != null;
    ArrayList<String> typeParams = new ArrayList<>();
    for (TypeParameterElement typeParam : execElem.getTypeParameters()) {
      for (TypeMirror bound : typeParam.getBounds()) {
        if (!isObjectBound(bound)) {
          throw new GenException(execElem, "Type parameter bound not supported " + bound);
        }
      }
      typeParams.add(typeParam.getSimpleName().toString());
    }
    List<ParamInfo> mParams = getParams(typeUtils, elementUtils, containing, execElem);
    TypeInfo returnType = TypeInfo.create(typeUtils, containing, execElem.getReturnType());
    returnType.collectImports(importedTypes);
    if (returnType.toString().equals("void")) {
      if (isCacheReturn) {
        throw new GenException(execElem, "void method can't be marked with @CacheReturn");
      }
      if (isFluent) {
        throw new GenException(execElem, "Methods marked with @Fluent must return a value");
      }
    }
    String methodName = execElem.getSimpleName().toString();
    // Only check the return type if not fluent, because generated code won't look it at anyway
    if (!isFluent) {
      // If it's the generic type of the interface (currently we only support a single generic type)
      // then don't validate it, as we don't know what the real type is
      if (!returnType.toString().equals(Helper.getGenericType(ifaceFQCN))) {
        checkReturnType(elementUtils, execElem, returnType.toString());
      }
    }

    MethodInfo methodInfo = new MethodInfo(methodName, returnType,
        isFluent, isIndexGetter, isIndexSetter, isCacheReturn, mParams, elementUtils.getDocComment(execElem), isStatic, typeParams);
    List<MethodInfo> meths = methodMap.get(methodInfo.getName());
    if (meths == null) {
      meths = new ArrayList<>();
      methodMap.put(methodInfo.getName(), meths);
    } else {
      // Overloaded methods must have same parameter at each position in the param list
      for (MethodInfo meth: meths) {
        int pos = 0;
        for (ParamInfo param: meth.params) {
          if (pos < methodInfo.params.size()) {
            if (!methodInfo.params.get(pos).equals(param)) {
              throw new GenException(this.modelElt, "Overloaded method " + methodInfo.name + " has versions with different sequences of parameters");
            }
          } else {
            break;
          }
          pos++;
        }
      }
    }
    meths.add(methodInfo);
    methods.add(methodInfo);
    methodInfo.collectImports(importedTypes);
    MethodInfo squashed = squashedMethods.get(methodInfo.name);
    if (squashed == null) {
      squashed = new MethodInfo(methodInfo.name, methodInfo.returnType,
          methodInfo.fluent, methodInfo.indexGetter, methodInfo.indexSetter, methodInfo.cacheReturn, methodInfo.params, methodInfo.comment, methodInfo.staticMethod, methodInfo.typeParams);
      squashedMethods.put(methodInfo.name, squashed);
    } else {
      squashed.addParams(methodInfo.params);
    }
  }

  private boolean isObjectBound(TypeMirror bound) {
    return bound.getKind() == TypeKind.DECLARED && bound.toString().equals(Object.class.getName());
  }

  private List<ParamInfo> getParams(Types typeUtils, Elements elementUtils, DeclaredType containing, ExecutableElement execElem) {
    List<? extends VariableElement> params = execElem.getParameters();
    List<ParamInfo> mParams = new ArrayList<>();
    for (VariableElement param: params) {
      TypeInfo type;
      try {
        type = TypeInfo.create(typeUtils, containing, param.asType());
      } catch (Exception e) {
        throw new GenException(param, e.getMessage());
      }
      checkParamType(elementUtils, execElem, type.getName());
      ParamInfo mParam = new ParamInfo(param.getSimpleName().toString(), type);
      mParams.add(mParam);
    }
    return mParams;
  }
}
