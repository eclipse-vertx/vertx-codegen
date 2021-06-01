package io.vertx.test.codegen.testapi;

import io.vertx.codegen.annotations.VertxGen;

@VertxGen
public interface OverrideB extends OverrideA {

  @Override
  void someMethod();
}
