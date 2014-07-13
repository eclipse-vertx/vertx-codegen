package io.vertx.codegen.testmodel;

import io.vertx.codegen.annotations.CacheReturn;
import io.vertx.codegen.annotations.Fluent;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.util.List;
import java.util.Set;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
@VertxGen
public interface TestInterface<T> extends SuperInterface1, SuperInterface2 {

  // Test params

  void methodWithBasicParams(byte b, short s, int i, long l, float f, double d, boolean bool, char ch, String str);

  void methodWithBasicBoxedParams(Byte b, Short s, Integer i, Long l, Float f, Double d, Boolean bool, Character ch);

  void methodWithHandlerBasicTypes(Handler<Byte> byteHandler, Handler<Short> shortHandler, Handler<Integer> intHandler,
                                   Handler<Long> longHandler, Handler<Float> floatHandler, Handler<Double> doubleHandler,
                                   Handler<Boolean> booleanHandler, Handler<Character> charHandler, Handler<String> stringHandler);

  void methodWithHandlerAsyncResultBasicTypes(boolean sendFailure, Handler<AsyncResult<Byte>> byteHandler, Handler<AsyncResult<Short>> shortHandler, Handler<AsyncResult<Integer>> intHandler,
                                              Handler<AsyncResult<Long>> longHandler, Handler<AsyncResult<Float>> floatHandler, Handler<AsyncResult<Double>> doubleHandler,
                                              Handler<AsyncResult<Boolean>> booleanHandler, Handler<AsyncResult<Character>> charHandler, Handler<AsyncResult<String>> stringHandler);

  void methodWithUserTypes(RefedInterface1 refed);

  void methodWithObjectParam(String str, Object obj);

  void methodWithOptionsParam(TestOptions options);

  void methodWithHandlerListAndSet(Handler<List<String>> listStringHandler, Handler<List<Integer>> listIntHandler,
                                   Handler<Set<String>> setStringHandler, Handler<Set<Integer>> setIntHandler);

  void methodWithHandlerAsyncResultListAndSet(Handler<AsyncResult<List<String>>> listStringHandler, Handler<AsyncResult<List<Integer>>> listIntHandler,
                                              Handler<AsyncResult<Set<String>>> setStringHandler, Handler<AsyncResult<Set<Integer>>> setIntHandler);

  void methodWithHandlerUserTypes(Handler<RefedInterface1> handler);

  void methodWithHandlerAsyncResultUserTypes(Handler<AsyncResult<RefedInterface1>> handler);

  void methodWithHandlerVoid(Handler<Void> handler);

  void methodWithHandlerAsyncResultVoid(boolean sendFailure, Handler<AsyncResult<Void>> handler);

  void methodWithHandlerThrowable(Handler<Throwable> handler);

  byte methodWithByteReturn();

  short methodWithShortReturn();

  int methodWithIntReturn();

  long methodWithLongReturn();

  float methodWithFloatReturn();

  double methodWithDoubleReturn();

  boolean methodWithBooleanReturn();

  char methodWithCharReturn();

  String methodWithStringReturn();

  RefedInterface1 methodWithVertxGenReturn();

  List<String> methodWithListStringReturn();

  Set<String> methodWithSetStringReturn();

  String overloadedMethod(String str, RefedInterface1 refed);

  String overloadedMethod(String str, RefedInterface1 refed, long period);

  String overloadedMethod(String str, RefedInterface1 refed, long period, Handler<String> handler);

  T methodWithGenericReturn(boolean obj);

  @Fluent
  TestInterface fluentMethod(String str);

  static RefedInterface1 staticFactoryMethod(String foo) {
    RefedInterface1 refed = new RefedInterface1Impl();
    refed.setString(foo);
    return refed;
  }

  @CacheReturn
  RefedInterface1 methodWithCachedReturn(String foo);

  JsonObject methodwithJsonObjectReturn();

  JsonArray methodWithJsonArrayReturn();

  void methodWithJsonParams(JsonObject jsonObject, JsonArray jsonArray);

  void methodWithHandlerJson(Handler<JsonObject> jsonObjectHandler, Handler<JsonArray> jsonArrayHandler);

  void methodWithHandlerAsyncResultJson(Handler<AsyncResult<JsonObject>> jsonObjectHandler, Handler<AsyncResult<JsonArray>> jsonArrayHandler);

}
