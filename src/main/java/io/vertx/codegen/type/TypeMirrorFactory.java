package io.vertx.codegen.type;

import io.vertx.codegen.*;
import io.vertx.codegen.annotations.DataObject;
import io.vertx.codegen.annotations.ModuleGen;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;

import javax.lang.model.element.*;
import javax.lang.model.type.*;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.util.*;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Type info factory based on <i>javax.lang.model</i> and type mirrors. </br>
 * This factory also caches json codecs, so you should not share it through various packages
 *
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class TypeMirrorFactory {

  final Elements elementUtils;
  final Types typeUtils;
  // Each map entry contains json codec and json target type
  final Map<String, Map.Entry<DeclaredType, TypeMirror>> jsonCodecs;

  public TypeMirrorFactory(Elements elementUtils, Types typeUtils, PackageElement pkgElt) {
    this.elementUtils = elementUtils;
    this.typeUtils = typeUtils;
    this.jsonCodecs = resolveJsonCodecs(ModuleInfo.resolveFirstModuleGenAnnotatedPackageElement(elementUtils, pkgElt));
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

  /**
   * Interfaces/classes TypeInfo factory
   *
   * @param use
   * @param type
   * @return
   */
  public TypeInfo create(TypeUse use, DeclaredType type) {
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
      ClassTypeInfo raw;
      if (kind == ClassKind.BOXED_PRIMITIVE) {
        raw = ClassTypeInfo.PRIMITIVES.get(fqcn);
        if (nullable) {
          raw = new ClassTypeInfo(raw.kind, raw.name, raw.module, true, raw.params);
        }
      } else {
        List<TypeParamInfo.Class> typeParams = createTypeParams(type);
        Optional<Map.Entry<DeclaredType, TypeMirror>> codec = findCodecForType(type);
        if (codec.isPresent()) {
          raw = new JsonifiableTypeInfo(
            fqcn,
            module,
            nullable,
            typeParams,
            (ClassTypeInfo) this.create(codec.get().getKey()),
            this.create(codec.get().getValue())
          );
        } else if (kind == ClassKind.API) {
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
          boolean _abstract = elt.getModifiers().contains(Modifier.ABSTRACT);
          raw = new DataObjectTypeInfo(kind, fqcn, module, _abstract, nullable, typeParams);
        } else {
          raw = new ClassTypeInfo(kind, fqcn, module, nullable, typeParams);
        }
      }
      List<? extends TypeMirror> typeArgs = type.getTypeArguments();
      if (typeArgs.size() > 0) {
        List<TypeInfo> typeArguments;
        typeArguments = new ArrayList<>(typeArgs.size());
        for (int i = 0; i < typeArgs.size(); i++) {
          TypeUse argUse = use != null ? use.getArg(fqcn, i) : null;
          TypeInfo typeArgDesc = create(argUse, typeArgs.get(i));
          // Need to check it is an interface type
          typeArguments.add(typeArgDesc);
        }
        return new ParameterizedTypeInfo(raw, nullable, typeArguments);
      } else {
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

  /**
   * Resolve package defined json codecs inspecting the ModuleGen annotation.
   * For each codec declared, it inspects the type parameter used. For example the class ZonedDateTimeCodec implements
   * @<code>JsonCodec<ZonedDateTime></code>, so this function creates a map entry with ZonedDateTime: ZonedDateTimeCodec
   *
   * @param pkgElt
   * @return
   */
  private Map<String, Map.Entry<DeclaredType, TypeMirror>> resolveJsonCodecs(PackageElement pkgElt) {
    if (pkgElt == null) return new HashMap<>();
    AnnotationMirror mirror = elementUtils
      .getAllAnnotationMirrors(pkgElt)
      .stream()
      .filter(am -> am.getAnnotationType().toString().equals(ModuleGen.class.getName()))
      .findFirst()
      .orElseThrow(() -> new GenException(pkgElt, "Cannot find json codecs for package without ModuleGen annotation"));
    AnnotationValue codecsAnnotationValue = elementUtils
      .getElementValuesWithDefaults(mirror)
      .entrySet()
      .stream()
      .filter(e -> e.getKey().getSimpleName().contentEquals("codecs"))
      .map(Map.Entry::getValue).findFirst()
      .orElseThrow(() -> new GenException(pkgElt, "Cannot find json codecs inside ModuleGen annotation"));
    List<AnnotationValue> declaredJsonCodecs = (List<AnnotationValue>) codecsAnnotationValue.getValue();

    return declaredJsonCodecs.stream()
      .map(t -> (DeclaredType)t.getValue())
      .map(codecDeclaredType -> {
        // Check if codecDeclaredType is concrete and has empty constructor
        if (codecDeclaredType.asElement().getKind() != ElementKind.CLASS) throw new GenException(codecDeclaredType.asElement(), "The json codec must be a concrete class");
        TypeElement codecDeclaredElement = (TypeElement) codecDeclaredType.asElement();
        if (codecDeclaredElement.getModifiers().contains(Modifier.ABSTRACT)) throw new GenException(codecDeclaredElement, "The json codec must be a concrete class");
        if (elementUtils
          .getAllMembers(codecDeclaredElement).stream()
          .filter(e -> e.getKind() == ElementKind.CONSTRUCTOR)
          .noneMatch(e -> ((ExecutableElement)e).getParameters().isEmpty())) throw new GenException(codecDeclaredElement, "The json codec must have an empty constructor");

        List<? extends TypeMirror> superTypesOfCodecDeclaredType = typeUtils.directSupertypes(codecDeclaredType);
        Map.Entry<? extends TypeMirror, ? extends TypeMirror> codecGenericsValues = superTypesOfCodecDeclaredType
          .stream()
          .filter(t -> t.getKind() == TypeKind.DECLARED)
          .map(t -> (DeclaredType)t)
          .filter(t -> t.asElement().getSimpleName().contentEquals("JsonCodec"))
          .map(t -> new SimpleImmutableEntry<>(t.getTypeArguments().get(0), t.getTypeArguments().get(1)))
          .findFirst()
          .orElseThrow(() -> new GenException(codecDeclaredType.asElement(), "Cannot find what type the json codec handles"));
        return new SimpleImmutableEntry<>(codecGenericsValues.getKey().toString(), new SimpleImmutableEntry<>(codecDeclaredType, (TypeMirror)codecGenericsValues.getValue()));
      })
      .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
  }

  /**
   * Resolve package defined json codecs
   *
   * @param typeToFindCodec
   * @return
   */
  private Optional<Map.Entry<DeclaredType, TypeMirror>> findCodecForType(TypeMirror typeToFindCodec) {
    return Optional.ofNullable(jsonCodecs.get(typeToFindCodec.toString()));
  }
}
