package io.vertx.test.codegen.testapi;

import io.vertx.codegen.annotations.VertxGen;
import io.vertx.codegen.testmodel.TestDataObject;
import io.vertx.codegen.testmodel.TestEnum;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
@VertxGen
public interface MethodWithValidHandlerParams {

  void methodWithHandlerParams(Handler<Byte> byteHandler, Handler<Short> shortHandler, Handler<Integer> intHandler,
                               Handler<Long> longHandler, Handler<Float> floatHandler, Handler<Double> doubleHandler,
                               Handler<Boolean> booleanHandler, Handler<Character> charHandler, Handler<String> strHandler,
                               Handler<VertxGenClass1> gen1Handler, Handler<VertxGenClass2> gen2Handler,
                               Handler<Void> voidHandler, Handler<Throwable> throwableHandler, Handler<TestDataObject> dataObjectHandler,
                               Handler<TestEnum> enumHandler);

  void methodWithListHandlerParams(Handler<List<Byte>> listByteHandler, Handler<List<Short>> listShortHandler, Handler<List<Integer>> listIntHandler,
                               Handler<List<Long>> listLongHandler, Handler<List<Float>> listFloatHandler, Handler<List<Double>> listDoubleHandler,
                               Handler<List<Boolean>> listBooleanHandler, Handler<List<Character>> listCharHandler, Handler<List<String>> listStrHandler,
                               Handler<List<VertxGenClass1>> listVertxGenHandler, Handler<List<JsonObject>> listJsonObjectHandler, Handler<List<JsonArray>> listJsonArrayHandler,
                               Handler<List<TestDataObject>> listDataObjectHandler, Handler<List<TestEnum>> listEnumHandler);

  void methodWithSetHandlerParams(Handler<Set<Byte>> setByteHandler, Handler<Set<Short>> setShortHandler, Handler<Set<Integer>> setIntHandler,
                                   Handler<Set<Long>> setLongHandler, Handler<Set<Float>> setFloatHandler, Handler<Set<Double>> setDoubleHandler,
                                   Handler<Set<Boolean>> setBooleanHandler, Handler<Set<Character>> setCharHandler, Handler<Set<String>> setStrHandler,
                                   Handler<Set<VertxGenClass1>> setVertxGenHandler, Handler<Set<JsonObject>> setJsonObjectHandler, Handler<Set<JsonArray>> setJsonArrayHandler,
                                   Handler<Set<TestDataObject>> setDataObjectHandler, Handler<Set<TestEnum>> setEnumHandler);

  void methodWithMapHandlerParams(Handler<Map<String,Byte>> mapByteHandler, Handler<Map<String,Short>> mapShortHandler, Handler<Map<String,Integer>> mapIntHandler,
                                  Handler<Map<String,Long>> mapLongHandler, Handler<Map<String,Float>> mapFloatHandler, Handler<Map<String,Double>> mapDoubleHandler,
                                  Handler<Map<String,Boolean>> mapBooleanHandler, Handler<Map<String,Character>> mapCharHandler, Handler<Map<String,String>> mapStrHandler,
                                  Handler<Map<String,JsonObject>> mapJsonObjectHandler, Handler<Map<String,JsonArray>> mapJsonArrayHandler);
}
