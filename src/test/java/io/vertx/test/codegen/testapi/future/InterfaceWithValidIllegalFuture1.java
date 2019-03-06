package io.vertx.test.codegen.testapi.future;

import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;

import java.util.List;

@VertxGen
public interface InterfaceWithValidIllegalFuture1 {

  // Future<List<String>> is an illegal type but Handler<AsyncResult<List<String>>> is
  void method(Handler<AsyncResult<List<String>>> the_handler);
  Future<List<String>> method();

}
