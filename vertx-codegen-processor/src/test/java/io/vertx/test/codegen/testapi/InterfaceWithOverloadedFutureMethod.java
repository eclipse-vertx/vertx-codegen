package io.vertx.test.codegen.testapi;

import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@VertxGen
public interface InterfaceWithOverloadedFutureMethod {

  void close();

  void close(Handler<AsyncResult<String>> handler);

  void foo(String s);

  void foo(String s, Handler<AsyncResult<String>> handler);

}
