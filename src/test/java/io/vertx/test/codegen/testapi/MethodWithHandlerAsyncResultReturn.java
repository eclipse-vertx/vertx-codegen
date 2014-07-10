package io.vertx.test.codegen.testapi;

import io.vertx.codegen.testmodel.AsyncResult;
import io.vertx.codegen.testmodel.Handler;
import io.vertx.codegen.annotations.VertxGen;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
@VertxGen
public interface MethodWithHandlerAsyncResultReturn {

  Handler<AsyncResult<String>> foo(String param);
}
