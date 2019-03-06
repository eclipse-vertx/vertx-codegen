package io.vertx.test.codegen.testapi.future;

import io.vertx.codegen.annotations.Fluent;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;

@VertxGen
public interface InterfaceWithValidFluentFutureMethodOverload1 {

  @Fluent
  InterfaceWithValidFluentFutureMethodOverload1 method(Handler<AsyncResult<String>> the_handler);
  Future<String> method();

  @Fluent
  InterfaceWithValidFluentFutureMethodOverload1 method(String s, Handler<AsyncResult<String>> the_handler);
  Future<String> method(String s);
}
