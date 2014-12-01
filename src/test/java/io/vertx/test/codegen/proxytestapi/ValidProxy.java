package io.vertx.test.codegen.proxytestapi;

import io.vertx.codegen.annotations.ProxyClose;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.ProxyIgnore;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

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

  void methodWithListParams(List<String> listString, List<Byte> listByte, List<Short> listShort, List<Integer> listInt, List<Long> listLong, List<JsonObject> listJsonObject, List<JsonArray> listJsonArray);

  void methodWithSetParams(Set<String> setString, Set<Byte> setByte, Set<Short> setShort, Set<Integer> setInt, Set<Long> setLong, Set<JsonObject> setJsonObject, Set<JsonArray> setJsonArray);

  void methodWithMapParams(Map<String, String> mapString, Map<String, Byte> mapByte, Map<String, Short> mapShort, Map<String, Integer> mapInt, Map<String, Long> mapLong, Map<String, JsonObject> mapJsonObject, Map<String, JsonArray> mapJsonArray);

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

  void handler24(Handler<AsyncResult<Set<String>>> stringSetHandler);
  void handler25(Handler<AsyncResult<Set<Byte>>> byteSetHandler);
  void handler26(Handler<AsyncResult<Set<Short>>> shortSetHandler);
  void handler27(Handler<AsyncResult<Set<Integer>>> intSetHandler);
  void handler28(Handler<AsyncResult<Set<Long>>> longSetHandler);
  void handler29(Handler<AsyncResult<Set<Float>>> floatSetHandler);
  void handler30(Handler<AsyncResult<Set<Double>>> doubleSetHandler);
  void handler31(Handler<AsyncResult<Set<Character>>> charSetHandler);
  void handler32(Handler<AsyncResult<Set<Boolean>>> boolSetHandler);
  void handler33(Handler<AsyncResult<Set<JsonObject>>> jsonObjectSetHandler);
  void handler34(Handler<AsyncResult<Set<JsonArray>>> jsonArraySetHandler);

  @ProxyIgnore
  void ignored();

  @ProxyClose
  void closeIt();

  void connection(String foo, Handler<AsyncResult<ProxyConnection>> resultHandler);

}
