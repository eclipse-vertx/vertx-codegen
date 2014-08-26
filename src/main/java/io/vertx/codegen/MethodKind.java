package io.vertx.codegen;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public enum MethodKind {

  /**
   * A method has an handler kind when it has at least one parameter and its last parameter
   * is a parameterized handler type having a legal type argument that is not an <i>async_result</i> class
   * kind.
   */
  HANDLER,

  /**
   * A method has a future kind when it has at least one parameter and its last parameter
   * is a parameterized handler type having a <i>async_result</i> parameterized type argument.
   */
  FUTURE,

  INDEX_GETTER,

  INDEX_SETTER,

  OTHER;

  /**
   * Useful for testing the method kind, allows to do method.kind == METHOD_HANDLER instead of method.kind.name() == "HANDLER"
   */
  public static Map<String, MethodKind> vars() {
    HashMap<String, MethodKind> vars = new HashMap<>();
    for (MethodKind classKind : MethodKind.values()) {
      vars.put("METHOD_" + classKind.name(), classKind);
    }
    return vars;
  }
}
