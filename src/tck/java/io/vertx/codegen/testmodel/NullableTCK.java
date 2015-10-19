package io.vertx.codegen.testmodel;

import io.vertx.codegen.annotations.Nullable;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The TCK for @Nullable.
 *
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@VertxGen
public interface NullableTCK {

  // Test @Nullable Byte type
  boolean methodWithNonNullableByteParam(Byte param);
  void methodWithNullableByteParam(boolean expectNull, @Nullable Byte param);
  void methodWithNullableByteHandler(boolean notNull, Handler<@Nullable Byte> handler);
  void methodWithNullableByteHandlerAsyncResult(boolean notNull, Handler<AsyncResult<@Nullable Byte>> handler);
  @Nullable Byte methodWithNullableByteReturn(boolean notNull);

  // Test @Nullable Short type
  boolean methodWithNonNullableShortParam(Short param);
  void methodWithNullableShortParam(boolean expectNull, @Nullable Short param);
  void methodWithNullableShortHandler(boolean notNull, Handler<@Nullable Short> handler);
  void methodWithNullableShortHandlerAsyncResult(boolean notNull, Handler<AsyncResult<@Nullable Short>> handler);
  @Nullable Short methodWithNullableShortReturn(boolean notNull);

  // Test @Nullable Integer type
  boolean methodWithNonNullableIntegerParam(Integer param);
  void methodWithNullableIntegerParam(boolean expectNull, @Nullable Integer param);
  void methodWithNullableIntegerHandler(boolean notNull, Handler<@Nullable Integer> handler);
  void methodWithNullableIntegerHandlerAsyncResult(boolean notNull, Handler<AsyncResult<@Nullable Integer>> handler);
  @Nullable Integer methodWithNullableIntegerReturn(boolean notNull);

  // Test @Nullable Long type
  boolean methodWithNonNullableLongParam(Long param);
  void methodWithNullableLongParam(boolean expectNull, @Nullable Long param);
  void methodWithNullableLongHandler(boolean notNull, Handler<@Nullable Long> handler);
  void methodWithNullableLongHandlerAsyncResult(boolean notNull, Handler<AsyncResult<@Nullable Long>> handler);
  @Nullable Long methodWithNullableLongReturn(boolean notNull);

  // Test @Nullable Float type
  boolean methodWithNonNullableFloatParam(Float param);
  void methodWithNullableFloatParam(boolean expectNull, @Nullable Float param);
  void methodWithNullableFloatHandler(boolean notNull, Handler<@Nullable Float> handler);
  void methodWithNullableFloatHandlerAsyncResult(boolean notNull, Handler<AsyncResult<@Nullable Float>> handler);
  @Nullable Float methodWithNullableFloatReturn(boolean notNull);

  // Test @Nullable Double type
  boolean methodWithNonNullableDoubleParam(Double param);
  void methodWithNullableDoubleParam(boolean expectNull, @Nullable Double param);
  void methodWithNullableDoubleHandler(boolean notNull, Handler<@Nullable Double> handler);
  void methodWithNullableDoubleHandlerAsyncResult(boolean notNull, Handler<AsyncResult<@Nullable Double>> handler);
  @Nullable Double methodWithNullableDoubleReturn(boolean notNull);

  // Test @Nullable Boolean type
  boolean methodWithNonNullableBooleanParam(Boolean param);
  void methodWithNullableBooleanParam(boolean expectNull, @Nullable Boolean param);
  void methodWithNullableBooleanHandler(boolean notNull, Handler<@Nullable Boolean> handler);
  void methodWithNullableBooleanHandlerAsyncResult(boolean notNull, Handler<AsyncResult<@Nullable Boolean>> handler);
  @Nullable Boolean methodWithNullableBooleanReturn(boolean notNull);

  // Test @Nullable String type
  boolean methodWithNonNullableStringParam(String param);
  void methodWithNullableStringParam(boolean expectNull, @Nullable String param);
  void methodWithNullableStringHandler(boolean notNull, Handler<@Nullable String> handler);
  void methodWithNullableStringHandlerAsyncResult(boolean notNull, Handler<AsyncResult<@Nullable String>> handler);
  @Nullable String methodWithNullableStringReturn(boolean notNull);

