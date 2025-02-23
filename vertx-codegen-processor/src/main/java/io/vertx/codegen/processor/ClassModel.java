package io.vertx.codegen.processor;

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
import io.vertx.codegen.processor.doc.Doc;
import io.vertx.codegen.processor.type.*;
import io.vertx.codegen.processor.doc.Tag;
import io.vertx.codegen.processor.doc.Text;
import io.vertx.codegen.processor.doc.Token;

import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.*;
import javax.lang.model.type.*;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A processed source.
 *
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
public class ClassModel implements Model {

  public static final String VERTX_READ_STREAM = "io.vertx.core.streams.ReadStream";
  public static final String VERTX_WRITE_STREAM = "io.vertx.core.streams.WriteStream";
  public static final String VERTX_HANDLER = "io.vertx.core.Handler";
  public static final String JSON_OBJECT = "io.vertx.core.json.JsonObject";
  public static final String JSON_ARRAY = "io.vertx.core.json.JsonArray";
  public static final String ITERABLE = "java.lang.Iterable";
  public static final String ITERATOR = "java.util.Iterator";
  public static final String FUNCTION = "java.util.function.Function";
  public static final String SUPPLIER = "java.util.function.Supplier";

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
  protected Set<MethodInfo> futureMethods = new HashSet<>();
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
  protected TypeInfo handlerArg;
  protected TypeInfo readStreamArg;
  protected TypeInfo writeStreamArg;
  protected TypeInfo iterableArg;
  protected TypeInfo iteratorArg;
  protected TypeInfo[] functionArgs;
  protected TypeInfo supplierArg;
  // The methods, grouped by name
  protected Map<String, List<MethodInfo>> methodMap;
  protected Map<String, List<AnnotationValueInfo>> methodAnnotationsMap = new LinkedHashMap<>();
  protected List<AnnotationValueInfo> annotations;
  protected boolean deprecated;
  protected Text deprecatedDesc;

