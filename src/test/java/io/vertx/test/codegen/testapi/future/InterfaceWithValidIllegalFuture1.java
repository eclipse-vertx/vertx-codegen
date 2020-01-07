package io.vertx.test.codegen.testapi.future;

import io.vertx.codegen.annotations.GenIgnore;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;

import java.util.List;

@VertxGen
public interface InterfaceWithValidIllegalFuture1 {

  // Future<List<String>> is an illegal type but Handler<AsyncResult<List<String>>> is
  void method1(Handler<AsyncResult<List<String>>> the_handler);
  Future<List<String>> method1();

  // Future<List<Thread>> is an illegal type but Handler<AsyncResult<List<Thread>>> is
  @GenIgnore(GenIgnore.PERMITTED_TYPE) void method2(Handler<AsyncResult<List<Thread>>> the_handler);
  @GenIgnore(GenIgnore.PERMITTED_TYPE) Future<List<Thread>> method2();

}