  // Test @Nullable Char type
  boolean methodWithNonNullableCharParam(Character param);
  void methodWithNullableCharParam(boolean expectNull, @Nullable Character param);
  void methodWithNullableCharHandler(boolean notNull, Handler<@Nullable Character> handler);
  void methodWithNullableCharHandlerAsyncResult(boolean notNull, Handler<AsyncResult<@Nullable Character>> handler);
  @Nullable Character methodWithNullableCharReturn(boolean notNull);

  // Test @Nullable JsonObject type
  boolean methodWithNonNullableJsonObjectParam(JsonObject param);
  void methodWithNullableJsonObjectParam(boolean expectNull, @Nullable JsonObject param);
  void methodWithNullableJsonObjectHandler(boolean notNull, Handler<@Nullable JsonObject> handler);
  void methodWithNullableJsonObjectHandlerAsyncResult(boolean notNull, Handler<AsyncResult<@Nullable JsonObject>> handler);
  @Nullable JsonObject methodWithNullableJsonObjectReturn(boolean notNull);

  // Test @Nullable JsonArray type
  boolean methodWithNonNullableJsonArrayParam(JsonArray param);
  void methodWithNullableJsonArrayParam(boolean expectNull, @Nullable JsonArray param);
  void methodWithNullableJsonArrayHandler(boolean notNull, Handler<@Nullable JsonArray> handler);
  void methodWithNullableJsonArrayHandlerAsyncResult(boolean notNull, Handler<AsyncResult<@Nullable JsonArray>> handler);
  @Nullable JsonArray methodWithNullableJsonArrayReturn(boolean notNull);

  // Test @Nullable Api type
  boolean methodWithNonNullableApiParam(RefedInterface1 param);
  void methodWithNullableApiParam(boolean expectNull, @Nullable RefedInterface1 param);
  void methodWithNullableApiHandler(boolean notNull, Handler<@Nullable RefedInterface1> handler);
  void methodWithNullableApiHandlerAsyncResult(boolean notNull, Handler<AsyncResult<@Nullable RefedInterface1>> handler);
  @Nullable RefedInterface1 methodWithNullableApiReturn(boolean notNull);

  // Test @Nullable DataObject type
  boolean methodWithNonNullableDataObjectParam(TestDataObject param);
  void methodWithNullableDataObjectParam(boolean expectNull, @Nullable TestDataObject param);
  void methodWithNullableDataObjectHandler(boolean notNull, Handler<@Nullable TestDataObject> handler);
  void methodWithNullableDataObjectHandlerAsyncResult(boolean notNull, Handler<AsyncResult<@Nullable TestDataObject>> handler);
  @Nullable TestDataObject methodWithNullableDataObjectReturn(boolean notNull);

  // Test @Nullable Enum type
  boolean methodWithNonNullableEnumParam(TestEnum param);
  void methodWithNullableEnumParam(boolean expectNull, @Nullable TestEnum param);
  void methodWithNullableEnumHandler(boolean notNull, Handler<@Nullable TestEnum> handler);
  void methodWithNullableEnumHandlerAsyncResult(boolean notNull, Handler<AsyncResult<@Nullable TestEnum>> handler);
  @Nullable TestEnum methodWithNullableEnumReturn(boolean notNull);

  // Test @Nullable type variable
  <T> void methodWithNullableTypeVariableParam(boolean expectNull, T param);
  <T> void methodWithNullableTypeVariableHandler(boolean notNull, T value, Handler<T> handler);
  <T> void methodWithNullableTypeVariableHandlerAsyncResult(boolean notNull, T value, Handler<AsyncResult<T>> handler);
  <T> @Nullable T methodWithNullableTypeVariableReturn(boolean notNull, T value);

  // Test @Nullable List<Byte> type
  boolean methodWithNonNullableListByteParam(List<Byte> param);
  void methodWithNullableListByteParam(boolean expectNull, @Nullable List<Byte> param);
  void methodWithNullableListByteHandler(boolean notNull, Handler<@Nullable List<Byte>> handler);
  void methodWithNullableListByteHandlerAsyncResult(boolean notNull, Handler<AsyncResult<@Nullable List<Byte>>> handler);
  @Nullable List<Byte> methodWithNullableListByteReturn(boolean notNull);

