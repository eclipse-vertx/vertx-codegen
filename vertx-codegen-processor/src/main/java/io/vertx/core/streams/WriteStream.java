package io.vertx.core.streams;

import io.vertx.codegen.annotations.Fluent;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.Handler;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@VertxGen(concrete = false)
public interface WriteStream<T> {

  void handler(Handler<T> handler);

  @Fluent
  WriteStream<T> exceptionHandler(Handler<Throwable> handler);
}
