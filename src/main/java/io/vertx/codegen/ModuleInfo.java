package io.vertx.codegen;

/**
 * Describes a module.
 *
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class ModuleInfo {

  private final String fqn;
  private final String name;

  public ModuleInfo(String fqn, String name) {
    this.fqn = fqn;
    this.name = name;
  }

  /**
   * @return the module fqn, i.e the name of the package annotated with the {@link io.vertx.codegen.annotations.GenModule} annotation
   */
  public String getFqn() {
    return fqn;
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
    return _case.format(Case.CAMEL.parse(name));
  }
}
