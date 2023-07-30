package io.vertx.test.codegen.testapi;

import io.vertx.codegen.annotations.VertxGen;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@VertxGen
public interface InterfaceWithMethodOverride extends VertxGenInterface {
  @Override
  void bar(String str);

  void foo(String str);
}