  public ClassModel(ProcessingEnvironment env, TypeMirrorFactory typeFactory, TypeElement modelElt) {
    this.elementUtils = env.getElementUtils();
    this.typeUtils = env.getTypeUtils();
    this.env = env;
    this.typeFactory = typeFactory;
    this.docFactory = new Doc.Factory(env.getMessager(), elementUtils, typeUtils, typeFactory, modelElt);
    this.messager = env.getMessager();
    this.modelElt = modelElt;
    this.annotationValueInfoFactory = new AnnotationValueInfoFactory(typeFactory);
    this.deprecated = modelElt.getAnnotation(Deprecated.class) != null;
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
        filter(TypeInfo::isDataObjectHolder).
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
    DeclaredType declaredType = (DeclaredType) elem.asType();

    switch (elem.getKind()) {
      case ENUM:
      case CLASS: {
        throw new GenException(elem, "@VertxGen can only be used with interfaces or enums in " + declaredType.toString());
      }
      case INTERFACE: {
        if (ifaceFQCN != null) {
          throw new GenException(elem, "Can only have one interface per file");
        }
        type = typeFactory.create(declaredType).getRaw();
        Helper.checkUnderModule(this, "@VertxGen");
        ifaceFQCN = declaredType.toString();
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
        DeclaredType tm = declaredType;
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
        if (concreteSuperType != null && concreteSuperType.isParameterized()) {
          tm = (DeclaredType) modelElt.asType();
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

    handlerArg = extractArg(VERTX_HANDLER, declaredType);
    readStreamArg = extractArg(VERTX_READ_STREAM, declaredType);
    writeStreamArg = extractArg(VERTX_WRITE_STREAM, declaredType);
    iterableArg = extractArg(ITERABLE, declaredType);
    iteratorArg = extractArg(ITERATOR, declaredType);
    functionArgs = extractArgs(FUNCTION, declaredType);
    supplierArg = extractArg(SUPPLIER, declaredType);

    // Traverse nested elements that are not methods (like nested interfaces)
    for (Element enclosedElt : elem.getEnclosedElements()) {
      if (!Helper.isGenIgnore(enclosedElt)) {
        switch (enclosedElt.getKind()) {
          case METHOD:
          case FIELD:
            // Allowed
            break;
          default:
            throw new GenException(elem, "@VertxGen can only declare methods and not " + declaredType.toString());
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
          ConstantInfo cst = fieldMethod(typeUtils, elt, allowAnyJavaType);
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
                boolean isAnyJavaType = TypeValidator.isAnyJavaType(meth);
                if (isAnyJavaType) {
                  anyJavaTypeMethods.put(elt, meth);
                } else {
                  methodAnnotationsMap.put(meth.getName(), elt.getAnnotationMirrors().stream().map(annotationValueInfoFactory::processAnnotation).collect(Collectors.toList()));
                  methods.put(elt, meth);
                }
              }
            }
          });

      // Validate return types
      Stream.concat(methods.entrySet().stream(), anyJavaTypeMethods.entrySet().stream()).forEach(entry -> {
        MethodInfo method = entry.getValue();
        TypeInfo returnType = method.getReturnType();

        // Valid return type
        // Only check the return type if not fluent, because generated code won't look it at anyway
        ExecutableElement methodElt = entry.getKey();
        if (!method.isFluent()) {
          // Only validate when it's not inherited
          if (method.isOwnedBy(type)) {
            checkReturnType(methodElt, returnType, anyJavaTypeMethods.containsKey(methodElt));
          }
        } else if (returnType.isNullable()) {
          throw new GenException(methodElt, "Fluent return type cannot be nullable");
        }
      });

      // Build method map
      methodMap = methods
        .values()
        .stream()
        .collect(Collectors.groupingBy(MethodInfo::getName));
      sortMethodMap(methodMap);

      // Now check for overloaded methods
      for (List<MethodInfo> meths: methodMap.values()) {

        // checkMethod hook validation
        meths.forEach(this::checkMethod);

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

  // Service proxy override
  protected void checkParamType(ExecutableElement elem, TypeInfo typeInfo, int pos, int numParams, boolean allowAnyJavaType) {
    TypeValidator.validateParamType(elem, typeInfo, allowAnyJavaType);
  }

  // Service proxy override
  protected void checkReturnType(ExecutableElement elem, TypeInfo type, boolean allowAnyJavaType) {
    TypeValidator.validateReturnType(elem, type, allowAnyJavaType);
  }

  private TypeInfo extractArg(String subType, DeclaredType declaredType) {
    TypeInfo[] typeInfos = extractArgs(subType, declaredType);
    return typeInfos != null && typeInfos.length > 0 ? typeInfos[0] : null;
  }

  private TypeInfo[] extractArgs(String subType, DeclaredType declaredType) {
    TypeElement parameterizedElt = elementUtils.getTypeElement(subType);
    TypeMirror parameterizedType = parameterizedElt.asType();
    TypeMirror rawType = typeUtils.erasure(parameterizedType);
    if (typeUtils.isSubtype(declaredType, rawType)) {
      List<? extends TypeParameterElement> typeParameters = parameterizedElt.getTypeParameters();
      TypeInfo[] typeInfos = new TypeInfo[typeParameters.size()];
      for (int i = 0; i < typeParameters.size(); i++) {
        TypeMirror resolved = Helper.resolveTypeParameter(typeUtils, declaredType, typeParameters.get(i));
        typeInfos[i] = typeFactory.create(resolved);
      }
      return typeInfos;
    }
    return null;
  }

  private ConstantInfo fieldMethod(Types typeUtils, VariableElement modelField, boolean allowAnyJavaType) {
    Set<Modifier> mods = modelField.getModifiers();
    if (!mods.contains(Modifier.PUBLIC)) {
      return null;
    }
    TypeInfo type = typeFactory.create(modelField.asType());
    TypeValidator.validateConstantType(typeUtils, modelField, type, modelField.asType(),allowAnyJavaType);
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
    ExecutableType resolvedMethodType = (ExecutableType) typeUtils.asMemberOf((DeclaredType) modelElt.asType(), modelMethod);

    if (!declaringElt.equals(modelElt) && (declaringType.getKind() != ClassKind.API && declaringType.getKind() != ClassKind.HANDLER)) {
      return null;
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

    // Add this type too
    ownerTypes.add(type);

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
        env.getMessager().printMessage(Diagnostic.Kind.WARNING, msg);
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

    boolean methodDeprecated = modelMethod.getAnnotation(Deprecated.class) != null || deprecatedDesc != null;
    boolean methodOverride = modelMethod.getAnnotation(Override.class) != null;
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
      methodDeprecatedDesc,
      methodOverride);

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

    // Valid param types
    if (ownerTypes.size() == 1) {
      // Only validate when it's not inherited
      List<ParamInfo> p = methodInfo.getParams();
      for (int i = 0;i < p.size();i++) {
        checkParamType(modelMethod, p.get(i).getType(), i, p.size(), allowAnyJavaType);
      }
    }

    return methodInfo;
  }

  // This is a hook to allow a specific type of method to be created
  protected MethodInfo createMethodInfo(Set<ClassTypeInfo> ownerTypes, String methodName, String comment, Doc doc, TypeInfo returnType,
                                        Text returnDescription,
                                        boolean isFluent, boolean isCacheReturn, List<ParamInfo> mParams,
                                        ExecutableElement methodElt, boolean isStatic, boolean isDefault, ArrayList<TypeParamInfo.Method> typeParams,
                                        TypeElement declaringElt, boolean methodDeprecated, Text methodDeprecatedDesc, boolean methodOverride) {
    return new MethodInfo(ownerTypes, methodName, returnType, returnDescription,
      isFluent, isCacheReturn, mParams, comment, doc, isStatic, isDefault, typeParams, methodDeprecated, methodDeprecatedDesc, methodOverride);
  }

  // This is a hook to allow different model implementations to check methods in different ways
  protected void checkMethod(MethodInfo methodInfo) {
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
    vars.put("handlerType", getHandlerArg());
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

  public boolean isHandler() {
    return handlerArg != null;
  }

  public TypeInfo getHandlerArg() {
    return handlerArg;
  }

  public boolean isReadStream() {
    return readStreamArg != null;
  }

  public TypeInfo getReadStreamArg() {
    return readStreamArg;
  }

  public boolean isWriteStream() {
    return writeStreamArg != null;
  }

  public TypeInfo getWriteStreamArg() {
    return writeStreamArg;
  }

  public boolean isIterable() {
    return iterableArg != null;
  }

  public TypeInfo getIterableArg() {
    return iterableArg;
  }

  public boolean isIterator() {
    return iteratorArg != null;
  }

  public TypeInfo getIteratorArg() {
    return iteratorArg;
  }

  public boolean isFunction() {
    return functionArgs != null;
  }

  public TypeInfo[] getFunctionArgs() {
    return functionArgs;
  }

  public boolean isSupplier() {
    return supplierArg != null;
  }

  public TypeInfo getSupplierArg() {
    return supplierArg;
  }
}
