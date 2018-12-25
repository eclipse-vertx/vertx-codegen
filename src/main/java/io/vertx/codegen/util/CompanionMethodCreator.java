package io.vertx.codegen.util;

import io.vertx.codegen.GenException;
import io.vertx.codegen.Helper;
import io.vertx.codegen.MethodInfo;
import io.vertx.codegen.ParamInfo;
import io.vertx.codegen.TypeParamInfo;
import io.vertx.codegen.annotations.CacheReturn;
import io.vertx.codegen.annotations.Fluent;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.codegen.doc.Doc;
import io.vertx.codegen.doc.Tag;
import io.vertx.codegen.doc.Text;
import io.vertx.codegen.doc.Token;
import io.vertx.codegen.service.method.checker.Checker;
import io.vertx.codegen.service.method.checker.ParamTypeChecker;
import io.vertx.codegen.service.method.checker.ReturnTypeChecker;
import io.vertx.codegen.type.AnnotationValueInfo;
import io.vertx.codegen.type.AnnotationValueInfoFactory;
import io.vertx.codegen.type.ClassTypeInfo;
import io.vertx.codegen.type.TypeInfo;
import io.vertx.codegen.type.TypeMirrorFactory;
import io.vertx.codegen.type.TypeUse;

import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.ExecutableType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static io.vertx.codegen.util.ModelUtils.isObjectBound;

/**
 * @author Евгений Уткин (evgeny.utkin@mediascope.net)
 */
public class CompanionMethodCreator implements MethodCreator {

  private static final Logger logger = Logger.getLogger(CompanionMethodCreator.class.getName());

  protected final ProcessingEnvironment env;
  protected final AnnotationValueInfoFactory annotationValueInfoFactory;
  protected final Messager messager;
  protected final TypeMirrorFactory typeFactory;
  protected final Elements elementUtils;
  protected final Types typeUtils;
  protected boolean deprecated;
  protected final boolean concrete;

  protected final Checker returnTypeChecker;
  protected final Checker paramTypeChecker;

  public CompanionMethodCreator(ProcessingEnvironment env, boolean concrete) {
    this.elementUtils = env.getElementUtils();
    this.typeUtils = env.getTypeUtils();
    this.env = env;
    this.concrete = concrete;
    this.typeFactory = new TypeMirrorFactory(elementUtils, typeUtils);
    this.messager = env.getMessager();
    this.annotationValueInfoFactory = new AnnotationValueInfoFactory(typeFactory);
    this.returnTypeChecker = ReturnTypeChecker.getInstance(elementUtils);
    this.paramTypeChecker = ParamTypeChecker.getInstance(elementUtils);

  }

