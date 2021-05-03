package io.vertx.test.codegen.nextgen;

import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
@VertxGen
public interface MethodWithCallback {

  //
  void methodWithFuture(Handler<AsyncResult<String>> handler);
}
