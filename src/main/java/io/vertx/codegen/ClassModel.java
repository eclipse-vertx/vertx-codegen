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

import io.vertx.codegen.annotations.GenIgnore;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.codegen.doc.Doc;
import io.vertx.codegen.doc.Text;
import io.vertx.codegen.doc.Token;
import io.vertx.codegen.overloadcheck.MethodOverloadChecker;
import io.vertx.codegen.type.AnnotationValueInfo;
import io.vertx.codegen.type.AnnotationValueInfoFactory;
import io.vertx.codegen.type.ApiTypeInfo;
import io.vertx.codegen.type.ClassKind;
import io.vertx.codegen.type.ClassTypeInfo;
import io.vertx.codegen.type.DataObjectTypeInfo;
import io.vertx.codegen.type.EnumTypeInfo;
import io.vertx.codegen.type.ParameterizedTypeInfo;
import io.vertx.codegen.type.TypeInfo;
import io.vertx.codegen.type.TypeMirrorFactory;
import io.vertx.codegen.type.TypeVariableInfo;
import io.vertx.codegen.util.CompanionMethodCreator;
import io.vertx.codegen.util.DefaultMethodCreator;
import io.vertx.codegen.util.MethodCreator;

import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.ExecutableType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVariable;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
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

  protected final ProcessingEnvironment env;
  protected final AnnotationValueInfoFactory annotationValueInfoFactory;
  protected final Messager messager;
  protected final TypeMirrorFactory typeFactory;
  protected final Doc.Factory docFactory;
  protected final TypeElement modelElt;
  protected final Elements elementUtils;
  protected final Types typeUtils;
  protected boolean processed = false;
  protected LinkedHashMap<ExecutableElement, MethodInfo> methods = new LinkedHashMap<>();
  protected LinkedHashMap<ExecutableElement, MethodInfo> anyJavaTypeMethods = new LinkedHashMap<>();
  protected List<ConstantInfo> constants = new ArrayList<>();
  protected Set<ClassTypeInfo> collectedTypes = new HashSet<>();
  protected Set<ClassTypeInfo> importedTypes = new HashSet<>();
  protected Set<ApiTypeInfo> referencedTypes = new HashSet<>();
  protected Set<ClassTypeInfo> referencedDataObjectTypes = new HashSet<>();
  protected Set<EnumTypeInfo> referencedEnumTypes = new HashSet<>();
  protected boolean concrete;
  protected ClassTypeInfo type;
  protected String ifaceSimpleName;
  protected String ifaceFQCN;
  protected String ifacePackageName;
  protected String ifaceComment;
  protected Doc doc;
  protected List<TypeInfo> superTypes = new ArrayList<>();
  protected TypeInfo concreteSuperType;
  private List<TypeInfo> superTypeArguments;
  protected List<TypeInfo> abstractSuperTypes = new ArrayList<>();
  // The methods, grouped by name
  protected Map<String, List<MethodInfo>> methodMap = new LinkedHashMap<>();
  protected Map<String, List<AnnotationValueInfo>> methodAnnotationsMap = new LinkedHashMap<>();
  protected List<AnnotationValueInfo> annotations;
  protected boolean deprecated;
  protected Text deprecatedDesc;

  protected MethodCreator defaultMethodCreator;
  protected MethodCreator companionMethodCreator;

  public ClassModel(ProcessingEnvironment env, TypeElement modelElt) {
    this.elementUtils = env.getElementUtils();
    this.typeUtils = env.getTypeUtils();
    this.env = env;
    this.typeFactory = new TypeMirrorFactory(elementUtils, typeUtils);
    this.docFactory = new Doc.Factory(env.getMessager(), elementUtils, typeUtils, typeFactory, modelElt);
    this.messager = env.getMessager();
    this.modelElt = modelElt;
    this.annotationValueInfoFactory = new AnnotationValueInfoFactory(typeFactory);
    this.deprecated = modelElt.getAnnotation(Deprecated.class) != null;
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

  public List<MethodInfo> getAnyJavaTypeMethods() {
    return new ArrayList<>(anyJavaTypeMethods.values());
  }

  public List<MethodInfo> getStaticMethods() {
    return methods.values().stream().filter(MethodInfo::isStaticMethod).collect(Collectors.toList());
  }

  public List<MethodInfo> getInstanceMethods() {
    return methods.values().stream().filter(m -> !m.isStaticMethod()).collect(Collectors.toList());
  }

  public List<ConstantInfo> getConstants() {
    return constants;
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

  /**
   * @return all the referenced enum types
   */
  public Set<EnumTypeInfo> getReferencedEnumTypes() {
    return referencedEnumTypes;
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
    return (type.getKind() == ClassKind.API) ? ((ApiTypeInfo) type).getHandlerArg() : null;
  }

  public Map<String, List<MethodInfo>> getMethodMap() {
    return methodMap;
  }

  public List<TypeParamInfo.Class> getTypeParams() {
    return type.getRaw().getParams();
  }

  public List<TypeInfo> getSuperTypeArguments() {
    return superTypeArguments;
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
    for (List<MethodInfo> list : map.values()) {
      list.sort(Comparator.comparingInt(meth -> meth.getParams().size()));
    }
  }

  protected void checkConstantType(VariableElement elem, TypeInfo type, TypeMirror typeMirror, boolean allowAnyJavaType) {
    if (isLegalNonCallableReturnType(type, allowAnyJavaType)) {
      return;
    }
    throw new GenException(elem, "type " + type + " is not legal for use for a constant type in code generation");
  }

  /**
   * The <i>Return</i> set but not `void`.
   */
  private boolean isLegalNonCallableReturnType(TypeInfo type, boolean allowAnyJavaType) {
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
    if (allowAnyJavaType && type.getKind() == ClassKind.OTHER) {
      return true;
    }
    if (isLegalContainerReturn(type, allowAnyJavaType)) {
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
  private boolean isLegalNonCallableParam(TypeInfo typeInfo, boolean allowAnyJavaType) {
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
    if (allowAnyJavaType && typeInfo.getKind() == ClassKind.OTHER) {
      return true;
    }
    if (isLegalContainerParam(typeInfo, allowAnyJavaType)) {
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

  protected boolean isLegalContainerParam(TypeInfo type, boolean allowAnyJavaType) {
    // List<T> and Set<T> are also legal for params if T = basic type, json, @VertxGen, @DataObject
    // Map<K,V> is also legal for returns and params if K is a String and V is a basic type, json, or a @VertxGen interface
    if (rawTypeIs(type, List.class, Set.class, Map.class)) {
      TypeInfo argument = ((ParameterizedTypeInfo) type).getArgs().get(0);
      if (type.getKind() != ClassKind.MAP) {
        if (argument.getKind().basic ||
          argument.getKind().json ||
          isVertxGenInterface(argument, false) ||
          isLegalDataObjectTypeParam(argument) ||
          argument.getKind() == ClassKind.ENUM ||
          (allowAnyJavaType && argument.getKind() == ClassKind.OTHER)) {
          return true;
        }
      } else if (argument.getKind() == ClassKind.STRING) { // Only allow Map's with String's for keys
        argument = ((ParameterizedTypeInfo) type).getArgs().get(1);
        if (argument.getKind().basic || argument.getKind().json || isVertxGenInterface(argument, false) || (allowAnyJavaType && argument.getKind() == ClassKind.OTHER)) {
          return true;
        }
      }
    }
    return false;
  }

  protected boolean isLegalContainerReturn(TypeInfo type, boolean allowAnyJavaType) {
    if (rawTypeIs(type, List.class, Set.class, Map.class)) {
      List<TypeInfo> args = ((ParameterizedTypeInfo) type).getArgs();
      if (type.getKind() == ClassKind.MAP) {
        if (args.get(0).getKind() != ClassKind.STRING) {
          return false;
        }
        TypeInfo valueType = args.get(1);
        if (valueType.getKind().basic ||
          valueType.getKind().json ||
          (allowAnyJavaType && valueType.getKind() == ClassKind.OTHER)) {
          return true;
        }
      } else {
        TypeInfo valueType = args.get(0);
        if (valueType.getKind().basic ||
          valueType.getKind().json ||
          valueType.getKind() == ClassKind.ENUM ||
          isVertxGenInterface(valueType, false) ||
          (allowAnyJavaType && valueType.getKind() == ClassKind.OTHER) ||
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
              || kind.basic || kind.json || kind == ClassKind.DATA_OBJECT || kind == ClassKind.ENUM)) {
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

  private boolean isLegalHandlerType(TypeInfo type, boolean allowAnyJavaType) {
    if (type.getErased().getKind() == ClassKind.HANDLER) {
      TypeInfo eventType = ((ParameterizedTypeInfo) type).getArgs().get(0);
      if (isLegalCallbackValueType(eventType, allowAnyJavaType) || eventType.getKind() == ClassKind.THROWABLE) {
        return true;
      }
    }
    return false;
  }

  private boolean isLegalHandlerAsyncResultType(TypeInfo type, boolean allowAnyJavaType) {
    if (type.getErased().getKind() == ClassKind.HANDLER) {
      TypeInfo eventType = ((ParameterizedTypeInfo) type).getArgs().get(0);
      if (eventType.getErased().getKind() == ClassKind.ASYNC_RESULT && !eventType.isNullable()) {
        TypeInfo resultType = ((ParameterizedTypeInfo) eventType).getArgs().get(0);
        if (isLegalCallbackValueType(resultType, allowAnyJavaType)) {
          return true;
        }
      }
    }
    return false;
  }

  private boolean isLegalCallbackValueType(TypeInfo type, boolean allowAnyJavaType) {
    if (type.getKind() == ClassKind.VOID) {
      return true;
    }
    return isLegalNonCallableReturnType(type, allowAnyJavaType);
  }

  private void determineApiTypes() {
    importedTypes = collectedTypes.stream().
      map(ClassTypeInfo::getRaw).
      flatMap(Helper.instanceOf(ClassTypeInfo.class)).
      filter(t -> !t.getPackageName().equals(ifaceFQCN)).
      collect(Collectors.toSet());

    referencedTypes = collectedTypes.stream().
      map(ClassTypeInfo::getRaw).
      flatMap(Helper.instanceOf(ApiTypeInfo.class)).
      filter(t -> !t.equals(type.getRaw())).
      collect(Collectors.toSet());

    referencedDataObjectTypes = collectedTypes.stream().
      map(ClassTypeInfo::getRaw).
      flatMap(Helper.instanceOf(ClassTypeInfo.class)).
      filter(t -> t.getKind() == ClassKind.DATA_OBJECT).
      collect(Collectors.toSet());

    referencedEnumTypes = collectedTypes.stream().
      map(ClassTypeInfo::getRaw).
      flatMap(Helper.instanceOf(EnumTypeInfo.class)).
      filter(t -> t.getKind() == ClassKind.ENUM).
      collect(Collectors.toSet());
  }

  public boolean process() {
    if (!processed) {
      traverseType(modelElt);
      determineApiTypes();
      processTypeAnnotations();
      processed = true;
      return true;
    } else {
      return false;
    }
  }

  private void processTypeAnnotations() {
    annotations = elementUtils
      .getAllAnnotationMirrors(modelElt)
      .stream()
      .map(annotationValueInfoFactory::processAnnotation)
      .collect(Collectors.toList());
  }

  private void traverseType(Element elem) {
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
        if (doc != null)
          doc.getBlockTags().stream().filter(tag -> tag.getName().equals("deprecated")).findFirst().ifPresent(tag ->
            deprecatedDesc = new Text(Helper.normalizeWhitespaces(tag.getValue())).map(Token.tagMapper(elementUtils, typeUtils, modelElt))
          );
        deprecated = deprecated || deprecatedDesc != null;
        concrete = elem.getAnnotation(VertxGen.class) == null || elem.getAnnotation(VertxGen.class).concrete();

        this.defaultMethodCreator = new DefaultMethodCreator(env, concrete);
        this.companionMethodCreator = new CompanionMethodCreator(env, concrete);

        DeclaredType tm = (DeclaredType) elem.asType();
        List<? extends TypeMirror> typeArgs = tm.getTypeArguments();
        for (TypeMirror typeArg : typeArgs) {
          TypeVariable varTypeArg = (TypeVariable) typeArg;
          if (!isObjectBound(varTypeArg.getUpperBound())) {
            throw new GenException(elem, "Type variable bounds not supported " + varTypeArg.getUpperBound());
          }
        }
        List<? extends TypeMirror> st = typeUtils.directSupertypes(tm);
        for (TypeMirror tmSuper : st) {
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
        if (concreteSuperType != null && concreteSuperType.isParameterized()) {
          tm = (DeclaredType) modelElt.asType();
          ;
          st = typeUtils.directSupertypes(tm);
          for (TypeMirror tmSuper : st) {
            if (tmSuper.getKind() == TypeKind.DECLARED) {
              DeclaredType abc = (DeclaredType) tmSuper;
              TypeElement tt = (TypeElement) abc.asElement();
              if (tt.getQualifiedName().toString().equals(concreteSuperType.getRaw().getName())) {
                List<TypeInfo> list = new ArrayList<>();
                int size = tt.getTypeParameters().size();
                for (int i = 0; i < size; i++) {
                  TypeMirror q = abc.getTypeArguments().get(i);
                  TypeInfo ti = typeFactory.create(q);
                  list.add(ti);
                }
                superTypeArguments = list;
              }
            }
          }
        }
        break;
      }
    }


    Optional<TypeElement> companionOpt = CompanionHelper.getCompanion(elem);
    // Traverse nested elements that are not methods (like nested interfaces)
    for (Element enclosedElt : elem.getEnclosedElements()) {
      if (!isGenIgnore(enclosedElt)) {
        switch (enclosedElt.getKind()) {
          case METHOD:
          case FIELD:
            // Allowed
            break;
          case CLASS: {
            if (companionOpt.isPresent()) {
              break;
            }
          }
          default:
            throw new GenException(elem, "@VertxGen can only declare methods and not " + elem.asType().toString());
        }
      }
    }


    if (elem.getKind() == ElementKind.INTERFACE) {

      TypeMirror objectType = elementUtils.getTypeElement("java.lang.Object").asType();

      companionOpt.ifPresent(companion -> {
        elementUtils.getAllMembers(companion)
          .stream()
          .filter(elt -> !typeUtils.isSameType(elt.getEnclosingElement().asType(), objectType))
          .flatMap(Helper.FILTER_METHOD)
          .filter(elt -> !isGenIgnore(elt))
          .forEach(elt -> {
            boolean allowAnyJavaType = Helper.allowAnyJavaType(elt);
            Optional<MethodInfo> methodInfoOpt = companionMethodCreator.createMethod(companion, elt, allowAnyJavaType, collectedTypes, deprecatedDesc)
              .filter(methodInfo -> {
                // Check we don't hide another method, we don't check overrides but we are more
                // interested by situations like diamond inheritance of the same method, in this case
                // we see two methods with the same signature that don't override each other
                for (Map.Entry<ExecutableElement, MethodInfo> otherMethod : methods.entrySet()) {
                  if (otherMethod.getValue().getName().equals(elt.getSimpleName().toString())) {
                    ExecutableType t1 = (ExecutableType) otherMethod.getKey().asType();
                    ExecutableType t2 = (ExecutableType) elt.asType();
                    if (typeUtils.isSubsignature(t1, t2) && typeUtils.isSubsignature(t2, t1)) {
                      otherMethod.getValue().getOwnerTypes().addAll(methodInfo.getOwnerTypes());
                      return false;
                    }
                  }
                }
                return true;
              });
            methodInfoOpt.filter(methodInfo -> !methodInfo.isContainingAnyJavaType()).ifPresent(methodInfo -> {
              List<MethodInfo> methodsByName = methodMap.get(methodInfo.getName());
              if (methodsByName != null) {
                // Overloaded methods must have same return type
                for (MethodInfo method : methodsByName) {
                  if (!method.isContainingAnyJavaType() && !method.getReturnType().equals(methodInfo.getReturnType())) {
                    throw new GenException(this.modelElt, "Overloaded method " + methodInfo.getName() + " must have the same return type "
                      + method.getReturnType() + " != " + methodInfo.getReturnType());
                  }
                }
              } else {
                methodsByName = new ArrayList<>();
                methodMap.put(methodInfo.getName(), methodsByName);
                methodAnnotationsMap.put(methodInfo.getName(), methodInfo.getAnnotations());
              }
              methodsByName.add(methodInfo);
            });
            methodInfoOpt
              .filter(methodInfo -> {
                TypeElement declaringElt = (TypeElement) elt.getEnclosingElement();
                TypeInfo declaringType = typeFactory.create(declaringElt.asType());
                ExecutableType methodType = (ExecutableType) typeUtils.asMemberOf((DeclaredType) modelElt.asType(), elt);
                // Filter methods inherited from abstract ancestors
                if (!declaringElt.equals(modelElt) && declaringType.getKind() == ClassKind.API) {
                  ApiTypeInfo declaringApiType = (ApiTypeInfo) declaringType.getRaw();
                  if (declaringApiType.isConcrete()) {
                    return !typeUtils.isSameType(methodType, elt.asType());
                  }
                }
                return true;
              })
              .ifPresent(methodInfo -> {
                methodInfo.collectImports(collectedTypes);
                if (methodInfo.isContainingAnyJavaType()) {
                  anyJavaTypeMethods.put(elt, methodInfo);
                } else {
                  methods.put(elt, methodInfo);
                }
              });

          });
      });

      // Traverse fields
      elementUtils.getAllMembers((TypeElement) elem).stream().
        filter(elt -> !typeUtils.isSameType(elt.getEnclosingElement().asType(), objectType)).
        flatMap(Helper.FILTER_FIELD).
        filter(elt -> !isGenIgnore(elt)).
        forEach(elt -> {
          boolean allowAnyJavaType = Helper.allowAnyJavaType(elt);
          boolean isCompanionField = companionOpt.filter(companion -> elt.asType() == companion.asType()).isPresent();
          ConstantInfo cst = fieldMethod(elt, allowAnyJavaType || isCompanionField);
          if (cst != null) {
            cst.getType().collectImports(collectedTypes);
            constants.add(cst);
          }
        });


      // Traverse methods
      elementUtils.getAllMembers((TypeElement) elem).stream().
        filter(elt -> !typeUtils.isSameType(elt.getEnclosingElement().asType(), objectType)).
        flatMap(Helper.FILTER_METHOD).
        filter(elt -> !isGenIgnore(elt)).
        forEach(elt -> {
          boolean allowAnyJavaType = Helper.allowAnyJavaType(elt);
          Optional<MethodInfo> methodInfoOpt = defaultMethodCreator.createMethod(modelElt, elt, allowAnyJavaType, collectedTypes, deprecatedDesc)
            .filter(methodInfo -> {
              // Check we don't hide another method, we don't check overrides but we are more
              // interested by situations like diamond inheritance of the same method, in this case
              // we see two methods with the same signature that don't override each other
              for (Map.Entry<ExecutableElement, MethodInfo> otherMethod : methods.entrySet()) {
                if (otherMethod.getValue().getName().equals(elt.getSimpleName().toString())) {
                  ExecutableType t1 = (ExecutableType) otherMethod.getKey().asType();
                  ExecutableType t2 = (ExecutableType) elt.asType();
                  if (typeUtils.isSubsignature(t1, t2) && typeUtils.isSubsignature(t2, t1)) {
                    otherMethod.getValue().getOwnerTypes().addAll(methodInfo.getOwnerTypes());
                    return false;
                  }
                }
              }
              return true;
            });
          methodInfoOpt.filter(methodInfo -> !methodInfo.isContainingAnyJavaType()).ifPresent(methodInfo -> {
            List<MethodInfo> methodsByName = methodMap.get(methodInfo.getName());
            if (methodsByName != null) {
              // Overloaded methods must have same return type
              for (MethodInfo method : methodsByName) {
                if (!method.isContainingAnyJavaType() && !method.getReturnType().equals(methodInfo.getReturnType())) {
                  throw new GenException(this.modelElt, "Overloaded method " + methodInfo.getName() + " must have the same return type "
                    + method.getReturnType() + " != " + methodInfo.getReturnType());
                }
              }
            } else {
              methodsByName = new ArrayList<>();
              methodMap.put(methodInfo.getName(), methodsByName);
              methodAnnotationsMap.put(methodInfo.getName(), methodInfo.getAnnotations());
            }
            methodsByName.add(methodInfo);
          });
          methodInfoOpt
            .filter(methodInfo -> {
              TypeElement declaringElt = (TypeElement) elt.getEnclosingElement();
              TypeInfo declaringType = typeFactory.create(declaringElt.asType());
              ExecutableType methodType = (ExecutableType) typeUtils.asMemberOf((DeclaredType) modelElt.asType(), elt);
              // Filter methods inherited from abstract ancestors
              if (!declaringElt.equals(modelElt) && declaringType.getKind() == ClassKind.API) {
                ApiTypeInfo declaringApiType = (ApiTypeInfo) declaringType.getRaw();
                if (declaringApiType.isConcrete()) {
                  return !typeUtils.isSameType(methodType, elt.asType());
                }
              }
              return true;
            })
            .ifPresent(methodInfo -> {
              methodInfo.collectImports(collectedTypes);
              if (methodInfo.isContainingAnyJavaType()) {
                anyJavaTypeMethods.put(elt, methodInfo);
              } else {
                methods.put(elt, methodInfo);
              }
            });

        });

      // Sort method map
      sortMethodMap(methodMap);

      // Now check for overloaded methods
      for (List<MethodInfo> meths : methodMap.values()) {

        // Ambiguous
        try {
          MethodOverloadChecker.INSTANCE.checkAmbiguous(meths.stream());
        } catch (RuntimeException e) {
          throw new GenException(elem, e.getMessage());
        }

        // Cannot be both static and non static
        MethodInfo first = meths.get(0);
        for (MethodInfo method : meths) {
          if (method.isStaticMethod() != first.isStaticMethod()) {
            throw new GenException(elem, "Overloaded method " + method.getName() + " cannot be both static and instance");
          }
        }
      }
    }
  }

  private static boolean isGenIgnore(Element elt) {
    return elt.getAnnotation(GenIgnore.class) != null;
  }

  private ConstantInfo fieldMethod(VariableElement modelField, boolean allowAnyJavaType) {
    Set<Modifier> mods = modelField.getModifiers();
    if (!mods.contains(Modifier.PUBLIC)) {
      return null;
    }
    TypeInfo type = typeFactory.create(modelField.asType());
    checkConstantType(modelField, type, modelField.asType(), allowAnyJavaType);
    Doc doc = docFactory.createDoc(modelField);
    return new ConstantInfo(doc, modelField.getSimpleName().toString(), type);
  }

  private boolean isObjectBound(TypeMirror bound) {
    return bound.getKind() == TypeKind.DECLARED && bound.toString().equals(Object.class.getName());
  }

  /**
   * @return {@code true} if the class has a {@code @Deprecated} annotation
   */
  public boolean isDeprecated() {
    return deprecated;
  }

  /**
   * @return the description of deprecated
   */
  public Text getDeprecatedDesc() {
    return deprecatedDesc;
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
    vars.put("constants", getConstants());
    vars.put("referencedTypes", getReferencedTypes());
    vars.put("superTypes", getSuperTypes());
    vars.put("concreteSuperType", getConcreteSuperType());
    vars.put("abstractSuperTypes", getAbstractSuperTypes());
    vars.put("handlerType", getHandlerType());
    vars.put("methodsByName", getMethodMap());
    vars.put("classAnnotations", getAnnotations());
    vars.put("annotationsByMethodName", getMethodAnnotations());
    vars.put("referencedDataObjectTypes", getReferencedDataObjectTypes());
    vars.put("referencedEnumTypes", getReferencedEnumTypes());
    vars.put("typeParams", getTypeParams());
    vars.put("instanceMethods", getInstanceMethods());
    vars.put("staticMethods", getStaticMethods());
    vars.put("deprecated", isDeprecated());
    vars.put("deprecatedDesc", getDeprecatedDesc());
    return vars;
  }
}
