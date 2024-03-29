package io.vertx.codegen.testmodel;

import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@VertxGen
public interface CollectionTCK {

  void methodWithListParams(List<String> listString, List<Byte> listByte, List<Short> listShort, List<Integer> listInt, List<Long> listLong, List<JsonObject> listJsonObject, List<JsonArray> listJsonArray, List<RefedInterface1> listVertxGen, List<TestDataObject> listDataObject, List<TestEnum> listEnum, List<Object> listObject);

  void methodWithSetParams(Set<String> setString, Set<Byte> setByte, Set<Short> setShort, Set<Integer> setInt, Set<Long> setLong, Set<JsonObject> setJsonObject, Set<JsonArray> setJsonArray, Set<RefedInterface1> setVertxGen, Set<TestDataObject> setDataObject, Set<TestEnum> setEnum, Set<Object> setObject);

  void methodWithMapParams(Map<String, String> mapString, Map<String, Byte> mapByte, Map<String, Short> mapShort, Map<String, Integer> mapInt, Map<String, Long> mapLong, Map<String, JsonObject> mapJsonObject, Map<String, JsonArray> mapJsonArray, Map<String, RefedInterface1> mapVertxGen, Map<String, TestDataObject> mapDataObject, Map<String, TestEnum> mapEnum, Map<String, Object> mapObject);

  void methodWithHandlerListAndSet(Handler<List<String>> listStringHandler, Handler<List<Integer>> listIntHandler,
                                   Handler<Set<String>> setStringHandler, Handler<Set<Integer>> setIntHandler);

  Future<List<String>> methodWithHandlerAsyncResultListString();
  Future<List<Integer>> methodWithHandlerAsyncResultListInteger();
  void methodWithHandlerListVertxGen(Handler<List<RefedInterface1>> listHandler);
  void methodWithHandlerListAbstractVertxGen(Handler<List<RefedInterface2>> listHandler);
  void methodWithHandlerListJsonObject(Handler<List<JsonObject>> listHandler);
  void methodWithHandlerListComplexJsonObject(Handler<List<JsonObject>> listHandler);
  void methodWithHandlerListJsonArray(Handler<List<JsonArray>> listHandler);
  void methodWithHandlerListComplexJsonArray(Handler<List<JsonArray>> listHandler);
  void methodWithHandlerListDataObject(Handler<List<TestDataObject>> listHandler);
  void methodWithHandlerListEnum(Handler<List<TestEnum>> listHandler);

  Future<Set<String>> methodWithHandlerAsyncResultSetString();
  Future<Set<Integer>> methodWithHandlerAsyncResultSetInteger();
  void methodWithHandlerSetVertxGen(Handler<Set<RefedInterface1>> listHandler);
  void methodWithHandlerSetAbstractVertxGen(Handler<Set<RefedInterface2>> listHandler);
  void methodWithHandlerSetJsonObject(Handler<Set<JsonObject>> listHandler);
  void methodWithHandlerSetComplexJsonObject(Handler<Set<JsonObject>> listHandler);
  void methodWithHandlerSetJsonArray(Handler<Set<JsonArray>> listHandler);
  void methodWithHandlerSetComplexJsonArray(Handler<Set<JsonArray>> setHandler);
  void methodWithHandlerSetDataObject(Handler<Set<TestDataObject>> setHandler);
  void methodWithHandlerSetEnum(Handler<Set<TestEnum>> setHandler);

  Future<List<RefedInterface1>> methodWithHandlerAsyncResultListVertxGen();
  Future<List<RefedInterface2>> methodWithHandlerAsyncResultListAbstractVertxGen();
  Future<List<JsonObject>> methodWithHandlerAsyncResultListJsonObject();
  Future<List<JsonObject>> methodWithHandlerAsyncResultListComplexJsonObject();
  Future<List<JsonArray>> methodWithHandlerAsyncResultListJsonArray();
  Future<List<JsonArray>> methodWithHandlerAsyncResultListComplexJsonArray();
  Future<List<TestDataObject>> methodWithHandlerAsyncResultListDataObject();
  Future<List<TestEnum>> methodWithHandlerAsyncResultListEnum();

  Future<Set<RefedInterface1>> methodWithHandlerAsyncResultSetVertxGen();
  Future<Set<RefedInterface2>> methodWithHandlerAsyncResultSetAbstractVertxGen();
  Future<Set<JsonObject>> methodWithHandlerAsyncResultSetJsonObject();
  Future<Set<JsonObject>> methodWithHandlerAsyncResultSetComplexJsonObject();
  Future<Set<JsonArray>> methodWithHandlerAsyncResultSetJsonArray();
  Future<Set<JsonArray>> methodWithHandlerAsyncResultSetComplexJsonArray();
  Future<Set<TestDataObject>> methodWithHandlerAsyncResultSetDataObject();
  Future<Set<TestEnum>> methodWithHandlerAsyncResultSetEnum();

  Map<String, String> methodWithMapStringReturn(Handler<String> handler);
  Map<String, Long> methodWithMapLongReturn(Handler<String> handler);
  Map<String, Integer> methodWithMapIntegerReturn(Handler<String> handler);
  Map<String, Short> methodWithMapShortReturn(Handler<String> handler);
  Map<String, Byte> methodWithMapByteReturn(Handler<String> handler);
  Map<String, Character> methodWithMapCharacterReturn(Handler<String> handler);
  Map<String, Boolean> methodWithMapBooleanReturn(Handler<String> handler);
  Map<String, Float> methodWithMapFloatReturn(Handler<String> handler);
  Map<String, Double> methodWithMapDoubleReturn(Handler<String> handler);
  Map<String, RefedInterface1> methodWithMapVertxGenReturn(Handler<String> handler);
  Map<String, TestDataObject> methodWithMapDataObjectReturn(Handler<String> handler);
  Map<String, TestEnum> methodWithMapEnumReturn(Handler<String> handler);
  Map<String, JsonObject> methodWithMapJsonObjectReturn(Handler<String> handler);
  Map<String, JsonObject> methodWithMapComplexJsonObjectReturn(Handler<String> handler);
  Map<String, JsonArray> methodWithMapJsonArrayReturn(Handler<String> handler);
  Map<String, JsonArray> methodWithMapComplexJsonArrayReturn(Handler<String> handler);
  Map<String, Object> methodWithMapObjectReturn(Handler<String> handler);

  List<String> methodWithListStringReturn();
  List<Long> methodWithListLongReturn();
  List<RefedInterface1> methodWithListVertxGenReturn();
  List<JsonObject> methodWithListJsonObjectReturn();
  List<JsonObject> methodWithListComplexJsonObjectReturn();
  List<JsonArray> methodWithListJsonArrayReturn();
  List<JsonArray> methodWithListComplexJsonArrayReturn();
  List<TestDataObject> methodWithListDataObjectReturn();
  List<TestEnum> methodWithListEnumReturn();
  List<Object> methodWithListObjectReturn();

  Set<String> methodWithSetStringReturn();
  Set<Long> methodWithSetLongReturn();
  Set<RefedInterface1> methodWithSetVertxGenReturn();
  Set<JsonObject> methodWithSetJsonObjectReturn();
  Set<JsonObject> methodWithSetComplexJsonObjectReturn();
  Set<JsonArray> methodWithSetJsonArrayReturn();
  Set<JsonArray> methodWithSetComplexJsonArrayReturn();
  Set<TestDataObject> methodWithSetDataObjectReturn();
  Set<TestEnum> methodWithSetEnumReturn();
  Set<Object> methodWithSetObjectReturn();
}
