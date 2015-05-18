package io.vertx.test.codegen.testapi;

import io.vertx.codegen.testmodel.TestDataObject;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.util.List;
import java.util.Set;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
@VertxGen
public interface MethodWithValidHandlerAsyncResultParams {

  void methodWithHandlerParams(Handler<AsyncResult<Byte>> byteHandler, Handler<AsyncResult<Short>> shortHandler, Handler<AsyncResult<Integer>> intHandler,
                               Handler<AsyncResult<Long>> longHandler, Handler<AsyncResult<Float>> floatHandler, Handler<AsyncResult<Double>> doubleHandler,
                               Handler<AsyncResult<Boolean>> booleanHandler, Handler<AsyncResult<Character>> charHandler, Handler<AsyncResult<String>> strHandler,
                               Handler<AsyncResult<VertxGenClass1>> gen1Handler, Handler<AsyncResult<VertxGenClass2>> gen2Handler,
                               Handler<AsyncResult<List<Byte>>> listByteHandler, Handler<AsyncResult<List<Short>>> listShortHandler, Handler<AsyncResult<List<Integer>>> listIntHandler,
                               Handler<AsyncResult<List<Long>>> listLongHandler, Handler<AsyncResult<List<Float>>> listFloatHandler, Handler<AsyncResult<List<Double>>> listDoubleHandler,
                               Handler<AsyncResult<List<Boolean>>> listBooleanHandler, Handler<AsyncResult<List<Character>>> listCharHandler, Handler<AsyncResult<List<String>>> listStrHandler,
                               Handler<AsyncResult<List<VertxGenClass1>>> listVertxGenHandler, Handler<AsyncResult<List<JsonObject>>> listJsonObjectHandler, Handler<AsyncResult<List<JsonArray>>> listJsonArrayHandler,
                               Handler<AsyncResult<Set<Byte>>> setByteHandler, Handler<AsyncResult<Set<Short>>> setShortHandler, Handler<AsyncResult<Set<Integer>>> setIntHandler,
                               Handler<AsyncResult<Set<Long>>> setLongHandler, Handler<AsyncResult<Set<Float>>> setFloatHandler, Handler<AsyncResult<Set<Double>>> setDoubleHandler,
                               Handler<AsyncResult<Set<Boolean>>> setBooleanHandler, Handler<AsyncResult<Set<Character>>> setCharHandler, Handler<AsyncResult<Set<String>>> setStrHandler,
                               Handler<AsyncResult<Set<VertxGenClass1>>> setVertxGenHandler, Handler<AsyncResult<Set<JsonObject>>> setJsonObjectHandler, Handler<AsyncResult<Set<JsonArray>>> setJsonArrayHandler,
                               Handler<AsyncResult<Void>> voidHandler, Handler<AsyncResult<TestDataObject>> dataObjectHandler, Handler<AsyncResult<List<TestDataObject>>> listDataObjectHandler,
                               Handler<AsyncResult<Set<TestDataObject>>> setDataObjectHandler);
}
