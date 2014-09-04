package io.vertx.codegen.testmodel;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.VertxException;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
public class TestInterfaceImpl<T> implements TestInterface<T> {

  @Override
  public void methodWithBasicParams(byte b, short s, int i, long l, float f, double d, boolean bool, char ch, String str) {
    assertEquals((byte) 123, b);
    assertEquals((short) 12345, s);
    assertEquals(1234567, i);
    assertEquals(1265615234l, l);
    assertEquals(12.345f, f, 0);
    assertEquals(12.34566d, d, 0);
    assertTrue(bool);
    assertEquals('X', ch);
    assertEquals("foobar", str);
  }

  @Override
  public void methodWithBasicBoxedParams(Byte b, Short s, Integer i, Long l, Float f, Double d, Boolean bool, Character ch) {
    assertEquals((byte) 123, b.byteValue());
    assertEquals((short) 12345, s.shortValue());
    assertEquals(1234567, i.intValue());
    assertEquals(1265615234l, l.longValue());
    assertEquals(12.345f, f.floatValue(), 0);
    assertEquals(12.34566d, d.doubleValue(), 0);
    assertTrue(bool);
    assertEquals('X', ch.charValue());
  }

  @Override
  public void methodWithHandlerBasicTypes(Handler<Byte> byteHandler, Handler<Short> shortHandler,
                                          Handler<Integer> intHandler, Handler<Long> longHandler,
                                          Handler<Float> floatHandler, Handler<Double> doubleHandler,
                                          Handler<Boolean> booleanHandler, Handler<Character> charHandler,
                                          Handler<String> stringHandler) {
    byteHandler.handle((byte)123);
    shortHandler.handle((short)12345);
    intHandler.handle(1234567);
    longHandler.handle(1265615234l);
    floatHandler.handle(12.345f);
    doubleHandler.handle(12.34566d);
    booleanHandler.handle(true);
    charHandler.handle('X');
    stringHandler.handle("quux!");
  }

  @Override
  public void methodWithHandlerAsyncResultByte(boolean sendFailure, Handler<AsyncResult<Byte>> handler) {
    if (sendFailure) {
      Exception e = new Exception("foobar!");
      handler.handle(Future.completedFuture(e));
    } else {
      handler.handle(Future.completedFuture((byte) 123));
    }
  }

  @Override
  public void methodWithHandlerAsyncResultShort(boolean sendFailure, Handler<AsyncResult<Short>> handler) {
    if (sendFailure) {
      Exception e = new Exception("foobar!");
      handler.handle(Future.completedFuture(e));
    } else {
      handler.handle(Future.completedFuture((short) 12345));
    }
  }

  @Override
  public void methodWithHandlerAsyncResultInteger(boolean sendFailure, Handler<AsyncResult<Integer>> handler) {
    if (sendFailure) {
      Exception e = new Exception("foobar!");
      handler.handle(Future.completedFuture(e));
    } else {
      handler.handle(Future.completedFuture(1234567));
    }
  }

  @Override
  public void methodWithHandlerAsyncResultLong(boolean sendFailure, Handler<AsyncResult<Long>> handler) {
    if (sendFailure) {
      Exception e = new Exception("foobar!");
      handler.handle(Future.completedFuture(e));
    } else {
      handler.handle(Future.completedFuture(1265615234l));
    }
  }

  @Override
  public void methodWithHandlerAsyncResultFloat(boolean sendFailure, Handler<AsyncResult<Float>> handler) {
    if (sendFailure) {
      Exception e = new Exception("foobar!");
      handler.handle(Future.completedFuture(e));
    } else {
      handler.handle(Future.completedFuture(12.345f));
    }
  }

  @Override
  public void methodWithHandlerAsyncResultDouble(boolean sendFailure, Handler<AsyncResult<Double>> handler) {
    if (sendFailure) {
      Exception e = new Exception("foobar!");
      handler.handle(Future.completedFuture(e));
    } else {
      handler.handle(Future.completedFuture(12.34566d));
    }
  }

