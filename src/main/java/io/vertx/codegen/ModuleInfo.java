package io.vertx.codegen;

import io.vertx.codegen.annotations.ModuleGen;

import javax.lang.model.element.PackageElement;
import javax.lang.model.util.Elements;

/**
 * Describes a module.
 *
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class ModuleInfo {

  private final String packageName;
  private final String name;
  private final String groupPackageName;

  public ModuleInfo(String packageName, String name, String groupPackageName) {
    this.packageName = packageName;
    this.name = name;
    this.groupPackageName = groupPackageName;
  }

  /**
   * Resolve a module info for the specified {@code pkgElt} argument, returns null for undertermined.
   *
   * @param elementUtils the element utils
   * @param pkgElt the package element
   * @return the module info
   */
  public static ModuleInfo resolve(Elements elementUtils, PackageElement pkgElt) {
    while (pkgElt != null) {
      ModuleGen annotation = pkgElt.getAnnotation(ModuleGen.class);
      if (annotation != null) {
        return new ModuleInfo(pkgElt.getQualifiedName().toString(), annotation.name(), annotation.groupPackage());
      }
      String pkgQN = pkgElt.getQualifiedName().toString();
      int pos = pkgQN.lastIndexOf('.');
      if (pos == -1) {
        break;
      } else {
        pkgElt = elementUtils.getPackageElement(pkgQN.substring(0, pos));
      }
    }
    return null;
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
    if (qualifiedName.startsWith(groupPackageName)) {
      return groupPackageName + "." + lang + qualifiedName.substring(groupPackageName.length(), qualifiedName.length());
    }
    return qualifiedName;
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
