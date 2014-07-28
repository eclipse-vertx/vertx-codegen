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
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A processed source.
 *
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
public class Model {

  public static final String VERTX_ASYNC_RESULT = "io.vertx.core.AsyncResult";
  public static final String VERTX_HANDLER = "io.vertx.core.Handler";
  public static final String JSON_OBJECT = "io.vertx.core.json.JsonObject";
  public static final String JSON_ARRAY = "io.vertx.core.json.JsonArray";
  public static final String VERTX = "io.vertx.core.Vertx";

  private final Generator generator;
  private final TypeElement modelElt;
  private boolean processed = false;
  private List<MethodInfo> methods = new ArrayList<>();
  private HashSet<TypeInfo.Class> importedTypes = new HashSet<>();
  private Set<String> referencedTypes = new HashSet<>();
  private boolean concrete;
  private String ifaceSimpleName;
  private String ifaceFQCN;
  private String ifacePackageName;
  private String ifaceComment;
  private List<TypeInfo> superTypes = new ArrayList<>();
  private List<TypeInfo> concreteSuperTypes = new ArrayList<>();
  private List<TypeInfo> abstractSuperTypes = new ArrayList<>();
  // The methods, grouped by name
  private Map<String, List<MethodInfo>> methodMap = new LinkedHashMap<>();
  private Set<String> referencedOptionsTypes = new HashSet<>();

  // Methods where all overloaded methods with same name are squashed into a single method with all parameters
  private Map<String, MethodInfo> squashedMethods = new LinkedHashMap<>();