  @Override
  public void methodWithHandlerAsyncResultBoolean(boolean sendFailure, Handler<AsyncResult<Boolean>> handler) {
    if (sendFailure) {
      Exception e = new Exception("foobar!");
      handler.handle(Future.completedFuture(e));
    } else {
      handler.handle(Future.completedFuture(true));
    }
  }

  @Override
  public void methodWithHandlerAsyncResultCharacter(boolean sendFailure, Handler<AsyncResult<Character>> handler) {
    if (sendFailure) {
      Exception e = new Exception("foobar!");
      handler.handle(Future.completedFuture(e));
    } else {
      handler.handle(Future.completedFuture('X'));
    }
  }

  @Override
  public void methodWithHandlerAsyncResultString(boolean sendFailure, Handler<AsyncResult<String>> handler) {
    if (sendFailure) {
      Exception e = new Exception("foobar!");
      handler.handle(Future.completedFuture(e));
    } else {
      handler.handle(Future.completedFuture("quux!"));
    }
  }

  @Override
  public void methodWithUserTypes(RefedInterface1 refed) {
    assertEquals("aardvarks", refed.getString());
  }

  @Override
  public void methodWithObjectParam(String str, Object obj) {
    switch (str) {
      case "JsonObject" : {
        assertTrue(obj instanceof JsonObject);
        JsonObject jsonObject = (JsonObject)obj;
        assertEquals("hello", jsonObject.getString("foo"));
        assertEquals(123, jsonObject.getInteger("bar").intValue());
        break;
      }
      case "JsonArray": {
        assertTrue(obj instanceof JsonArray);
        JsonArray arr = (JsonArray)obj;
        assertEquals(3, arr.size());
        assertEquals("foo", arr.get(0));
        assertEquals("bar", arr.get(1));
        assertEquals("wib", arr.get(2));
        break;
      }
      default: fail("invalid type");
    }
  }

  @Override
  public void methodWithOptionsParam(TestOptions options) {
    assertEquals("hello", options.getFoo());
    assertEquals(123, options.getBar());
    assertEquals(1.23, options.getWibble(), 0);
  }

  @Override
  public void methodWithNullOptionsParam(TestOptions options) {
    assertNull(options);
  }

  @Override
  public void methodWithHandlerListAndSet(Handler<List<String>> listStringHandler, Handler<List<Integer>> listIntHandler,
                                          Handler<Set<String>> setStringHandler, Handler<Set<Integer>> setIntHandler) {
    List<String> listString = Arrays.asList("foo", "bar", "wibble");
    List<Integer> listInt = Arrays.asList(5, 12, 100);
    Set<String> setString = new LinkedHashSet<>( Arrays.asList("foo", "bar", "wibble"));
    Set<Integer> setInt = new LinkedHashSet<>(Arrays.asList(new Integer[] {5, 12, 100}));
    listStringHandler.handle(listString);
    listIntHandler.handle(listInt);
    setStringHandler.handle(setString);
    setIntHandler.handle(setInt);
  }

  @Override
  public void methodWithHandlerAsyncResultListString(Handler<AsyncResult<List<String>>> handler) {
    List<String> listString = Arrays.asList("foo", "bar", "wibble");
    handler.handle(Future.completedFuture(listString));
  }

  @Override
  public void methodWithHandlerAsyncResultListInteger(Handler<AsyncResult<List<Integer>>> handler) {
    List<Integer> listInt = Arrays.asList(5, 12, 100);
    handler.handle(Future.completedFuture(listInt));
  }

  @Override
  public void methodWithHandlerAsyncResultSetString(Handler<AsyncResult<Set<String>>> handler) {
    Set<String> setString = new LinkedHashSet<>( Arrays.asList("foo", "bar", "wibble"));
    handler.handle(Future.completedFuture(setString));
  }

  @Override
  public void methodWithHandlerAsyncResultSetInteger(Handler<AsyncResult<Set<Integer>>> handler) {
    Set<Integer> setInt = new LinkedHashSet<>(Arrays.asList(5, 12, 100));
    handler.handle(Future.completedFuture(setInt));
  }

