package io.vertx.test.codegen.converter;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.impl.JsonUtil;
import java.time.Instant;
import java.time.format.DateTimeFormatter;

/**
 * Converter and mapper for {@link io.vertx.test.codegen.converter.SnakeFormattedDataObject}.
 * NOTE: This class has been automatically generated from the {@link io.vertx.test.codegen.converter.SnakeFormattedDataObject} original class using Vert.x codegen.
 */
public class SnakeFormattedDataObjectConverter {


  public static void fromJson(Iterable<java.util.Map.Entry<String, Object>> json, SnakeFormattedDataObject obj) {
    for (java.util.Map.Entry<String, Object> member : json) {
      switch (member.getKey()) {
        case "foo":
          if (member.getValue() instanceof String) {
            obj.setFoo((String)member.getValue());
          }
          break;
        case "foo_bar":
          if (member.getValue() instanceof String) {
            obj.setFooBar((String)member.getValue());
          }
          break;
        case "foo_bar_juu":
          if (member.getValue() instanceof String) {
            obj.setFooBarJuu((String)member.getValue());
          }
          break;
      }
    }
  }

  public static void toJson(SnakeFormattedDataObject obj, JsonObject json) {
    toJson(obj, json.getMap());
  }

  public static void toJson(SnakeFormattedDataObject obj, java.util.Map<String, Object> json) {
    if (obj.getFoo() != null) {
      json.put("foo", obj.getFoo());
    }
    if (obj.getFooBar() != null) {
      json.put("foo_bar", obj.getFooBar());
    }
    if (obj.getFooBarJuu() != null) {
      json.put("foo_bar_juu", obj.getFooBarJuu());
    }
  }
}
