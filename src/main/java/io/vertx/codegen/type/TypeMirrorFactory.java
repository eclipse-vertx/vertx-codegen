package io.vertx.codegen.type;

import io.vertx.codegen.*;
import io.vertx.codegen.annotations.DataObject;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;

import javax.lang.model.element.*;
import javax.lang.model.type.*;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.util.*;
import java.util.stream.Stream;

/**
 * Type info factory based on <i>javax.lang.model</i> and type mirrors.
 *
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class TypeMirrorFactory {

  final Elements elementUtils;
  final Types typeUtils;
  final PackageElement p;

  public TypeMirrorFactory(Elements elementUtils, Types typeUtils, PackageElement pkgElt) {
    this.elementUtils = elementUtils;
    this.typeUtils = typeUtils;
    this.p = pkgElt;
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
    String simpleName = elt.getSimpleName().toString();
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
            TypeInfo[] args = Stream.of(
                ClassModel.VERTX_READ_STREAM,
                ClassModel.VERTX_WRITE_STREAM,
                ClassModel.VERTX_HANDLER
            ).map(s -> {
              TypeElement parameterizedElt = elementUtils.getTypeElement(s);
              TypeMirror parameterizedType = parameterizedElt.asType();
              TypeMirror rawType = typeUtils.erasure(parameterizedType);
              if (typeUtils.isSubtype(type, rawType)) {
                TypeMirror resolved = Helper.resolveTypeParameter(typeUtils, type, parameterizedElt.getTypeParameters().get(0));
                if (resolved.getKind() == TypeKind.DECLARED) {
                  DeclaredType dt = (DeclaredType) resolved;
                  TypeElement a = (TypeElement) dt.asElement();
                  if (a.getQualifiedName().toString().equals("io.vertx.core.AsyncResult")) {
                    return null;
                  }
                }
                return create(resolved);
              }
              return null;
            }).toArray(TypeInfo[]::new);
            raw = new ApiTypeInfo(fqcn, genAnn.concrete(), typeParams, args[0], args[1], args[2], module, nullable, proxyGen);
          } else if (kind == ClassKind.DATA_OBJECT) {
            boolean encodable = Helper.isDataObjectAnnotatedEncodable(elementUtils, elt);
            boolean decodable = Helper.isDataObjectAnnotatedDecodable(elementUtils, typeUtils, elt);
            raw = new DataObjectTypeInfo(
              fqcn,
              module,
              nullable,
              typeParams,
              (encodable) ? simpleName + "Converter" : null,
              null,
              (encodable) ? pkgElt.toString() : null,
              (decodable) ? simpleName + "Converter" : null,
              null,
              (decodable) ? pkgElt.toString() : null,
              this.create(elementUtils.getTypeElement("io.vertx.core.json.JsonObject").asType())
            );
          } else {
            DeclaredType jsonCodecType = ModuleInfo.resolveJsonCodec(elementUtils, typeUtils, p, type);
            if (jsonCodecType != null) {
              TypeElement jsonCodecElt = elementUtils.getTypeElement("io.vertx.core.spi.json.JsonCodec");
              TypeParameterElement typeParamElt = jsonCodecElt.getTypeParameters().get(1);
              TypeMirror jsonType = Helper.resolveTypeParameter(typeUtils, jsonCodecType, typeParamElt);
              String codecSimpleName = jsonCodecType.asElement().getSimpleName().toString();
              String codecEnclosingClass =
                jsonCodecType.asElement().getEnclosingElement().getKind() != ElementKind.PACKAGE ?
                  jsonCodecType.asElement().getEnclosingElement().getSimpleName().toString() : null;
              String codecPkgName = elementUtils.getPackageOf(jsonCodecType.asElement()).toString();
              raw = new DataObjectTypeInfo(
                fqcn,
                module,
                nullable,
                typeParams,
                codecSimpleName,
                codecEnclosingClass,
                codecPkgName,
                codecSimpleName,
                codecEnclosingClass,
                codecPkgName,
                create(jsonType)
              );
            } else {
              raw = new ClassTypeInfo(kind, fqcn, module, nullable, typeParams);
            }
          }
        }
        return raw;
      }
    }
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
