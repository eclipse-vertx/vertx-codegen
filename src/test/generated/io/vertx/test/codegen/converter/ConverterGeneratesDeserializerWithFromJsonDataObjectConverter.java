package io.vertx.test.codegen.converter;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;
import java.time.Instant;
import java.time.format.DateTimeFormatter;

/**
 * Converter and mapper for {@link io.vertx.test.codegen.converter.ConverterGeneratesDeserializerWithFromJsonDataObject}.
 * NOTE: This class has been automatically generated from the {@link io.vertx.test.codegen.converter.ConverterGeneratesDeserializerWithFromJsonDataObject} original class using Vert.x codegen.
 */
public class ConverterGeneratesDeserializerWithFromJsonDataObjectConverter {


  public static void fromJson(Iterable<java.util.Map.Entry<String, Object>> json, ConverterGeneratesDeserializerWithFromJsonDataObject obj) {
    for (java.util.Map.Entry<String, Object> member : json) {
      switch (member.getKey()) {
        case "a":
          if (member.getValue() instanceof Number) {
            obj.setA(((Number)member.getValue()).intValue());
          }
          break;
      }
    }
  }

  public static void toJson(ConverterGeneratesDeserializerWithFromJsonDataObject obj, JsonObject json) {
    toJson(obj, json.getMap());
  }

  public static void toJson(ConverterGeneratesDeserializerWithFromJsonDataObject obj, java.util.Map<String, Object> json) {
    json.put("a", obj.getA());
  }
}
