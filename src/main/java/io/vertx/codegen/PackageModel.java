package io.vertx.codegen;

import javax.lang.model.element.Element;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class PackageModel implements Model {

  private final String fqn;
  private final ModuleInfo module;

  public PackageModel(String fqn, ModuleInfo module) {
    this.fqn = fqn;
    this.module = module;
  }

  @Override
  public boolean process() {
    return false;
  }

  @Override
  public String getKind() {
    return "package";
  }

  @Override
  public Element getElement() {
    return null;
  }

  @Override
  public String getFqn() {
    return fqn;
  }

  @Override
  public Map<String, Object> getVars() {
    Map<String, Object> vars = Model.super.getVars();
    vars.put("fqn", fqn);
    vars.put("module", module);
    return vars;
  }

  @Override
  public ModuleInfo getModule() {
    return module;
  }
}
