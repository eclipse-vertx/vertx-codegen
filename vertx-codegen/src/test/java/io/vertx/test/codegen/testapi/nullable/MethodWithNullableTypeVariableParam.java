package io.vertx.test.codegen.testapi.nullable;

import io.vertx.codegen.annotations.Nullable;
import io.vertx.codegen.annotations.VertxGen;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@VertxGen
public interface MethodWithNullableTypeVariableParam {

  <T> void method(@Nullable T s);

}
