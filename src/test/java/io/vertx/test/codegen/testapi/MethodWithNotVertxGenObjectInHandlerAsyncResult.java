package io.vertx.test.codegen.testapi;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.gen.VertxGen;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
@VertxGen
public interface MethodWithNotVertxGenObjectInHandlerAsyncResult {

  void foo(Handler<AsyncResult<NonVertxGenInterface>> handler);
}
