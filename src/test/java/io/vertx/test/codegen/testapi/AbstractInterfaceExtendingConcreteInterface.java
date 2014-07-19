package io.vertx.test.codegen.testapi;

import io.vertx.codegen.annotations.VertxGen;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@VertxGen(concrete = false)
public interface AbstractInterfaceExtendingConcreteInterface extends VertxGenClass1 {
  void foo(String str);
}
