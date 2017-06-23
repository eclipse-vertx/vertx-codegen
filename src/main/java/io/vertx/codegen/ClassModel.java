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
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.codegen.doc.Doc;
import io.vertx.codegen.doc.Tag;
import io.vertx.codegen.doc.Text;
import io.vertx.codegen.doc.Token;
import io.vertx.codegen.overloadcheck.MethodOverloadChecker;
import io.vertx.codegen.type.*;

import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.*;
import javax.lang.model.type.*;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Method;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * A processed source.
 *
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
public class ClassModel implements Model {

  public static final String VERTX_READ_STREAM = "io.vertx.core.streams.ReadStream";
  public static final String VERTX_WRITE_STREAM = "io.vertx.core.streams.WriteStream";
  public static final String VERTX_ASYNC_RESULT = "io.vertx.core.AsyncResult";
  public static final String VERTX_HANDLER = "io.vertx.core.Handler";
  public static final String JSON_OBJECT = "io.vertx.core.json.JsonObject";
  public static final String JSON_ARRAY = "io.vertx.core.json.JsonArray";
  public static final String VERTX = "io.vertx.core.Vertx";
  private static final Logger logger = Logger.getLogger(ClassModel.class.getName());

  protected final ProcessingEnvironment env;
  protected final AnnotationValueInfoFactory annotationValueInfoFactory;
  protected final MethodOverloadChecker methodOverloadChecker;
  protected final Messager messager;
  protected final TypeMirrorFactory typeFactory;
  protected final Doc.Factory docFactory;
  protected final Map<String, TypeElement> sources;
  protected final TypeElement modelElt;
  protected final Elements elementUtils;
  protected final Types typeUtils;
  protected boolean processed = false;
  protected LinkedHashMap<ExecutableElement, MethodInfo> methods = new LinkedHashMap<>();
  protected Set<ClassTypeInfo> collectedTypes = new HashSet<>();
  protected Set<ClassTypeInfo> importedTypes = new HashSet<>();
  protected Set<ApiTypeInfo> referencedTypes = new HashSet<>();
  protected Set<ClassTypeInfo> referencedDataObjectTypes = new HashSet<>();
  protected boolean concrete;
  protected ClassTypeInfo type;
  protected String ifaceSimpleName;
  protected String ifaceFQCN;
  protected String ifacePackageName;
  protected String ifaceComment;
  protected Doc doc;
  protected List<TypeInfo> superTypes = new ArrayList<>();
  protected TypeInfo concreteSuperType;
  protected List<TypeInfo> abstractSuperTypes = new ArrayList<>();
  // The methods, grouped by name
  protected Map<String, List<MethodInfo>> methodMap = new LinkedHashMap<>();
  protected List<AnnotationValueInfo> annotations = new ArrayList<>();
  protected Map<String, List<AnnotationValueInfo>> methodAnnotationsMap = new LinkedHashMap<>();

  public ClassModel(ProcessingEnvironment env, MethodOverloadChecker methodOverloadChecker,
                    Messager messager,  Map<String, TypeElement> sources, Elements elementUtils,
                    Types typeUtils, TypeElement modelElt) {
    this.env = env;
    this.methodOverloadChecker = methodOverloadChecker;
    this.typeFactory = new TypeMirrorFactory(elementUtils, typeUtils);
    this.docFactory = new Doc.Factory(messager, elementUtils, typeUtils, typeFactory, modelElt);
    this.messager = messager;
    this.sources = sources;
    this.elementUtils = elementUtils;
    this.typeUtils = typeUtils;
    this.modelElt = modelElt;
    this.annotationValueInfoFactory = new AnnotationValueInfoFactory(elementUtils, typeUtils);
  }

