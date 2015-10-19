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
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class NullableTCKImpl implements NullableTCK {

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