  // Test @Nullable List<Short> type
  boolean methodWithNonNullableListShortParam(List<Short> param);
  void methodWithNullableListShortParam(boolean expectNull, @Nullable List<Short> param);
  void methodWithNullableListShortHandler(boolean notNull, Handler<@Nullable List<Short>> handler);
  void methodWithNullableListShortHandlerAsyncResult(boolean notNull, Handler<AsyncResult<@Nullable List<Short>>> handler);
  @Nullable List<Short> methodWithNullableListShortReturn(boolean notNull);

  // Test @Nullable List<Integer> type
  boolean methodWithNonNullableListIntegerParam(List<Integer> param);
  void methodWithNullableListIntegerParam(boolean expectNull, @Nullable List<Integer> param);
  void methodWithNullableListIntegerHandler(boolean notNull, Handler<@Nullable List<Integer>> handler);
  void methodWithNullableListIntegerHandlerAsyncResult(boolean notNull, Handler<AsyncResult<@Nullable List<Integer>>> handler);
  @Nullable List<Integer> methodWithNullableListIntegerReturn(boolean notNull);

  // Test @Nullable List<Long> type
  boolean methodWithNonNullableListLongParam(List<Long> param);
  void methodWithNullableListLongParam(boolean expectNull, @Nullable List<Long> param);
  void methodWithNullableListLongHandler(boolean notNull, Handler<@Nullable List<Long>> handler);
  void methodWithNullableListLongHandlerAsyncResult(boolean notNull, Handler<AsyncResult<@Nullable List<Long>>> handler);
  @Nullable List<Long> methodWithNullableListLongReturn(boolean notNull);

  // Test @Nullable List<Float> type
  boolean methodWithNonNullableListFloatParam(List<Float> param);
  void methodWithNullableListFloatParam(boolean expectNull, @Nullable List<Float> param);
  void methodWithNullableListFloatHandler(boolean notNull, Handler<@Nullable List<Float>> handler);
  void methodWithNullableListFloatHandlerAsyncResult(boolean notNull, Handler<AsyncResult<@Nullable List<Float>>> handler);
  @Nullable List<Float> methodWithNullableListFloatReturn(boolean notNull);

  // Test @Nullable List<Double> type
  boolean methodWithNonNullableListDoubleParam(List<Double> param);
  void methodWithNullableListDoubleParam(boolean expectNull, @Nullable List<Double> param);
  void methodWithNullableListDoubleHandler(boolean notNull, Handler<@Nullable List<Double>> handler);
  void methodWithNullableListDoubleHandlerAsyncResult(boolean notNull, Handler<AsyncResult<@Nullable List<Double>>> handler);
  @Nullable List<Double> methodWithNullableListDoubleReturn(boolean notNull);

  // Test @Nullable List<Boolean> type
  boolean methodWithNonNullableListBooleanParam(List<Boolean> param);
  void methodWithNullableListBooleanParam(boolean expectNull, @Nullable List<Boolean> param);
  void methodWithNullableListBooleanHandler(boolean notNull, Handler<@Nullable List<Boolean>> handler);
  void methodWithNullableListBooleanHandlerAsyncResult(boolean notNull, Handler<AsyncResult<@Nullable List<Boolean>>> handler);
  @Nullable List<Boolean> methodWithNullableListBooleanReturn(boolean notNull);

  // Test @Nullable List<String> type
  boolean methodWithNonNullableListStringParam(List<String> param);
  void methodWithNullableListStringParam(boolean expectNull, @Nullable List<String> param);
  void methodWithNullableListStringHandler(boolean notNull, Handler<@Nullable List<String>> handler);
  void methodWithNullableListStringHandlerAsyncResult(boolean notNull, Handler<AsyncResult<@Nullable List<String>>> handler);
  @Nullable List<String> methodWithNullableListStringReturn(boolean notNull);

