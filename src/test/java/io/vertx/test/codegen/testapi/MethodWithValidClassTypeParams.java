package io.vertx.test.codegen.testapi;

import io.vertx.codegen.annotations.VertxGen;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@VertxGen
public interface MethodWithValidClassTypeParams {

  <T> void methodParam(T param, Class<T> type);
  <T> T returnParam(Class<T> type);

}