  @Override
  public void methodWithHandlerListVertxGen(Handler<List<RefedInterface1>> listHandler) {
    List<RefedInterface1> list = Arrays.asList(new RefedInterface1Impl().setString("foo"), new RefedInterface1Impl().setString("bar"));
    listHandler.handle(list);
  }

  @Override
  public void methodWithHandlerSetVertxGen(Handler<Set<RefedInterface1>> setHandler) {
    Set<RefedInterface1> list = new LinkedHashSet<>(Arrays.asList(new RefedInterface1Impl().setString("foo"), new RefedInterface1Impl().setString("bar")));
    setHandler.handle(list);
  }

  @Override
  public void methodWithHandlerListJsonObject(Handler<List<JsonObject>> listHandler) {
    List<JsonObject> list = Arrays.asList(new JsonObject().putString("cheese", "stilton"), new JsonObject().putString("socks", "tartan"));
    listHandler.handle(list);
  }

  @Override
  public void methodWithHandlerListNullJsonObject(Handler<List<JsonObject>> listHandler) {
    List<JsonObject> list = Collections.singletonList(null);
    listHandler.handle(list);
  }

  @Override
  public void methodWithHandlerSetJsonObject(Handler<Set<JsonObject>> setHandler) {
    Set<JsonObject> set = new LinkedHashSet<>(Arrays.asList(new JsonObject().putString("cheese", "stilton"), new JsonObject().putString("socks", "tartan")));
    setHandler.handle(set);
  }

  @Override
  public void methodWithHandlerSetNullJsonObject(Handler<Set<JsonObject>> setHandler) {
    Set<JsonObject> set = Collections.singleton(null);
    setHandler.handle(set);
  }

  @Override
  public void methodWithHandlerListJsonArray(Handler<List<JsonArray>> listHandler) {
    List<JsonArray> list = Arrays.asList(new JsonArray().add("green").add("blue"), new JsonArray().add("yellow").add("purple"));
    listHandler.handle(list);
  }

  @Override
  public void methodWithHandlerListNullJsonArray(Handler<List<JsonArray>> listHandler) {
    List<JsonArray> list = Collections.singletonList(null);
    listHandler.handle(list);
  }

  @Override
  public void methodWithHandlerSetJsonArray(Handler<Set<JsonArray>> listHandler) {
    Set<JsonArray> set = new LinkedHashSet<>(Arrays.asList(new JsonArray().add("green").add("blue"), new JsonArray().add("yellow").add("purple")));
    listHandler.handle(set);
  }

  @Override
  public void methodWithHandlerSetNullJsonArray(Handler<Set<JsonArray>> listHandler) {
    Set<JsonArray> set = Collections.singleton(null);
    listHandler.handle(set);
  }

  @Override
  public void methodWithHandlerAsyncResultListVertxGen(Handler<AsyncResult<List<RefedInterface1>>> listHandler) {
    List<RefedInterface1> list = Arrays.asList(new RefedInterface1Impl().setString("foo"), new RefedInterface1Impl().setString("bar"));
    listHandler.handle(Future.completedFuture(list));
  }

  @Override
  public void methodWithHandlerAsyncResultSetVertxGen(Handler<AsyncResult<Set<RefedInterface1>>> setHandler) {
    Set<RefedInterface1> list = new LinkedHashSet<>(Arrays.asList(new RefedInterface1Impl().setString("foo"), new RefedInterface1Impl().setString("bar")));
    setHandler.handle(Future.completedFuture(list));
  }

  @Override
  public void methodWithHandlerAsyncResultListJsonObject(Handler<AsyncResult<List<JsonObject>>> listHandler) {
    List<JsonObject> list = Arrays.asList(new JsonObject().putString("cheese", "stilton"), new JsonObject().putString("socks", "tartan"));
    listHandler.handle(Future.completedFuture(list));
  }

