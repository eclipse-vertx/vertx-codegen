package io.vertx.test.codegen.testapi;

import io.vertx.codegen.annotations.GenIgnore;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@VertxGen
public interface RecursiveFuture extends Future<RecursiveFuture> {

  @GenIgnore
  @Override
  void handle(AsyncResult<RecursiveFuture> recursiveFutureAsyncResult);

  @Override
  boolean succeeded();

  @Override
  RecursiveFuture result();

  @Override
  Throwable cause();
}
