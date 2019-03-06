package io.vertx.test.codegen.testapi;

import io.vertx.codegen.annotations.GenIgnore;
import io.vertx.codegen.testmodel.TestDataObject;
import io.vertx.codegen.testmodel.TestEnum;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
@VertxGen
public interface MethodWithValidHandlerAsyncResultParams {

  void methodWithHandlerParams(
    Handler<AsyncResult<Byte>> byteHandler,
    Handler<AsyncResult<Short>> shortHandler,
    Handler<AsyncResult<Integer>> intHandler,
    Handler<AsyncResult<Long>> longHandler,
    Handler<AsyncResult<Float>> floatHandler,
    Handler<AsyncResult<Double>> doubleHandler,
    Handler<AsyncResult<Boolean>> booleanHandler,
    Handler<AsyncResult<Character>> charHandler,
    Handler<AsyncResult<String>> strHandler,
    Handler<AsyncResult<VertxGenClass1>> gen1Handler,
    Handler<AsyncResult<VertxGenClass2>> gen2Handler,
    Handler<AsyncResult<JsonObject>> jsonObjectHandler,
    Handler<AsyncResult<JsonArray>> jsonArrayHandler,
    Handler<AsyncResult<Void>> voidHandler,
    Handler<AsyncResult<TestDataObject>> dataObjectHandler,
    Handler<AsyncResult<TestEnum>> enumHandler,
    Handler<AsyncResult<Object>> objectHandler);

  void methodWithListHandlerParams(
    Handler<AsyncResult<List<Byte>>> listByteHandler,
    Handler<AsyncResult<List<Short>>> listShortHandler,
    Handler<AsyncResult<List<Integer>>> listIntHandler,
    Handler<AsyncResult<List<Long>>> listLongHandler,
    Handler<AsyncResult<List<Float>>> listFloatHandler,
    Handler<AsyncResult<List<Double>>> listDoubleHandler,
    Handler<AsyncResult<List<Boolean>>> listBooleanHandler,
    Handler<AsyncResult<List<Character>>> listCharHandler,
    Handler<AsyncResult<List<String>>> listStrHandler,
    Handler<AsyncResult<List<VertxGenClass1>>> listGen1Handler,
    Handler<AsyncResult<List<VertxGenClass2>>> listGen2Handler,
    Handler<AsyncResult<List<JsonObject>>> listJsonObjectHandler,
    Handler<AsyncResult<List<JsonArray>>> listJsonArrayHandler,
    // Handler<AsyncResult<List<Void>>> listVoidHandler,
    Handler<AsyncResult<List<TestDataObject>>> listDataObjectHandler,
    Handler<AsyncResult<List<TestEnum>>> listEnumHandler,
    Handler<AsyncResult<List<Object>>> listObjectHandler);

  void methodWithSetHandlerParams(
    Handler<AsyncResult<Set<Byte>>> setByteHandler,
    Handler<AsyncResult<Set<Short>>> setShortHandler,
    Handler<AsyncResult<Set<Integer>>> setIntHandler,
    Handler<AsyncResult<Set<Long>>> setLongHandler,
    Handler<AsyncResult<Set<Float>>> setFloatHandler,
    Handler<AsyncResult<Set<Double>>> setDoubleHandler,
    Handler<AsyncResult<Set<Boolean>>> setBooleanHandler,
    Handler<AsyncResult<Set<Character>>> setCharHandler,
    Handler<AsyncResult<Set<String>>> setStrHandler,
    Handler<AsyncResult<Set<VertxGenClass1>>> setGen1Handler,
    Handler<AsyncResult<Set<VertxGenClass2>>> setGen2Handler,
    Handler<AsyncResult<Set<JsonObject>>> setJsonObjectHandler,
    Handler<AsyncResult<Set<JsonArray>>> setJsonArrayHandler,
    // Handler<AsyncResult<Set<Void>>> setVoidHandler,
    Handler<AsyncResult<Set<TestDataObject>>> setDataObjectHandler,
    Handler<AsyncResult<Set<TestEnum>>> setEnumHandler,
    Handler<AsyncResult<Set<Object>>> setObjectHandler);


  void methodWithMapHandlerParams(
    Handler<AsyncResult<Map<String, Byte>>> mapByteHandler,
    Handler<AsyncResult<Map<String, Short>>> mapShortHandler,
    Handler<AsyncResult<Map<String, Integer>>> mapIntHandler,
    Handler<AsyncResult<Map<String, Long>>> mapLongHandler,
    Handler<AsyncResult<Map<String, Float>>> mapFloatHandler,
    Handler<AsyncResult<Map<String, Double>>> mapDoubleHandler,
    Handler<AsyncResult<Map<String, Boolean>>> mapBooleanHandler,
    Handler<AsyncResult<Map<String, Character>>> mapCharHandler,
    Handler<AsyncResult<Map<String, String>>> mapStrHandler,
    Handler<AsyncResult<Map<String, VertxGenClass1>>> mapGen1Handler,
    Handler<AsyncResult<Map<String, VertxGenClass2>>> mapGen2Handler,
    Handler<AsyncResult<Map<String, JsonObject>>> mapJsonObjectHandler,
    Handler<AsyncResult<Map<String, JsonArray>>> mapJsonArrayHandler,
    // Handler<AsyncResult<Map<String, Void>>> mapVoidHandler,
    Handler<AsyncResult<Map<String, TestDataObject>>> mapDataObjectHandler,
    Handler<AsyncResult<Map<String, TestEnum>>> mapEnumHandler,
    Handler<AsyncResult<Map<String, Object>>> mapObjectHandler);


  void methodWithGenericHandlerParams(
    Handler<AsyncResult<GenericInterface<Byte>>> genericByteHandler,
    Handler<AsyncResult<GenericInterface<Short>>> genericShortHandler,
    Handler<AsyncResult<GenericInterface<Integer>>> genericIntHandler,
    Handler<AsyncResult<GenericInterface<Long>>> genericLongHandler,
    Handler<AsyncResult<GenericInterface<Float>>> genericFloatHandler,
    Handler<AsyncResult<GenericInterface<Double>>> genericDoubleHandler,
    Handler<AsyncResult<GenericInterface<Boolean>>> genericBooleanHandler,
    Handler<AsyncResult<GenericInterface<Character>>> genericCharHandler,
    Handler<AsyncResult<GenericInterface<String>>> genericStrHandler,
    Handler<AsyncResult<GenericInterface<VertxGenClass1>>> genericGen1Handler,
    Handler<AsyncResult<GenericInterface<VertxGenClass2>>> genericGen2Handler,
    Handler<AsyncResult<GenericInterface<JsonObject>>> genericJsonObjectHandler,
    Handler<AsyncResult<GenericInterface<JsonArray>>> genericJsonArrayHandler,
    Handler<AsyncResult<GenericInterface<Void>>> genericVoidHandler,
    Handler<AsyncResult<GenericInterface<TestDataObject>>> genericDataObjectHandler,
    Handler<AsyncResult<GenericInterface<TestEnum>>> genericEnumHandler/*,
    Handler<AsyncResult<GenericInterface<Object>>> genericObjectHandler*/);
}
