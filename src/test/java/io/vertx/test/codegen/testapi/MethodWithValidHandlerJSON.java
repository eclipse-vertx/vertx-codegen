package io.vertx.test.codegen.testapi;

import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
@VertxGen
public interface MethodWithValidHandlerJSON {

  void methodWithJsonHandlers(Handler<JsonObject> jsonObjectHandler, Handler<JsonArray> jsonArrayHandler);
}
