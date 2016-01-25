package io.vertx.codegen.testmodel;

import io.vertx.codegen.annotations.CacheReturn;
import io.vertx.codegen.annotations.Fluent;
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
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
@VertxGen
public interface TestInterface extends SuperInterface1, SuperInterface2 {

  // Test params

  void methodWithBasicParams(byte b, short s, int i, long l, float f, double d, boolean bool, char ch, String str);

  void methodWithBasicBoxedParams(Byte b, Short s, Integer i, Long l, Float f, Double d, Boolean bool, Character ch);

  void methodWithHandlerBasicTypes(Handler<Byte> byteHandler, Handler<Short> shortHandler, Handler<Integer> intHandler,
                                   Handler<Long> longHandler, Handler<Float> floatHandler, Handler<Double> doubleHandler,
                                   Handler<Boolean> booleanHandler, Handler<Character> charHandler, Handler<String> stringHandler);

  Handler<String> methodWithHandlerStringReturn(String expected);
  <T> Handler<T> methodWithHandlerGenericReturn(Handler<T> handler);
  Handler<RefedInterface1> methodWithHandlerVertxGenReturn(String expected);

  void methodWithHandlerAsyncResultByte(boolean sendFailure, Handler<AsyncResult<Byte>> handler);
  void methodWithHandlerAsyncResultShort(boolean sendFailure, Handler<AsyncResult<Short>> handler);
  void methodWithHandlerAsyncResultInteger(boolean sendFailure, Handler<AsyncResult<Integer>> handler);
  void methodWithHandlerAsyncResultLong(boolean sendFailure, Handler<AsyncResult<Long>> handler);
  void methodWithHandlerAsyncResultFloat(boolean sendFailure, Handler<AsyncResult<Float>> handler);
  void methodWithHandlerAsyncResultDouble(boolean sendFailure, Handler<AsyncResult<Double>> handler);
  void methodWithHandlerAsyncResultBoolean(boolean sendFailure, Handler<AsyncResult<Boolean>> handler);
  void methodWithHandlerAsyncResultCharacter(boolean sendFailure, Handler<AsyncResult<Character>> handler);
  void methodWithHandlerAsyncResultString(boolean sendFailure, Handler<AsyncResult<String>> handler);
  void methodWithHandlerAsyncResultDataObject(boolean sendFailure, Handler<AsyncResult<TestDataObject>> handler);

  Handler<AsyncResult<String>> methodWithHandlerAsyncResultStringReturn(String expected, boolean fail);
  <T> Handler<AsyncResult<T>> methodWithHandlerAsyncResultGenericReturn(Handler<AsyncResult<T>> handler);
  Handler<AsyncResult<RefedInterface1>> methodWithHandlerAsyncResultVertxGenReturn(String expected, boolean fail);

  void methodWithUserTypes(RefedInterface1 refed);

  void methodWithObjectParam(String str, Object obj);

  void methodWithDataObjectParam(TestDataObject dataObject);
  void methodWithListOfDataObjectsParam(List<TestDataObject> dataObjects);
  void methodWithSetOfDataObjectsParam(Set<TestDataObject> dataObjects);

  void methodWithNullDataObjectParam(@Nullable TestDataObject dataObject);

  void methodWithListParams(List<String> listString, List<Byte> listByte, List<Short> listShort, List<Integer> listInt, List<Long> listLong, List<JsonObject> listJsonObject, List<JsonArray> listJsonArray, List<RefedInterface1> listVertxGen, List<TestDataObject> listDataObject, List<TestEnum> listEnum);

  void methodWithSetParams(Set<String> setString, Set<Byte> setByte, Set<Short> setShort, Set<Integer> setInt, Set<Long> setLong, Set<JsonObject> setJsonObject, Set<JsonArray> setJsonArray, Set<RefedInterface1> setVertxGen, Set<TestDataObject> setDataObject, Set<TestEnum> setEnum);

  void methodWithMapParams(Map<String, String> mapString, Map<String, Byte> mapByte, Map<String, Short> mapShort, Map<String, Integer> mapInt, Map<String, Long> mapLong, Map<String, JsonObject> mapJsonObject, Map<String, JsonArray> mapJsonArray, Map<String, RefedInterface1> mapVertxGen);

