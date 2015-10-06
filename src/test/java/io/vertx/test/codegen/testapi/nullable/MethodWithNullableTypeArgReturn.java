package io.vertx.test.codegen.testapi.nullable;

import io.vertx.codegen.annotations.Nullable;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.codegen.testmodel.TestDataObject;
import io.vertx.codegen.testmodel.TestEnum;
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
public interface MethodWithNullableTypeArgReturn {

  List<@Nullable Byte> listNullableByteReturn();
  List<@Nullable Short> listNullableShortReturn();
  List<@Nullable Integer> listNullableIntegerReturn();
  List<@Nullable Long> listNullableLongReturn();
  List<@Nullable Float> listNullableFloatReturn();
  List<@Nullable Double> listNullableDoubleReturn();
  List<@Nullable Character> listNullableCharacterReturn();
  List<@Nullable String> listNullableStringReturn();
  List<@Nullable JsonObject> listNullableJsonObjectReturn();
  List<@Nullable JsonArray> listNullableJsonArrayReturn();
  List<@Nullable TestEnum> listNullableEnumReturn();
  List<@Nullable TestDataObject> listNullableDataObjectReturn();
  List<@Nullable VertxGenClass1> listNullableApiReturn();

  Set<@Nullable Byte> setNullableByteReturn();
  Set<@Nullable Short> setNullableShortReturn();
  Set<@Nullable Integer> setNullableIntegerReturn();
  Set<@Nullable Long> setNullableLongReturn();
  Set<@Nullable Float> setNullableFloatReturn();
  Set<@Nullable Double> setNullableDoubleReturn();
  Set<@Nullable Character> setNullableCharacterReturn();
  Set<@Nullable String> setNullableStringReturn();
  Set<@Nullable JsonObject> setNullableJsonObjectReturn();
  Set<@Nullable JsonArray> setNullableJsonArrayReturn();
  Set<@Nullable TestEnum> setNullableEnumReturn();
  Set<@Nullable TestDataObject> setNullableDataObjectReturn();
  Set<@Nullable VertxGenClass1> setNullableApiReturn();

  Map<String, @Nullable Byte> mapNullableByteReturn();
  Map<String, @Nullable Short> mapNullableShortReturn();
  Map<String, @Nullable Integer> mapNullableIntegerReturn();
  Map<String, @Nullable Long> mapNullableLongReturn();
  Map<String, @Nullable Float> mapNullableFloatReturn();
  Map<String, @Nullable Double> mapNullableDoubleReturn();
  Map<String, @Nullable Character> mapNullableCharacterReturn();
  Map<String, @Nullable String> mapNullableStringReturn();
  Map<String, @Nullable JsonObject> mapNullableJsonObjectReturn();
  Map<String, @Nullable JsonArray> mapNullableJsonArrayReturn();
  Map<String, @Nullable TestEnum> mapNullableEnumReturn();
  Map<String, @Nullable TestDataObject> mapNullableDataObjectReturn();
  Map<String, @Nullable VertxGenClass1> mapNullableApiReturn();
}
