package io.vertx.test.codegen.testapi.nullable;

import io.vertx.codegen.annotations.Nullable;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.Future;
import io.vertx.core.Handler;

import java.util.function.Function;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@VertxGen
public interface MethodWithFutureNullableArgument {

  Future<@Nullable String> m1();
  void m2(Future<@Nullable String> arg);
  void m3(Function<Future<@Nullable String>, Future<@Nullable String>> f);

}
