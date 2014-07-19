package io.vertx.codegen;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public enum TypeKind {

  // Not of a specific kind
  NONE,

  // Annotated with @VertxGen
  GEN,

  // Annotated with @Option
  OPTIONS,

  // Vert.x special types
  HANDLER, ASYNC_RESULT, JSON_OBJECT, JSON_ARRAY

}
