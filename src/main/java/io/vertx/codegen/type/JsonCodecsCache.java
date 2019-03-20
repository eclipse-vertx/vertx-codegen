package io.vertx.codegen.type;

import io.vertx.codegen.GenException;
import io.vertx.codegen.ModuleInfo;
import io.vertx.codegen.annotations.ModuleGen;

import javax.lang.model.element.*;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.util.*;
import java.util.stream.Collectors;

public class JsonCodecsCache {

  // Each map entry contains json codec and json target type
  private final Map<String, Map.Entry<DeclaredType, TypeMirror>> jsonCodecs;
  private final Elements elementUtils;
  private final Types typeUtils;

  public JsonCodecsCache(Elements elementUtils, Types typeUtils, PackageElement pkgElt) {
    this.elementUtils = elementUtils;
    this.typeUtils = typeUtils;
    this.jsonCodecs = resolveJsonCodecs(ModuleInfo.resolveFirstModuleGenAnnotatedPackageElement(elementUtils, pkgElt));
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
          .map(t -> new AbstractMap.SimpleImmutableEntry<>(t.getTypeArguments().get(0), t.getTypeArguments().get(1)))
          .findFirst()
          .orElseThrow(() -> new GenException(codecDeclaredType.asElement(), "Cannot find what type the json codec handles"));
        return new AbstractMap.SimpleImmutableEntry<>(codecGenericsValues.getKey().toString(), new AbstractMap.SimpleImmutableEntry<>(codecDeclaredType, (TypeMirror)codecGenericsValues.getValue()));
      })
      .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
  }

  /**
   * Resolve package defined json codecs
   *
   * @param typeToFindCodec
   * @return
   */
  public Optional<Map.Entry<DeclaredType, TypeMirror>> findCodecForType(TypeMirror typeToFindCodec) {
    return Optional.ofNullable(jsonCodecs.get(typeToFindCodec.toString()));
  }

}
