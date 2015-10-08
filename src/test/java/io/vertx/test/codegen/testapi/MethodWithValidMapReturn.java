package io.vertx.test.codegen.testapi;

import io.vertx.codegen.annotations.VertxGen;
import io.vertx.codegen.testmodel.TestDataObject;
import io.vertx.codegen.testmodel.TestEnum;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:nscavell@redhat.com">Nick Scavelli</a>
 */
@VertxGen
public interface MethodWithValidMapReturn {

  Map<String, Byte> byteMap();
  Map<String, Short> shortMap();
  Map<String, Integer> intMap();
  Map<String, Long> longMap();
  Map<String, Float> floatMap();
  Map<String, Double> doubleMap();
  Map<String, Boolean> booleanMap();
  Map<String, Character> charMap();
  Map<String, String> stringMap();

  Map<String, JsonArray> jsonArrayMap();
  Map<String, JsonObject> jsonObjectMap();

  Map<String, VertxGenClass1> vertxGen1Map();
  Map<String, VertxGenClass2> vertxGen2Map();
  Map<String, TestDataObject> dataObjectMap();

  Map<String, TestEnum> enumMap();
}
