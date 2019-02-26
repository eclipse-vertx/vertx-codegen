package io.vertx.codegen;

import io.vertx.codegen.annotations.ModuleGen;
import io.vertx.codegen.type.ClassTypeInfo;
import io.vertx.codegen.type.TypeNameTranslator;

import javax.lang.model.element.*;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

/**
 * Describes a module.
 *
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class ModuleInfo {

  private final String packageName;
  private final String name;
  private final String groupPackage;
  private final Map<String, DeclaredType> jsonCodecs;

  private ModuleInfo(String packageName, String name, String groupPackage, Map<String, DeclaredType> jsonCodecs) {
    this.packageName = packageName;
    this.name = name;
    this.groupPackage = groupPackage;
    this.jsonCodecs = jsonCodecs;
  }

  private static final BiFunction<Elements, String, Set<PackageElement>> getPackageElementJava8 = (elts, fqn) -> {
    PackageElement elt = elts.getPackageElement(fqn);
    return elt != null ? Collections.singleton(elt) : Collections.emptySet();
  };

  private static final BiFunction<Elements, String, Set<PackageElement>> getPackageElement;

  static {
    BiFunction<Elements, String, Set<PackageElement>> result = getPackageElementJava8;
    try {
      Method method = Elements.class.getDeclaredMethod("getAllPackageElements", CharSequence.class);
      result = (elts, fqn) -> {
        try {
          return (Set<PackageElement>) method.invoke(elts, fqn);
        } catch (Exception e) {
          return getPackageElementJava8.apply(elts, fqn);
        }
      };
    } catch (NoSuchMethodException e) {
      // Java 8
    }
    getPackageElement = result;
  }


  /**
   * Resolve package defined json codecs inspecting the ModuleGen annotation.
   * For each codec declared, it inspects the type parameter used. For example the class ZonedDateTimeCodec implements
   * @<code>JsonCodec<ZonedDateTime></code>, so this function creates a map entry with ZonedDateTime: ZonedDateTimeCodec
   *
   * @param pkgElt
   * @return
   */
  public static Map<String, DeclaredType> resolveJsonCodecs(Elements elementUtils, Types typeUtils, PackageElement pkgElt) {
    if (pkgElt == null) return new HashMap<>();
    Optional<? extends AnnotationMirror> mirror = elementUtils
      .getAllAnnotationMirrors(pkgElt)
      .stream()
      .filter(am -> am.getAnnotationType().toString().equals(ModuleGen.class.getName()))
      .findFirst();
    if (!mirror.isPresent()) return new HashMap<>();
    AnnotationValue codecsAnnotationValue = elementUtils
      .getElementValuesWithDefaults(mirror.get())
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
        TypeMirror jsonifiableType = superTypesOfCodecDeclaredType
          .stream()
          .filter(t -> t.getKind() == TypeKind.DECLARED)
          .map(t -> (DeclaredType)t)
          .filter(t -> t.asElement().getSimpleName().contentEquals("JsonCodec"))
          .map(t -> t.getTypeArguments().get(0))
          .findFirst()
          .orElseThrow(() -> new GenException(codecDeclaredType.asElement(), "Cannot find what type the json codec handles"));
        return new AbstractMap.SimpleImmutableEntry<>(jsonifiableType.toString(), codecDeclaredType);
      }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
  }

  public static ModuleInfo create(String packageName, String name, String groupPackage, Elements elementUtils, Types typeUtils, PackageElement pkgElt) {
    return new ModuleInfo(packageName, name, groupPackage, resolveJsonCodecs(elementUtils, typeUtils, pkgElt));
  }

  public static ModuleInfo createWithNoCodecs(String packageName, String name, String groupPackage) {
    return new ModuleInfo(packageName, name, groupPackage, new HashMap<>());
  }

  /**
   * Resolve a module info for the specified {@code pkgElt} argument, returns null for undertermined.
   *
   * @param elementUtils the element utils
   * @param typeUtils
   * @param pkgElt the package element
   * @return the module info
   */
  public static ModuleInfo resolve(Elements elementUtils, Types typeUtils, PackageElement pkgElt) {
    PackageElement result = resolveFirstModuleGenAnnotatedPackageElement(elementUtils, pkgElt);
    if (result != null) {
      ModuleGen annotation = result.getAnnotation(ModuleGen.class);
      return create(result.getQualifiedName().toString(), annotation.name(), annotation.groupPackage(), elementUtils, typeUtils, pkgElt);
    } else return null;
  }

  public static PackageElement resolveFirstModuleGenAnnotatedPackageElement(Elements elementUtils, PackageElement pkgElt) {
    String pkgQN = pkgElt.getQualifiedName().toString();
    while (true) {
      if (pkgElt != null) {
        ModuleGen annotation = pkgElt.getAnnotation(ModuleGen.class);
        if (annotation != null) {
          return pkgElt;
        }
      }
      int pos = pkgQN.lastIndexOf('.');
      if (pos == -1) {
        break;
      } else {
        pkgQN = pkgQN.substring(0, pos);
        Set<PackageElement> pkgElts = getPackageElement.apply(elementUtils, pkgQN);
        pkgElt = pkgElts.isEmpty() ? null : pkgElts.iterator().next();
      }
    }
    return null;
  }

  public String getGroupPackage() {
    return groupPackage;
  }

  /**
   * @return the module package name, i.e the name of the package annotated with the {@link ModuleGen} annotation
   */
  public String getPackageName() {
    return packageName;
  }

  /**
   * Translates the module package name for the specified {@code lang} parameter language.
   *
   * @param lang the language, for instance {@literal groovy}
   * @return the translated package name
   */
  public String translatePackageName(String lang) {
    return translateQualifiedName(packageName, lang);
  }

  /**
   * Translate a given {@code qualified name} based on the module group package name and the specified
   * {@code lang} parameter.
   *
   * @param qualifiedName the qualified name
   * @param lang the language, for instance {@literal groovy}
   * @return the translated qualified name
   */
  public String translateQualifiedName(String qualifiedName, String lang) {
    return TypeNameTranslator.hierarchical(lang).translate(this, qualifiedName);
  }

  /**
   * @return the module name
   */
  public String getName() {
    return name;
  }

  /**
   * @param _case the formatting case
   * @return the module name in the specified case
   */
  public String getName(Case _case) {
    return _case.format(Case.KEBAB.parse(name));
  }

  /**
   * Resolve package defined json codecs
   *
   * @param typeToFindCodec
   * @return
   */
  public Optional<DeclaredType> findCodec(TypeMirror typeToFindCodec) {
    return Optional.ofNullable(jsonCodecs.get(typeToFindCodec.toString()));
  }

  /**
   * Resolve package defined json codecs
   *
   * @param classTypeInfo
   * @return
   */
  public Optional<DeclaredType> findCodec(ClassTypeInfo classTypeInfo) {
    return Optional.ofNullable(jsonCodecs.get(classTypeInfo.toString()));
  }
}