  private static boolean rawTypeIs(TypeInfo type, Class<?>... classes) {
    if (type instanceof ParameterizedTypeInfo) {
      String rawClassName = type.getRaw().getName();
      for (Class<?> c : classes) {
        if (rawClassName.equals(c.getName())) {
          return true;
        }
      }
    }

    return false;
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

  public boolean isConcrete() {
    return concrete;
  }

  /**
   * @return all classes that are not in the same package
   */
  public Set<ClassTypeInfo> getImportedTypes() {
    return importedTypes;
  }

  /**
   * @return all the referenced api types
   */
  public Set<ApiTypeInfo> getReferencedTypes() {
    return referencedTypes;
  }

  /**
   * @return all the referenced data object types
   */
  public Set<ClassTypeInfo> getReferencedDataObjectTypes() {
    return referencedDataObjectTypes;
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

  public ClassTypeInfo getType() {
    return type;
  }

  public ModuleInfo getModule() {
    return type.getRaw().getModule();
  }

  public List<TypeInfo> getSuperTypes() {
    return superTypes;
  }

  public TypeInfo getConcreteSuperType() {
    return concreteSuperType;
  }

  public List<TypeInfo> getAbstractSuperTypes() {
    return abstractSuperTypes;
  }

  public TypeInfo getHandlerType() {
    return (type.getKind() == ClassKind.API) ? ((ApiTypeInfo)type).getHandlerArg() : null;
  }

  public Map<String, List<MethodInfo>> getMethodMap() {
    return methodMap;
  }

  public List<TypeParamInfo.Class> getTypeParams() {
    return type.getRaw().getParams();
  }

  public List<TypeInfo> getSuperTypeArguments() {
    if (concreteSuperType != null && concreteSuperType.isParameterized()) {
      DeclaredType tm = (DeclaredType) modelElt.asType();;
      List<? extends TypeMirror> st = typeUtils.directSupertypes(tm);
      for (TypeMirror tmSuper: st) {
        if (tmSuper.getKind() == TypeKind.DECLARED) {
          DeclaredType abc = (DeclaredType) tmSuper;
          TypeElement tt = (TypeElement) abc.asElement();
          if (tt.getQualifiedName().toString().equals(concreteSuperType.getRaw().getName())) {
            List<TypeInfo> list = new ArrayList<>();
            int size = tt.getTypeParameters().size();
            for (int i = 0; i< size;i++) {
              TypeMirror q = abc.getTypeArguments().get(i);
              TypeInfo ti =  typeFactory.create(q);
              list.add(ti);
            }
            return list;
          }
        }
      }
    }
    return null;
  }

  /**
   * @return all the annotations on this class
   */
  public List<AnnotationValueInfo> getAnnotations() {
    return annotations;
  }

  /**
   * @return a map of the method's annotations for this class, by method name.
   */
  public Map<String, List<AnnotationValueInfo>> getMethodAnnotations() {
    return methodAnnotationsMap;
  }

  private void sortMethodMap(Map<String, List<MethodInfo>> map) {
    for (List<MethodInfo> list: map.values()) {
      list.sort((meth1, meth2) -> meth1.params.size() - meth2.params.size());
    }
  }

  protected void checkParamType(ExecutableElement elem, TypeMirror type, TypeInfo typeInfo, int pos, int numParams) {
    if (isLegalNonCallableParam(typeInfo)) {
      return;
    }
    if (isLegalClassTypeParam(elem, typeInfo)) {
      return;
    }
    if (isLegalHandlerType(typeInfo)) {
      return;
    }
    if (isLegalHandlerAsyncResultType(typeInfo)) {
      return;
    }
    if (isLegalFunctionType(typeInfo)) {
      return;
    }
    throw new GenException(elem, "type " + typeInfo + " is not legal for use for a parameter in code generation");
  }

  protected void checkReturnType(ExecutableElement elem, TypeInfo type, TypeMirror typeMirror) {
    if (type.isVoid()) {
      return;
    }
    if (isLegalNonCallableReturnType(type)) {
      return;
    }
    if (isLegalHandlerType(type)) {
      return;
    }
    if (isLegalHandlerAsyncResultType(type)) {
      return;
    }
    throw new GenException(elem, "type " + type + " is not legal for use for a return type in code generation");
  }

  /**
   * The <i>Return</i> set but not `void`.
   */
  private boolean isLegalNonCallableReturnType(TypeInfo type) {
    if (type.getKind().basic) {
      return true;
    }
    if (type.getKind().json) {
      return true;
    }
    if (isLegalDataObjectTypeReturn(type)) {
      return true;
    }
    if (isLegalEnum(type)) {
      return true;
    }
    if (type.getKind() == ClassKind.THROWABLE) {
      return true;
    }
    if (isTypeVariable(type)) {
      return true;
    }
    if (type.getKind() == ClassKind.OBJECT) {
      return true;
    }
    if (isVertxGenInterface(type, true)) {
      return true;
    }
    if (isLegalContainerReturn(type)) {
      return true;
    }
    return false;
  }

  private boolean isLegalEnum(TypeInfo info) {
    return info.getKind() == ClassKind.ENUM;
  }

  /**
   * The set <i>Param</i>
   */
  private boolean isLegalNonCallableParam(TypeInfo typeInfo) {
    if (typeInfo.getKind().basic) {
      return true;
    }
    if (typeInfo.getKind().json) {
      return true;
    }
    if (isLegalDataObjectTypeParam(typeInfo)) {
      return true;
    }
    if (isLegalEnum(typeInfo)) {
      return true;
    }
    if (typeInfo.getKind() == ClassKind.THROWABLE) {
      return true;
    }
    if (isTypeVariable(typeInfo)) {
      return true;
    }
    if (typeInfo.getKind() == ClassKind.OBJECT) {
      return true;
    }
    if (isVertxGenInterface(typeInfo, true)) {
      return true;
    }
    if (isLegalContainerParam(typeInfo)) {
      return true;
    }
    return false;
  }

  private boolean isTypeVariable(TypeInfo type) {
    return type instanceof TypeVariableInfo;
  }

  private boolean isLegalDataObjectTypeParam(TypeInfo type) {
    if (type.getKind() == ClassKind.DATA_OBJECT) {
      DataObjectTypeInfo classType = (DataObjectTypeInfo) type;
      return !classType.isAbstract();
    }
    return false;
  }

  private boolean isLegalClassTypeParam(ExecutableElement elt, TypeInfo type) {
    if (type.getKind() == ClassKind.CLASS_TYPE && type.isParameterized()) {
      ParameterizedTypeInfo parameterized = (ParameterizedTypeInfo) type;
      TypeInfo arg = parameterized.getArg(0);
      if (arg.isVariable()) {
        TypeVariableInfo variable = (TypeVariableInfo) arg;
        for (TypeParameterElement typeParamElt : elt.getTypeParameters()) {
          if (typeParamElt.getSimpleName().toString().equals(variable.getName())) {
            return true;
          }
        }
      }
    }
    return false;
  }

  protected boolean isLegalDataObjectTypeReturn(TypeInfo type) {
    if (type.getKind() == ClassKind.DATA_OBJECT) {
      TypeElement typeElt = elementUtils.getTypeElement(type.getName());
      if (typeElt != null) {
        Optional<ExecutableElement> opt = elementUtils.
            getAllMembers(typeElt).
            stream().
            flatMap(Helper.FILTER_METHOD).
            filter(m -> m.getSimpleName().toString().equals("toJson") &&
                m.getParameters().isEmpty() &&
                m.getReturnType().toString().equals(JSON_OBJECT)).
            findFirst();
        return opt.isPresent();
      }
    }
    return false;
  }

  protected boolean isLegalContainerParam(TypeInfo type) {
    // List<T> and Set<T> are also legal for params if T = basic type, json, @VertxGen, @DataObject
    // Map<K,V> is also legal for returns and params if K is a String and V is a basic type, json, or a @VertxGen interface
    if (rawTypeIs(type, List.class, Set.class, Map.class)) {
      TypeInfo argument = ((ParameterizedTypeInfo) type).getArgs().get(0);
      if (type.getKind() != ClassKind.MAP) {
        if (argument.getKind().basic || argument.getKind().json || isVertxGenInterface(argument, false) || isLegalDataObjectTypeParam(argument) || argument.getKind() == ClassKind.ENUM) {
          return true;
        }
      } else if (argument.getKind() == ClassKind.STRING) { // Only allow Map's with String's for keys
        argument = ((ParameterizedTypeInfo) type).getArgs().get(1);
        if (argument.getKind().basic || argument.getKind().json || isVertxGenInterface(argument, false)) {
          return true;
        }
      }
    }
    return false;
  }

  protected boolean isLegalContainerReturn(TypeInfo type) {
    if (rawTypeIs(type, List.class, Set.class, Map.class)) {
      List<TypeInfo> args = ((ParameterizedTypeInfo) type).getArgs();
      if (type.getKind() == ClassKind.MAP) {
        if (args.get(0).getKind() != ClassKind.STRING) {
          return false;
        }
        TypeInfo valueType = args.get(1);
        if (valueType.getKind().basic ||
            valueType.getKind().json) {
          return true;
        }
      } else {
        TypeInfo valueType = args.get(0);
        if (valueType.getKind().basic ||
            valueType.getKind().json ||
            valueType.getKind() == ClassKind.ENUM ||
            isVertxGenInterface(valueType, false) ||
            isLegalDataObjectTypeReturn(valueType)) {
          return true;
        }
      }
    }
    return false;
  }

  private boolean isVertxGenInterface(TypeInfo type, boolean allowParameterized) {
    if (type.getKind() == ClassKind.API) {
      if (type.isParameterized()) {
        if (allowParameterized) {
          ParameterizedTypeInfo parameterized = (ParameterizedTypeInfo) type;
          for (TypeInfo paramType : parameterized.getArgs()) {
            ClassKind kind = paramType.getKind();
            if (!(paramType instanceof ApiTypeInfo || paramType.isVariable() || kind == ClassKind.VOID
              || kind.basic || kind.json || kind == ClassKind.DATA_OBJECT || kind == ClassKind.ENUM )) {
              return false;
            }
            if (paramType.isNullable()) {
              return false;
            }
          }
          return true;
        } else {
          return false;
        }
      } else {
        return true;
      }
    }
    return false;
  }

  private boolean isLegalFunctionType(TypeInfo typeInfo) {
    if (typeInfo.getErased().getKind() == ClassKind.FUNCTION) {
      TypeInfo paramType = ((ParameterizedTypeInfo) typeInfo).getArgs().get(0);
      if (isLegalCallbackValueType(paramType) || paramType.getKind() == ClassKind.THROWABLE) {
        TypeInfo returnType = ((ParameterizedTypeInfo) typeInfo).getArgs().get(1);
        return isLegalNonCallableParam(returnType);
      }
    }
    return false;
  }

  private boolean isLegalHandlerType(TypeInfo type) {
    if (type.getErased().getKind() == ClassKind.HANDLER) {
      TypeInfo eventType = ((ParameterizedTypeInfo) type).getArgs().get(0);
      if (isLegalCallbackValueType(eventType) || eventType.getKind() == ClassKind.THROWABLE) {
        return true;
      }
    }
    return false;
  }

  private boolean isLegalHandlerAsyncResultType(TypeInfo type) {
    if (type.getErased().getKind() == ClassKind.HANDLER) {
      TypeInfo eventType = ((ParameterizedTypeInfo) type).getArgs().get(0);
      if (eventType.getErased().getKind() == ClassKind.ASYNC_RESULT && !eventType.isNullable()) {
        TypeInfo resultType = ((ParameterizedTypeInfo) eventType).getArgs().get(0);
        if (isLegalCallbackValueType(resultType)) {
          return true;
        }
      }
    }
    return false;
  }

  private boolean isLegalCallbackValueType(TypeInfo type) {
    if (type.getKind() == ClassKind.VOID) {
      return !type.isNullable();
    }
    return isLegalNonCallableReturnType(type);
  }

  private void determineApiTypes() {
    collectedTypes.stream().
        map(ClassTypeInfo::getRaw).
        flatMap(Helper.instanceOf(ClassTypeInfo.class)).
        filter(t -> !t.getPackageName().equals(ifaceFQCN)).
        forEach(importedTypes::add);
    collectedTypes.stream().
        map(ClassTypeInfo::getRaw).
        flatMap(Helper.instanceOf(ApiTypeInfo.class)).
        filter(t -> !t.equals(type.getRaw())).
        forEach(referencedTypes::add);
    collectedTypes.stream().
        map(ClassTypeInfo::getRaw).
        flatMap(Helper.instanceOf(ClassTypeInfo.class)).
        filter(t -> t.getKind() == ClassKind.DATA_OBJECT).
        forEach(referencedDataObjectTypes::add);
  }

  boolean process() {
    if (!processed) {
      traverseElem(modelElt);
      determineApiTypes();
      processed = true;
      return true;
    } else {
      return false;
    }
  }

  private void traverseElem(Element elem) {
    switch (elem.getKind()) {
      case ENUM:
      case CLASS: {
        throw new GenException(elem, "@VertxGen can only be used with interfaces or enums in " + elem.asType().toString());
      }
      case INTERFACE: {
        if (ifaceFQCN != null) {
          throw new GenException(elem, "Can only have one interface per file");
        }
        type = typeFactory.create(elem.asType()).getRaw();
        Helper.checkUnderModule(this, "@VertxGen");
        ifaceFQCN = elem.asType().toString();
        ifaceSimpleName = elem.getSimpleName().toString();
        ifacePackageName = elementUtils.getPackageOf(elem).getQualifiedName().toString();
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
                  ApiTypeInfo superType = (ApiTypeInfo) typeFactory.create(tmSuper).getRaw();
                  if (superType.isConcrete()) {
                    if (concrete) {
                      if (concreteSuperType != null) {
                        throw new GenException(elem, "A concrete interface cannot extend more than one concrete interfaces");
                      }
                    } else {
                      throw new GenException(elem, "A abstract interface cannot extend a concrete interface");
                    }
                    concreteSuperType = superTypeInfo;
                  } else {
                    abstractSuperTypes.add(superTypeInfo);
                  }
                  superTypes.add(superTypeInfo);
                } catch (Exception e) {
                  throw new GenException(elem, e.getMessage());
                }
                break;
              }
            }
            superTypeInfo.collectImports(collectedTypes);
          }
        }
        elem.getAnnotationMirrors().stream().map(annotationValueInfoFactory::processAnnotation).forEach(annotations::add);
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

