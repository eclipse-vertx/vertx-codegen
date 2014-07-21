package io.vertx.test.codegen.testapi;

import io.vertx.codegen.annotations.VertxGen;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@VertxGen
public interface InterfaceWithMethodOverride extends VertxGenInterface1, VertxGenInterface2 {
  @Override
  void bar(String str);
  @Override
  void juu(String str_renamed);
}
