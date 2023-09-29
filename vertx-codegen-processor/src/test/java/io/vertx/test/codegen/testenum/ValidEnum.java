package io.vertx.test.codegen.testenum;

import io.vertx.codegen.annotations.VertxGen;

/**ValidEnum doc*/
@VertxGen
public enum ValidEnum {

  /**RED doc*/
  @SomeAnnotation("red")
  RED,

  /**GREEN doc*/
  @SomeAnnotation("green")
  GREEN,

  /**BLUE doc*/
  @SomeAnnotation("blue")
  BLUE

}