      boolean hasNoMethods = methods.values().stream().filter(m -> !m.isDefaultMethod()).count() == 0;
      if (hasNoMethods && superTypes.isEmpty()) {
        throw new GenException(elem, "Interface " + ifaceFQCN + " does not contain any methods for generation");
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

  private void addMethod(ExecutableElement modelMethod) {
    boolean isIgnore = modelMethod.getAnnotation(GenIgnore.class) != null;
    if (isIgnore) {
      return;
    }
    Set<Modifier> mods = modelMethod.getModifiers();
    if (!mods.contains(Modifier.PUBLIC)) {
      return;
    }

    TypeElement declaringElt = (TypeElement) modelMethod.getEnclosingElement();
    TypeInfo declaringType = typeFactory.create(declaringElt.asType());

    if (!declaringElt.equals(modelElt) && (declaringType.getKind() != ClassKind.API && declaringType.getKind() != ClassKind.HANDLER)) {
      return;
    }

    ClassTypeInfo type = typeFactory.create(declaringElt.asType()).getRaw();

    boolean isDefault = mods.contains(Modifier.DEFAULT);
    boolean isStatic = mods.contains(Modifier.STATIC);
    if (isStatic && !concrete) {
      throw new GenException(modelMethod, "Abstract interface cannot declare static methods");
    }

    boolean isCacheReturn = modelMethod.getAnnotation(CacheReturn.class) != null;
    ArrayList<TypeParamInfo.Method> typeParams = new ArrayList<>();
    for (TypeParameterElement typeParam : modelMethod.getTypeParameters()) {
      for (TypeMirror bound : typeParam.getBounds()) {
        if (!isObjectBound(bound)) {
          throw new GenException(modelMethod, "Type parameter bound not supported " + bound);
        }
      }
      typeParams.add((TypeParamInfo.Method) TypeParamInfo.create(typeParam));
    }

    //
    List<Method> reflectMethods;
    List<ExecutableElement> modelMethods;
    Method reflectMethod = Helper.getReflectMethod(modelMethod);
    if (reflectMethod != null) {
      reflectMethods = new ArrayList<>();
      reflectMethods.add(reflectMethod);
      modelMethods = null;
    } else {
      reflectMethods = null;
      modelMethods = new ArrayList<>();
      modelMethods.add(modelMethod);
    }

    // Owner types
    Set<ClassTypeInfo> ownerTypes = new HashSet<>();
    ownerTypes.add(type);

    ArrayList<DeclaredType> ancestors = new ArrayList<>(Helper.resolveAncestorTypes(modelElt, true, true));

    // Sort to have super types the last, etc..
    // solve some problem with diamond inheritance order that can show up in type use
    Collections.sort(ancestors, (o1, o2) -> {
      if (typeUtils.isSubtype(o1, o2)) {
        return -1;
      } else if (typeUtils.isSubtype(o2, o1)) {
        return 1;
      } else {
        return ((TypeElement) o1.asElement()).getQualifiedName().toString().compareTo(((TypeElement) o2.asElement()).getQualifiedName().toString());
      }
    });

    // Check overrides and merge type use
    for (DeclaredType ancestorType : ancestors) {
      TypeElement ancestorElt = (TypeElement) ancestorType.asElement();
      if (ancestorElt.getAnnotation(VertxGen.class) != null) {
        elementUtils.getAllMembers(ancestorElt).
            stream().
            flatMap(Helper.FILTER_METHOD).
            filter(meth -> elementUtils.overrides(modelMethod, meth, modelElt)).
            forEach(overridenMethodElt -> {
              if (reflectMethods != null) {
                Method overridenMethodRef = Helper.getReflectMethod(overridenMethodElt);
                if (overridenMethodRef != null) {
                  reflectMethods.add(overridenMethodRef);
                }
              } else {
                modelMethods.add(overridenMethodElt);
              }
              ownerTypes.add(typeFactory.create((DeclaredType) ancestorElt.asType()).getRaw());
            });
      }
    }

    //
    Map<String, String> paramDescs = new HashMap<>();
    String comment = elementUtils.getDocComment(modelMethod);
    Doc doc = docFactory.createDoc(modelMethod);
    Text returnDesc = null;
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
        returnDesc = new Text(Helper.normalizeWhitespaces(returnTag.get().getValue())).map(Token.tagMapper(elementUtils, typeUtils, modelElt));
      }
    }

