package io.vertx.test.codegen.testapi;

import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;

/**
 * @deprecated deprecated info
 */
@VertxGen
@ProxyGen
@Deprecated
public interface DeprecatedInterface {
  /**
   * @deprecated method deprecated info
   */
  @Deprecated
  void test();
}
