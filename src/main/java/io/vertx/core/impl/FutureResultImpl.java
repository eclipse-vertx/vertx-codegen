package io.vertx.core.impl;

import io.vertx.core.AsyncResult;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class FutureResultImpl<T> implements AsyncResult<T> {

  public FutureResultImpl(T t) {
  }

  public FutureResultImpl(Throwable t) {
  }
}
