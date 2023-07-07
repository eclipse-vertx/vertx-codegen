package io.vertx.test.codegen.testapi.nullable;

import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.Future;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@VertxGen
public interface MethodWithNullableNonAnnotatedTypeVariableFuture {

  <T> Future<T> method();

}
