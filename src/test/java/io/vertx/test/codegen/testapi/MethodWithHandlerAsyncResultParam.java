package io.vertx.test.codegen.testapi;

import io.vertx.codegen.annotations.Fluent;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@VertxGen
public interface MethodWithHandlerAsyncResultParam {

  void foo_1(Handler<AsyncResult<String>> handler);
  void foo_2(String s, Handler<AsyncResult<String>> handler);
  void foo_3(Handler<String> handler1, Handler<AsyncResult<String>> handler2);

  @Fluent MethodWithHandlerAsyncResultParam foo_4(Handler<AsyncResult<String>> handler);
  @Fluent MethodWithHandlerAsyncResultParam foo_5(String s, Handler<AsyncResult<String>> handler);
  @Fluent MethodWithHandlerAsyncResultParam foo_6(Handler<String> handler1, Handler<AsyncResult<String>> handler2);

  // Unrecognized
  String foo_7(Handler<AsyncResult<String>> handler);
  void foo_8(Handler<AsyncResult<String>> handler, String s);

}
