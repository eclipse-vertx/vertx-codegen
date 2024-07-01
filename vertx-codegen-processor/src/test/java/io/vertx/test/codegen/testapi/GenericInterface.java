package io.vertx.test.codegen.testapi;

import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.Handler;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
@VertxGen
public interface GenericInterface<T> {

  T methodWithClassTypeParam(T t, Handler<T> handler);

  <R> GenericInterface<R> someGenericMethod(R r, Handler<R> handler);

}
