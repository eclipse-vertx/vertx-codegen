package io.vertx.codegen;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public enum TypeKind {

  // Basic types
  STRING(true),
  BOXED_PRIMITIVE(true),
  PRIMITIVE(true),
  JSON_OBJECT(true),
  JSON_ARRAY(true),

  // Various stuff
  THROWABLE(false),  // java.lang.Throwable
  VOID(false),       // java.lang.Void
  LIST(false),       // java.util.List
  SET(false),        // java.util.Set
  OBJECT(false),     // java.lang.Object

  // API types
  API(false),

  // Options
  OPTIONS(false),

  // Handler
  HANDLER(false),

  // AsyncResult
  ASYNC_RESULT(false),

  // Anything else
  OTHER(false);

  // True when basic
  boolean basic;

  TypeKind(boolean basic) {
    this.basic = basic;
  }
}
