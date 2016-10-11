package io.vertx.codegen;

import javax.lang.model.element.Element;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public interface Model {

  String getKind();

  Element getElement();

  String getFqn();

  default Map<String, Object> getVars() {
    HashMap<String, Object> vars = new HashMap<>();
    vars.put("helper", new Helper());
    return vars;
  }

  ModuleInfo getModule();

}
