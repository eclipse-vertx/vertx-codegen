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
import io.vertx.codegen.doc.Doc;
import io.vertx.codegen.doc.Tag;
import io.vertx.codegen.overloadcheck.MethodOverloadChecker;
import io.vertx.core.json.JsonObject;

import javax.annotation.processing.Messager;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.ExecutableType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVariable;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * A processed source.
 *
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
public class ClassModel implements Model {

  private static final Logger logger = Logger.getLogger(ClassModel.class.getName());

  public static final String VERTX_READ_STREAM = "io.vertx.core.streams.ReadStream";
  public static final String VERTX_WRITE_STREAM = "io.vertx.core.streams.WriteStream";
  public static final String VERTX_ASYNC_RESULT = "io.vertx.core.AsyncResult";
  public static final String VERTX_HANDLER = "io.vertx.core.Handler";
  public static final String JSON_OBJECT = "io.vertx.core.json.JsonObject";
  public static final String JSON_ARRAY = "io.vertx.core.json.JsonArray";
  public static final String VERTX = "io.vertx.core.Vertx";

  protected final MethodOverloadChecker methodOverloadChecker;
  protected final Messager messager;
  protected final TypeInfo.Factory typeFactory;
  protected final Doc.Factory docFactory;
  protected final Map<String, TypeElement> sources;
  protected final TypeElement modelElt;
  protected final Elements elementUtils;
  protected final Types typeUtils;
  protected boolean processed = false;
  protected LinkedHashMap<ExecutableElement, MethodInfo> methods = new LinkedHashMap<>();
  protected Set<TypeInfo.Class> importedTypes = new HashSet<>();
  protected Set<TypeInfo.Class> referencedTypes = new HashSet<>();
  protected boolean concrete;
  protected TypeInfo type;
  protected String ifaceSimpleName;
  protected String ifaceFQCN;
  protected String ifacePackageName;
  protected String ifaceComment;
  protected Doc doc;
  protected List<TypeInfo> superTypes = new ArrayList<>();
  protected List<TypeInfo> concreteSuperTypes = new ArrayList<>();
  protected List<TypeInfo> abstractSuperTypes = new ArrayList<>();
  protected TypeInfo handlerSuperType;
  // The methods, grouped by name
  protected Map<String, List<MethodInfo>> methodMap = new LinkedHashMap<>();
  protected List<TypeInfo> referencedDataObjectTypes = new ArrayList<>();
  protected List<TypeParamInfo.Class> typeParams = new ArrayList<>();

  public ClassModel(MethodOverloadChecker methodOverloadChecker,
                    Messager messager,  Map<String, TypeElement> sources, Elements elementUtils,
                    Types typeUtils, TypeElement modelElt) {
    this.methodOverloadChecker = methodOverloadChecker;
    this.typeFactory = new TypeInfo.Factory(elementUtils, typeUtils);
    this.docFactory = new Doc.Factory(messager, elementUtils, typeUtils, typeFactory, modelElt);
    this.messager = messager;
    this.sources = sources;
    this.elementUtils = elementUtils;
    this.typeUtils = typeUtils;
    this.modelElt = modelElt;
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
    return new ArrayList<>(methods.values());
  }

  public List<MethodInfo> getStaticMethods() {
    return methods.values().stream().filter(MethodInfo::isStaticMethod).collect(Collectors.toList());
  }

