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
  protected Set<DataObjectTypeInfo> referencedDataObjectTypes = new HashSet<>();
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

  public ClassModel(ProcessingEnvironment env, TypeElement modelElt) {
    this.elementUtils = env.getElementUtils();
    this.typeUtils = env.getTypeUtils();
    this.env = env;
    this.typeFactory = new TypeMirrorFactory(elementUtils, typeUtils, elementUtils.getPackageOf(modelElt));
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
  public Set<DataObjectTypeInfo> getReferencedDataObjectTypes() {
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
    return (type.getKind() == ClassKind.API) ? ((ApiTypeInfo)type).getHandlerArg() : null;
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
    for (List<MethodInfo> list: map.values()) {
      list.sort(Comparator.comparingInt(meth -> meth.getParams().size()));
    }
  }

  protected void checkSuperType(Element elt, TypeInfo type) {
    if (type.getKind() == ClassKind.API) {
      if (!isLegalVertxGenInterface(type, true)) {
        throw new GenException(elt, "type " + type + " is not legal for use for super type in code generation");
      }
    }
  }

  protected void checkParamType(ExecutableElement elem, TypeMirror type, TypeInfo typeInfo, int pos, int numParams, boolean allowAnyJavaType) {
    if (isLegalNonCallableParam(typeInfo, allowAnyJavaType)) {
      return;
    }
    if (isLegalClassTypeParam(elem, typeInfo)) {
      return;
    }
    if (isLegalHandlerType(typeInfo, allowAnyJavaType)) {
      return;
    }
    if (isLegalHandlerAsyncResultType(typeInfo, allowAnyJavaType)) {
      return;
    }
    if (isLegalFunctionType(typeInfo, allowAnyJavaType)) {
      return;
    }
    throw new GenException(elem, "type " + typeInfo + " is not legal for use for a parameter in code generation");
  }

  protected void checkReturnType(ExecutableElement elem, TypeInfo type, TypeMirror typeMirror, boolean allowAnyJavaType) {
    if (type.isVoid()) {
      return;
    }
    if (isLegalNonCallableReturnType(type, allowAnyJavaType)) {
      return;
    }
    if (isLegalHandlerType(type, allowAnyJavaType)) {
      return;
    }
    if (isLegalHandlerAsyncResultType(type, allowAnyJavaType)) {
      return;
    }
    throw new GenException(elem, "type " + type + " is not legal for use for a return type in code generation");
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
    if (type.isVariable()) {
      return true;
    }
    if (type.getKind() == ClassKind.OBJECT) {
      return true;
    }
    if (isLegalVertxGenInterface(type, true)) {
      return true;
    }
    if (allowAnyJavaType && type.getKind() == ClassKind.OTHER) {
      return true;
    }
    if (isLegalContainer(type, allowAnyJavaType)) {
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
    if (type.isVariable()) {
      return true;
    }
    if (typeInfo.getKind() == ClassKind.OBJECT) {
      return true;
    }
    if (isLegalVertxGenInterface(typeInfo, true)) {
      return true;
    }
    if (allowAnyJavaType && typeInfo.getKind() == ClassKind.OTHER) {
      return true;
    }
    if (isLegalContainer(typeInfo, allowAnyJavaType)) {
      return true;
    }
    return false;
  }

  private boolean isLegalDataObjectTypeParam(TypeInfo type) {
    return type.getKind() == ClassKind.DATA_OBJECT && ((DataObjectTypeInfo) type).hasJsonDecoder();
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
    return type.getKind() == ClassKind.DATA_OBJECT && ((DataObjectTypeInfo) type).hasJsonEncoder();
  }

  protected boolean isLegalContainer(TypeInfo type, boolean allowAnyJavaType) {
    if (rawTypeIs(type, List.class, Set.class, Map.class)) {
      ParameterizedTypeInfo parameterizedType = (ParameterizedTypeInfo) type;
      TypeInfo argument = parameterizedType.getArgs().get(0);
      if (type.getKind() != ClassKind.MAP) {
        return isLegalContainerComponent(argument, allowAnyJavaType);
      } else if (argument.getKind() == ClassKind.STRING) { // Only allow Map's with String's for keys
        return isLegalContainerComponent(parameterizedType.getArgs().get(1), allowAnyJavaType);
      }
    }
    return false;
  }

  private boolean isLegalContainerComponent(TypeInfo arg, boolean allowAnyJavaType) {
    ClassKind argumentKind = arg.getKind();
    return argumentKind.basic
      || argumentKind.json
      || isLegalVertxGenInterface(arg, false)
      || argumentKind == ClassKind.OBJECT
      || isLegalDataObjectTypeParam(arg)
      || arg.getKind() == ClassKind.ENUM
      || (allowAnyJavaType && argumentKind == ClassKind.OTHER);
  }

  private boolean isLegalVertxGenTypeArgument(TypeInfo arg) {
    ClassKind kind = arg.getKind();
    return kind == ClassKind.API || arg.isVariable() || kind == ClassKind.VOID
      || kind.basic || kind.json || kind == ClassKind.DATA_OBJECT || kind == ClassKind.ENUM || kind == ClassKind.OTHER;
  }

  private boolean isLegalVertxGenInterface(TypeInfo type, boolean allowParameterized) {
    if (type.getKind() == ClassKind.API) {
      if (type.isParameterized()) {
        ParameterizedTypeInfo parameterized = (ParameterizedTypeInfo) type;
        return allowParameterized &&
          parameterized
            .getArgs()
            .stream()
            .noneMatch(arg -> !isLegalVertxGenTypeArgument(arg) || arg.isNullable());
      } else {
        return true;
      }
    }
    return false;
  }

  private boolean isLegalFunctionType(TypeInfo typeInfo, boolean allowAnyJavaType) {
    if (typeInfo.getErased().getKind() == ClassKind.FUNCTION) {
      TypeInfo paramType = ((ParameterizedTypeInfo) typeInfo).getArgs().get(0);
      if (isLegalCallbackValueType(paramType, allowAnyJavaType) || paramType.getKind() == ClassKind.THROWABLE) {
        TypeInfo returnType = ((ParameterizedTypeInfo) typeInfo).getArgs().get(1);
        return isLegalNonCallableParam(returnType, allowAnyJavaType);
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
        flatMap(Helper.instanceOf(DataObjectTypeInfo.class)).
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
            checkSuperType(modelElt, superTypeInfo);
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
          tm = (DeclaredType) modelElt.asType();;
          st = typeUtils.directSupertypes(tm);
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
                superTypeArguments = list;
              }
            }
          }
        }
        break;
      }
    }

    // Traverse nested elements that are not methods (like nested interfaces)
    for (Element enclosedElt : elem.getEnclosedElements()) {
      if (!Helper.isGenIgnore(enclosedElt)) {
        switch (enclosedElt.getKind()) {
          case METHOD:
          case FIELD:
            // Allowed
            break;
          default:
            throw new GenException(elem, "@VertxGen can only declare methods and not " + elem.asType().toString());
        }
      }
    }

    if (elem.getKind() == ElementKind.INTERFACE) {

      TypeMirror objectType = elementUtils.getTypeElement("java.lang.Object").asType();

      // Traverse fields
      elementUtils.getAllMembers((TypeElement) elem).stream().
        filter(elt -> !typeUtils.isSameType(elt.getEnclosingElement().asType(), objectType)).
        flatMap(Helper.FILTER_FIELD).
        forEach(elt -> {
          GenIgnore genIgnore = elt.getAnnotation(GenIgnore.class);
          boolean allowAnyJavaType;
          if (genIgnore != null) {
            if (!Arrays.asList(genIgnore.value()).contains(GenIgnore.PERMITTED_TYPE)) {
              // Regular ignore
              return;
            }
            allowAnyJavaType = true;
          } else {
            allowAnyJavaType = false;
          }
          ConstantInfo cst = fieldMethod(elt, allowAnyJavaType);
          if (cst != null) {
            cst.getType().collectImports(collectedTypes);
            constants.add(cst);
          }
        });


      // Traverse methods
      elementUtils.getAllMembers((TypeElement) elem).stream().
          filter(elt -> !typeUtils.isSameType(elt.getEnclosingElement().asType(), objectType)).
          flatMap(Helper.FILTER_METHOD).
          forEach(elt -> {
            GenIgnore genIgnore = elt.getAnnotation(GenIgnore.class);
            boolean allowAnyJavaType;
            if (genIgnore != null) {
              if (!Arrays.asList(genIgnore.value()).contains(GenIgnore.PERMITTED_TYPE)) {
                // Regular ignore
                return;
              }
              allowAnyJavaType = true;
            } else {
              allowAnyJavaType = false;
            }
            MethodInfo meth = createMethod(elt, allowAnyJavaType);
            if (meth != null) {
              meth.collectImports(collectedTypes);
              if (allowAnyJavaType) {
                anyJavaTypeMethods.put(elt, meth);
              } else {
                methods.put(elt, meth);
              }
            }
          });

      // Sort method map
      sortMethodMap(methodMap);

      // Now check for overloaded methods
      for (List<MethodInfo> meths: methodMap.values()) {

        // Ambiguous
        try {
          MethodOverloadChecker.INSTANCE.checkAmbiguous(meths.stream().filter(m -> !anyJavaTypeMethods.containsValue(m)));
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

  private ConstantInfo fieldMethod(VariableElement modelField, boolean allowAnyJavaType) {
    Set<Modifier> mods = modelField.getModifiers();
    if (!mods.contains(Modifier.PUBLIC)) {
      return null;
    }
    TypeInfo type = typeFactory.create(modelField.asType());
    checkConstantType(modelField, type, modelField.asType(),allowAnyJavaType);
    Doc doc = docFactory.createDoc(modelField);
    return new ConstantInfo(doc, modelField.getSimpleName().toString(), type);
  }

  private MethodInfo createMethod(ExecutableElement modelMethod, boolean allowAnyJavaType) {
    Set<Modifier> mods = modelMethod.getModifiers();
    if (!mods.contains(Modifier.PUBLIC)) {
      return null;
    }

    TypeElement declaringElt = (TypeElement) modelMethod.getEnclosingElement();
    TypeInfo declaringType = typeFactory.create(declaringElt.asType());

    if (!declaringElt.equals(modelElt) && (declaringType.getKind() != ClassKind.API && declaringType.getKind() != ClassKind.HANDLER)) {
      return null;
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
    List<ExecutableElement> modelMethods = new ArrayList<>();
    modelMethods.add(modelMethod);

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
              modelMethods.add(overridenMethodElt);
              ownerTypes.add(typeFactory.create((DeclaredType) ancestorElt.asType()).getRaw());
            });
      }
    }

    //
    Map<String, String> paramDescs = new HashMap<>();
    String comment = elementUtils.getDocComment(modelMethod);
    Doc doc = docFactory.createDoc(modelMethod);
    Text returnDesc = null;
    Text methodDeprecatedDesc = null;
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
      Optional<Tag> methodDeprecatedTag = doc.
          getBlockTags().
          stream().
          filter(tag -> tag.getName().equals("deprecated")).
          findFirst();
      if (methodDeprecatedTag.isPresent()) {
        methodDeprecatedDesc = new Text(Helper.normalizeWhitespaces(methodDeprecatedTag.get().getValue())).map(Token.tagMapper(elementUtils, typeUtils, modelElt));
      }
    }

    //
    List<ParamInfo> mParams = new ArrayList<>();
    ExecutableType resolvedMethodType = (ExecutableType) typeUtils.asMemberOf((DeclaredType) modelElt.asType(), modelMethod);
    ExecutableType methodType = (ExecutableType) modelMethod.asType();
    List<? extends VariableElement> params = modelMethod.getParameters();
    for (int i = 0; i < params.size();i++) {
      VariableElement param = params.get(i);
      TypeMirror typeMirror = resolvedMethodType.getParameterTypes().get(i);
      TypeInfo typeInfo;
      TypeUse typeUse = TypeUse.createParamTypeUse(env, modelMethods.<ExecutableElement>toArray(new ExecutableElement[0]), i);
      try {
        typeInfo = typeFactory.create(typeUse, typeMirror);
      } catch (Exception e) {
        GenException ex = new GenException(param, e.getMessage());
        ex.setStackTrace(e.getStackTrace());
        throw ex;
      }
      // Only validate when it's not inherited
      if (ownerTypes.size() == 1) {
        checkParamType(modelMethod, typeMirror, typeInfo, i, params.size(), allowAnyJavaType);
      }
      String name = param.getSimpleName().toString();
      String desc = paramDescs.get(name);
      Text text = desc != null ? new Text(desc).map(Token.tagMapper(elementUtils, typeUtils, modelElt)) : null;
      TypeInfo unresolvedTypeInfo;
      try {
        unresolvedTypeInfo = typeFactory.create(typeUse, methodType.getParameterTypes().get(i));
      } catch (Exception e) {
        throw new GenException(param, e.getMessage());
      }
      ParamInfo mParam = new ParamInfo(i, name, text, typeInfo, unresolvedTypeInfo);
      mParams.add(mParam);
    }

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
    TypeUse returnTypeUse = TypeUse.createReturnTypeUse(env,  modelMethods.toArray(new ExecutableElement[modelMethods.size()]));
    TypeInfo returnType;
    try {
      returnType = typeFactory.create(returnTypeUse, resolvedMethodType.getReturnType());
    } catch (Exception e) {
      GenException genEx = new GenException(modelMethod, e.getMessage());
      genEx.initCause(e);
      throw genEx;
    }
    returnType.collectImports(collectedTypes);
    if (isCacheReturn && returnType.isVoid()) {
      throw new GenException(modelMethod, "void method can't be marked with @CacheReturn");
    }

    // Only check the return type if not fluent, because generated code won't look it at anyway
    if (!isFluent) {
      // Only validate when it's not inherited
      if (ancestors.isEmpty()) {
        checkReturnType(modelMethod, returnType, resolvedMethodType.getReturnType(), allowAnyJavaType);
      }
    } else if (returnType.isNullable()) {
      throw new GenException(modelMethod, "Fluent return type cannot be nullable");
    }

    boolean methodDeprecated = modelMethod.getAnnotation(Deprecated.class) != null || deprecatedDesc != null;
    String methodName = modelMethod.getSimpleName().toString();

    MethodInfo methodInfo = createMethodInfo(
      ownerTypes,
      methodName,
      comment,
      doc,
      returnType,
      returnDesc,
      isFluent,
      isCacheReturn,
      mParams,
      modelMethod,
      isStatic,
      isDefault,
      typeParams,
      declaringElt,
      methodDeprecated,
      methodDeprecatedDesc);

    // Check we don't hide another method, we don't check overrides but we are more
    // interested by situations like diamond inheritance of the same method, in this case
    // we see two methods with the same signature that don't override each other
    for (Map.Entry<ExecutableElement, MethodInfo> otherMethod : methods.entrySet()) {
      if (otherMethod.getValue().getName().equals(modelMethod.getSimpleName().toString())) {
        ExecutableType t1 = (ExecutableType) otherMethod.getKey().asType();
        ExecutableType t2 = (ExecutableType) modelMethod.asType();
        if (typeUtils.isSubsignature(t1, t2) && typeUtils.isSubsignature(t2, t1)) {
          otherMethod.getValue().getOwnerTypes().addAll(methodInfo.getOwnerTypes());
          return null;
        }
      }
    }

    // Add the method to the method map (it's a bit ugly but useful for JS and Ruby)
    if (!allowAnyJavaType) {
      checkMethod(methodInfo);
      List<MethodInfo> methodsByName = methodMap.get(methodInfo.getName());
      if (methodsByName == null) {
        methodsByName = new ArrayList<>();
        methodMap.put(methodInfo.getName(), methodsByName);
        methodAnnotationsMap.put(methodInfo.getName(), modelMethod.getAnnotationMirrors().stream().map(annotationValueInfoFactory::processAnnotation).collect(Collectors.toList()));
      }
      methodsByName.add(methodInfo);
    }

    // Filter methods inherited from abstract ancestors
    if (!declaringElt.equals(modelElt) && declaringType.getKind() == ClassKind.API) {
      ApiTypeInfo declaringApiType = (ApiTypeInfo) declaringType.getRaw();
      if (declaringApiType.isConcrete()) {
        if (typeUtils.isSameType(resolvedMethodType, modelMethod.asType())) {
          return null;
        }
      }
    }

    return methodInfo;
  }

  // This is a hook to allow a specific type of method to be created
  protected MethodInfo createMethodInfo(Set<ClassTypeInfo> ownerTypes, String methodName, String comment, Doc doc, TypeInfo returnType,
                                        Text returnDescription,
                                        boolean isFluent, boolean isCacheReturn, List<ParamInfo> mParams,
                                        ExecutableElement methodElt, boolean isStatic, boolean isDefault, ArrayList<TypeParamInfo.Method> typeParams,
                                        TypeElement declaringElt, boolean methodDeprecated, Text methodDeprecatedDesc) {
    return new MethodInfo(ownerTypes, methodName, returnType, returnDescription,
      isFluent, isCacheReturn, mParams, comment, doc, isStatic, isDefault, typeParams, methodDeprecated, methodDeprecatedDesc);
  }

  // This is a hook to allow different model implementations to check methods in different ways
  protected void checkMethod(MethodInfo methodInfo) {
    List<MethodInfo> methodsByName = methodMap.get(methodInfo.getName());
    if (methodsByName != null) {
      // Overloaded methods must have same return type
      for (MethodInfo meth: methodsByName) {
        if (!meth.isContainingAnyJavaType() && !meth.getReturnType().equals(methodInfo.getReturnType())) {
          throw new GenException(this.modelElt, "Overloaded method " + methodInfo.getName() + " must have the same return type "
            + meth.getReturnType() + " != " + methodInfo.getReturnType());
        }
      }
    }
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
