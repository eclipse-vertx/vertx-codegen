package io.vertx.codegen;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public enum TypeKind {

  // Basic types
  STRING(true, false),
  BOXED_PRIMITIVE(true, false),
  PRIMITIVE(true, false),

  // Json types
  JSON_OBJECT(false, true),
  JSON_ARRAY(false, true),

  // Various stuff
  THROWABLE(false, false),  // java.lang.Throwable
  VOID(false, false),       // java.lang.Void
  LIST(false, false),       // java.util.List
  SET(false, false),        // java.util.Set
  OBJECT(false, false),     // java.lang.Object

  // API types
  API(false, false),

  // Options
  OPTIONS(false, false),

  // Handler
  HANDLER(false, false),

  // AsyncResult
  ASYNC_RESULT(false, false),

  // Variable (i.e. type paramater)
  VARIABLE(false, false),

  // Anything else
  OTHER(false, false);

  // True when basic
  public final boolean basic;
  // True when json
  public final boolean json;

  TypeKind(boolean basic, boolean json) {
    this.basic = basic;
    this.json = json;
  }
}