  @Override
  public void methodWithHandlerAsyncResultListNullJsonObject(Handler<AsyncResult<List<JsonObject>>> listHandler) {
    List<JsonObject> list = Collections.singletonList(null);
    listHandler.handle(Future.completedFuture(list));
  }

  @Override
  public void methodWithHandlerAsyncResultSetJsonObject(Handler<AsyncResult<Set<JsonObject>>> setHandler) {
    Set<JsonObject> set = new LinkedHashSet<>(Arrays.asList(new JsonObject().putString("cheese", "stilton"), new JsonObject().putString("socks", "tartan")));
    setHandler.handle(Future.completedFuture(set));
  }

  @Override
  public void methodWithHandlerAsyncResultSetNullJsonObject(Handler<AsyncResult<Set<JsonObject>>> setHandler) {
    Set<JsonObject> set = Collections.singleton(null);
    setHandler.handle(Future.completedFuture(set));
  }

  @Override
  public void methodWithHandlerAsyncResultListJsonArray(Handler<AsyncResult<List<JsonArray>>> listHandler) {
    List<JsonArray> list = Arrays.asList(new JsonArray().add("green").add("blue"), new JsonArray().add("yellow").add("purple"));
    listHandler.handle(Future.completedFuture(list));
  }

  @Override
  public void methodWithHandlerAsyncResultListNullJsonArray(Handler<AsyncResult<List<JsonArray>>> listHandler) {
    List<JsonArray> list = Collections.singletonList(null);
    listHandler.handle(Future.completedFuture(list));
  }

  @Override
  public void methodWithHandlerAsyncResultSetJsonArray(Handler<AsyncResult<Set<JsonArray>>> listHandler) {
    Set<JsonArray> set = new LinkedHashSet<>(Arrays.asList(new JsonArray().add("green").add("blue"), new JsonArray().add("yellow").add("purple")));
    listHandler.handle(Future.completedFuture(set));
  }

  @Override
  public void methodWithHandlerAsyncResultSetNullJsonArray(Handler<AsyncResult<Set<JsonArray>>> listHandler) {
    Set<JsonArray> set = Collections.singleton(null);
    listHandler.handle(Future.completedFuture(set));
  }

  @Override
  public void methodWithHandlerUserTypes(Handler<RefedInterface1> handler) {
    RefedInterface1 refed = new RefedInterface1Impl();
    refed.setString("echidnas");
    handler.handle(refed);
  }

  @Override
  public void methodWithHandlerAsyncResultUserTypes(Handler<AsyncResult<RefedInterface1>> handler) {
    RefedInterface1 refed = new RefedInterface1Impl();
    refed.setString("cheetahs");
    handler.handle(Future.completedFuture(refed));
  }

  @Override
  public void methodWithHandlerVoid(Handler<Void> handler) {
    handler.handle(null);
  }

  @Override
  public void methodWithHandlerAsyncResultVoid(boolean sendFailure, Handler<AsyncResult<Void>> handler) {
    if (sendFailure) {
      handler.handle(Future.completedFuture(new VertxException("foo!")));
    } else {
      handler.handle(Future.completedFuture((Void) null));
    }
  }

  @Override
  public void methodWithHandlerThrowable(Handler<Throwable> handler) {
    handler.handle(new VertxException("cheese!"));
  }

  @Override
  public byte methodWithByteReturn() {
    return (byte)123;
  }

  @Override
  public short methodWithShortReturn() {
    return (short)12345;
  }

  @Override
  public int methodWithIntReturn() {
    return 12345464;
  }

  @Override
  public long methodWithLongReturn() {
    return 65675123l;
  }

  @Override
  public float methodWithFloatReturn() {
    return 1.23f;
  }

  @Override
  public double methodWithDoubleReturn() {
    return 3.34535d;
  }

  @Override
  public boolean methodWithBooleanReturn() {
    return true;
  }

  @Override
  public char methodWithCharReturn() {
    return 'Y';
  }

  @Override
  public String methodWithStringReturn() {
    return "orangutan";
  }

