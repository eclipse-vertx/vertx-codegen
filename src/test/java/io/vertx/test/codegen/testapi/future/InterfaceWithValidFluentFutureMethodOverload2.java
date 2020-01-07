package io.vertx.test.codegen.testapi.future;

import io.vertx.codegen.annotations.Fluent;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;

@VertxGen
public interface InterfaceWithValidFluentFutureMethodOverload2 {

  Future<String> method();
  @Fluent
  InterfaceWithValidFluentFutureMethodOverload2 method(Handler<AsyncResult<String>> the_handler);

  Future<String> method(String s);
  @Fluent
  InterfaceWithValidFluentFutureMethodOverload2 method(String s, Handler<AsyncResult<String>> the_handler);
}
