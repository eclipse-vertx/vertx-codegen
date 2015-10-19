package io.vertx.codegen.testmodel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import io.vertx.codegen.annotations.Nullable;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class NullableTCKImpl implements NullableTCK {

  @Override
  public boolean methodWithNonNullableByteParam(Byte param) {
    return param == null;
  }

  @Override
  public void methodWithNullableByteParam(boolean expectNull, Byte param) {
    assertEquals(methodWithNullableByteReturn(!expectNull), param);
  }

  @Override
  public void methodWithNullableByteHandler(boolean notNull, Handler<@Nullable Byte> handler) {
    handler.handle(methodWithNullableByteReturn(notNull));
  }

  @Override
  public void methodWithNullableByteHandlerAsyncResult(boolean notNull, Handler<AsyncResult<@Nullable Byte>> handler) {
    handler.handle(Future.succeededFuture(methodWithNullableByteReturn(notNull)));
  }

  @Override
  public @Nullable Byte methodWithNullableByteReturn(boolean notNull) {
    return notNull ? (byte)67 : null;
  }

  @Override
  public boolean methodWithNonNullableShortParam(Short param) {
    return param == null;
  }

  @Override
  public void methodWithNullableShortParam(boolean expectNull, Short param) {
    assertEquals(methodWithNullableShortReturn(!expectNull), param);
  }

  @Override
  public void methodWithNullableShortHandler(boolean notNull, Handler<@Nullable Short> handler) {
    handler.handle(methodWithNullableShortReturn(notNull));
  }

  @Override
  public void methodWithNullableShortHandlerAsyncResult(boolean notNull, Handler<AsyncResult<@Nullable Short>> handler) {
    handler.handle(Future.succeededFuture(methodWithNullableShortReturn(notNull)));
  }

  @Override
  public @Nullable Short methodWithNullableShortReturn(boolean notNull) {
    return notNull ? (short)1024 : null;
  }

  @Override
  public boolean methodWithNonNullableIntegerParam(Integer param) {
    return param == null;
  }

  @Override
  public void methodWithNullableIntegerParam(boolean expectNull, Integer param) {
    assertEquals(methodWithNullableIntegerReturn(!expectNull), param);
  }

  @Override
  public void methodWithNullableIntegerHandler(boolean notNull, Handler<@Nullable Integer> handler) {
    handler.handle(methodWithNullableIntegerReturn(notNull));
  }

  @Override
  public void methodWithNullableIntegerHandlerAsyncResult(boolean notNull, Handler<AsyncResult<@Nullable Integer>> handler) {
    handler.handle(Future.succeededFuture(methodWithNullableIntegerReturn(notNull)));
  }

  @Override
  public @Nullable Integer methodWithNullableIntegerReturn(boolean notNull) {
    return notNull ? 1234567 : null;
  }

  @Override
  public boolean methodWithNonNullableLongParam(Long param) {
    return param == null;
  }

  @Override
  public void methodWithNullableLongParam(boolean expectNull, Long param) {
    assertEquals(methodWithNullableLongReturn(!expectNull), param);
  }

  @Override
  public void methodWithNullableLongHandler(boolean notNull, Handler<@Nullable Long> handler) {
    handler.handle(methodWithNullableLongReturn(notNull));
  }

  @Override
  public void methodWithNullableLongHandlerAsyncResult(boolean notNull, Handler<AsyncResult<@Nullable Long>> handler) {
    handler.handle(Future.succeededFuture(methodWithNullableLongReturn(notNull)));
  }

  @Override
  public @Nullable Long methodWithNullableLongReturn(boolean notNull) {
    return notNull ? 9876543210L : null;
  }

  @Override
  public boolean methodWithNonNullableFloatParam(Float param) {
    return param == null;
  }

  @Override
  public void methodWithNullableFloatParam(boolean expectNull, Float param) {
    assertEquals(methodWithNullableFloatReturn(!expectNull), param);
  }

  @Override
  public void methodWithNullableFloatHandler(boolean notNull, Handler<@Nullable Float> handler) {
    handler.handle(methodWithNullableFloatReturn(notNull));
  }

  @Override
  public void methodWithNullableFloatHandlerAsyncResult(boolean notNull, Handler<AsyncResult<@Nullable Float>> handler) {
    handler.handle(Future.succeededFuture(methodWithNullableFloatReturn(notNull)));
  }

  @Override
  public @Nullable Float methodWithNullableFloatReturn(boolean notNull) {
    return notNull ? 3.14f : null;
  }

  @Override
  public boolean methodWithNonNullableDoubleParam(Double param) {
    return param == null;
  }

  @Override
  public void methodWithNullableDoubleParam(boolean expectNull, Double param) {
    assertEquals(methodWithNullableDoubleReturn(!expectNull), param);
  }

  @Override
  public void methodWithNullableDoubleHandler(boolean notNull, Handler<@Nullable Double> handler) {
    handler.handle(methodWithNullableDoubleReturn(notNull));
  }

  @Override
  public void methodWithNullableDoubleHandlerAsyncResult(boolean notNull, Handler<AsyncResult<@Nullable Double>> handler) {
    handler.handle(Future.succeededFuture(methodWithNullableDoubleReturn(notNull)));
  }

  @Override
  public @Nullable Double methodWithNullableDoubleReturn(boolean notNull) {
    return notNull ? 3.1415926D : null;
  }

  @Override
  public boolean methodWithNonNullableBooleanParam(Boolean param) {
    return param == null;
  }

  @Override
  public void methodWithNullableBooleanParam(boolean expectNull, Boolean param) {
    assertEquals(methodWithNullableBooleanReturn(!expectNull), param);
  }

  @Override
  public void methodWithNullableBooleanHandler(boolean notNull, Handler<@Nullable Boolean> handler) {
    handler.handle(methodWithNullableBooleanReturn(notNull));
  }

  @Override
  public void methodWithNullableBooleanHandlerAsyncResult(boolean notNull, Handler<AsyncResult<@Nullable Boolean>> handler) {
    handler.handle(Future.succeededFuture(methodWithNullableBooleanReturn(notNull)));
  }

  @Override
  public @Nullable Boolean methodWithNullableBooleanReturn(boolean notNull) {
    return notNull ? true : null;
  }

  @Override
  public boolean methodWithNonNullableStringParam(String param) {
    return param == null;
  }

  @Override
  public void methodWithNullableStringParam(boolean expectNull, @Nullable String param) {
    assertEquals(methodWithNullableStringReturn(!expectNull), param);
  }

  @Override
  public void methodWithNullableStringHandler(boolean notNull, Handler<@Nullable String> handler) {
    handler.handle(methodWithNullableStringReturn(notNull));
  }

  @Override
  public void methodWithNullableStringHandlerAsyncResult(boolean notNull, Handler<AsyncResult<@Nullable String>> handler) {
    handler.handle(Future.succeededFuture(methodWithNullableStringReturn(notNull)));
  }

  @Override
  public @Nullable String methodWithNullableStringReturn(boolean notNull) {
    return notNull ? "the_string_value" : null;
  }

  @Override
  public boolean methodWithNonNullableCharParam(Character param) {
    return param == null;
  }

  @Override
  public void methodWithNullableCharParam(boolean expectNull, Character param) {
    assertEquals(methodWithNullableCharReturn(!expectNull), param);
  }

  @Override
  public void methodWithNullableCharHandler(boolean notNull, Handler<@Nullable Character> handler) {
    handler.handle(methodWithNullableCharReturn(notNull));
  }

  @Override
  public void methodWithNullableCharHandlerAsyncResult(boolean notNull, Handler<AsyncResult<@Nullable Character>> handler) {
    handler.handle(Future.succeededFuture(methodWithNullableCharReturn(notNull)));
  }

  @Override
  public @Nullable Character methodWithNullableCharReturn(boolean notNull) {
    return notNull ? 'f' : null;
  }

  @Override
  public boolean methodWithNonNullableJsonObjectParam(JsonObject param) {
    return param == null;
  }

  @Override
  public void methodWithNullableJsonObjectParam(boolean expectNull, JsonObject param) {
    assertEquals(methodWithNullableJsonObjectReturn(!expectNull), param);
  }

  @Override
  public void methodWithNullableJsonObjectHandler(boolean notNull, Handler<@Nullable JsonObject> handler) {
    handler.handle(methodWithNullableJsonObjectReturn(notNull));
  }

  @Override
  public void methodWithNullableJsonObjectHandlerAsyncResult(boolean notNull, Handler<AsyncResult<@Nullable JsonObject>> handler) {
    handler.handle(Future.succeededFuture(methodWithNullableJsonObjectReturn(notNull)));
  }

  @Override
  public @Nullable JsonObject methodWithNullableJsonObjectReturn(boolean notNull) {
    if (notNull) {
      return new JsonObject().put("foo", "wibble").put("bar", 3);
    } else {
      return null;
    }
  }

  @Override
  public boolean methodWithNonNullableJsonArrayParam(JsonArray param) {
    return param == null;
  }

  @Override
  public void methodWithNullableJsonArrayParam(boolean expectNull, JsonArray param) {
    assertEquals(methodWithNullableJsonArrayReturn(!expectNull), param);
  }

  @Override
  public void methodWithNullableJsonArrayHandler(boolean notNull, Handler<@Nullable JsonArray> handler) {
    handler.handle(methodWithNullableJsonArrayReturn(notNull));
  }

  @Override
  public void methodWithNullableJsonArrayHandlerAsyncResult(boolean notNull, Handler<AsyncResult<@Nullable JsonArray>> handler) {
    handler.handle(Future.succeededFuture(methodWithNullableJsonArrayReturn(notNull)));
  }

  @Override
  public @Nullable JsonArray methodWithNullableJsonArrayReturn(boolean notNull) {
    return notNull ? new JsonArray().add("one").add("two").add("three") : null;
  }

  @Override
  public boolean methodWithNonNullableApiParam(RefedInterface1 param) {
    return param == null;
  }

  @Override
  public void methodWithNullableApiParam(boolean expectNull, RefedInterface1 param) {
    assertEquals(methodWithNullableApiReturn(!expectNull), param);
  }

  @Override
  public void methodWithNullableApiHandler(boolean notNull, Handler<RefedInterface1> handler) {
    handler.handle(methodWithNullableApiReturn(notNull));
  }

  @Override
  public void methodWithNullableApiHandlerAsyncResult(boolean notNull, Handler<AsyncResult<RefedInterface1>> handler) {
    handler.handle(Future.succeededFuture(methodWithNullableApiReturn(notNull)));
  }

  @Override
  public @Nullable RefedInterface1 methodWithNullableApiReturn(boolean notNull) {
    return notNull ? new RefedInterface1Impl().setString("lovely_dae") : null;
  }

  @Override
  public boolean methodWithNonNullableDataObjectParam(TestDataObject param) {
    return param == null;
  }

  @Override
  public void methodWithNullableDataObjectParam(boolean expectNull, TestDataObject param) {
    if (expectNull) {
      assertNull(param);
    } else {
      assertEquals(methodWithNullableDataObjectReturn(true).toJson(), param.toJson());
    }
  }

  @Override
  public void methodWithNullableDataObjectHandler(boolean notNull, Handler<TestDataObject> handler) {
    handler.handle(methodWithNullableDataObjectReturn(notNull));
  }

  @Override
  public void methodWithNullableDataObjectHandlerAsyncResult(boolean notNull, Handler<AsyncResult<TestDataObject>> handler) {
    handler.handle(Future.succeededFuture(methodWithNullableDataObjectReturn(notNull)));
  }

  @Override
  public @Nullable TestDataObject methodWithNullableDataObjectReturn(boolean notNull) {
    return notNull ? new TestDataObject().setFoo("foo_value").setBar(12345).setWibble(3.5) : null;
  }

  @Override
  public boolean methodWithNonNullableEnumParam(TestEnum param) {
    return param == null;
  }

  @Override
  public void methodWithNullableEnumParam(boolean expectNull, TestEnum param) {
    assertEquals(methodWithNullableEnumReturn(!expectNull), param);
  }

  @Override
  public void methodWithNullableEnumHandler(boolean notNull, Handler<TestEnum> handler) {
    handler.handle(methodWithNullableEnumReturn(notNull));
  }

  @Override
  public void methodWithNullableEnumHandlerAsyncResult(boolean notNull, Handler<AsyncResult<TestEnum>> handler) {
    handler.handle(Future.succeededFuture(methodWithNullableEnumReturn(notNull)));
  }

  @Override
  public @Nullable TestEnum methodWithNullableEnumReturn(boolean notNull) {
    return notNull ? TestEnum.TIM : null;
  }

  @Override
  public <T> void methodWithNullableTypeVariableParam(boolean expectNull, T param) {
    if (expectNull) {
      assertNull(param);
    } else {
      assertNotNull(param);
    }
  }

  @Override
  public <T> void methodWithNullableTypeVariableHandler(boolean notNull, T value, Handler<T> handler) {
    if (notNull) {
      handler.handle(value);
    } else {
      handler.handle(null);
    }
  }

  @Override
  public <T> void methodWithNullableTypeVariableHandlerAsyncResult(boolean notNull, T value, Handler<AsyncResult<T>> handler) {
    if (notNull) {
      handler.handle(Future.succeededFuture(value));
    } else {
      handler.handle(Future.succeededFuture(null));
    }
}

  @Override
  public <T> T methodWithNullableTypeVariableReturn(boolean notNull, T value) {
    return notNull ? value : null;
  }

  @Override
  public boolean methodWithNonNullableListIntegerParam(List<Integer> param) {
    return param == null;
  }

  @Override
  public void methodWithNullableListIntegerParam(boolean expectNull, List<Integer> param) {
    assertEquals(methodWithNullableListIntegerReturn(!expectNull), param);
  }

  @Override
  public void methodWithNullableListIntegerHandler(boolean notNull, Handler<@Nullable List<Integer>> handler) {
    handler.handle(methodWithNullableListIntegerReturn(notNull));
  }

  @Override
  public void methodWithNullableListIntegerHandlerAsyncResult(boolean notNull, Handler<AsyncResult<@Nullable List<Integer>>> handler) {
    handler.handle(Future.succeededFuture(methodWithNullableListIntegerReturn(notNull)));
  }

  @Override
  public @Nullable List<Integer> methodWithNullableListIntegerReturn(boolean notNull) {
    if (notNull) {
      return Arrays.asList(1, 2, 3);
    } else {
      return null;
    }
  }

  @Override
  public boolean methodWithNonNullableListBooleanParam(List<Boolean> param) {
    return param == null;
  }

  @Override
  public void methodWithNullableListBooleanParam(boolean expectNull, List<Boolean> param) {
    assertEquals(methodWithNullableListBooleanReturn(!expectNull), param);
  }

  @Override
  public void methodWithNullableListBooleanHandler(boolean notNull, Handler<@Nullable List<Boolean>> handler) {
    handler.handle(methodWithNullableListBooleanReturn(notNull));
  }

  @Override
  public void methodWithNullableListBooleanHandlerAsyncResult(boolean notNull, Handler<AsyncResult<@Nullable List<Boolean>>> handler) {
    handler.handle(Future.succeededFuture(methodWithNullableListBooleanReturn(notNull)));
  }

  @Override
  public @Nullable List<Boolean> methodWithNullableListBooleanReturn(boolean notNull) {
    if (notNull) {
      return Arrays.asList(true, false, true);
    } else {
      return null;
    }
  }

  @Override
  public boolean methodWithNonNullableListStringParam(List<String> param) {
    return param == null;
  }

  @Override
  public void methodWithNullableListStringParam(boolean expectNull, List<String> param) {
    assertEquals(methodWithNullableListStringReturn(!expectNull), param);
  }

  @Override
  public void methodWithNullableListStringHandler(boolean notNull, Handler<@Nullable List<String>> handler) {
    handler.handle(methodWithNullableListStringReturn(notNull));
  }

  @Override
  public void methodWithNullableListStringHandlerAsyncResult(boolean notNull, Handler<AsyncResult<@Nullable List<String>>> handler) {
    handler.handle(Future.succeededFuture(methodWithNullableListStringReturn(notNull)));
  }

  @Override
  public @Nullable List<String> methodWithNullableListStringReturn(boolean notNull) {
    if (notNull) {
      return Arrays.asList("first", "second", "third");
    } else {
      return null;
    }
  }

  @Override
  public boolean methodWithNonNullableListJsonObjectParam(List<JsonObject> param) {
    return param == null;
  }

  @Override
  public void methodWithNullableListJsonObjectParam(boolean expectNull, List<JsonObject> param) {
    assertEquals(methodWithNullableListJsonObjectReturn(!expectNull), param);
  }

  @Override
  public void methodWithNullableListJsonObjectHandler(boolean notNull, Handler<@Nullable List<JsonObject>> handler) {
    handler.handle(methodWithNullableListJsonObjectReturn(notNull));
  }

  @Override
  public void methodWithNullableListJsonObjectHandlerAsyncResult(boolean notNull, Handler<AsyncResult<@Nullable List<JsonObject>>> handler) {
    handler.handle(Future.succeededFuture(methodWithNullableListJsonObjectReturn(notNull)));
  }

  @Override
  public @Nullable List<JsonObject> methodWithNullableListJsonObjectReturn(boolean notNull) {
    if (notNull) {
      return Arrays.asList(new JsonObject().put("foo", "bar"), new JsonObject().put("juu", 3));
    } else {
      return null;
    }
  }

  @Override
  public boolean methodWithNonNullableListJsonArrayParam(List<JsonArray> param) {
    return param == null;
  }

  @Override
  public void methodWithNullableListJsonArrayParam(boolean expectNull, List<JsonArray> param) {
    assertEquals(methodWithNullableListJsonArrayReturn(!expectNull), param);
  }

  @Override
  public void methodWithNullableListJsonArrayHandler(boolean notNull, Handler<@Nullable List<JsonArray>> handler) {
    handler.handle(methodWithNullableListJsonArrayReturn(notNull));
  }

  @Override
  public void methodWithNullableListJsonArrayHandlerAsyncResult(boolean notNull, Handler<AsyncResult<@Nullable List<JsonArray>>> handler) {
    handler.handle(Future.succeededFuture(methodWithNullableListJsonArrayReturn(notNull)));
  }

  @Override
  public @Nullable List<JsonArray> methodWithNullableListJsonArrayReturn(boolean notNull) {
    if (notNull) {
      return Arrays.asList(new JsonArray().add("foo").add("bar"), new JsonArray().add("juu"));
    } else {
      return null;
    }
  }

  @Override
  public boolean methodWithNonNullableListApiParam(List<RefedInterface1> param) {
    return param == null;
  }

  @Override
  public void methodWithNullableListApiParam(boolean expectNull, List<RefedInterface1> param) {
    assertEquals(methodWithNullableListApiReturn(!expectNull), param);
  }

  @Override
  public void methodWithNullableListApiHandler(boolean notNull, Handler<@Nullable List<RefedInterface1>> handler) {
    handler.handle(methodWithNullableListApiReturn(notNull));
  }

  @Override
  public void methodWithNullableListApiHandlerAsyncResult(boolean notNull, Handler<AsyncResult<@Nullable List<RefedInterface1>>> handler) {
    handler.handle(Future.succeededFuture(methodWithNullableListApiReturn(notNull)));
  }

  @Override
  public @Nullable List<RefedInterface1> methodWithNullableListApiReturn(boolean notNull) {
    return notNull ? Arrays.asList(new RefedInterface1Impl().setString("refed_is_here")) : null;
  }

  @Override
  public boolean methodWithNonNullableListDataObjectParam(List<TestDataObject> param) {
    return param == null;
  }

  @Override
  public void methodWithNullableListDataObjectParam(boolean expectNull, List<TestDataObject> param) {
    if (expectNull) {
      assertEquals(null, param);
    } else {
      assertEquals(methodWithNullableListDataObjectReturn(true).stream().map(TestDataObject::toJson).collect(Collectors.toList()), param.stream().map(TestDataObject::toJson).collect(Collectors.toList()));
    }
  }

  @Override
  public void methodWithNullableListDataObjectHandler(boolean notNull, Handler<@Nullable List<TestDataObject>> handler) {
    handler.handle(methodWithNullableListDataObjectReturn(notNull));
  }

  @Override
  public void methodWithNullableListDataObjectHandlerAsyncResult(boolean notNull, Handler<AsyncResult<@Nullable List<TestDataObject>>> handler) {
    handler.handle(Future.succeededFuture(methodWithNullableListDataObjectReturn(notNull)));
  }

  @Override
  public @Nullable List<TestDataObject> methodWithNullableListDataObjectReturn(boolean notNull) {
    return notNull ? Arrays.asList(new TestDataObject().setFoo("foo_value").setBar(12345).setWibble(5.6)) : null;
  }

  @Override
  public boolean methodWithNonNullableListEnumParam(List<TestEnum> param) {
    return param == null;
  }

  @Override
  public void methodWithNullableListEnumParam(boolean expectNull, List<TestEnum> param) {
    assertEquals(methodWithNullableListEnumReturn(!expectNull), param);
  }

  @Override
  public void methodWithNullableListEnumHandler(boolean notNull, Handler<@Nullable List<TestEnum>> handler) {
    handler.handle(methodWithNullableListEnumReturn(notNull));
  }

  @Override
  public void methodWithNullableListEnumHandlerAsyncResult(boolean notNull, Handler<AsyncResult<@Nullable List<TestEnum>>> handler) {
    handler.handle(Future.succeededFuture(methodWithNullableListEnumReturn(notNull)));
  }

  @Override
  public @Nullable List<TestEnum> methodWithNullableListEnumReturn(boolean notNull) {
    return notNull ? Arrays.asList(TestEnum.TIM,TestEnum.JULIEN) : null;
  }

  @Override
  public boolean methodWithNonNullableSetStringParam(Set<String> param) {
    return param == null;
  }

  @Override
  public void methodWithNullableSetStringParam(boolean expectNull, Set<String> param) {
    assertEquals(methodWithNullableSetStringReturn(!expectNull), param);
  }

  @Override
  public void methodWithNullableSetStringHandler(boolean notNull, Handler<@Nullable Set<String>> handler) {
    handler.handle(methodWithNullableSetStringReturn(notNull));
  }

  @Override
  public void methodWithNullableSetStringHandlerAsyncResult(boolean notNull, Handler<AsyncResult<@Nullable Set<String>>> handler) {
    handler.handle(Future.succeededFuture(methodWithNullableSetStringReturn(notNull)));
  }

  @Override
  public @Nullable Set<String> methodWithNullableSetStringReturn(boolean notNull) {
    if (notNull) {
      return new LinkedHashSet<>(Arrays.asList("first", "second", "third"));
    } else {
      return null;
    }
  }

  @Override
  public boolean methodWithNonNullableMapStringParam(Map<String, String> param) {
    return param == null;
  }

  @Override
  public void methodWithNullableMapStringParam(boolean expectNull, Map<String, String> param) {
    assertEquals(methodWithNullableMapStringReturn(!expectNull), param);
  }

  @Override
  public void methodWithNullableMapStringHandler(boolean notNull, Handler<@Nullable Map<String, String>> handler) {
    handler.handle(methodWithNullableMapStringReturn(notNull));
  }

  @Override
  public void methodWithNullableMapStringHandlerAsyncResult(boolean notNull, Handler<AsyncResult<@Nullable Map<String, String>>> handler) {
    handler.handle(Future.succeededFuture(methodWithNullableMapStringReturn(notNull)));
  }

  @Override
  public @Nullable Map<String, String> methodWithNullableMapStringReturn(boolean notNull) {
    if (notNull) {
      Map<String, String> map = new LinkedHashMap<>();
      map.put("1", "first");
      map.put("2", "second");
      map.put("3", "third");
      return map;
    } else {
      return null;
    }
  }

  @Override
  public void methodWithListNullableIntegerParam(List<@Nullable Integer> param) {
    assertEquals(param, methodWithListNullableIntegerReturn());
  }

  @Override
  public void methodWithListNullableIntegerHandler(Handler<List<@Nullable Integer>> handler) {
    handler.handle(methodWithListNullableIntegerReturn());
  }

  @Override
  public void methodWithListNullableIntegerHandlerAsyncResult(Handler<AsyncResult<List<@Nullable Integer>>> handler) {
    handler.handle(Future.succeededFuture(methodWithListNullableIntegerReturn()));
  }

  @Override
  public List<@Nullable Integer> methodWithListNullableIntegerReturn() {
    ArrayList<Integer> ret = new ArrayList<>();
    ret.add(12345);
    ret.add(null);
    ret.add(54321);
    return ret;
  }

  @Override
  public void methodWithListNullableBooleanParam(List<@Nullable Boolean> param) {
    assertEquals(param, methodWithListNullableBooleanReturn());
  }

  @Override
  public void methodWithListNullableBooleanHandler(Handler<List<@Nullable Boolean>> handler) {
    handler.handle(methodWithListNullableBooleanReturn());
  }

  @Override
  public void methodWithListNullableBooleanHandlerAsyncResult(Handler<AsyncResult<List<@Nullable Boolean>>> handler) {
    handler.handle(Future.succeededFuture(methodWithListNullableBooleanReturn()));
  }

  @Override
  public List<@Nullable Boolean> methodWithListNullableBooleanReturn() {
    ArrayList<Boolean> ret = new ArrayList<>();
    ret.add(true);
    ret.add(null);
    ret.add(false);
    return ret;
  }

  @Override
  public void methodWithListNullableStringParam(List<@Nullable String> param) {
    assertEquals(param, methodWithListNullableStringReturn());
  }

  @Override
  public void methodWithListNullableStringHandler(Handler<List<@Nullable String>> handler) {
    handler.handle(methodWithListNullableStringReturn());
  }

  @Override
  public void methodWithListNullableStringHandlerAsyncResult(Handler<AsyncResult<List<@Nullable String>>> handler) {
    handler.handle(Future.succeededFuture(methodWithListNullableStringReturn()));
  }

  @Override
  public List<@Nullable String> methodWithListNullableStringReturn() {
    ArrayList<String> ret = new ArrayList<>();
    ret.add("first");
    ret.add(null);
    ret.add("third");
    return ret;
  }


  @Override
  public void methodWithListNullableJsonObjectParam(List<@Nullable JsonObject> param) {
    assertEquals(param, methodWithListNullableJsonObjectReturn());
  }

  @Override
  public void methodWithListNullableJsonObjectHandler(Handler<List<@Nullable JsonObject>> handler) {
    handler.handle(methodWithListNullableJsonObjectReturn());
  }

  @Override
  public void methodWithListNullableJsonObjectHandlerAsyncResult(Handler<AsyncResult<List<@Nullable JsonObject>>> handler) {
    handler.handle(Future.succeededFuture(methodWithListNullableJsonObjectReturn()));
  }

  @Override
  public List<@Nullable JsonObject> methodWithListNullableJsonObjectReturn() {
    ArrayList<JsonObject> ret = new ArrayList<>();
    ret.add(new JsonObject().put("foo", "bar"));
    ret.add(null);
    ret.add(new JsonObject().put("juu", 3));
    return ret;
  }

  @Override
  public void methodWithListNullableJsonArrayParam(List<@Nullable JsonArray> param) {
    assertEquals(param, methodWithListNullableJsonArrayReturn());
  }

  @Override
  public void methodWithListNullableJsonArrayHandler(Handler<List<@Nullable JsonArray>> handler) {
    handler.handle(methodWithListNullableJsonArrayReturn());
  }

  @Override
  public void methodWithListNullableJsonArrayHandlerAsyncResult(Handler<AsyncResult<List<@Nullable JsonArray>>> handler) {
    handler.handle(Future.succeededFuture(methodWithListNullableJsonArrayReturn()));
  }

  @Override
  public List<@Nullable JsonArray> methodWithListNullableJsonArrayReturn() {
    ArrayList<JsonArray> ret = new ArrayList<>();
    ret.add(new JsonArray().add("foo").add("bar"));
    ret.add(null);
    ret.add(new JsonArray().add("juu"));
    return ret;
  }

  @Override
  public void methodWithListNullableApiParam(List<@Nullable RefedInterface1> param) {
    assertEquals(param, methodWithListNullableApiReturn());
  }

  @Override
  public void methodWithListNullableApiHandler(Handler<List<@Nullable RefedInterface1>> handler) {
    handler.handle(methodWithListNullableApiReturn());
  }

  @Override
  public void methodWithListNullableApiHandlerAsyncResult(Handler<AsyncResult<List<@Nullable RefedInterface1>>> handler) {
    handler.handle(Future.succeededFuture(methodWithListNullableApiReturn()));
  }

  @Override
  public List<@Nullable RefedInterface1> methodWithListNullableApiReturn() {
    ArrayList<RefedInterface1> ret = new ArrayList<>();
    ret.add(new RefedInterface1Impl().setString("first"));
    ret.add(null);
    ret.add(new RefedInterface1Impl().setString("third"));
    return ret;
  }

  @Override
  public void methodWithListNullableDataObjectParam(List<@Nullable TestDataObject> param) {
    Function<@Nullable TestDataObject, JsonObject> conv = obj -> (obj != null) ? obj.toJson() : null;
    assertEquals(param.stream().map(conv).collect(Collectors.toList()), methodWithListNullableDataObjectReturn().stream().map(conv).collect(Collectors.toList()));
  }

  @Override
  public void methodWithListNullableDataObjectHandler(Handler<List<@Nullable TestDataObject>> handler) {
    handler.handle(methodWithListNullableDataObjectReturn());
  }

  @Override
  public void methodWithListNullableDataObjectHandlerAsyncResult(Handler<AsyncResult<List<@Nullable TestDataObject>>> handler) {
    handler.handle(Future.succeededFuture(methodWithListNullableDataObjectReturn()));
  }

  @Override
  public List<@Nullable TestDataObject> methodWithListNullableDataObjectReturn() {
    ArrayList<TestDataObject> ret = new ArrayList<>();
    ret.add(new TestDataObject().setFoo("first").setBar(1).setWibble(1.1));
    ret.add(null);
    ret.add(new TestDataObject().setFoo("third").setBar(3).setWibble(3.3));
    return ret;
  }

  @Override
  public void methodWithListNullableEnumParam(List<@Nullable TestEnum> param) {
    assertEquals(param, methodWithListNullableEnumReturn());
  }

  @Override
  public void methodWithListNullableEnumHandler(Handler<List<@Nullable TestEnum>> handler) {
    handler.handle(methodWithListNullableEnumReturn());
  }

  @Override
  public void methodWithListNullableEnumHandlerAsyncResult(Handler<AsyncResult<List<@Nullable TestEnum>>> handler) {
    handler.handle(Future.succeededFuture(methodWithListNullableEnumReturn()));
  }

  @Override
  public List<@Nullable TestEnum> methodWithListNullableEnumReturn() {
    ArrayList<TestEnum> ret = new ArrayList<>();
    ret.add(TestEnum.TIM);
    ret.add(null);
    ret.add(TestEnum.JULIEN);
    return ret;
  }

  @Override
  public void methodWithNullableHandler(boolean expectNull, Handler<String> handler) {
    if (expectNull) {
      assertNull(handler);
    } else {
      handler.handle(methodWithNullableStringReturn(true));
    }
  }

  @Override
  public void methodWithNullableHandlerAsyncResult(boolean expectNull, Handler<AsyncResult<String>> handler) {
    if (expectNull) {
      assertNull(handler);
    } else {
      handler.handle(Future.succeededFuture(methodWithNullableStringReturn(true)));
    }
  }
}
