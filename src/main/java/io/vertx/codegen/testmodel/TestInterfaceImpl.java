package io.vertx.codegen.testmodel;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.VertxException;
import io.vertx.core.impl.FutureResultImpl;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.util.Arrays;
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
  public void methodWithHandlerAsyncResultBasicTypes(boolean sendFailure, Handler<AsyncResult<Byte>> byteHandler, Handler<AsyncResult<Short>> shortHandler,
                                                     Handler<AsyncResult<Integer>> intHandler, Handler<AsyncResult<Long>> longHandler,
                                                     Handler<AsyncResult<Float>> floatHandler, Handler<AsyncResult<Double>> doubleHandler,
                                                     Handler<AsyncResult<Boolean>> booleanHandler, Handler<AsyncResult<Character>> charHandler,
                                                     Handler<AsyncResult<String>> stringHandler) {
    if (sendFailure) {
      Exception e = new Exception("foobar!");
      byteHandler.handle(new FutureResultImpl<>(e));
      shortHandler.handle(new FutureResultImpl<>(e));
      intHandler.handle(new FutureResultImpl<>(e));
      longHandler.handle(new FutureResultImpl<>(e));
      floatHandler.handle(new FutureResultImpl<>(e));
      doubleHandler.handle(new FutureResultImpl<>(e));
      booleanHandler.handle(new FutureResultImpl<>(e));
      charHandler.handle(new FutureResultImpl<>(e));
      stringHandler.handle(new FutureResultImpl<>(e));    
    } else {
      byteHandler.handle(new FutureResultImpl<>((byte) 123));
      shortHandler.handle(new FutureResultImpl<>((short) 12345));
      intHandler.handle(new FutureResultImpl<>(1234567));
      longHandler.handle(new FutureResultImpl<>(1265615234l));
      floatHandler.handle(new FutureResultImpl<>(12.345f));
      doubleHandler.handle(new FutureResultImpl<>(12.34566d));
      booleanHandler.handle(new FutureResultImpl<>(true));
      charHandler.handle(new FutureResultImpl<>('X'));
      stringHandler.handle(new FutureResultImpl<>("quux!"));
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
  public void methodWithHandlerAsyncResultListAndSet(Handler<AsyncResult<List<String>>> listStringHandler, Handler<AsyncResult<List<Integer>>> listIntHandler,
                                                     Handler<AsyncResult<Set<String>>> setStringHandler, Handler<AsyncResult<Set<Integer>>> setIntHandler) {
    List<String> listString = Arrays.asList("foo", "bar", "wibble");
    List<Integer> listInt = Arrays.asList(5, 12, 100);
    Set<String> setString = new LinkedHashSet<>( Arrays.asList("foo", "bar", "wibble"));
    Set<Integer> setInt = new LinkedHashSet<>(Arrays.asList(5, 12, 100));
    listStringHandler.handle(new FutureResultImpl<>(listString));
    listIntHandler.handle(new FutureResultImpl<>(listInt));
    setStringHandler.handle(new FutureResultImpl<>(setString));
    setIntHandler.handle(new FutureResultImpl<>(setInt));
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
  public void methodWithHandlerSetJsonObject(Handler<Set<JsonObject>> setHandler) {
    Set<JsonObject> set = new LinkedHashSet<>(Arrays.asList(new JsonObject().putString("cheese", "stilton"), new JsonObject().putString("socks", "tartan")));
    setHandler.handle(set);
  }

  @Override
  public void methodWithHandlerListJsonArray(Handler<List<JsonArray>> listHandler) {
    List<JsonArray> list = Arrays.asList(new JsonArray().add("green").add("blue"), new JsonArray().add("yellow").add("purple"));
    listHandler.handle(list);
  }

  @Override
  public void methodWithHandlerSetJsonArray(Handler<Set<JsonArray>> listHandler) {
    Set<JsonArray> set = new LinkedHashSet<>(Arrays.asList(new JsonArray().add("green").add("blue"), new JsonArray().add("yellow").add("purple")));
    listHandler.handle(set);
  }

  @Override
  public void methodWithHandlerAsyncResultListVertxGen(Handler<AsyncResult<List<RefedInterface1>>> listHandler) {
    List<RefedInterface1> list = Arrays.asList(new RefedInterface1Impl().setString("foo"), new RefedInterface1Impl().setString("bar"));
    listHandler.handle(new FutureResultImpl<>(list));
  }

  @Override
  public void methodWithHandlerAsyncResultSetVertxGen(Handler<AsyncResult<Set<RefedInterface1>>> setHandler) {
    Set<RefedInterface1> list = new LinkedHashSet<>(Arrays.asList(new RefedInterface1Impl().setString("foo"), new RefedInterface1Impl().setString("bar")));
    setHandler.handle(new FutureResultImpl<>(list));
  }

  @Override
  public void methodWithHandlerAsyncResultListJsonObject(Handler<AsyncResult<List<JsonObject>>> listHandler) {
    List<JsonObject> list = Arrays.asList(new JsonObject().putString("cheese", "stilton"), new JsonObject().putString("socks", "tartan"));
    listHandler.handle(new FutureResultImpl<>(list));
  }

  @Override
  public void methodWithHandlerAsyncResultSetJsonObject(Handler<AsyncResult<Set<JsonObject>>> setHandler) {
    Set<JsonObject> set = new LinkedHashSet<>(Arrays.asList(new JsonObject().putString("cheese", "stilton"), new JsonObject().putString("socks", "tartan")));
    setHandler.handle(new FutureResultImpl<>(set));
  }

  @Override
  public void methodWithHandlerAsyncResultListJsonArray(Handler<AsyncResult<List<JsonArray>>> listHandler) {
    List<JsonArray> list = Arrays.asList(new JsonArray().add("green").add("blue"), new JsonArray().add("yellow").add("purple"));
    listHandler.handle(new FutureResultImpl<>(list));
  }

  @Override
  public void methodWithHandlerAsyncResultSetJsonArray(Handler<AsyncResult<Set<JsonArray>>> listHandler) {
    Set<JsonArray> set = new LinkedHashSet<>(Arrays.asList(new JsonArray().add("green").add("blue"), new JsonArray().add("yellow").add("purple")));
    listHandler.handle(new FutureResultImpl<>(set));
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
    handler.handle(new FutureResultImpl<>(refed));
  }

  @Override
  public void methodWithHandlerVoid(Handler<Void> handler) {
    handler.handle(null);
  }

  @Override
  public void methodWithHandlerAsyncResultVoid(boolean sendFailure, Handler<AsyncResult<Void>> handler) {
    if (sendFailure) {
      handler.handle(new FutureResultImpl<>(new VertxException("foo!")));
    } else {
      handler.handle(new FutureResultImpl<>((Void) null));
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
  public T methodWithGenericReturn(boolean obj) {
    if (obj) {
      return (T) (new JsonObject().putString("foo", "bar"));
    } else {
      return (T) (new JsonArray().add("foo").add("bar"));
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
  public JsonObject methodwithJsonObjectReturn() {
    return new JsonObject().putString("cheese", "stilton");
  }

  @Override
  public JsonArray methodWithJsonArrayReturn() {
    return new JsonArray().add("socks").add("shoes");
  }

  @Override
  public void methodWithJsonParams(JsonObject jsonObject, JsonArray jsonArray) {
    System.out.println("Got jsonObject:" + jsonObject.toString());
    System.out.println("Got jsonArray:" + jsonArray.toString());
    assertNotNull(jsonObject);
    assertNotNull(jsonArray);
    assertEquals("lion", jsonObject.getString("cat"));
    assertEquals("cheddar", jsonObject.getString("cheese"));
    assertEquals("house", jsonArray.get(0));
    assertEquals("spider", jsonArray.get(1));
  }

  @Override
  public void methodWithHandlerJson(Handler<JsonObject> jsonObjectHandler, Handler<JsonArray> jsonArrayHandler) {
    assertNotNull(jsonObjectHandler);
    assertNotNull(jsonArrayHandler);
    jsonObjectHandler.handle(new JsonObject().putString("cheese", "stilton"));
    jsonArrayHandler.handle(new JsonArray().add("socks").add("shoes"));
  }

  @Override
  public void methodWithHandlerAsyncResultJson(Handler<AsyncResult<JsonObject>> jsonObjectHandler, Handler<AsyncResult<JsonArray>> jsonArrayHandler) {
    assertNotNull(jsonObjectHandler);
    assertNotNull(jsonArrayHandler);
    jsonObjectHandler.handle(new FutureResultImpl<>(new JsonObject().putString("cheese", "stilton")));
    jsonArrayHandler.handle(new FutureResultImpl<>(new JsonArray().add("socks").add("shoes")));
  }
}
