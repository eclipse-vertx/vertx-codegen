package io.vertx.test.codegen.testapi.future;

import io.vertx.codegen.annotations.VertxGen;
import io.vertx.codegen.testmodel.TestDataObject;
import io.vertx.codegen.testmodel.TestEnum;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.test.codegen.testapi.VertxGenClass1;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
@VertxGen
public interface MethodWithValidFutureReturn {

  Future<Byte> regularByteFuture();
  Future<Short> regularShortFuture();
  Future<Integer> regularIntegerFuture();
  Future<Long> regularLongFuture();
  Future<Float> regularFloatFuture();
  Future<Double> regularDoubleFuture();
  Future<Boolean> regularBooleanFuture();
  Future<Character> regularCharacterFuture();
  Future<String> regularStringFuture();
  Future<VertxGenClass1> regularVertxGenFuture();
  Future<JsonObject> regularJsonObjectFuture();
  Future<JsonArray> regularJsonArrayFuture();
  Future<Void> regularVoidFuture();
  Future<TestDataObject> regularDataObjectFuture();
  Future<TestEnum> regularEnumFuture();

  Future<Byte> byteFuture();
  void byteFuture(Handler<AsyncResult<Byte>> handler);
  Future<Short> shortFuture();
  void shortFuture(Handler<AsyncResult<Short>> handler);
  Future<Integer> integerFuture();
  void integerFuture(Handler<AsyncResult<Integer>> handler);
  Future<Long> longFuture();
  void longFuture(Handler<AsyncResult<Long>> handler);
  Future<Float> floatFuture();
  void floatFuture(Handler<AsyncResult<Float>> handler);
  Future<Double> doubleFuture();
  void doubleFuture(Handler<AsyncResult<Double>> handler);
  Future<Boolean> booleanFuture();
  void booleanFuture(Handler<AsyncResult<Boolean>> handler);
  Future<Character> characterFuture();
  void characterFuture(Handler<AsyncResult<Character>> handler);
  Future<String> stringFuture();
  void stringFuture(Handler<AsyncResult<String>> handler);
  Future<VertxGenClass1> vertxGenFuture();
  void vertxGenFuture(Handler<AsyncResult<VertxGenClass1>> handler);
  Future<JsonObject> jsonObjectFuture();
  void jsonObjectFuture(Handler<AsyncResult<JsonObject>> handler);
  Future<JsonArray> jsonArrayFuture();
  void jsonArrayFuture(Handler<AsyncResult<JsonArray>> handler);
  Future<Void> voidFuture();
  void voidFuture(Handler<AsyncResult<Void>> handler);
  Future<TestDataObject> dataObjectFuture();
  void dataObjectFuture(Handler<AsyncResult<TestDataObject>> handler);
  Future<TestEnum> enumFuture();
  void enumFuture(Handler<AsyncResult<TestEnum>> handler);
}
