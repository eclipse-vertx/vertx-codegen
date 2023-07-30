package io.vertx.core;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public interface Handler<T> {
  void handle(T t);
}