    //
    List<ParamInfo> mParams = getParams(reflectMethods, modelMethods, modelMethod, paramDescs);

    //
    AnnotationMirror fluentAnnotation = Helper.resolveMethodAnnotation(Fluent.class, elementUtils, typeUtils, declaringElt, modelMethod);
    boolean isFluent = fluentAnnotation != null;
    if (isFluent) {
      isFluent = true;
      if (!typeUtils.isSameType(declaringElt.asType(), modelElt.asType())) {
        String msg = "Interface " + modelElt + " does not redeclare the @Fluent return type " +
            " of method " + modelMethod + " declared by " + declaringElt;
        messager.printMessage(Diagnostic.Kind.WARNING, msg, modelElt, fluentAnnotation);
        logger.warning(msg);
      } else {
        TypeMirror fluentType = modelMethod.getReturnType();
        if (!typeUtils.isAssignable(fluentType, modelElt.asType())) {
          throw new GenException(modelMethod, "Methods marked with @Fluent must have a return type that extends the type");
        }
      }
    }

    //
    TypeUse returnTypeUse;
    if (reflectMethods != null) {
      returnTypeUse = TypeUse.createTypeUse(reflectMethods.stream().map(Method::getAnnotatedReturnType).toArray(AnnotatedType[]::new));
    } else {
//      returnTypeUse = TypeUse.createTypeUse(modelMethods.stream().map(ExecutableElement::getReturnType).toArray(TypeMirror[]::new));
      returnTypeUse = TypeUse.createReturnTypeUse(env,  modelMethods.toArray(new ExecutableElement[modelMethods.size()]));
    }

