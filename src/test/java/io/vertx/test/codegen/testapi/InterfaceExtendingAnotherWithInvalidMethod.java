package io.vertx.test.codegen.testapi;

import io.vertx.codegen.annotations.VertxGen;

import java.util.Map;

@VertxGen
public interface InterfaceExtendingAnotherWithInvalidMethod extends VertxGenClass1 {

  // Should fail
  Map<String, Thread> invalidMethod();

}
