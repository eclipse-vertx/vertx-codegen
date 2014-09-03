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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A processed source.
 *
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
public class ClassModel implements Model {

  public static final String VERTX_ASYNC_RESULT = "io.vertx.core.AsyncResult";
  public static final String VERTX_HANDLER = "io.vertx.core.Handler";
  public static final String JSON_OBJECT = "io.vertx.core.json.JsonObject";
  public static final String JSON_ARRAY = "io.vertx.core.json.JsonArray";
  public static final String VERTX = "io.vertx.core.Vertx";

  private final TypeInfo.Factory typeFactory;
  private final Map<String, TypeElement> sources;
  private final TypeElement modelElt;
  private final Elements elementUtils;
  private final Types typeUtils;
  private boolean processed = false;
  private List<MethodInfo> methods = new ArrayList<>();
  private HashSet<TypeInfo.Class> importedTypes = new HashSet<>();
  private Set<TypeInfo.Class> referencedTypes = new HashSet<>();
  private boolean concrete;
  private TypeInfo type;
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

  public ClassModel(Map<String, TypeElement> sources, Elements elementUtils, Types typeUtils, TypeElement modelElt) {
    this.sources = sources;
    this.elementUtils = elementUtils;
    this.typeUtils = typeUtils;
    this.modelElt = modelElt;
    this.typeFactory = new TypeInfo.Factory(elementUtils, typeUtils);
  }

  @Override
  public String getKind() {
    return "class";
  }

  @Override
  public String getFqn() {
    return type.getRaw().getName();
  }

  public TypeElement getElement() {
    return modelElt;
  }

  public List<MethodInfo> getMethods() {
    return methods;
  }

  public Set<TypeInfo.Class> getImportedTypes() {
    return importedTypes;
  }

  public boolean isConcrete() {
    return concrete;
  }

