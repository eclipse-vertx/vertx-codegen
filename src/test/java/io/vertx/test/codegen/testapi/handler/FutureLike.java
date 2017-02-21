package io.vertx.test.codegen.testapi.handler;

import io.vertx.codegen.annotations.GenIgnore;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@VertxGen
public interface FutureLike<T> extends AsyncResult<T>, Handler<AsyncResult<T>> {

  @GenIgnore
  @Override
  void handle(AsyncResult<T> asyncResult);

  @Override
  boolean succeeded();

  @Override
  boolean failed();

  @Override
  T result();

  @Override
  Throwable cause();
}
