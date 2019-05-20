package io.vertx.codegen.type;

import io.vertx.codegen.ClassModel;
import io.vertx.codegen.Helper;
import io.vertx.codegen.ModuleInfo;
import io.vertx.codegen.TypeParamInfo;
import io.vertx.codegen.annotations.DataObject;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.codegen.type.ApiTypeInfo.ApiTypeArgInfo;

import javax.lang.model.element.*;
import javax.lang.model.type.*;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Type info factory based on <i>javax.lang.model</i> and type mirrors.
 *
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class TypeMirrorFactory {

  final Elements elementUtils;
  final Types typeUtils;

  public TypeMirrorFactory(Elements elementUtils, Types typeUtils) {
    this.elementUtils = elementUtils;
    this.typeUtils = typeUtils;
  }

  public TypeInfo create(TypeMirror type) {
    return create(null, type);
  }

  public TypeInfo create(TypeUse use, TypeMirror type) {
    switch (type.getKind()) {
      case VOID:
        return VoidTypeInfo.INSTANCE;
      case ERROR:
      case DECLARED:
        return create(use, (DeclaredType) type);
      case DOUBLE:
      case LONG:
      case FLOAT:
      case CHAR:
      case BYTE:
      case SHORT:
      case BOOLEAN:
      case INT:
        if (use != null && use.isNullable()) {
          throw new IllegalArgumentException("Primitive types cannot be annotated with @Nullable");
        }
        return PrimitiveTypeInfo.PRIMITIVES.get(type.getKind().name().toLowerCase());
      case TYPEVAR:
        return create(use, (TypeVariable) type);
      case ARRAY:
        return create(use, (ArrayType) type);
      default:
        throw new IllegalArgumentException("Illegal type " + type + " of kind " + type.getKind());
    }
  }

  public TypeInfo create(DeclaredType type) {
    return create(null, type);
  }

  public TypeInfo create(TypeUse use, DeclaredType type) {
    return create(use, type, true);
  }

  public TypeInfo create(TypeUse use, DeclaredType type, boolean checkTypeArgs) {
    boolean nullable = use != null && use.isNullable();
    TypeElement elt = (TypeElement) type.asElement();
    PackageElement pkgElt = elementUtils.getPackageOf(elt);
    ModuleInfo module = ModuleInfo.resolve(elementUtils, pkgElt);
    String fqcn = elt.getQualifiedName().toString();
    boolean proxyGen = elt.getAnnotation(ProxyGen.class) != null;
    if (elt.getKind() == ElementKind.ENUM) {
      ArrayList<String> values = new ArrayList<>();
      for (Element enclosedElt : elt.getEnclosedElements()) {
        if (enclosedElt.getKind() == ElementKind.ENUM_CONSTANT) {
          values.add(enclosedElt.getSimpleName().toString());
        }
      }
      boolean gen = elt.getAnnotation(VertxGen.class) != null;
      return new EnumTypeInfo(fqcn, gen, values, module, nullable);
    } else {
      ClassKind kind = ClassKind.getKind(fqcn, elt.getAnnotation(DataObject.class) != null, elt.getAnnotation(VertxGen.class) != null);
      List<? extends TypeMirror> typeArgs = type.getTypeArguments();
      if (checkTypeArgs && typeArgs.size() > 0) {
        List<TypeInfo> typeArguments;
        typeArguments = new ArrayList<>(typeArgs.size());
        for (int i = 0; i < typeArgs.size(); i++) {
          TypeUse argUse = use != null ? use.getArg(fqcn, i) : null;
          TypeInfo typeArgDesc = create(argUse, typeArgs.get(i));
          // Need to check it is an interface type
          typeArguments.add(typeArgDesc);
        }
        ClassTypeInfo raw = (ClassTypeInfo) create(null, (DeclaredType) type.asElement().asType(), false);
        return new ParameterizedTypeInfo(raw, nullable, typeArguments);
      } else {
        ClassTypeInfo raw;
        if (kind == ClassKind.BOXED_PRIMITIVE) {
          raw = ClassTypeInfo.PRIMITIVES.get(fqcn);
          if (nullable) {
            raw = new ClassTypeInfo(raw.kind, raw.name, raw.module, true, raw.params);
          }
        } else {
          List<TypeParamInfo.Class> typeParams = createTypeParams(type);
          if (kind == ClassKind.API) {
            VertxGen genAnn = elt.getAnnotation(VertxGen.class);
            List<TypeInfo> functionArgs = extractArgs(type, ClassModel.FUNCTION);
            ApiTypeArgInfo argInfo = new ApiTypeArgInfo(
              elementOrNull(extractArgs(type, ClassModel.VERTX_READ_STREAM), 0),
              elementOrNull(extractArgs(type, ClassModel.VERTX_WRITE_STREAM), 0),
              elementOrNull(extractArgs(type, ClassModel.VERTX_HANDLER), 0),
              elementOrNull(extractArgs(type, ClassModel.ITERABLE), 0),
              elementOrNull(extractArgs(type, ClassModel.ITERATOR), 0),
              elementOrNull(functionArgs, 0),
              elementOrNull(functionArgs, 1)
            );
            raw = new ApiTypeInfo(fqcn, genAnn.concrete(), typeParams, module, nullable, proxyGen, argInfo);
          } else if (kind == ClassKind.DATA_OBJECT) {
            boolean _abstract = elt.getModifiers().contains(Modifier.ABSTRACT);
            raw = new DataObjectTypeInfo(kind, fqcn, module, _abstract, nullable, typeParams);
          } else {
            raw = new ClassTypeInfo(kind, fqcn, module, nullable, typeParams);
          }
        }
        return raw;
      }
    }
  }


  private <T> List<TypeInfo> extractArgs(DeclaredType type, String className) {
    TypeElement parameterizedElt = elementUtils.getTypeElement(className);
    TypeMirror parameterizedType = parameterizedElt.asType();
    TypeMirror rawType = typeUtils.erasure(parameterizedType);
    if (typeUtils.isSubtype(type, rawType)) {
      return parameterizedElt.getTypeParameters().stream()
        .map(typeParam -> {
          TypeMirror resolved = Helper.resolveTypeParameter(typeUtils, type, typeParam);
          if (resolved != null && resolved.getKind() == TypeKind.DECLARED) {
            DeclaredType dt = (DeclaredType) resolved;
            TypeElement a = (TypeElement) dt.asElement();
            if (a.getQualifiedName().toString().equals("io.vertx.core.AsyncResult")) {
              return null;
            }
          }
          return create(resolved);
        })
        .collect(toList());
    }
    return Collections.emptyList();
  }

  private TypeInfo elementOrNull(List<TypeInfo> list, int index) {
    return index < list.size() ? list.get(index) : null;
  }

  public TypeVariableInfo create(TypeUse use, TypeVariable type) {
    TypeParameterElement elt = (TypeParameterElement) type.asElement();
    TypeParamInfo param = TypeParamInfo.create(elt);
    return new TypeVariableInfo(param, use != null && use.isNullable(), elt.getSimpleName().toString());
  }

  public ArrayTypeInfo create(TypeUse use, ArrayType type) {
    TypeMirror componentType = type.getComponentType();
    return new ArrayTypeInfo(create(componentType), use != null && use.isNullable());
  }

  private List<TypeParamInfo.Class> createTypeParams(DeclaredType type) {
    List<TypeParamInfo.Class> typeParams = new ArrayList<>();
    TypeElement elt = (TypeElement) type.asElement();
    List<? extends TypeParameterElement> typeParamElts = elt.getTypeParameters();
    for (int index = 0; index < typeParamElts.size(); index++) {
      TypeParameterElement typeParamElt = typeParamElts.get(index);
      typeParams.add(new TypeParamInfo.Class(elt.getQualifiedName().toString(), index, typeParamElt.getSimpleName().toString()));
    }
    return typeParams;
  }
}
