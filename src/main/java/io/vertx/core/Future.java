package io.vertx.core;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public interface Future<T> extends AsyncResult<T> {

  static <T> Future<T> completedFuture(T result) {
    throw new UnsupportedOperationException();
  }

  static <T> Future<T> completedFuture(Throwable t) {
    throw new UnsupportedOperationException();
  }
}