  // Test @Nullable List<Character> type
  boolean methodWithNonNullableListCharParam(List<Character> param);
  void methodWithNullableListCharParam(boolean expectNull, @Nullable List<Character> param);
  void methodWithNullableListCharHandler(boolean notNull, Handler<@Nullable List<Character>> handler);
  void methodWithNullableListCharHandlerAsyncResult(boolean notNull, Handler<AsyncResult<@Nullable List<Character>>> handler);
  @Nullable List<Character> methodWithNullableListCharReturn(boolean notNull);

  // Test @Nullable List<JsonObject> type
  boolean methodWithNonNullableListJsonObjectParam(List<JsonObject> param);
  void methodWithNullableListJsonObjectParam(boolean expectNull, @Nullable List<JsonObject> param);
  void methodWithNullableListJsonObjectHandler(boolean notNull, Handler<@Nullable List<JsonObject>> handler);
  void methodWithNullableListJsonObjectHandlerAsyncResult(boolean notNull, Handler<AsyncResult<@Nullable List<JsonObject>>> handler);
  @Nullable List<JsonObject> methodWithNullableListJsonObjectReturn(boolean notNull);

  // Test @Nullable List<JsonArray> type
  boolean methodWithNonNullableListJsonArrayParam(List<JsonArray> param);
  void methodWithNullableListJsonArrayParam(boolean expectNull, @Nullable List<JsonArray> param);
  void methodWithNullableListJsonArrayHandler(boolean notNull, Handler<@Nullable List<JsonArray>> handler);
  void methodWithNullableListJsonArrayHandlerAsyncResult(boolean notNull, Handler<AsyncResult<@Nullable List<JsonArray>>> handler);
  @Nullable List<JsonArray> methodWithNullableListJsonArrayReturn(boolean notNull);

  // Test @Nullable List<Api> type
  boolean methodWithNonNullableListApiParam(List<RefedInterface1> param);
  void methodWithNullableListApiParam(boolean expectNull, @Nullable List<RefedInterface1> param);
  void methodWithNullableListApiHandler(boolean notNull, Handler<@Nullable List<RefedInterface1>> handler);
  void methodWithNullableListApiHandlerAsyncResult(boolean notNull, Handler<AsyncResult<@Nullable List<RefedInterface1>>> handler);
  @Nullable List<RefedInterface1> methodWithNullableListApiReturn(boolean notNull);

  // Test @Nullable List<DataObject> type
  boolean methodWithNonNullableListDataObjectParam(List<TestDataObject> param);
  void methodWithNullableListDataObjectParam(boolean expectNull, @Nullable List<TestDataObject> param);
  void methodWithNullableListDataObjectHandler(boolean notNull, Handler<@Nullable List<TestDataObject>> handler);
  void methodWithNullableListDataObjectHandlerAsyncResult(boolean notNull, Handler<AsyncResult<@Nullable List<TestDataObject>>> handler);
  @Nullable List<TestDataObject> methodWithNullableListDataObjectReturn(boolean notNull);

  // Test @Nullable List<Enum> type
  boolean methodWithNonNullableListEnumParam(List<TestEnum> param);
  void methodWithNullableListEnumParam(boolean expectNull, @Nullable List<TestEnum> param);
  void methodWithNullableListEnumHandler(boolean notNull, Handler<@Nullable List<TestEnum>> handler);
  void methodWithNullableListEnumHandlerAsyncResult(boolean notNull, Handler<AsyncResult<@Nullable List<TestEnum>>> handler);
  @Nullable List<TestEnum> methodWithNullableListEnumReturn(boolean notNull);

  // Test @Nullable Set<String> type
  boolean methodWithNonNullableSetStringParam(Set<String> param);
  void methodWithNullableSetStringParam(boolean expectNull, @Nullable Set<String> param);
  void methodWithNullableSetStringHandler(boolean notNull, Handler<@Nullable Set<String>> handler);
  void methodWithNullableSetStringHandlerAsyncResult(boolean notNull, Handler<AsyncResult<@Nullable Set<String>>> handler);
  @Nullable Set<String> methodWithNullableSetStringReturn(boolean notNull);

