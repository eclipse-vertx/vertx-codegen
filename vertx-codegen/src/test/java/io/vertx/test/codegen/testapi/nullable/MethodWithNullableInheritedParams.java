package io.vertx.test.codegen.testapi.nullable;

import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

import java.util.List;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@VertxGen
public interface MethodWithNullableInheritedParams extends MethodWithNullableParams {

  @Override
  void m(String s, List<String> t, Handler<AsyncResult<String>> u);
}
