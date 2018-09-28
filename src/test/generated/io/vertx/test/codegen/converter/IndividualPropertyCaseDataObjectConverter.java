package io.vertx.test.codegen.converter;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;
import java.time.Instant;
import java.time.format.DateTimeFormatter;

/**
 * Converter for {@link io.vertx.test.codegen.converter.IndividualPropertyCaseDataObject}.
 * NOTE: This class has been automatically generated from the {@link io.vertx.test.codegen.converter.IndividualPropertyCaseDataObject} original class using Vert.x codegen.
 */
public class IndividualPropertyCaseDataObjectConverter {

  public static void fromJson(Iterable<java.util.Map.Entry<String, Object>> json, IndividualPropertyCaseDataObject obj) {
    for (java.util.Map.Entry<String, Object> member : json) {
      switch (member.getKey()) {
        case "kebab-case":
          if (member.getValue() instanceof String) {
            obj.setKebabCase((String)member.getValue());
          }
          break;
        case "lowerCamelCase":
          if (member.getValue() instanceof String) {
            obj.setLowerCamelCase((String)member.getValue());
          }
          break;
        case "snake_case":
          if (member.getValue() instanceof String) {
            obj.setSnakeCase((String)member.getValue());
          }
          break;
        case "UpperCamelCase":
          if (member.getValue() instanceof String) {
            obj.setUpperCamelCase((String)member.getValue());
          }
          break;
      }
    }
  }

  public static void toJson(IndividualPropertyCaseDataObject obj, JsonObject json) {
    toJson(obj, json.getMap());
  }

  public static void toJson(IndividualPropertyCaseDataObject obj, java.util.Map<String, Object> json) {
    if (obj.getKebabCase() != null) {
      json.put("kebab-case", obj.getKebabCase());
    }
    if (obj.getLowerCamelCase() != null) {
      json.put("lowerCamelCase", obj.getLowerCamelCase());
    }
    if (obj.getSnakeCase() != null) {
      json.put("snake_case", obj.getSnakeCase());
    }
    if (obj.getUpperCamelCase() != null) {
      json.put("UpperCamelCase", obj.getUpperCamelCase());
    }
  }
}
