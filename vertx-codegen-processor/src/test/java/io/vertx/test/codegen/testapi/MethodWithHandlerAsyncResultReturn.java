package io.vertx.test.codegen.testapi;

import io.vertx.codegen.annotations.GenIgnore;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.codegen.annotations.VertxGen;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
@VertxGen
public interface MethodWithHandlerAsyncResultReturn {

  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  Handler<AsyncResult<String>> methodWithHandlerAsyncResultStringReturn();
}