  public Set<TypeInfo.Class> getReferencedTypes() {
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

  public TypeInfo getType() {
    return type;
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
      if (!type.getName().equals(VERTX)) {
        referencedTypes.add(type.getRaw());
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

  boolean process() {
    if (!processed) {
      traverseElem(modelElt);
      processed = true;
      return true;
    } else {
      return false;
    }
  }

  private void traverseElem(Element elem) {
    switch (elem.getKind()) {
      case CLASS: {
        throw new GenException(elem, "@VertxGen can only be used with interfaces in " + elem.asType().toString());
      }
      case INTERFACE: {
        if (ifaceFQCN != null) {
          throw new GenException(elem, "Can only have one interface per file");
        }
        type = typeFactory.create(elem.asType());
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
              try {
                TypeInfo.Class superType = typeFactory.create(tmSuper).getRaw();
                referencedTypes.add(superType);
              } catch (Exception e) {
                throw new GenException(elem, e.getMessage());
              }
            }
            try {
              TypeInfo superTypeInfo = typeFactory.create(tmSuper);
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
        traverseElem(enclosedElt);
      }
    }

    if (elem.getKind() == ElementKind.INTERFACE) {

      // Traverse methods
      traverseMethods(new LinkedList<>(), elem);

      // We're done
      if (methods.isEmpty() && superTypes.isEmpty()) {
        throw new GenException(elem, "Interface " + ifaceFQCN + " does not contain any methods for generation");
      }
      // don't reference yourself
      for (Iterator<TypeInfo.Class> i = referencedTypes.iterator();i.hasNext();) {
        TypeInfo.Class next = i.next();
        if (next.getName().equals(Helper.getNonGenericType(ifaceFQCN))) {
          i.remove();
        }
      }
      sortMethodMap(methodMap);
    }
  }

  private void traverseMethods(LinkedList<DeclaredType> resolvingTypes, Element currentElt) {
    for (Element currentEnclosedElt : currentElt.getEnclosedElements()) {
      if (currentEnclosedElt.getKind() == ElementKind.METHOD) {
        ExecutableElement currentMethodElt = (ExecutableElement) currentEnclosedElt;
        addMethod(resolvingTypes, currentMethodElt);
      }
    }
    LinkedList<DeclaredType> resolvedTypes = new LinkedList<>();
    resolveAbstractSuperTypes(currentElt.asType(), new HashSet<>(), resolvedTypes);
    for (DeclaredType superType : resolvedTypes) {
      TypeElement superTypeElt = (TypeElement) superType.asElement();
      String superTypeName = superTypeElt.getQualifiedName().toString();
      if (sources.containsKey(superTypeName)) {
        // Use the one from the sources
        superTypeElt = sources.get(superTypeName);
      }
      resolvingTypes.addFirst(superType);
      traverseMethods(resolvingTypes, superTypeElt);
      resolvingTypes.removeFirst();
    }
  }

  private void resolveAbstractSuperTypes(TypeMirror type, HashSet<String> knownTypes, LinkedList<DeclaredType> resolvedTypes) {
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
        resolveAbstractSuperTypes(superType, knownTypes, resolvedTypes);
      }
    }
  }

  private void addMethod(LinkedList<DeclaredType> resolvingTypes, ExecutableElement execElem) {
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
    ArrayList<String> typeParams = new ArrayList<>();
    for (TypeParameterElement typeParam : execElem.getTypeParameters()) {
      for (TypeMirror bound : typeParam.getBounds()) {
        if (!isObjectBound(bound)) {
          throw new GenException(execElem, "Type parameter bound not supported " + bound);
        }
      }
      typeParams.add(typeParam.getSimpleName().toString());
    }
    List<ParamInfo> mParams = getParams(resolvingTypes, execElem);

    TypeInfo returnType = typeFactory.create(resolvingTypes, execElem.getReturnType());
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
    TypeInfo ownerType = typeFactory.create(execElem.getEnclosingElement().asType());
    if (ownerType instanceof TypeInfo.Parameterized) {
      ownerTypes.add(ownerType.getRaw());
    } else {
      ownerTypes.add((TypeInfo.Class) ownerType);
    }

    // Determine method kind + validate
    MethodKind kind = MethodKind.OTHER;
    if (execElem.getAnnotation(IndexGetter.class) != null) {
      if (!mParams.stream().anyMatch(param -> param.type.getName().equals("int"))) {
        throw new GenException(execElem, "No int arg found in index getter method");
      }
      kind = MethodKind.INDEX_GETTER;
    } else if (execElem.getAnnotation(IndexSetter.class) != null) {
      if (!mParams.stream().anyMatch(param -> param.type.getName().equals("int"))) {
        throw new GenException(execElem, "No int arg found in index setter method");
      }
      kind = MethodKind.INDEX_SETTER;
    } else {
      int lastParamIndex = mParams.size() - 1;
      if (lastParamIndex >= 0 && (returnType instanceof TypeInfo.Void || isFluent)) {
        TypeInfo lastParamType = mParams.get(lastParamIndex).type;
        if (lastParamType.getKind() == ClassKind.HANDLER) {
          TypeInfo typeArg = ((TypeInfo.Parameterized) lastParamType).getArgs().get(0);
          if (typeArg.getKind() == ClassKind.ASYNC_RESULT) {
            kind = MethodKind.FUTURE;
          } else {
            kind = MethodKind.HANDLER;
          }
        }
      }
    }

    //
    MethodInfo methodInfo = new MethodInfo(ownerTypes, methodName, kind, returnType,
        isFluent, isCacheReturn, mParams, elementUtils.getDocComment(execElem), isStatic, typeParams);
    List<MethodInfo> methodsByName = methodMap.get(methodInfo.getName());
    if (methodsByName == null) {
      methodsByName = new ArrayList<>();
      methodMap.put(methodInfo.getName(), methodsByName);
    } else {
      // Check if we already have the signature
      List<MethodInfo> sameSignatureMethods = methods.stream().filter(meth -> meth.hasSameSignature(methodInfo)).collect(Collectors.toList());
      if (sameSignatureMethods.size() > 0) {
        sameSignatureMethods.forEach(m -> m.ownerTypes.addAll(methodInfo.ownerTypes));
        squashedMethods.get(methodInfo.name).ownerTypes.addAll(methodInfo.ownerTypes);
        return;
      }
      // Overloaded methods must have same parameter at each position in the param list and the same return type
      for (MethodInfo meth: methodsByName) {
        if (!meth.returnType.equals(methodInfo.returnType)) {
          throw new GenException(this.modelElt, "Overloaded method " + methodInfo.name + " must have the same return type "
              + meth.returnType + " != " + methodInfo.returnType);
        }
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


    methodsByName.add(methodInfo);
    methods.add(methodInfo);
    methodInfo.collectImports(importedTypes);
    MethodInfo squashed = squashedMethods.get(methodInfo.name);
    if (squashed == null) {
      squashed = new MethodInfo(new LinkedHashSet<>(methodInfo.ownerTypes), methodInfo.name, methodInfo.kind, methodInfo.returnType,
          methodInfo.fluent, methodInfo.cacheReturn, methodInfo.params, methodInfo.comment, methodInfo.staticMethod, methodInfo.typeParams);
      squashedMethods.put(methodInfo.name, squashed);
    } else {
      squashed.mergeParams(methodInfo.params);
      try {
        squashed.mergeTypeParams(methodInfo.typeParams);
      } catch (IllegalArgumentException e) {
        throw new GenException(this.modelElt, "Overloaded method " + methodInfo.name + " has versions with different sequences of type parameters");
      }
      squashed.ownerTypes.addAll(methodInfo.ownerTypes);
    }
  }

  private boolean isObjectBound(TypeMirror bound) {
    return bound.getKind() == TypeKind.DECLARED && bound.toString().equals(Object.class.getName());
  }

  private List<ParamInfo> getParams(LinkedList<DeclaredType> resolvingTypes, ExecutableElement execElem) {
    List<? extends VariableElement> params = execElem.getParameters();
    List<ParamInfo> mParams = new ArrayList<>();
    for (VariableElement param: params) {
      TypeInfo type;
      try {
        type = typeFactory.create(resolvingTypes, param.asType());
      } catch (Exception e) {
        throw new GenException(param, e.getMessage());
      }
      checkParamType(execElem, type);
      ParamInfo mParam = new ParamInfo(param.getSimpleName().toString(), type);
      mParams.add(mParam);
    }
    return mParams;
  }

  @Override
  public Map<String, Object> getVars() {
    Map<String, Object> vars = new HashMap<>();
    vars.put("importedTypes", getImportedTypes());
    vars.put("concrete", isConcrete());
    vars.put("type", getType());
    vars.put("ifacePackageName", getIfacePackageName());
    vars.put("ifaceSimpleName", getIfaceSimpleName());
    vars.put("ifaceFQCN", getIfaceFQCN());
    vars.put("ifaceComment", getIfaceComment());
    vars.put("helper", new Helper());
    vars.put("methods", getMethods());
    vars.put("referencedTypes", getReferencedTypes());
    vars.put("superTypes", getSuperTypes());
    vars.put("concreteSuperTypes", getConcreteSuperTypes());
    vars.put("abstractSuperTypes", getAbstractSuperTypes());
    vars.put("squashedMethods", getSquashedMethods().values());
    vars.put("methodsByName", getMethodMap());
    vars.put("referencedOptionsTypes", getReferencedOptionsTypes());
    vars.putAll(ClassKind.vars());
    vars.putAll(MethodKind.vars());
    return vars;
  }
}
