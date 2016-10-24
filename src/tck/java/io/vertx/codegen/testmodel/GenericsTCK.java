package io.vertx.codegen.testmodel;

import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@VertxGen
public interface GenericsTCK {

  GenericRefedInterface<Byte> methodWithByteParameterizedReturn();
  GenericRefedInterface<Short> methodWithShortParameterizedReturn();
  GenericRefedInterface<Integer> methodWithIntegerParameterizedReturn();
  GenericRefedInterface<Long> methodWithLongParameterizedReturn();
  GenericRefedInterface<Float> methodWithFloatParameterizedReturn();
  GenericRefedInterface<Double> methodWithDoubleParameterizedReturn();
  GenericRefedInterface<Boolean> methodWithBooleanParameterizedReturn();
  GenericRefedInterface<Character> methodWithCharacterParameterizedReturn();
  GenericRefedInterface<String> methodWithStringParameterizedReturn();
  GenericRefedInterface<JsonObject> methodWithJsonObjectParameterizedReturn();
  GenericRefedInterface<JsonArray> methodWithJsonArrayParameterizedReturn();
  GenericRefedInterface<TestDataObject> methodWithDataObjectParameterizedReturn();
  GenericRefedInterface<TestEnum> methodWithEnumParameterizedReturn();
  GenericRefedInterface<TestGenEnum> methodWithGenEnumParameterizedReturn();
  GenericRefedInterface<RefedInterface1> methodWithUserTypeParameterizedReturn();

  void methodWithHandlerByteParameterized(Handler<GenericRefedInterface<Byte>> handler);
  void methodWithHandlerShortParameterized(Handler<GenericRefedInterface<Short>> handler);
  void methodWithHandlerIntegerParameterized(Handler<GenericRefedInterface<Integer>> handler);
  void methodWithHandlerLongParameterized(Handler<GenericRefedInterface<Long>> handler);
  void methodWithHandlerFloatParameterized(Handler<GenericRefedInterface<Float>> handler);
  void methodWithHandlerDoubleParameterized(Handler<GenericRefedInterface<Double>> handler);
  void methodWithHandlerBooleanParameterized(Handler<GenericRefedInterface<Boolean>> handler);
  void methodWithHandlerCharacterParameterized(Handler<GenericRefedInterface<Character>> handler);
  void methodWithHandlerStringParameterized(Handler<GenericRefedInterface<String>> handler);
  void methodWithHandlerJsonObjectParameterized(Handler<GenericRefedInterface<JsonObject>> handler);
  void methodWithHandlerJsonArrayParameterized(Handler<GenericRefedInterface<JsonArray>> handler);
  void methodWithHandlerDataObjectParameterized(Handler<GenericRefedInterface<TestDataObject>> handler);
  void methodWithHandlerEnumParameterized(Handler<GenericRefedInterface<TestEnum>> handler);
  void methodWithHandlerGenEnumParameterized(Handler<GenericRefedInterface<TestGenEnum>> handler);
  void methodWithHandlerUserTypeParameterized(Handler<GenericRefedInterface<RefedInterface1>> handler);

  void methodWithHandlerAsyncResultByteParameterized(Handler<AsyncResult<GenericRefedInterface<Byte>>> handler);
  void methodWithHandlerAsyncResultShortParameterized(Handler<AsyncResult<GenericRefedInterface<Short>>> handler);
  void methodWithHandlerAsyncResultIntegerParameterized(Handler<AsyncResult<GenericRefedInterface<Integer>>> handler);
  void methodWithHandlerAsyncResultLongParameterized(Handler<AsyncResult<GenericRefedInterface<Long>>> handler);
  void methodWithHandlerAsyncResultFloatParameterized(Handler<AsyncResult<GenericRefedInterface<Float>>> handler);
  void methodWithHandlerAsyncResultDoubleParameterized(Handler<AsyncResult<GenericRefedInterface<Double>>> handler);
  void methodWithHandlerAsyncResultBooleanParameterized(Handler<AsyncResult<GenericRefedInterface<Boolean>>> handler);
  void methodWithHandlerAsyncResultCharacterParameterized(Handler<AsyncResult<GenericRefedInterface<Character>>> handler);
  void methodWithHandlerAsyncResultStringParameterized(Handler<AsyncResult<GenericRefedInterface<String>>> handler);
  void methodWithHandlerAsyncResultJsonObjectParameterized(Handler<AsyncResult<GenericRefedInterface<JsonObject>>> handler);
  void methodWithHandlerAsyncResultJsonArrayParameterized(Handler<AsyncResult<GenericRefedInterface<JsonArray>>> handler);
  void methodWithHandlerAsyncResultDataObjectParameterized(Handler<AsyncResult<GenericRefedInterface<TestDataObject>>> handler);
  void methodWithHandlerAsyncResultEnumParameterized(Handler<AsyncResult<GenericRefedInterface<TestEnum>>> handler);
  void methodWithHandlerAsyncResultGenEnumParameterized(Handler<AsyncResult<GenericRefedInterface<TestGenEnum>>> handler);
  void methodWithHandlerAsyncResultUserTypeParameterized(Handler<AsyncResult<GenericRefedInterface<RefedInterface1>>> handler);

  <U> GenericRefedInterface<U> methodWithClassTypeParameterizedReturn(Class<U> type);
  <U> void methodWithHandlerClassTypeParameterized(Class<U> type, Handler<GenericRefedInterface<U>> handler);
  <U> void methodWithHandlerAsyncResultClassTypeParameterized(Class<U> type, Handler<AsyncResult<GenericRefedInterface<U>>> handler);

  InterfaceWithApiArg interfaceWithApiArg(RefedInterface1 value);
  InterfaceWithStringArg interfaceWithStringArg(String value);
  <T, U> InterfaceWithVariableArg<T, U> interfaceWithVariableArg(T value1, Class<U> type, U value2);

}
