package io.vertx.test.codegen.testapi.constant;

import io.vertx.codegen.annotations.VertxGen;

@VertxGen
public interface InterfaceWithIllegalConstantType {

  Thread CONSTANT = null;

}