  @Override
  public Optional<MethodInfo> createMethod(TypeElement companion, ExecutableElement modelMethod, boolean allowAnyJavaType, Set<ClassTypeInfo> collectedTypes, Text deprecatedDesc) {

    Doc.Factory docFactory = new Doc.Factory(env.getMessager(), elementUtils, typeUtils, typeFactory, companion);

    Set<Modifier> mods = modelMethod.getModifiers();
    if (!mods.contains(Modifier.PUBLIC)) {
      return Optional.empty();
    }

    ClassTypeInfo type = typeFactory.create(companion.getEnclosingElement().asType()).getRaw();

    boolean isStatic = true;

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

    List<DeclaredType> ancestors = new ArrayList<>(Helper.resolveAncestorTypes(companion, true, true));

    // Sort to have super types the last, etc..
    // solve some problem with diamond inheritance order that can show up in type use
    ancestors.sort((o1, o2) -> {
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
          filter(meth -> elementUtils.overrides(modelMethod, meth, companion)).
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
        returnDesc = new Text(Helper.normalizeWhitespaces(returnTag.get().getValue())).map(Token.tagMapper(elementUtils, typeUtils, companion));
      }
      Optional<Tag> methodDeprecatedTag = doc.
        getBlockTags().
        stream().
        filter(tag -> tag.getName().equals("deprecated")).
        findFirst();
      if (methodDeprecatedTag.isPresent()) {
        methodDeprecatedDesc = new Text(Helper.normalizeWhitespaces(methodDeprecatedTag.get().getValue())).map(Token.tagMapper(elementUtils, typeUtils, companion));
      }
    }

    //
    List<ParamInfo> mParams = getCompanionMethodParams(companion, modelMethods, modelMethod, paramDescs, allowAnyJavaType);

    //
    AnnotationMirror fluentAnnotation = Helper.resolveMethodAnnotation(Fluent.class, elementUtils, typeUtils, companion, modelMethod);
//    boolean isFluent = fluentAnnotation != null;
//    if (isFluent) {
//      if (!typeUtils.isSameType(companion.asType(), modelElt.asType())) {
//        String msg = "Interface " + modelElt + " does not redeclare the @Fluent return type " +
//          " of method " + modelMethod + " declared by " + companion;
//        messager.printMessage(Diagnostic.Kind.WARNING, msg, modelElt, fluentAnnotation);
//        logger.warning(msg);
//      } else {
//        TypeMirror fluentType = modelMethod.getReturnType();
//        if (!typeUtils.isAssignable(fluentType, modelElt.asType())) {
//          throw new GenException(modelMethod, "Methods marked with @Fluent must have a return type that extends the type");
//        }
//      }
//    }

    //
    TypeUse returnTypeUse = TypeUse.createReturnTypeUse(env, modelMethods.toArray(new ExecutableElement[0]));

    ExecutableType methodType = (ExecutableType) typeUtils.asMemberOf((DeclaredType) companion.asType(), modelMethod);
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
    if (!returnTypeChecker.check(modelMethod, returnType, allowAnyJavaType)) {
      throw new GenException(modelMethod, "type " + returnType + " is not legal for use for a return type in code generation");
    }


    boolean methodDeprecated = modelMethod.getAnnotation(Deprecated.class) != null || deprecatedDesc != null;

    MethodInfo methodInfo = createMethodInfo(
      ownerTypes,
      methodName,
      comment,
      doc,
      returnType,
      returnDesc,
      false,
      isCacheReturn,
      mParams,
      modelMethod,
      isStatic,
      false,
      typeParams,
      companion,
      methodDeprecated,
      methodDeprecatedDesc).setCompanion(companion.getSimpleName());

    List<AnnotationValueInfo> annotations = modelMethod.getAnnotationMirrors()
      .stream()
      .map(annotationValueInfoFactory::processAnnotation)
      .collect(Collectors.toList());

    methodInfo.setAnnotations(annotations);

    return Optional.of(methodInfo);
  }

  private List<ParamInfo> getCompanionMethodParams(TypeElement companion, List<ExecutableElement> modelMethods,
                                                   ExecutableElement methodElt,
                                                   Map<String, String> descs,
                                                   boolean allowAnyJavaType) {
    ExecutableType methodType = (ExecutableType) typeUtils.asMemberOf((DeclaredType) companion.asType(), methodElt);
    ExecutableType methodType2 = (ExecutableType) methodElt.asType();
    List<? extends VariableElement> params = methodElt.getParameters();
    List<ParamInfo> mParams = new ArrayList<>();
    for (int i = 0; i < params.size(); i++) {
      VariableElement param = params.get(i);
      TypeMirror type = methodType.getParameterTypes().get(i);
      TypeInfo typeInfo;
      TypeUse typeUse = TypeUse.createParamTypeUse(env, modelMethods.toArray(new ExecutableElement[0]), i);
      try {
        typeInfo = typeFactory.create(typeUse, type);
      } catch (Exception e) {
        GenException ex = new GenException(param, e.getMessage());
        ex.setStackTrace(e.getStackTrace());
        throw ex;
      }
      if (!paramTypeChecker.check(methodElt, typeInfo, allowAnyJavaType)) {
        throw new GenException(methodElt, "type " + typeInfo + " is not legal for use for a parameter in code generation");
      }
      String name = param.getSimpleName().toString();
      String desc = descs.get(name);
      Text text = desc != null ? new Text(desc).map(Token.tagMapper(elementUtils, typeUtils, companion)) : null;
      TypeInfo unresolvedTypeInfo;
      try {
        unresolvedTypeInfo = typeFactory.create(typeUse, methodType2.getParameterTypes().get(i));
      } catch (Exception e) {
        throw new GenException(param, e.getMessage());
      }
      ParamInfo mParam = new ParamInfo(i, name, text, typeInfo, unresolvedTypeInfo);
      mParams.add(mParam);
    }
    return mParams;
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
}