  // Test @Nullable Map<String, String> type
  boolean methodWithNonNullableMapStringParam(Map<String, String> param);
  void methodWithNullableMapStringParam(boolean expectNull, @Nullable Map<String, String> param);
  void methodWithNullableMapStringHandler(boolean notNull, Handler<@Nullable Map<String, String>> handler);
  void methodWithNullableMapStringHandlerAsyncResult(boolean notNull, Handler<AsyncResult<@Nullable Map<String, String>>> handler);
  @Nullable Map<String, String> methodWithNullableMapStringReturn(boolean notNull);

  // Test List<@Nullable Integer> type
  void methodWithListNullableIntegerParam(List<@Nullable Integer> param);
  void methodWithListNullableIntegerHandler(Handler<List<@Nullable Integer>> handler);
  void methodWithListNullableIntegerHandlerAsyncResult(Handler<AsyncResult<List<@Nullable Integer>>> handler);
  List<@Nullable Integer> methodWithListNullableIntegerReturn();

  // Test List<@Nullable Boolean> type
  void methodWithListNullableBooleanParam(List<@Nullable Boolean> param);
  void methodWithListNullableBooleanHandler(Handler<List<@Nullable Boolean>> handler);
  void methodWithListNullableBooleanHandlerAsyncResult(Handler<AsyncResult<List<@Nullable Boolean>>> handler);
  List<@Nullable Boolean> methodWithListNullableBooleanReturn();

  // Test List<@Nullable String> type
  void methodWithListNullableStringParam(List<@Nullable String> param);
  void methodWithListNullableStringHandler(Handler<List<@Nullable String>> handler);
  void methodWithListNullableStringHandlerAsyncResult(Handler<AsyncResult<List<@Nullable String>>> handler);
  List<@Nullable String> methodWithListNullableStringReturn();

  // Test List<@Nullable JsonObject> type
  void methodWithListNullableJsonObjectParam(List<@Nullable JsonObject> param);
  void methodWithListNullableJsonObjectHandler(Handler<List<@Nullable JsonObject>> handler);
  void methodWithListNullableJsonObjectHandlerAsyncResult(Handler<AsyncResult<List<@Nullable JsonObject>>> handler);
  List<@Nullable JsonObject> methodWithListNullableJsonObjectReturn();

  // Test List<@Nullable String> type
  void methodWithListNullableJsonArrayParam(List<@Nullable JsonArray> param);
  void methodWithListNullableJsonArrayHandler(Handler<List<@Nullable JsonArray>> handler);
  void methodWithListNullableJsonArrayHandlerAsyncResult(Handler<AsyncResult<List<@Nullable JsonArray>>> handler);
  List<@Nullable JsonArray> methodWithListNullableJsonArrayReturn();

  // Test List<@Nullable Api> type
  void methodWithListNullableApiParam(List<@Nullable RefedInterface1> param);
  void methodWithListNullableApiHandler(Handler<List<@Nullable RefedInterface1>> handler);
  void methodWithListNullableApiHandlerAsyncResult(Handler<AsyncResult<List<@Nullable RefedInterface1>>> handler);
  List<@Nullable RefedInterface1> methodWithListNullableApiReturn();

  // Test List<@Nullable DataObject> type
  void methodWithListNullableDataObjectParam(List<@Nullable TestDataObject> param);
  void methodWithListNullableDataObjectHandler(Handler<List<@Nullable TestDataObject>> handler);
  void methodWithListNullableDataObjectHandlerAsyncResult(Handler<AsyncResult<List<@Nullable TestDataObject>>> handler);
  List<@Nullable TestDataObject> methodWithListNullableDataObjectReturn();

  // Test List<@Nullable Enum> type
  void methodWithListNullableEnumParam(List<@Nullable TestEnum> param);
  void methodWithListNullableEnumHandler(Handler<List<@Nullable TestEnum>> handler);
  void methodWithListNullableEnumHandlerAsyncResult(Handler<AsyncResult<List<@Nullable TestEnum>>> handler);
  List<@Nullable TestEnum> methodWithListNullableEnumReturn();

  // Test @Nullable handlers
  void methodWithNullableHandler(boolean expectNull, @Nullable Handler<String> handler);
  void methodWithNullableHandlerAsyncResult(boolean expectNull, @Nullable Handler<AsyncResult<String>> handler);

}
