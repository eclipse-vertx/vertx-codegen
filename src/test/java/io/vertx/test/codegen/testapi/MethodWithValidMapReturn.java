package io.vertx.test.codegen.testapi;

import io.vertx.codegen.annotations.VertxGen;
import io.vertx.codegen.testmodel.TestEnum;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

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

  Map<String, VertxGenClass1> vertxGenMap();
  Map<String, JsonArray> jsonArrayMap();
  Map<String, JsonObject> jsonObjectMap();
  Map<String, Object> objectMap();

}