  void methodWithHandlerListAndSet(Handler<List<String>> listStringHandler, Handler<List<Integer>> listIntHandler,
                                   Handler<Set<String>> setStringHandler, Handler<Set<Integer>> setIntHandler);

  void methodWithHandlerAsyncResultListString(Handler<AsyncResult<List<String>>> handler);
  void methodWithHandlerAsyncResultListInteger(Handler<AsyncResult<List<Integer>>> handler);
  void methodWithHandlerAsyncResultSetString(Handler<AsyncResult<Set<String>>> handler);
  void methodWithHandlerAsyncResultSetInteger(Handler<AsyncResult<Set<Integer>>> handler);

  void methodWithHandlerListVertxGen(Handler<List<RefedInterface1>> listHandler);

  void methodWithHandlerSetVertxGen(Handler<Set<RefedInterface1>> listHandler);

  void methodWithHandlerListAbstractVertxGen(Handler<List<RefedInterface2>> listHandler);

  void methodWithHandlerSetAbstractVertxGen(Handler<Set<RefedInterface2>> listHandler);

  void methodWithHandlerListJsonObject(Handler<List<JsonObject>> listHandler);

  void methodWithHandlerListNullJsonObject(Handler<List<JsonObject>> listHandler);

  void methodWithHandlerListComplexJsonObject(Handler<List<JsonObject>> listHandler);

  void methodWithHandlerSetJsonObject(Handler<Set<JsonObject>> listHandler);

  void methodWithHandlerSetNullJsonObject(Handler<Set<JsonObject>> listHandler);

  void methodWithHandlerSetComplexJsonObject(Handler<Set<JsonObject>> listHandler);

  void methodWithHandlerListJsonArray(Handler<List<JsonArray>> listHandler);

  void methodWithHandlerListNullJsonArray(Handler<List<JsonArray>> listHandler);

  void methodWithHandlerListComplexJsonArray(Handler<List<JsonArray>> listHandler);

  void methodWithHandlerSetJsonArray(Handler<Set<JsonArray>> listHandler);

  void methodWithHandlerSetNullJsonArray(Handler<Set<JsonArray>> listHandler);

  void methodWithHandlerSetComplexJsonArray(Handler<Set<JsonArray>> setHandler);

  void methodWithHandlerListDataObject(Handler<List<TestDataObject>> listHandler);

  void methodWithHandlerListNullDataObject(Handler<List<TestDataObject>> listHandler);

  void methodWithHandlerSetDataObject(Handler<Set<TestDataObject>> setHandler);

  void methodWithHandlerSetNullDataObject(Handler<Set<TestDataObject>> setHandler);

  void methodWithHandlerListEnum(Handler<List<TestEnum>> listHandler);

  void methodWithHandlerSetEnum(Handler<Set<TestEnum>> setHandler);

  void methodWithHandlerAsyncResultListVertxGen(Handler<AsyncResult<List<RefedInterface1>>> listHandler);

  void methodWithHandlerAsyncResultSetVertxGen(Handler<AsyncResult<Set<RefedInterface1>>> listHandler);

  void methodWithHandlerAsyncResultListAbstractVertxGen(Handler<AsyncResult<List<RefedInterface2>>> listHandler);

  void methodWithHandlerAsyncResultSetAbstractVertxGen(Handler<AsyncResult<Set<RefedInterface2>>> listHandler);

  void methodWithHandlerAsyncResultListJsonObject(Handler<AsyncResult<List<JsonObject>>> listHandler);

  void methodWithHandlerAsyncResultListNullJsonObject(Handler<AsyncResult<List<JsonObject>>> listHandler);

  void methodWithHandlerAsyncResultListComplexJsonObject(Handler<AsyncResult<List<JsonObject>>> listHandler);

  void methodWithHandlerAsyncResultSetJsonObject(Handler<AsyncResult<Set<JsonObject>>> listHandler);

  void methodWithHandlerAsyncResultSetNullJsonObject(Handler<AsyncResult<Set<JsonObject>>> listHandler);

  void methodWithHandlerAsyncResultSetComplexJsonObject(Handler<AsyncResult<Set<JsonObject>>> listHandler);