    ExecutableType methodType = (ExecutableType) typeUtils.asMemberOf((DeclaredType) modelElt.asType(), modelMethod);
    TypeInfo returnType;
    try {
      returnType = typeFactory.create(returnTypeUse, methodType.getReturnType());
    } catch (Exception e) {
      GenException genEx = new GenException(modelMethod, e.getMessage());
      genEx.initCause(e);
      throw genEx;
    }
    returnType.collectImports(collectedTypes);
    if (isCacheReturn && returnType.isVoid()) {
      throw new GenException(modelMethod, "void method can't be marked with @CacheReturn");
    }
    String methodName = modelMethod.getSimpleName().toString();

    // Only check the return type if not fluent, because generated code won't look it at anyway
    if (!isFluent) {
      checkReturnType(modelMethod, returnType, methodType.getReturnType());
    } else if (returnType.isNullable()) {
      throw new GenException(modelMethod, "Fluent return type cannot be nullable");
    }

    // Determine method kind + validate
    MethodKind kind = MethodKind.OTHER;
    int lastParamIndex = mParams.size() - 1;
    if (lastParamIndex >= 0 && (returnType.isVoid() || isFluent)) {
      TypeInfo lastParamType = mParams.get(lastParamIndex).type;
      if (lastParamType.getKind() == ClassKind.HANDLER) {
        TypeInfo typeArg = ((ParameterizedTypeInfo) lastParamType).getArgs().get(0);
        if (typeArg.getKind() == ClassKind.ASYNC_RESULT) {
          kind = MethodKind.FUTURE;
        } else {
          kind = MethodKind.HANDLER;
        }
      }
    }

