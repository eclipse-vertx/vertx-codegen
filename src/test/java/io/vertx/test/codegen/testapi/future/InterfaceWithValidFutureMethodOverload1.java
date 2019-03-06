package io.vertx.test.codegen.testapi.future;

import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;

@VertxGen
public interface InterfaceWithValidFutureMethodOverload1 {

  void method(Handler<AsyncResult<String>> the_handler);
  Future<String> method();

}
