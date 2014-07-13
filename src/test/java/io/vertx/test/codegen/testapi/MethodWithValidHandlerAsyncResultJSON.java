package io.vertx.test.codegen.testapi;

import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
@VertxGen
public interface MethodWithValidHandlerAsyncResultJSON {

  void methodwithJsonHandlersAsyncResult(Handler<AsyncResult<JsonObject>> jsonObjectHandler, Handler<AsyncResult<JsonArray>> jsonArrayHandler);
}
