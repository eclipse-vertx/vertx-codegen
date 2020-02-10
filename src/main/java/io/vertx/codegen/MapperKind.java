package io.vertx.codegen;

import java.util.HashMap;
import java.util.Map;

/**
 * Define what kind of mapper we deal with.
 */
public enum MapperKind {

  /**
   * The data object is the mapper, i.e it's a valid {@code @DataObject} annotated type.
   */
  SELF,

  /**
   * A public static method {@code J xxx(T t)} or {@code T xxx(J j)}.
   */
  STATIC_METHOD;

  public static Map<String, MapperKind> vars() {
    HashMap<String, MapperKind> vars = new HashMap<>();
    for (MapperKind kind : values()) {
      vars.put("MAPPER_" + kind.name(), kind);
    }
    return vars;
  }
}
