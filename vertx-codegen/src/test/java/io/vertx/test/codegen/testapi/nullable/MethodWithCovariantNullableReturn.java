package io.vertx.test.codegen.testapi.nullable;

import io.vertx.codegen.annotations.VertxGen;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@VertxGen
public interface MethodWithCovariantNullableReturn<T> extends MethodWithNullableReturn {

  MethodWithCovariantNullableReturn<String> exceptionHandler();

}