  @Override
  public RefedInterface1 methodWithVertxGenReturn() {
    RefedInterface1 refed = new RefedInterface1Impl();
    refed.setString("chaffinch");
    return refed;
  }

  @Override
  public List<String> methodWithListStringReturn() {
    return Arrays.asList("foo", "bar", "wibble");
  }

  @Override
  public Set<String> methodWithSetStringReturn() {
    return new LinkedHashSet<>( Arrays.asList("foo", "bar", "wibble"));
  }

  @Override
  public String overloadedMethod(String str, RefedInterface1 refed) {
    assertEquals("cat", str);
    assertEquals("dog", refed.getString());
    return "meth1";
  }

  @Override
  public String overloadedMethod(String str, RefedInterface1 refed, long period) {
    assertEquals("cat", str);
    assertEquals("dog", refed.getString());
    assertEquals(12345l, period);
    return "meth2";
  }

  @Override
  public String overloadedMethod(String str, RefedInterface1 refed, long period, Handler<String> handler) {
    assertEquals("cat", str);
    assertEquals("dog", refed.getString());
    assertEquals(12345l, period);
    assertNotNull(handler);
    handler.handle("giraffe");
    return "meth3";
  }

  @Override
  public void superMethodWithBasicParams(byte b, short s, int i, long l, float f, double d, boolean bool, char ch, String str) {
    assertEquals((byte) 123, b);
    assertEquals((short) 12345, s);
    assertEquals(1234567, i);
    assertEquals(1265615234l, l);
    assertEquals(12.345f, f, 0);
    assertEquals(12.34566d, d, 0);
    assertTrue(bool);
    assertEquals('X', ch);
    assertEquals("foobar", str);
  }

  @Override
  public void otherSuperMethodWithBasicParams(byte b, short s, int i, long l, float f, double d, boolean bool, char ch, String str) {
    superMethodWithBasicParams(b, s, i, l, f, d, bool, ch, str);
  }

  @Override
  public <U> U methodWithGenericReturn(boolean obj) {
    if (obj) {
      return (U) (new JsonObject().putString("foo", "bar"));
    } else {
      return (U) (new JsonArray().add("foo").add("bar"));
    }
  }

  @Override
  public <U> void methodWithGenericParam(String type, U u) {
    switch (type) {
      case "String": {
        assertEquals("foo", u);
        break;
      }
      case "Ref": {
        assertTrue(u instanceof RefedInterface1);
        RefedInterface1 expected = (RefedInterface1) u;
        assertEquals("foo", expected.getString());
        break;
      }
      case "JsonObject": {
        JsonObject jsonObject = (JsonObject)u;
        assertEquals("hello", jsonObject.getString("foo"));
        assertEquals(123, jsonObject.getInteger("bar").intValue());
        break;
      }
      case "JsonArray": {
        JsonArray jsonArray = (JsonArray)u;
        assertEquals(3, jsonArray.size());
        assertEquals("foo", jsonArray.get(0));
        assertEquals("bar", jsonArray.get(1));
        assertEquals("wib", jsonArray.get(2));
        break;
      }
    }
  }

  @Override
  public <U> void methodWithGenericHandler(String type, Handler<U> handler) {
    switch (type) {
      case "String": {
        handler.handle((U)"handlerFoo");
        break;
      }
      case "Ref": {
        RefedInterface1 ref = new RefedInterface1Impl();
        ref.setString("bar");
        handler.handle((U)ref);
        break;
      }
      case "JsonObject": {
        JsonObject jsonObject = new JsonObject();
        jsonObject.putString("foo", "hello");
        jsonObject.putNumber("bar", 123);
        handler.handle((U)jsonObject);
        break;
      }
      case "JsonArray": {
        JsonArray jsonArray = new JsonArray();
        jsonArray.add("foo");
        jsonArray.add("bar");
        jsonArray.add("wib");
        handler.handle((U) jsonArray);
        break;
      }
    }
  }

