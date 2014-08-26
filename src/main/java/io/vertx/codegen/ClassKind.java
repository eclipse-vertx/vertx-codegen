package io.vertx.codegen;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public enum ClassKind {

  // Basic types
  STRING(true, false, false),
  BOXED_PRIMITIVE(true, false, false),
  PRIMITIVE(true, false, false),

  // Json types
  JSON_OBJECT(false, true, false),
  JSON_ARRAY(false, true, false),

  // Various stuff
  THROWABLE(false, false, false),  // java.lang.Throwable
  VOID(false, false, false),       // java.lang.Void
  OBJECT(false, false, false),     // java.lang.Object

  // Collection types
  LIST(false, false, true),        // java.util.List
  SET(false, false, true),         // java.util.Set

  // API types
  API(false, false, false),

  // Options
  OPTIONS(false, false, false),

  // Handler
  HANDLER(false, false, false),

  // AsyncResult
  ASYNC_RESULT(false, false, false),

  // Anything else
  OTHER(false, false, false);

  // True when basic
  public final boolean basic;
  // True when json
  public final boolean json;
  // True when a java collection type
  public final boolean collection;

  ClassKind(boolean basic, boolean json, boolean collection) {
    this.basic = basic;
    this.json = json;
    this.collection = collection;
  }

  /**
   * Useful for testing the type class kind, allows to do type.kind == CLASS_API instead of type.kind.name() == "API"
   */
  public static Map<String, ClassKind> vars() {
    HashMap<String, ClassKind> vars = new HashMap<>();
    for (ClassKind classKind : ClassKind.values()) {
      vars.put("CLASS_" + classKind.name(), classKind);
    }
    return vars;
  }
}
