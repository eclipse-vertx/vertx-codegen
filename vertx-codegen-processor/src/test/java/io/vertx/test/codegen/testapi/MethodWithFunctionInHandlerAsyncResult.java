package io.vertx.test.codegen.testapi;

import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

import java.util.function.Function;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
@VertxGen
public interface MethodWithFunctionInHandlerAsyncResult {

  void foo(Handler<AsyncResult<Function<String, String>>> handler);
}