  void methodWithHandlerAsyncResultListJsonArray(Handler<AsyncResult<List<JsonArray>>> listHandler);

  void methodWithHandlerAsyncResultListNullJsonArray(Handler<AsyncResult<List<JsonArray>>> listHandler);

  void methodWithHandlerAsyncResultListComplexJsonArray(Handler<AsyncResult<List<JsonArray>>> listHandler);

  void methodWithHandlerAsyncResultSetJsonArray(Handler<AsyncResult<Set<JsonArray>>> listHandler);

  void methodWithHandlerAsyncResultSetNullJsonArray(Handler<AsyncResult<Set<JsonArray>>> listHandler);

  void methodWithHandlerAsyncResultSetComplexJsonArray(Handler<AsyncResult<Set<JsonArray>>> listHandler);

  void methodWithHandlerAsyncResultListDataObject(Handler<AsyncResult<List<TestDataObject>>> listHandler);

  void methodWithHandlerAsyncResultListNullDataObject(Handler<AsyncResult<List<TestDataObject>>> listHandler);

  void methodWithHandlerAsyncResultSetDataObject(Handler<AsyncResult<Set<TestDataObject>>> setHandler);

  void methodWithHandlerAsyncResultSetNullDataObject(Handler<AsyncResult<Set<TestDataObject>>> setHandler);

  void methodWithHandlerAsyncResultListEnum(Handler<AsyncResult<List<TestEnum>>> listHandler);

  void methodWithHandlerAsyncResultSetEnum(Handler<AsyncResult<Set<TestEnum>>> setHandler);

  void methodWithHandlerUserTypes(Handler<RefedInterface1> handler);

  void methodWithHandlerAsyncResultUserTypes(Handler<AsyncResult<RefedInterface1>> handler);

  void methodWithConcreteHandlerUserTypeSubtype(ConcreteHandlerUserType handler);

  void methodWithAbstractHandlerUserTypeSubtype(AbstractHandlerUserType handler);

  void methodWithConcreteHandlerUserTypeSubtypeExtension(ConcreteHandlerUserTypeExtension handler);

  void methodWithHandlerVoid(Handler<Void> handler);

  void methodWithHandlerAsyncResultVoid(boolean sendFailure, Handler<AsyncResult<Void>> handler);

  void methodWithHandlerThrowable(Handler<Throwable> handler);

  void methodWithHandlerDataObject(Handler<TestDataObject> handler);

  <U> void methodWithHandlerGenericUserType(U value, Handler<GenericRefedInterface<U>> handler);

  <U> void methodWithHandlerAsyncResultGenericUserType(U value, Handler<AsyncResult<GenericRefedInterface<U>>> handler);

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

  RefedInterface1 methodWithVertxGenNullReturn();

  RefedInterface2 methodWithAbstractVertxGenReturn();

  TestDataObject methodWithDataObjectReturn();

  TestDataObject methodWithDataObjectNullReturn();

  String overloadedMethod(String str, Handler<String> handler);

  String overloadedMethod(String str, RefedInterface1 refed);

  String overloadedMethod(String str, RefedInterface1 refed, Handler<String> handler);

  String overloadedMethod(String str, RefedInterface1 refed, long period, Handler<String> handler);

  <U> U methodWithGenericReturn(String type);

  <U> void methodWithGenericParam(String type, U u);

  <U> void methodWithGenericHandler(String type, Handler<U> handler);

  <U> void methodWithGenericHandlerAsyncResult(String type, Handler<AsyncResult<U>> asyncResultHandler);

  @Fluent
  TestInterface fluentMethod(String str);

  static RefedInterface1 staticFactoryMethod(String foo) {
    RefedInterface1 refed = new RefedInterface1Impl();
    refed.setString(foo);
    return refed;
  }

  @CacheReturn
  RefedInterface1 methodWithCachedReturn(String foo);

  @CacheReturn
  int methodWithCachedReturnPrimitive(int arg);

  @CacheReturn
  List<RefedInterface1> methodWithCachedListReturn();

  JsonObject methodWithJsonObjectReturn();

  JsonObject methodWithNullJsonObjectReturn();

  JsonObject methodWithComplexJsonObjectReturn();

  JsonArray methodWithJsonArrayReturn();

  JsonArray methodWithNullJsonArrayReturn();

