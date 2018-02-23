package io.vertx.test.codegen.testapi;

import io.vertx.codegen.annotations.VertxGen;

@VertxGen
public interface InterfaceExtendingAutoCloseable extends AutoCloseable {
  void foo();
}
