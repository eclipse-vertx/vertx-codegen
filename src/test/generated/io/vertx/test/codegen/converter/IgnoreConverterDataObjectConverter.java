package io.vertx.test.codegen.converter;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;
import java.time.Instant;
import java.time.format.DateTimeFormatter;

/**
 * Converter for {@link io.vertx.test.codegen.converter.IgnoreConverterDataObject}.
 * NOTE: This class has been automatically generated from the {@link io.vertx.test.codegen.converter.IgnoreConverterDataObject} original class using Vert.x codegen.
 */
public class IgnoreConverterDataObjectConverter {

  public static void fromJson(Iterable<java.util.Map.Entry<String, Object>> json, IgnoreConverterDataObject obj) {
    for (java.util.Map.Entry<String, Object> member : json) {
      switch (member.getKey()) {
        case "regularProperty":
          if (member.getValue() instanceof String) {
            obj.setRegularProperty((String)member.getValue());
          }
          break;
      }
    }
  }

  public static void toJson(IgnoreConverterDataObject obj, JsonObject json) {
    toJson(obj, json.getMap());
  }

  public static void toJson(IgnoreConverterDataObject obj, java.util.Map<String, Object> json) {
    if (obj.getRegularProperty() != null) {
      json.put("regularProperty", obj.getRegularProperty());
    }
  }
}
