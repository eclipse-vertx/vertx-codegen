package io.vertx.codegen;

import io.vertx.codegen.annotations.ModuleGen;
import io.vertx.codegen.format.Case;
import io.vertx.codegen.format.KebabCase;
import io.vertx.codegen.type.TypeNameTranslator;

import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;

/**
 * Describes a module.
 *
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class ModuleInfo {

  private final String packageName;
  private final String name;
  private final String groupPackage;

  // Only used internally
  final boolean useFutures;
  final boolean checkCallbackDeprecation;

  public ModuleInfo(String packageName, String name, String groupPackage, boolean useFutures, boolean checkCallbackDeprecation) {
    this.packageName = packageName;
    this.name = name;
    this.groupPackage = groupPackage;
    this.useFutures = useFutures;
    this.checkCallbackDeprecation = checkCallbackDeprecation;
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
   * Resolve a module info for the specified {@code pkgElt} argument, returns null for undertermined.
   *
   * @param elementUtils the element utils
   * @param pkgElt the package element
   * @return the module info
   */
  public static ModuleInfo resolve(Elements elementUtils, PackageElement pkgElt) {
    PackageElement result = resolveFirstModuleGenAnnotatedPackageElement(elementUtils, pkgElt);
    if (result != null) {
      ModuleGen annotation = result.getAnnotation(ModuleGen.class);
      return new ModuleInfo(result.getQualifiedName().toString(), annotation.name(), annotation.groupPackage(), annotation.useFutures(), annotation.checkCallbackDeprecation());
    } else return null;
  }

  public static DeclaredType resolveJsonMapper(Elements elementUtils, Types typeUtils, PackageElement pkgElt, DeclaredType javaType) {
    PackageElement result = resolveFirstModuleGenAnnotatedPackageElement(elementUtils, pkgElt);
    if (result != null) {
      TypeElement jsonMapperElt = elementUtils.getTypeElement("io.vertx.core.spi.json.JsonMapper");
      TypeParameterElement typeParamElt = jsonMapperElt.getTypeParameters().get(0);
      return elementUtils
        .getAllAnnotationMirrors(pkgElt)
        .stream()
        .filter(am -> am.getAnnotationType().toString().equals(ModuleGen.class.getName()))
        .flatMap(am -> am.getElementValues().entrySet().stream())
        .filter(e -> e.getKey().getSimpleName().toString().equals("mappers"))
        .flatMap(e -> ((List<AnnotationValue>) e.getValue().getValue()).stream())
        .map(annotationValue -> (DeclaredType) annotationValue.getValue())
        .filter(dt -> {
          TypeMirror mapperType = Helper.resolveTypeParameter(typeUtils, dt, typeParamElt);
          return mapperType != null && mapperType.getKind() == TypeKind.DECLARED && typeUtils.isSameType(mapperType, javaType);
        })
        .findFirst()
        .orElse(null);
    }
    return null;
  }

  public static PackageElement resolveFirstModuleGenAnnotatedPackageElement(Elements elementUtils, PackageElement pkgElt) {
    if (pkgElt == null) return null;
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
    return _case.format(KebabCase.INSTANCE.parse(name));
  }

  /**
   * @return whether the module declares asynchronous operations with future returning signatures
   */
  public boolean getUseFutures() {
    return useFutures;
  }

}