  public List<MethodInfo> getInstanceMethods() {
    return methods.values().stream().filter(m -> !m.isStaticMethod()).collect(Collectors.toList());
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

  public Doc getDoc() {
    return doc;
  }

  public TypeInfo getType() {
    return type;
  }

  public ModuleInfo getModule() {
    return type.getRaw().getModule();
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

  public TypeInfo getHandlerSuperType() {
    return handlerSuperType;
  }

  public Map<String, List<MethodInfo>> getMethodMap() {
    return methodMap;
  }

  public List<TypeInfo> getReferencedDataObjectTypes() {
    return referencedDataObjectTypes;
  }

  public List<TypeParamInfo.Class> getTypeParams() {
    return typeParams;
  }

  private void sortMethodMap(Map<String, List<MethodInfo>> map) {
    for (List<MethodInfo> list: map.values()) {
      list.sort((meth1, meth2) -> meth1.params.size() - meth2.params.size());
    }
  }

  protected void checkParamType(Element elem, TypeMirror type, TypeInfo typeInfo, int pos, int numParams) {

    // Basic types, int, long, String etc
    // JsonObject or JsonArray
    // Also can use Object as a param type (e.g. for EventBus)
    if (typeInfo.getKind().basic || typeInfo.getKind().json || typeInfo.getKind() == ClassKind.OBJECT) {
      return;
    }
    // We also allow enums as parameter types
    if (typeInfo.getKind() == ClassKind.ENUM) {
      return;
    }
    // Check legal handlers
    if (isLegalHandlerType(typeInfo)) {
      return;
    }
    if (isLegalHandlerAsyncResultType(typeInfo)) {
      return;
    }
    if (isLegalListSetMapParam(typeInfo)) {
      return;
    }
    // Another user defined interface with the @VertxGen annotation is OK
    if (isVertxGenInterface(typeInfo)) {
      return;
    }
    // Can also specify option classes (which aren't VertxGen)
    if (isDataObjectType(typeInfo)) {
      return;
    }
    // We also allow type parameters for param types
    if (isVariableType(typeInfo)) {
      return;
    }
    throw new GenException(elem, "type " + typeInfo + " is not legal for use for a parameter in code generation");
  }

  protected void checkReturnType(ExecutableElement elem, TypeInfo type) {
    // Basic types, int, long, String etc
    // JsonObject or JsonArray
    // void
    if (type.getKind().basic || type instanceof TypeInfo.Void || type.getKind().json) {
      return;
    }
    // We also allow enums as return types
    if (type.getKind() == ClassKind.ENUM) {
      return;
    }

    // We allow Throwable returns
    if (type.getKind() == ClassKind.THROWABLE) {
      return;
    }

    if (isLegalListSetMapReturn(type)) {
      return;
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

  private boolean isDataObjectType(TypeInfo type) {
    if (type.getKind() == ClassKind.DATA_OBJECT) {
      referencedDataObjectTypes.add(type);
      return true;
    }
    return false;
  }

  private boolean isLegalListOrSetForHandler(TypeInfo type) {
    if (type instanceof TypeInfo.Parameterized) {
      TypeInfo raw = type.getRaw();
      if (raw.getName().equals(List.class.getName()) || raw.getName().equals(Set.class.getName())) {
        TypeInfo elementType = ((TypeInfo.Parameterized) type).getArgs().get(0);
        if (elementType.getKind().basic || elementType.getKind().json || isVertxGenInterface(elementType)) {
          return true;
        }
      }
    }
    return false;
  }

  protected boolean isLegalListSetMapParam(TypeInfo type) {
    // List<T> and Set<T> are also legal for returns and params if T = basic type, json, or @VertxGen
    // Map<K,V> is also legal for returns and params if K is a String and V is a basic type, json, or a @VertxGen interface
    if (rawTypeIs(type, List.class, Set.class, Map.class)) {
      TypeInfo argument = ((TypeInfo.Parameterized) type).getArgs().get(0);
      if (type.getKind() != ClassKind.MAP) {
        if (argument.getKind().basic || argument.getKind().json || isVertxGenInterface(argument)) {
          return true;
        }
      } else if (argument.getKind() == ClassKind.STRING) { // Only allow Map's with String's for keys
        argument = ((TypeInfo.Parameterized) type).getArgs().get(1);
        if (argument.getKind().basic || argument.getKind().json || isVertxGenInterface(argument)) {
          return true;
        }
      }
    }
    return false;
  }

  protected boolean isLegalListSetMapReturn(TypeInfo type) {
    // List<T> and Set<T> are also legal for returns and params if T = basic type, json, or @VertxGen
    // Map<K,V> is also legal for returns and params if K is a String and V is a basic type, json, or a @VertxGen interface
    if (rawTypeIs(type, List.class, Set.class, Map.class)) {
      TypeInfo argument = ((TypeInfo.Parameterized) type).getArgs().get(0);
      if (type.getKind() != ClassKind.MAP) {
        if (argument.getKind().basic || argument.getKind().json || isVertxGenInterface(argument)) {
          return true;
        }
      } else if (argument.getKind() == ClassKind.STRING) { // Only allow Map's with String's for keys
        argument = ((TypeInfo.Parameterized) type).getArgs().get(1);
        if (argument.getKind().basic || argument.getKind().json) {
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
      if (type instanceof TypeInfo.Parameterized) {
        TypeInfo.Parameterized parameterized = (TypeInfo.Parameterized) type;
        for (TypeInfo param : parameterized.getArgs()) {
          if (!(param instanceof TypeInfo.Variable || param instanceof TypeInfo.Wildcard)) {
            return false;
          }
        }
      }
      return true;
    }
    return false;
  }

  private boolean isLegalHandlerType(TypeInfo type) {
    if (type.getErased().getKind() == ClassKind.HANDLER) {
      TypeInfo eventType = ((TypeInfo.Parameterized) type).getArgs().get(0);
      if (eventType.getKind().json || eventType.getKind().basic || isVertxGenInterface(eventType) ||
          isLegalListOrSetForHandler(eventType) || eventType.getKind() == ClassKind.VOID ||
          eventType.getKind() == ClassKind.THROWABLE || isVariableType(eventType) || isOptionTypeWithToJson(eventType)) {
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
            isLegalListOrSetForHandler(resultType) || resultType.getKind() == ClassKind.VOID ||
            isVariableType(resultType) || isOptionTypeWithToJson(resultType)) {
          return true;
        }
      }
    }
    return false;
  }

  protected boolean isOptionTypeWithToJson(TypeInfo type) {
    if (type.getKind() == ClassKind.DATA_OBJECT) {
      TypeElement typeElt = elementUtils.getTypeElement(type.getName());
      if (typeElt != null) {
        Optional<ExecutableElement> opt = elementUtils.
            getAllMembers(typeElt).
            stream().
            flatMap(Helper.FILTER_METHOD).
            filter(m -> m.getSimpleName().toString().equals("toJson") &&
                m.getParameters().isEmpty() &&
                m.getReturnType().toString().equals(JsonObject.class.getName())).
            findFirst();
        return opt.isPresent();
      }
    }
    return false;
  }

  boolean process() {
    if (!processed) {
      traverseElem(modelElt);
      determineSiteDeclVariance();
      processed = true;
      return true;
    } else {
      return false;
    }
  }

  private void determineSiteDeclVariance() {
    List<? extends TypeParameterElement> typeParamElts = modelElt.getTypeParameters();
    for (int index = 0;index < typeParamElts.size();index++) {
      TypeParameterElement typeParamElt = typeParamElts.get(index);
      Set<Variance> siteVariance = EnumSet.noneOf(Variance.class);
      for (Variance variance : Variance.values()) {
        if (Helper.resolveSiteVariance(typeParamElt, variance)) {
          siteVariance.add(variance);
        }
      }
      logger.log(Level.FINE, "Site variances of " + modelElt + " " + typeParamElt + " : " + siteVariance);
      typeParams.add(new TypeParamInfo.Class(modelElt.getQualifiedName().toString(), index, typeParamElt.getSimpleName().toString(), siteVariance));
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
        if (getModule() == null) {
          throw new GenException(elem, "@VertxGen type must have an ancestor package annotated with @ModuleGen");
        }
        ifaceFQCN = elem.asType().toString();
        ifaceSimpleName = elem.getSimpleName().toString();
        ifacePackageName = elementUtils.getPackageOf(elem).toString();
        ifaceComment = elementUtils.getDocComment(elem);
        doc = docFactory.createDoc(elem);
        concrete = elem.getAnnotation(VertxGen.class) == null || elem.getAnnotation(VertxGen.class).concrete();
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
          if (!tmSuper.toString().equals(Object.class.getName())) {
            TypeInfo superTypeInfo;
            try {
              superTypeInfo = typeFactory.create(tmSuper);
            } catch (IllegalArgumentException e) {
              throw new GenException(elem, e.getMessage());
            }
            switch (superTypeInfo.getKind()) {
              case API: {
                try {
                  TypeInfo.Class.Api superType = (TypeInfo.Class.Api) typeFactory.create(tmSuper).getRaw();
                  referencedTypes.add(superType);
                  (superType.isConcrete() ? concreteSuperTypes : abstractSuperTypes).add(superTypeInfo);
                  superTypes.add(superTypeInfo);
                } catch (Exception e) {
                  throw new GenException(elem, e.getMessage());
                }
                break;
              }
              case HANDLER:
                handlerSuperType = superTypeInfo;
                break;
            }
            superTypeInfo.collectImports(importedTypes);
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

      TypeMirror objectType = elementUtils.getTypeElement("java.lang.Object").asType();

      // Traverse methods
      elementUtils.getAllMembers((TypeElement) elem).stream().
          filter(elt -> !typeUtils.isSameType(elt.getEnclosingElement().asType(), objectType)).
          flatMap(Helper.FILTER_METHOD).
          forEach(this::addMethod);

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

      // Now check for overloaded methods
      for (List<MethodInfo> meths: methodMap.values()) {

        // Ambiguous
        try {
          methodOverloadChecker.checkAmbiguous(meths);
        } catch (RuntimeException e) {
          throw new GenException(elem, e.getMessage());
        }

        // Cannot be both static and non static
        MethodInfo first = meths.get(0);
        for (MethodInfo method : meths) {
          if (method.staticMethod != first.staticMethod) {
            throw new GenException(elem, "Overloaded method " + method.getName() + " cannot be both static and instance");
          }
        }
      }
    }
  }

  private void addMethod(ExecutableElement methodElt) {
    boolean isIgnore = methodElt.getAnnotation(GenIgnore.class) != null;
    if (isIgnore) {
      return;
    }
    Set<Modifier> mods = methodElt.getModifiers();
    if (!mods.contains(Modifier.PUBLIC)) {
      return;
    }
    if (mods.contains(Modifier.DEFAULT)) {
      return;
    }

    TypeElement declaringElt = (TypeElement) methodElt.getEnclosingElement();
    if (!declaringElt.equals(modelElt)) {
      TypeInfo declaringType = typeFactory.create(declaringElt.asType());
      switch (declaringType.getKind()) {
        case API: {
          TypeInfo.Class.Api declaringApiType = (TypeInfo.Class.Api) declaringType.getRaw();
          if (declaringApiType.isConcrete()) {
            return;
          }
          break;
        }
        case HANDLER: {
          break;
        }
        default:
          return;
      }
    }

    TypeInfo.Class ownerType = typeFactory.create(declaringElt.asType()).getRaw();

    // Check we don't hide another method
    for (Map.Entry<ExecutableElement, MethodInfo> method : methods.entrySet()) {
      if (method.getValue().getName().equals(methodElt.getSimpleName().toString())) {
        ExecutableType t1 = (ExecutableType) method.getKey().asType();
        ExecutableType t2 = (ExecutableType) methodElt.asType();
        if (typeUtils.isSubsignature(t1, t2) && typeUtils.isSubsignature(t2, t1)) {
          method.getValue().ownerTypes.add(ownerType);
          return;
        }
      }
    }

    boolean isStatic = mods.contains(Modifier.STATIC);
    if (isStatic && !concrete) {
      throw new GenException(methodElt, "Abstract interface cannot declare static methods");
    }

    boolean isCacheReturn = methodElt.getAnnotation(CacheReturn.class) != null;
    ArrayList<TypeParamInfo.Method> typeParams = new ArrayList<>();
    for (TypeParameterElement typeParam : methodElt.getTypeParameters()) {
      for (TypeMirror bound : typeParam.getBounds()) {
        if (!isObjectBound(bound)) {
          throw new GenException(methodElt, "Type parameter bound not supported " + bound);
        }
      }
      typeParams.add((TypeParamInfo.Method) TypeParamInfo.create(typeParam));
    }

    //
    Map<String, String> paramDescs = new HashMap<>();
    String comment = elementUtils.getDocComment(methodElt);
    Doc doc = docFactory.createDoc(methodElt);
    String returnDesc = null;
    if (doc != null) {
      doc.
          getBlockTags().
          stream().
          filter(tag -> tag.getName().equals("param")).
          map(Tag.Param::new).
          forEach(tag -> paramDescs.put(tag.getParamName(), tag.getParamDescription()));
      Optional<Tag> returnTag = doc.
          getBlockTags().
          stream().
          filter(tag -> tag.getName().equals("return")).
          findFirst();
      if (returnTag.isPresent()) {
        returnDesc = Helper.normalizeWhitespaces(returnTag.get().getValue());
      }
    }

    //
    ExecutableType methodType = (ExecutableType) typeUtils.asMemberOf((DeclaredType) modelElt.asType(), methodElt);
    List<ParamInfo> mParams = getParams(methodElt, methodType, paramDescs);

    //
    AnnotationMirror fluentAnnotation = Helper.resolveMethodAnnotation(Fluent.class, elementUtils, typeUtils, declaringElt, methodElt);
    boolean isFluent = fluentAnnotation != null;
    if (isFluent) {
      isFluent = true;
      if (!typeUtils.isSameType(declaringElt.asType(), modelElt.asType())) {
        String msg = "Interface " + modelElt + " does not redeclare the @Fluent return type " +
            " of method " + methodElt + " declared by " + declaringElt;
        messager.printMessage(Diagnostic.Kind.WARNING, msg, modelElt, fluentAnnotation);
        logger.warning(msg);
      } else {
        TypeMirror fluentType = methodElt.getReturnType();
        if (!typeUtils.isAssignable(fluentType, modelElt.asType())) {
          throw new GenException(methodElt, "Methods marked with @Fluent must have a return type that extends the type");
        }
      }
    }

    TypeInfo returnType = typeFactory.create(methodType.getReturnType());
    returnType.collectImports(importedTypes);
    if (isCacheReturn && returnType instanceof TypeInfo.Void) {
      throw new GenException(methodElt, "void method can't be marked with @CacheReturn");
    }
    String methodName = methodElt.getSimpleName().toString();

    // Only check the return type if not fluent, because generated code won't look it at anyway
    if (!isFluent) {
      checkReturnType(methodElt, returnType);
    }

    // Determine method kind + validate
    MethodKind kind = MethodKind.OTHER;
    if (methodElt.getAnnotation(IndexGetter.class) != null) {
      if (!mParams.stream().anyMatch(param -> param.type.getName().equals("int"))) {
        throw new GenException(methodElt, "No int arg found in index getter method");
      }
      kind = MethodKind.INDEX_GETTER;
    } else if (methodElt.getAnnotation(IndexSetter.class) != null) {
      if (!mParams.stream().anyMatch(param -> param.type.getName().equals("int"))) {
        throw new GenException(methodElt, "No int arg found in index setter method");
      }
      kind = MethodKind.INDEX_SETTER;
    } else {
      if (methodName.startsWith("is") && methodName.length() > 2 && Character.isUpperCase(methodName.charAt(2)) &&
          mParams.isEmpty() && !(returnType instanceof TypeInfo.Void)) {
        kind = MethodKind.GETTER;
      } else if (methodName.startsWith("get") && methodName.length() > 3 && Character.isUpperCase(methodName.charAt(3)) &&
          mParams.isEmpty() && !(returnType instanceof TypeInfo.Void)) {
        kind = MethodKind.GETTER;
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
    }

    MethodInfo methodInfo = createMethodInfo(ownerType, methodName, comment, doc, kind,
        returnType, returnDesc, isFluent, isCacheReturn, mParams, methodElt, isStatic, typeParams, declaringElt);
    checkMethod(methodInfo);
    List<MethodInfo> methodsByName = methodMap.get(methodInfo.getName());
    if (methodsByName == null) {
      methodsByName = new ArrayList<>();
      methodMap.put(methodInfo.getName(), methodsByName);
    }
    methodsByName.add(methodInfo);
    methods.put(methodElt, methodInfo);
    methodInfo.collectImports(importedTypes);
  }

  // This is a hook to allow a specific type of method to be created
  protected MethodInfo createMethodInfo(TypeInfo.Class ownerType, String methodName, String comment, Doc doc, MethodKind kind, TypeInfo returnType,
                                        String returnDescription,
                                        boolean isFluent, boolean isCacheReturn, List<ParamInfo> mParams,
                                        ExecutableElement methodElt, boolean isStatic, ArrayList<TypeParamInfo.Method> typeParams,
                                        TypeElement declaringElt) {
    return new MethodInfo(Collections.singleton(ownerType), methodName, kind, returnType, returnDescription,
      isFluent, isCacheReturn, mParams, comment, doc, isStatic, typeParams);
  }

  // This is a hook to allow different model implementations to check methods in different ways
  protected void checkMethod(MethodInfo methodInfo) {
    List<MethodInfo> methodsByName = methodMap.get(methodInfo.getName());
    if (methodsByName != null) {
      // Overloaded methods must have same return type
      for (MethodInfo meth: methodsByName) {
        if (!meth.returnType.equals(methodInfo.returnType)) {
          throw new GenException(this.modelElt, "Overloaded method " + methodInfo.name + " must have the same return type "
            + meth.returnType + " != " + methodInfo.returnType);
        }
      }
    }
  }

  private boolean isObjectBound(TypeMirror bound) {
    return bound.getKind() == TypeKind.DECLARED && bound.toString().equals(Object.class.getName());
  }

  private List<ParamInfo> getParams(ExecutableElement execElem, ExecutableType execType, Map<String, String> descs) {
    List<? extends VariableElement> params = execElem.getParameters();
    List<ParamInfo> mParams = new ArrayList<>();
    for (int i = 0; i < params.size();i++) {
      VariableElement param = params.get(i);
      TypeMirror type = execType.getParameterTypes().get(i);
      TypeInfo typeInfo;
      try {
        typeInfo = typeFactory.create(type);
      } catch (Exception e) {
        throw new GenException(param, e.getMessage());
      }
      checkParamType(execElem, type, typeInfo, i, params.size());
      String name = param.getSimpleName().toString();
      ParamInfo mParam = new ParamInfo(name, descs.get(name), typeInfo);
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
    vars.put("doc", doc);
    vars.put("helper", new Helper());
    vars.put("methods", getMethods());
    vars.put("referencedTypes", getReferencedTypes());
    vars.put("superTypes", getSuperTypes());
    vars.put("concreteSuperTypes", getConcreteSuperTypes());
    vars.put("abstractSuperTypes", getAbstractSuperTypes());
    vars.put("handlerSuperType", getHandlerSuperType());
    vars.put("methodsByName", getMethodMap());
    vars.put("referencedDataObjectTypes", getReferencedDataObjectTypes());
    vars.put("typeParams", getTypeParams());
    vars.put("instanceMethods", getInstanceMethods());
    vars.put("staticMethods", getStaticMethods());
    vars.putAll(ClassKind.vars());
    vars.putAll(MethodKind.vars());
    vars.putAll(Case.vars());
    return vars;
  }

  private static boolean rawTypeIs(TypeInfo type, Class<?>... classes) {
    if (type instanceof TypeInfo.Parameterized) {
      String rawClassName = type.getRaw().getName();
      for (Class<?> c : classes) {
        if (rawClassName.equals(c.getName())) {
          return true;
        }
      }
    }

    return false;
  }
}
