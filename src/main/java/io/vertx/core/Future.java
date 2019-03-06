package io.vertx.core;

import io.vertx.codegen.annotations.GenIgnore;
import io.vertx.codegen.annotations.VertxGen;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@VertxGen
public interface Future<T> extends AsyncResult<T>, Handler<AsyncResult<T>> {

  static <T> Future<T> succeededFuture(T result) {
    throw new UnsupportedOperationException();
  }

  static <T> Future<T> failedFuture(Throwable t) {
    throw new UnsupportedOperationException();
  }

  @GenIgnore
  void handle(AsyncResult<T> tAsyncResult);
}
