package io.vertx.test.codegen.proxytestapi;

import io.vertx.codegen.annotations.ProxyClose;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.ProxyIgnore;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.test.codegen.testapi.VertxGenClass1;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
@ProxyGen
public interface ValidProxy {

  void basicTypes(String str, byte b, short s, int i, long l, float f, double d, char c, boolean bool);

  void basicBoxedTypes(String str, Byte b, Short s, Integer i, Long l, Float f, Double d, Character c,
                       Boolean bool);

  void jsonTypes(JsonObject jsonObject, JsonArray jsonArray);

  void listTypes(List<String> listString, List<Long> listLong, List<JsonObject> listJsonObject, List<JsonArray> listJsonArray, List<VertxGenClass1> listVertxGen);

  void setTypes(Set<String> setString, Set<Long> setLong, Set<JsonObject> setJsonObject, Set<JsonArray> setJsonArray, Set<VertxGenClass1> setVertxGen);

  void mapTypes(Map<String, String> mapString, Map<String, Long> mapLong, Map<String, JsonObject> mapJsonObject, Map<String, JsonArray> mapJsonArray, Map<String, VertxGenClass1> mapVertxGen);

  void enumType(SomeEnum someEnum);

  void optionsType(ProxyOptions options);

  void handler0(Handler<AsyncResult<String>> stringHandler);
  void handler1(Handler<AsyncResult<Byte>> byteHandler);
  void handler2(Handler<AsyncResult<Short>> shortHandler);
  void handler3(Handler<AsyncResult<Integer>> intHandler);
  void handler4(Handler<AsyncResult<Long>> longHandler);
  void handler5(Handler<AsyncResult<Float>> floatHandler);
  void handler6(Handler<AsyncResult<Double>> doubleHandler);
  void handler7(Handler<AsyncResult<Character>> charHandler);
  void handler8(Handler<AsyncResult<Boolean>> boolHandler);
  void handler9(Handler<AsyncResult<JsonObject>> jsonObjectHandler);
  void handler10(Handler<AsyncResult<JsonArray>> jsonArrayHandler);
  void handler11(Handler<AsyncResult<SomeEnum>> enumHandler);
  void handler12(Handler<AsyncResult<List<String>>> stringListHandler);
  void handler13(Handler<AsyncResult<List<Byte>>> byteListHandler);
  void handler14(Handler<AsyncResult<List<Short>>> shortListHandler);
  void handler15(Handler<AsyncResult<List<Integer>>> intListHandler);
  void handler16(Handler<AsyncResult<List<Long>>> longListHandler);
  void handler17(Handler<AsyncResult<List<Float>>> floatListHandler);
  void handler18(Handler<AsyncResult<List<Double>>> doubleListHandler);
  void handler19(Handler<AsyncResult<List<Character>>> charListHandler);
  void handler20(Handler<AsyncResult<List<Boolean>>> boolListHandler);
  void handler21(Handler<AsyncResult<List<JsonObject>>> jsonObjectListHandler);
  void handler22(Handler<AsyncResult<List<JsonArray>>> jsonArrayListHandler);
  void handler23(Handler<AsyncResult<List<SomeEnum>>> enumListHandler);

  @ProxyIgnore
  void ignored();

  @ProxyClose
  void closeIt();

  ProxyConnection connection();

}
