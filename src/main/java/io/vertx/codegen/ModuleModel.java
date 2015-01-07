package io.vertx.codegen;

import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class ModuleModel implements Model {

  private final PackageElement element;
  private final ModuleInfo info;

  public ModuleModel(PackageElement element, ModuleInfo info) {
    this.element = element;
    this.info = info;
  }

  public String getName() {
    return info.getName();
  }

  @Override
  public String getKind() {
    return "module";
  }

  @Override
  public Element getElement() {
    return element;
  }

  @Override
  public String getFqn() {
    return info.getFqn();
  }

  @Override
  public Map<String, Object> getVars() {
    Map<String, Object> vars = new HashMap<>();
    vars.put("fqn", info.getFqn());
    vars.put("name", info.getName());
    vars.putAll(Case.vars());
    return vars;
  }
}
