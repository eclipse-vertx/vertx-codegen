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

  // Test @Nullable Integer type
  boolean methodWithNonNullableIntegerParam(Integer param);
  void methodWithNullableIntegerParam(boolean expectNull, @Nullable Integer param);
  void methodWithNullableIntegerHandler(boolean notNull, Handler<@Nullable Integer> handler);
  void methodWithNullableIntegerHandlerAsyncResult(boolean notNull, Handler<AsyncResult<@Nullable Integer>> handler);
  @Nullable Integer methodWithNullableIntegerReturn(boolean notNull);

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

  // Test @Nullable DataObject type
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

  // Test @Nullable List<Integer> type
  boolean methodWithNonNullableListIntegerParam(List<Integer> param);
  void methodWithNullableListIntegerParam(boolean expectNull, @Nullable List<Integer> param);
  void methodWithNullableListIntegerHandler(boolean notNull, Handler<@Nullable List<Integer>> handler);
  void methodWithNullableListIntegerHandlerAsyncResult(boolean notNull, Handler<AsyncResult<@Nullable List<Integer>>> handler);
  @Nullable List<Integer> methodWithNullableListIntegerReturn(boolean notNull);

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

  // Test List<@Nullable String> type
  void methodWithListNullableStringParam(List<@Nullable String> param);
  void methodWithListNullableStringHandler(Handler<List<@Nullable String>> handler);
  void methodWithListNullableStringHandlerAsyncResult(Handler<AsyncResult<List<@Nullable String>>> handler);
  List<@Nullable String> methodWithListNullableStringReturn();

  // Test @Nullable handlers
  void methodWithNullableHandler(boolean expectNull, @Nullable Handler<String> handler);
  void methodWithNullableHandlerAsyncResult(boolean expectNull, @Nullable Handler<AsyncResult<String>> handler);

}
