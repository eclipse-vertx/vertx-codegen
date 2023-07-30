package io.vertx.test.codegen.testapi;

import io.vertx.codegen.annotations.Fluent;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.Handler;
import io.vertx.core.streams.WriteStream;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@VertxGen
public interface MethodWithTypeVarParamByGenericType<K, V> extends WriteStream<GenericInterface2<K, V>> {

  @Override
  void handler(Handler<GenericInterface2<K, V>> handler);

  @Fluent
  MethodWithTypeVarParamByGenericType<K, V> exceptionHandler(Handler<Throwable> handler);
}
