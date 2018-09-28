package io.vertx.codegen.type;

/**
 * @author Richard Gomez
 */
public enum CaseFormat {
  LOWER_CAMEL,
  UPPER_CAMEL,
  KEBAB,
  SNAKE;

  CaseFormat() {
  }

  public io.vertx.codegen.Case getCase() {
    switch (this) {
      case UPPER_CAMEL:
        return io.vertx.codegen.Case.CAMEL;
      case KEBAB:
        return io.vertx.codegen.Case.KEBAB;
      case SNAKE:
        return io.vertx.codegen.Case.SNAKE;
      case LOWER_CAMEL:
      default:
        return io.vertx.codegen.Case.LOWER_CAMEL;
    }
  }
}
