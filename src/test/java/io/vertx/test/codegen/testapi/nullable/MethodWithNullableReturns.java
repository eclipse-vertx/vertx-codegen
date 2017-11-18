package io.vertx.test.codegen.testapi.nullable;

import io.vertx.codegen.annotations.Nullable;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.codegen.testmodel.TestDataObject;
import io.vertx.codegen.testmodel.TestEnum;
import io.vertx.codegen.testmodel.TestGenEnum;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.test.codegen.testapi.VertxGenClass1;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@VertxGen
public interface MethodWithNullableReturns {

  @Nullable Byte nullableByteReturn();
  @Nullable Short nullableShortReturn();
  @Nullable Integer nullableIntegerReturn();
  @Nullable Long nullableLongReturn();
  @Nullable Float nullableFloatReturn();
  @Nullable Double nullableDoubleReturn();
  @Nullable Character nullableCharacterReturn();
  @Nullable String nullableStringReturn();
  @Nullable JsonObject nullableJsonObjectReturn();
  @Nullable JsonArray nullableJsonArrayReturn();
  <T> @Nullable T nullableTypeVariableReturn();
  @Nullable TestEnum nullableEnumReturn();
  @Nullable TestDataObject nullableDataObjectReturn();
  @Nullable VertxGenClass1 nullableApiReturn();

  @Nullable List<Byte> nullableListByteReturn();
  @Nullable List<Short> nullableListShortReturn();
  @Nullable List<Integer> nullableListIntegerReturn();
  @Nullable List<Long> nullableListLongReturn();
  @Nullable List<Float> nullableListFloatReturn();
  @Nullable List<Double> nullableListDoubleReturn();
  @Nullable List<Character> nullableListCharacterReturn();
  @Nullable List<String> nullableListStringReturn();
  @Nullable List<JsonObject> nullableListJsonObjectReturn();
  @Nullable List<JsonArray> nullableListJsonArrayReturn();
  @Nullable List<TestEnum> nullableListEnumReturn();
  @Nullable List<TestDataObject> nullableListDataObjectReturn();
  @Nullable List<VertxGenClass1> nullableListApiReturn();

  @Nullable Set<Byte> nullableSetByteReturn();
  @Nullable Set<Short> nullableSetShortReturn();
  @Nullable Set<Integer> nullableSetIntegerReturn();
  @Nullable Set<Long> nullableSetLongReturn();
  @Nullable Set<Float> nullableSetFloatReturn();
  @Nullable Set<Double> nullableSetDoubleReturn();
  @Nullable Set<Character> nullableSetCharacterReturn();
  @Nullable Set<String> nullableSetStringReturn();
  @Nullable Set<JsonObject> nullableSetJsonObjectReturn();
  @Nullable Set<JsonArray> nullableSetJsonArrayReturn();
  @Nullable Set<TestEnum> nullableSetEnumReturn();
  @Nullable Set<TestDataObject> nullableSetDataObjectReturn();
  @Nullable Set<VertxGenClass1> nullableSetApiReturn();

  @Nullable Map<String, Byte> nullableMapByteReturn();
  @Nullable Map<String, Short> nullableMapShortReturn();
  @Nullable Map<String, Integer> nullableMapIntegerReturn();
  @Nullable Map<String, Long> nullableMapLongReturn();
  @Nullable Map<String, Float> nullableMapFloatReturn();
  @Nullable Map<String, Double> nullableMapDoubleReturn();
  @Nullable Map<String, Character> nullableMapCharacterReturn();
  @Nullable Map<String, String> nullableMapStringReturn();
  @Nullable Map<String, JsonObject> nullableMapJsonObjectReturn();
  @Nullable Map<String, JsonArray> nullableMapJsonArrayReturn();
}