    MethodInfo methodInfo = createMethodInfo(ownerTypes, methodName, comment, doc, kind,
        returnType, returnDesc, isFluent, isCacheReturn, mParams, modelMethod, isStatic, isDefault, typeParams, declaringElt);
    checkMethod(methodInfo);

    // Check we don't hide another method, we don't check overrides but we are more
    // interested by situations like diamond inheritance of the same method, in this case
    // we see two methods with the same signature that don't override each other
    for (Map.Entry<ExecutableElement, MethodInfo> otherMethod : methods.entrySet()) {
      if (otherMethod.getValue().getName().equals(modelMethod.getSimpleName().toString())) {
        ExecutableType t1 = (ExecutableType) otherMethod.getKey().asType();
        ExecutableType t2 = (ExecutableType) modelMethod.asType();
        if (typeUtils.isSubsignature(t1, t2) && typeUtils.isSubsignature(t2, t1)) {
          otherMethod.getValue().ownerTypes.addAll(methodInfo.ownerTypes);
          return;
        }
      }
    }

    // Add the method
    List<MethodInfo> methodsByName = methodMap.get(methodInfo.getName());
    if (methodsByName == null) {
      methodsByName = new ArrayList<>();
      methodMap.put(methodInfo.getName(), methodsByName);
      methodAnnotationsMap.put(methodInfo.getName(), modelMethod.getAnnotationMirrors().stream().map(annotationValueInfoFactory::processAnnotation).collect(Collectors.toList()));
    }
    methodsByName.add(methodInfo);
    methodInfo.collectImports(collectedTypes);

