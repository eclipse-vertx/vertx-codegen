package io.vertx.test.codegen.testapi;

import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;

@VertxGen
@ProxyGen
@Deprecated
public interface DeprecatedInterface {
  @Deprecated
  void test();
}
