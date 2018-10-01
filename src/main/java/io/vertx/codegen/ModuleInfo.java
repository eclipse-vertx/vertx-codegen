package io.vertx.codegen;

import io.vertx.codegen.annotations.ModuleGen;
import io.vertx.codegen.type.TypeNameTranslator;

import javax.lang.model.element.PackageElement;
import javax.lang.model.util.Elements;
import java.lang.reflect.Method;
import java.util.Collections;
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

  public ModuleInfo(String packageName, String name, String groupPackage) {
    this.packageName = packageName;
    this.name = name;
    this.groupPackage = groupPackage;
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
   * Resolve a module info for the specified {@code pkgElt} argument, returns null for undetermined.
   *
   * @param elementUtils the element utils
   * @param pkgElt the package element
   * @return the module info
   */
  public static ModuleInfo resolve(Elements elementUtils, PackageElement pkgElt) {
    String pkgQN = pkgElt.getQualifiedName().toString();
    while (true) {
      if (pkgElt != null) {
        ModuleGen annotation = pkgElt.getAnnotation(ModuleGen.class);
        if (annotation != null) {
          return new ModuleInfo(pkgElt.getQualifiedName().toString(), annotation.name(), annotation.groupPackage());
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
}
