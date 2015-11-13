package io.vertx.codegen;

import io.vertx.codegen.annotations.ModuleGen;
import io.vertx.codegen.type.TypeNameTranslator;

import javax.lang.model.element.PackageElement;
import javax.lang.model.util.Elements;
import java.util.ArrayList;
import java.util.List;

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