    if (!declaringElt.equals(modelElt) && declaringType.getKind() == ClassKind.API) {
      ApiTypeInfo declaringApiType = (ApiTypeInfo) declaringType.getRaw();
      if (declaringApiType.isConcrete()) {
        if (typeUtils.isSameType(methodType, modelMethod.asType())) {
          return;
        }
      }
    }
    methods.put(modelMethod, methodInfo);
  }

  // This is a hook to allow a specific type of method to be created
  protected MethodInfo createMethodInfo(Set<ClassTypeInfo> ownerTypes, String methodName, String comment, Doc doc, MethodKind kind, TypeInfo returnType,
                                        Text returnDescription,
                                        boolean isFluent, boolean isCacheReturn, List<ParamInfo> mParams,
                                        ExecutableElement methodElt, boolean isStatic, boolean isDefault, ArrayList<TypeParamInfo.Method> typeParams,
                                        TypeElement declaringElt) {
    return new MethodInfo(ownerTypes, methodName, kind, returnType, returnDescription,
      isFluent, isCacheReturn, mParams, comment, doc, isStatic, isDefault, typeParams);
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

  private List<ParamInfo> getParams(List<Method> reflectMethods, List<ExecutableElement> modelMethods, ExecutableElement methodElt, Map<String, String> descs) {
    ExecutableType methodType = (ExecutableType) typeUtils.asMemberOf((DeclaredType) modelElt.asType(), methodElt);
    List<? extends VariableElement> params = methodElt.getParameters();
    List<ParamInfo> mParams = new ArrayList<>();
    for (int i = 0; i < params.size();i++) {
      VariableElement param = params.get(i);
      TypeMirror type = methodType.getParameterTypes().get(i);
      TypeInfo typeInfo;
      int index = i;
      TypeUse typeUse;
      if (reflectMethods != null) {
        typeUse = TypeUse.createTypeUse(reflectMethods.stream().map(m -> m.getAnnotatedParameterTypes()[index]).toArray(AnnotatedType[]::new));
      } else {
        typeUse = TypeUse.createParamTypeUse(env, modelMethods.toArray(new ExecutableElement[modelMethods.size()]), index);
      }
      try {
        typeInfo = typeFactory.create(typeUse, type);
      } catch (Exception e) {
        throw new GenException(param, e.getMessage());
      }
      checkParamType(methodElt, type, typeInfo, i, params.size());
      String name = param.getSimpleName().toString();
      String desc = descs.get(name);
      Text text = desc != null ? new Text(desc).map(Token.tagMapper(elementUtils, typeUtils, modelElt)) : null;
      ParamInfo mParam = new ParamInfo(i, name, text, typeInfo);
      mParams.add(mParam);
    }
    return mParams;
  }

  @Override
  public Map<String, Object> getVars() {
    Map<String, Object> vars = Model.super.getVars();
    vars.put("importedTypes", getImportedTypes());
    vars.put("concrete", isConcrete());
    vars.put("type", getType());
    vars.put("ifacePackageName", getIfacePackageName());
    vars.put("ifaceSimpleName", getIfaceSimpleName());
    vars.put("ifaceFQCN", getIfaceFQCN());
    vars.put("ifaceComment", getIfaceComment());
    vars.put("doc", doc);
    vars.put("methods", getMethods());
    vars.put("referencedTypes", getReferencedTypes());
    vars.put("superTypes", getSuperTypes());
    vars.put("concreteSuperType", getConcreteSuperType());
    vars.put("abstractSuperTypes", getAbstractSuperTypes());
    vars.put("handlerType", getHandlerType());
    vars.put("methodsByName", getMethodMap());
    vars.put("classAnnotations", getAnnotations());
    vars.put("annotationsByMethodName", getMethodAnnotations());
    vars.put("referencedDataObjectTypes", getReferencedDataObjectTypes());
    vars.put("typeParams", getTypeParams());
    vars.put("instanceMethods", getInstanceMethods());
    vars.put("staticMethods", getStaticMethods());
    return vars;
  }
}
