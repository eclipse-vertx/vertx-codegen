package io.vertx.codegen.testmodel;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public interface Handler<T> {
  void handle(T t);
}
