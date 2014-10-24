package io.vertx.test.codegen.proxytestapi;

import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.ProxyIgnore;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.util.List;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
@ProxyGen
public interface ValidProxy {

  void basicTypes(String str, byte b, short s, int i, long l, float f, double d, char c, boolean bool);

  void basicBoxedTypes(String str, Byte b, Short s, Integer i, Long l, Float f, Double d, Character c,
                       Boolean bool);

  void jsonTypes(JsonObject jsonObject, JsonArray jsonArray);

  void enumType(SomeEnum someEnum);

  void handlers(Handler<AsyncResult<String>> stringHandler, Handler<AsyncResult<Byte>> byteHandler, Handler<AsyncResult<Short>> shortHandler,
                Handler<AsyncResult<Integer>> intHandler, Handler<AsyncResult<Long>> longHandler, Handler<AsyncResult<Float>> floatHandler,
                Handler<AsyncResult<Double>> doubleHandler, Handler<AsyncResult<Character>> charHandler, Handler<AsyncResult<Boolean>> boolHandler,
                Handler<AsyncResult<JsonObject>> jsonObjectHandler, Handler<AsyncResult<JsonArray>> jsonArrayHandler,
                Handler<AsyncResult<SomeEnum>> enumHandler,
                Handler<AsyncResult<List<String>>> stringListHandler, Handler<AsyncResult<List<Byte>>> byteListHandler,
                Handler<AsyncResult<List<Short>>> shortListHandler, Handler<AsyncResult<List<Integer>>> intListHandler,
                Handler<AsyncResult<List<Long>>> longListHandler, Handler<AsyncResult<List<Float>>> floatListHandler,
                Handler<AsyncResult<List<Double>>> doubleListHandler, Handler<AsyncResult<List<Character>>> charListHandler,
                Handler<AsyncResult<List<Boolean>>> boolListHandler,
                Handler<AsyncResult<List<JsonObject>>> jsonObjectListHandler, Handler<AsyncResult<List<JsonArray>>> jsonArrayListHandler,
                Handler<AsyncResult<List<SomeEnum>>> enumListHandler);

  @ProxyIgnore
  void ignored();

}
