package io.vertx.core;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public interface AsyncResult<T> {

  boolean succeeded();

  boolean failed();

  T result();

  Throwable cause();

}