  JsonArray methodWithComplexJsonArrayReturn();

  void methodWithJsonParams(JsonObject jsonObject, JsonArray jsonArray);

  void methodWithNullJsonParams(JsonObject jsonObject, JsonArray jsonArray);

  void methodWithHandlerJson(Handler<JsonObject> jsonObjectHandler, Handler<JsonArray> jsonArrayHandler);

  void methodWithHandlerNullJson(Handler<JsonObject> jsonObjectHandler, Handler<JsonArray> jsonArrayHandler);

  void methodWithHandlerComplexJson(Handler<JsonObject> jsonObjectHandler, Handler<JsonArray> jsonArrayHandler);

  void methodWithHandlerAsyncResultJsonObject(Handler<AsyncResult<JsonObject>> handler);

  void methodWithHandlerAsyncResultNullJsonObject(Handler<AsyncResult<JsonObject>> handler);

  void methodWithHandlerAsyncResultComplexJsonObject(Handler<AsyncResult<JsonObject>> handler);

  void methodWithHandlerAsyncResultJsonArray(Handler<AsyncResult<JsonArray>> handler);

  void methodWithHandlerAsyncResultNullJsonArray(Handler<AsyncResult<JsonArray>> handler);

  void methodWithHandlerAsyncResultComplexJsonArray(Handler<AsyncResult<JsonArray>> handler);

  Map<String, String> methodWithMapReturn(Handler<String> handler);

  Map<String, String> methodWithMapStringReturn(Handler<String> handler);

  Map<String, Long> methodWithMapLongReturn(Handler<String> handler);

  Map<String, Integer> methodWithMapIntegerReturn(Handler<String> handler);

  Map<String, Short> methodWithMapShortReturn(Handler<String> handler);

  Map<String, Byte> methodWithMapByteReturn(Handler<String> handler);

  Map<String, Character> methodWithMapCharacterReturn(Handler<String> handler);

  Map<String, Boolean> methodWithMapBooleanReturn(Handler<String> handler);

  Map<String, Float> methodWithMapFloatReturn(Handler<String> handler);

  Map<String, Double> methodWithMapDoubleReturn(Handler<String> handler);

  Map<String, JsonObject> methodWithMapJsonObjectReturn(Handler<String> handler);

  Map<String, JsonObject> methodWithMapComplexJsonObjectReturn(Handler<String> handler);

  Map<String, JsonArray> methodWithMapJsonArrayReturn(Handler<String> handler);

  Map<String, JsonArray> methodWithMapComplexJsonArrayReturn(Handler<String> handler);

  Map<String, String> methodWithNullMapReturn();

  List<String> methodWithListStringReturn();

  List<Long> methodWithListLongReturn();

  List<RefedInterface1> methodWithListVertxGenReturn();

  List<JsonObject> methodWithListJsonObjectReturn();

  List<JsonObject> methodWithListComplexJsonObjectReturn();

  List<JsonArray> methodWithListJsonArrayReturn();

  List<JsonArray> methodWithListComplexJsonArrayReturn();

  List<TestDataObject> methodWithListDataObjectReturn();

  List<TestEnum> methodWithListEnumReturn();

  List<String> methodWithNullListReturn();


  Set<String> methodWithSetStringReturn();

  Set<Long> methodWithSetLongReturn();

  Set<RefedInterface1> methodWithSetVertxGenReturn();

  Set<JsonObject> methodWithSetJsonObjectReturn();

  Set<JsonObject> methodWithSetComplexJsonObjectReturn();

  Set<JsonArray> methodWithSetJsonArrayReturn();

  Set<JsonArray> methodWithSetComplexJsonArrayReturn();

  Set<TestDataObject> methodWithSetDataObjectReturn();

  Set<TestEnum> methodWithSetEnumReturn();

  Set<String> methodWithNullSetReturn();


  String methodWithEnumParam(String strVal, TestEnum weirdo);

  TestEnum methodWithEnumReturn(String strVal);

  String methodWithGenEnumParam(String strVal, TestGenEnum weirdo);

  TestGenEnum methodWithGenEnumReturn(String strVal);

  Throwable methodWithThrowableReturn(String strVal);

  String methodWithThrowableParam(Throwable t);

}
