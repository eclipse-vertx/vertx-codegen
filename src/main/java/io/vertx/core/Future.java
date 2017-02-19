package io.vertx.core;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public interface Future<T> extends AsyncResult<T>, Handler<AsyncResult<T>> {

  static <T> Future<T> succeededFuture(T result) {
    throw new UnsupportedOperationException();
  }

  static <T> Future<T> failedFuture(Throwable t) {
    throw new UnsupportedOperationException();
  }
}
