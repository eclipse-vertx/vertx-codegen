package io.vertx.test.codegen.testapi.nullable;

import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.Handler;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@VertxGen
public interface MethodWithNullableNonAnnotatedTypeVariableHandler {

  <T> void method(Handler<T> s);

}