  @Override
  public <U> void methodWithGenericHandlerAsyncResult(String type, Handler<AsyncResult<U>> asyncResultHandler) {
    switch (type) {
      case "String": {
        asyncResultHandler.handle(Future.completedFuture((U)"asyncResultHandlerFoo"));
        break;
      }
      case "Ref": {
        RefedInterface1 ref = new RefedInterface1Impl();
        ref.setString("bar");
        asyncResultHandler.handle(Future.completedFuture((U)ref));
        break;
      }
      case "JsonObject": {
        JsonObject jsonObject = new JsonObject();
        jsonObject.putString("foo", "hello");
        jsonObject.putNumber("bar", 123);
        asyncResultHandler.handle(Future.completedFuture((U)jsonObject));
        break;
      }
      case "JsonArray": {
        JsonArray jsonArray = new JsonArray();
        jsonArray.add("foo");
        jsonArray.add("bar");
        jsonArray.add("wib");
        asyncResultHandler.handle(Future.completedFuture((U) jsonArray));
        break;
      }
    }
  }

  @Override
  public TestInterface fluentMethod(String str) {
    assertEquals("bar", str);
    return this;
  }

  @Override
  public RefedInterface1 methodWithCachedReturn(String foo) {
    RefedInterface1 refed = new RefedInterface1Impl();
    refed.setString(foo);
    return refed;
  }

  @Override
  public JsonObject methodWithJsonObjectReturn() {
    return new JsonObject().putString("cheese", "stilton");
  }

  @Override
  public JsonObject methodWithNullJsonObjectReturn() {
    return null;
  }

  @Override
  public JsonArray methodWithJsonArrayReturn() {
    return new JsonArray().add("socks").add("shoes");
  }

  @Override
  public JsonArray methodWithNullJsonArrayReturn() {
    return null;
  }

  @Override
  public void methodWithJsonParams(JsonObject jsonObject, JsonArray jsonArray) {
    assertNotNull(jsonObject);
    assertNotNull(jsonArray);
    assertEquals("lion", jsonObject.getString("cat"));
    assertEquals("cheddar", jsonObject.getString("cheese"));
    assertEquals("house", jsonArray.get(0));
    assertEquals("spider", jsonArray.get(1));
  }

  @Override
  public void methodWithNullJsonParams(JsonObject jsonObject, JsonArray jsonArray) {
    assertNull(jsonObject);
    assertNull(jsonArray);
  }

  @Override
  public void methodWithHandlerJson(Handler<JsonObject> jsonObjectHandler, Handler<JsonArray> jsonArrayHandler) {
    assertNotNull(jsonObjectHandler);
    assertNotNull(jsonArrayHandler);
    jsonObjectHandler.handle(new JsonObject().putString("cheese", "stilton"));
    jsonArrayHandler.handle(new JsonArray().add("socks").add("shoes"));
  }

  @Override
  public void methodWithHandlerNullJson(Handler<JsonObject> jsonObjectHandler, Handler<JsonArray> jsonArrayHandler) {
    assertNotNull(jsonObjectHandler);
    assertNotNull(jsonArrayHandler);
    jsonObjectHandler.handle(null);
    jsonArrayHandler.handle(null);
  }

  @Override
  public void methodWithHandlerAsyncResultJsonObject(Handler<AsyncResult<JsonObject>> handler) {
    assertNotNull(handler);
    handler.handle(Future.completedFuture(new JsonObject().putString("cheese", "stilton")));
  }

  @Override
  public void methodWithHandlerAsyncResultNullJsonObject(Handler<AsyncResult<JsonObject>> handler) {
    assertNotNull(handler);
    handler.handle(Future.completedFuture(null));
  }

  @Override
  public void methodWithHandlerAsyncResultJsonArray(Handler<AsyncResult<JsonArray>> handler) {
    assertNotNull(handler);
    handler.handle(Future.completedFuture(new JsonArray().add("socks").add("shoes")));
  }

  @Override
  public void methodWithHandlerAsyncResultNullJsonArray(Handler<AsyncResult<JsonArray>> handler) {
    assertNotNull(handler);
    handler.handle(Future.completedFuture(null));
  }
}