  public Model(Generator generator, TypeElement modelElt) {
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

  public String getIfacePackageName() {
    return ifacePackageName;
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
    vars.put("ifacePackageName", ifacePackageName);
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

    // Useful for testing the type kind, allows to do type.kind == API instead of type.kind.name() == "API"
    for (ClassKind classKind : ClassKind.values()) {
      vars.put(classKind.name(), classKind);
    }

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

  private void checkParamType(Element elem, TypeInfo typeInfo) {

    // Basic types, int, long, String etc
    // JsonObject or JsonArray
    // Also can use Object as a param type (e.g. for EventBus)
    if (typeInfo.getKind().basic || typeInfo.getKind().json || typeInfo.getKind() == ClassKind.OBJECT) {
      return;
    }
    // Check legal handlers
    if (isLegalHandlerType(typeInfo)) {
      return;
    }
    if (isLegalHandlerAsyncResultType(typeInfo)) {
      return;
    }
    // Another user defined interface with the @VertxGen annotation is OK
    if (isVertxGenInterface(typeInfo)) {
      return;
    }
    // Can also specify option classes (which aren't VertxGen)
    if (isOptionType(typeInfo)) {
      return;
    }
    // We also allow type parameters for param types
    if (isVariableType(typeInfo)) {
      return;
    }
    throw new GenException(elem, "type " + typeInfo + " is not legal for use for a parameter in code generation");
  }

  private void checkReturnType(Element elem, TypeInfo type) {
    // Basic types, int, long, String etc
    // JsonObject or JsonArray
    // void
    if (type.getKind().basic || type instanceof TypeInfo.Void || type.getKind().json) {
      return;
    }

    // List<T> and Set<T> are also legal for returns if T = basic type
    if (type instanceof TypeInfo.Parameterized) {
      TypeInfo raw = ((TypeInfo.Parameterized) type).getRaw();
      if (raw.getName().equals(List.class.getName()) || raw.getName().equals(Set.class.getName())) {
        TypeInfo argument = ((TypeInfo.Parameterized) type).getArgs().get(0);
        if (argument.getKind().basic || argument.getKind().json) {
          return;
        } else if (isVertxGenInterface(argument)) {
          return;
        }
      }
    }

    // Another user defined interface with the @VertxGen annotation is OK
    if (isVertxGenInterface(type)) {
      return;
    }

    // Variable type is ok
    if (isVariableType(type)) {
      return;
    }

    throw new GenException(elem, "type " + type + " is not legal for use for a return type in code generation");
  }

  private boolean isVariableType(TypeInfo type) {
    return type instanceof TypeInfo.Variable;
  }

  private boolean isOptionType(TypeInfo type) {
    if (type.getKind() == ClassKind.OPTIONS) {
      referencedOptionsTypes.add(type.getName());
      return true;
    }
    return false;
  }

  private boolean isLegalListOrSet(TypeInfo type) {
    if (type instanceof TypeInfo.Parameterized) {
      TypeInfo raw = ((TypeInfo.Parameterized) type).getRaw();
      if (raw.getName().equals(List.class.getName()) || raw.getName().equals(Set.class.getName())) {
        TypeInfo elementType = ((TypeInfo.Parameterized) type).getArgs().get(0);
        if (elementType.getKind().basic || elementType.getKind().json || isVertxGenInterface(elementType)) {
          return true;
        }
      }
    }
    return false;
  }

  private boolean isVertxGenInterface(TypeInfo type) {
    if (type.getKind() == ClassKind.API) {
      String name = type.getErased().getName();
      if (!name.equals(VERTX)) {
        referencedTypes.add(name);
      }
      return true;
    }
    return false;
  }

  private boolean isLegalHandlerType(TypeInfo type) {
    if (type.getErased().getKind() == ClassKind.HANDLER) {
      TypeInfo eventType = ((TypeInfo.Parameterized) type).getArgs().get(0);
      if (eventType.getKind().json || eventType.getKind().basic || isVertxGenInterface(eventType) ||
          isLegalListOrSet(eventType) || eventType.getKind() == ClassKind.VOID ||
          eventType.getKind() == ClassKind.THROWABLE || isVariableType(eventType)) {
        return true;
      }
    }
    return false;
  }

  private boolean isLegalHandlerAsyncResultType(TypeInfo type) {
    if (type.getErased().getKind() == ClassKind.HANDLER) {
      TypeInfo eventType = ((TypeInfo.Parameterized) type).getArgs().get(0);
      if (eventType.getErased().getKind() == ClassKind.ASYNC_RESULT) {
        TypeInfo resultType = ((TypeInfo.Parameterized) eventType).getArgs().get(0);
        if (resultType.getKind().json || resultType.getKind().basic || isVertxGenInterface(resultType) ||
            isLegalListOrSet(resultType) || resultType.getKind() == ClassKind.VOID ||
            isVariableType(resultType)) {
          return true;
        }
      }
    }
    return false;
  }

  Model process(Elements elementUtils, Types typeUtils) {
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
        ifacePackageName = elementUtils.getPackageOf(elem).toString();
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
              TypeInfo superTypeInfo = TypeInfo.create(typeUtils, Collections.emptyList(), (DeclaredType) tmSuper);
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

      // Traverse methods
      traverseMethods(elementUtils, typeUtils, new LinkedList<>(), elem);

      // We're done
      if (methods.isEmpty() && superTypes.isEmpty()) {
        throw new GenException(elem, "Interface " + ifaceFQCN + " does not contain any methods for generation");
      }
      // don't reference yourself
      for (Iterator<String> i = referencedTypes.iterator();i.hasNext();) {
        String next = i.next();
        if (Helper.getNonGenericType(next).equals(Helper.getNonGenericType(ifaceFQCN))) {
          i.remove();
        }
      }
      sortMethodMap(methodMap);
    }
  }

  private void traverseMethods(Elements elementUtils, Types typeUtils, LinkedList<DeclaredType> resolvingTypes,
                               Element currentElt) {
    for (Element currentEnclosedElt : currentElt.getEnclosedElements()) {
      if (currentEnclosedElt.getKind() == ElementKind.METHOD) {
        ExecutableElement currentMethodElt = (ExecutableElement) currentEnclosedElt;
        addMethod(elementUtils, typeUtils, resolvingTypes, currentMethodElt);
      }
    }
    LinkedList<DeclaredType> resolvedTypes = new LinkedList<>();
    resolveAbstractSuperTypes(elementUtils, typeUtils, currentElt.asType(), new HashSet<>(), resolvedTypes);
    for (DeclaredType superType : resolvedTypes) {
      TypeElement superTypeElt = (TypeElement) superType.asElement();
      String superTypeName = superTypeElt.getQualifiedName().toString();
      if (generator.sources.containsKey(superTypeName)) {
        // Use the one from the sources
        superTypeElt = generator.sources.get(superTypeName);
      }
      resolvingTypes.addFirst(superType);
      traverseMethods(elementUtils, typeUtils, resolvingTypes, superTypeElt);
      resolvingTypes.removeFirst();
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

  private void addMethod(Elements elementUtils, Types typeUtils,
                         LinkedList<DeclaredType> resolvingTypes, ExecutableElement execElem) {
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
    List<ParamInfo> mParams = getParams(typeUtils, resolvingTypes, execElem);

    TypeInfo returnType = TypeInfo.create(typeUtils, resolvingTypes, execElem.getReturnType());
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
      checkReturnType(execElem, returnType);
    }

    LinkedHashSet<TypeInfo.Class> ownerTypes = new LinkedHashSet<>();
    TypeInfo ownerType = TypeInfo.create(typeUtils, Collections.emptyList(), execElem.getEnclosingElement().asType());
    if (ownerType instanceof TypeInfo.Parameterized) {
      ownerTypes.add(((TypeInfo.Parameterized) ownerType).getRaw());
    } else {
      ownerTypes.add((TypeInfo.Class) ownerType);
    }
    MethodInfo methodInfo = new MethodInfo(ownerTypes, methodName, returnType,
        isFluent, isIndexGetter, isIndexSetter, isCacheReturn, mParams, elementUtils.getDocComment(execElem), isStatic, typeParams);
    List<MethodInfo> meths = methodMap.get(methodInfo.getName());
    if (meths == null) {
      meths = new ArrayList<>();
      methodMap.put(methodInfo.getName(), meths);
    } else {
      // Check if we already have the signature
      List<MethodInfo> sameSignatureMethods = methods.stream().filter(meth -> meth.hasSameSignature(methodInfo)).collect(Collectors.toList());
      if (sameSignatureMethods.size() > 0) {
        sameSignatureMethods.forEach(m -> m.ownerTypes.addAll(methodInfo.ownerTypes));
        squashedMethods.get(methodInfo.name).ownerTypes.addAll(methodInfo.ownerTypes);
        return;
      }
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
      squashed = new MethodInfo(new LinkedHashSet<>(methodInfo.ownerTypes), methodInfo.name, methodInfo.returnType,
          methodInfo.fluent, methodInfo.indexGetter, methodInfo.indexSetter, methodInfo.cacheReturn, methodInfo.params, methodInfo.comment, methodInfo.staticMethod, methodInfo.typeParams);
      squashedMethods.put(methodInfo.name, squashed);
    } else {
      squashed.addParams(methodInfo.params);
      squashed.ownerTypes.addAll(methodInfo.ownerTypes);
    }
  }

  private boolean isObjectBound(TypeMirror bound) {
    return bound.getKind() == TypeKind.DECLARED && bound.toString().equals(Object.class.getName());
  }

  private List<ParamInfo> getParams(Types typeUtils, LinkedList<DeclaredType> resolvingTypes, ExecutableElement execElem) {
    List<? extends VariableElement> params = execElem.getParameters();
    List<ParamInfo> mParams = new ArrayList<>();
    for (VariableElement param: params) {
      TypeInfo type;
      try {
        type = TypeInfo.create(typeUtils, resolvingTypes, param.asType());
      } catch (Exception e) {
        throw new GenException(param, e.getMessage());
      }
      checkParamType(execElem, type);
      ParamInfo mParam = new ParamInfo(param.getSimpleName().toString(), type);
      mParams.add(mParam);
    }
    return mParams;
  }
}
