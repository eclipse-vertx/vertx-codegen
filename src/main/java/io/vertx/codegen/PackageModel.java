package io.vertx.codegen;

import javax.lang.model.element.Element;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class PackageModel implements Model {

  private final String fqn;

  public PackageModel(String fqn) {
    this.fqn = fqn;
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
    HashMap<String, Object> vars = new HashMap<>();
    vars.put("fqn", fqn);
    return vars;
  }
}
