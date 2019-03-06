package io.vertx.test.codegen.testapi.future;

import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;

@VertxGen
public interface InterfaceWithValidFutureMethodOverload2 {

  Future<String> method();
  void method(Handler<AsyncResult<String>> the_handler);

}
