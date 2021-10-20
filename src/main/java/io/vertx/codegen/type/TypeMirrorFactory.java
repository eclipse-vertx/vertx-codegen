package io.vertx.codegen.type;

import io.vertx.codegen.*;
import io.vertx.codegen.annotations.DataObject;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;

import javax.lang.model.element.*;
import javax.lang.model.type.*;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Type info factory based on <i>javax.lang.model</i> and type mirrors.
 *
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class TypeMirrorFactory {

  private static final ModuleInfo VERTX_CORE_MOD = new ModuleInfo("io.vertx.core", "vertx", "io.vertx", false);
  private static final ClassTypeInfo JSON_OBJECT = new ClassTypeInfo(ClassKind.JSON_OBJECT, "io.vertx.core.json.JsonObject", VERTX_CORE_MOD, false, Collections.emptyList(), null);
  private static final ClassTypeInfo STRING = new ClassTypeInfo(ClassKind.STRING, "java.lang.String", null, false, Collections.emptyList(), null);

  final Elements elementUtils;
  final Types typeUtils;
  final Map<String, MapperInfo> serializers = new HashMap<>();
  final Map<String, MapperInfo> deserializers = new HashMap<>();

  public TypeMirrorFactory(Elements elementUtils, Types typeUtils) {
    this.elementUtils = elementUtils;
    this.typeUtils = typeUtils;
  }

  public void addDataObjectDeserializer(Element elt, TypeMirror dataObjectType, MapperInfo mapper) {
    String key = dataObjectType.toString();
    if (serializers.containsKey(key) && !serializers.get(key).getJsonType().equals(mapper.getJsonType())) {
      throw new GenException(elt, "Mapper cannot declare mixed JSON types");
    }
    deserializers.putIfAbsent(key, mapper);
  }

  public void addDataObjectSerializer(Element elt, TypeMirror dataObjectType, MapperInfo mapper) {
    String key = dataObjectType.toString();
    if (deserializers.containsKey(key) && !deserializers.get(key).getJsonType().equals(mapper.getJsonType())) {
      throw new GenException(elt, "Mapper cannot declare mixed JSON types " + deserializers.get(key).getJsonType() + " " + mapper.getJsonType());
    }
    serializers.putIfAbsent(key, mapper);
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
      MapperInfo serializer = serializers.get(type.toString());
      MapperInfo deserializer = deserializers.get(type.toString());
      DataObjectInfo dataObject = null;
      if (serializer != null || deserializer != null) {
        dataObject = new DataObjectInfo(false, serializer, deserializer);
      }
      return new EnumTypeInfo(fqcn, gen, values, module, nullable, dataObject);
    } else {
      ClassKind kind = ClassKind.getKind(fqcn, elt.getAnnotation(VertxGen.class) != null);
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
            raw = new ClassTypeInfo(raw.kind, raw.name, raw.module, true, raw.params, null);
          }
        } else {
          MapperInfo serializer = serializers.get(fqcn);
          MapperInfo deserializer = deserializers.get(fqcn);
          DataObjectInfo dataObject = null;
          if (elt.getAnnotation(DataObject.class) != null) {
            ClassKind serializable = Helper.getAnnotatedDataObjectAnnotatedSerializationType(elementUtils, elt);
            ClassKind deserializable = Helper.getAnnotatedDataObjectDeserialisationType(elementUtils, typeUtils, elt);
            if (serializer == null && serializable != null) {
              serializer = new MapperInfo();
              serializer.setQualifiedName(fqcn);
              serializer.setKind(MapperKind.SELF);
              if (serializable == ClassKind.JSON_OBJECT) {
                serializer.setTargetType(JSON_OBJECT);
                serializer.setSelectors(Collections.singletonList("toJson"));
              } else {
                serializer.setTargetType(STRING);
                serializer.setSelectors(Collections.singletonList("toJson"));
              }
            }
            if (deserializer == null && deserializable != null) {
              deserializer = new MapperInfo();
              deserializer.setQualifiedName(fqcn);
              deserializer.setKind(MapperKind.SELF);
              if (deserializable == ClassKind.JSON_OBJECT) {
                deserializer.setTargetType(JSON_OBJECT);
              } else {
                deserializer.setTargetType(STRING);
              }
            }
            dataObject = new DataObjectInfo(true, serializer, deserializer);
          } else if (serializer != null || deserializer != null) {
            dataObject = new DataObjectInfo(false, serializer, deserializer);
          }

          List<TypeParamInfo.Class> typeParams = createTypeParams(type);
          if (kind == ClassKind.API) {
            VertxGen genAnn = elt.getAnnotation(VertxGen.class);
            TypeInfo handlerArg = null;
            TypeElement parameterizedElt = elementUtils.getTypeElement(ClassModel.VERTX_HANDLER);
            TypeMirror parameterizedType = parameterizedElt.asType();
            TypeMirror rawType = typeUtils.erasure(parameterizedType);
            if (typeUtils.isSubtype(type, rawType)) {
              TypeMirror resolved = Helper.resolveTypeParameter(typeUtils, type, parameterizedElt.getTypeParameters().get(0));
              if (resolved.getKind() == TypeKind.DECLARED) {
                DeclaredType dt = (DeclaredType) resolved;
                TypeElement a = (TypeElement) dt.asElement();
                if (!a.getQualifiedName().toString().equals("io.vertx.core.AsyncResult")) {
                  handlerArg = create(resolved);
                }
              } else {
                handlerArg = create(resolved);
              }
            }
            raw = new ApiTypeInfo(fqcn, genAnn.concrete(), typeParams, handlerArg, module, nullable, proxyGen, dataObject);
          } else {
            raw = new ClassTypeInfo(kind, fqcn, module, nullable, typeParams, dataObject);
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
