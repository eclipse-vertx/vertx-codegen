package io.vertx.test.codegen.converter;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;
import java.time.Instant;
import java.time.format.DateTimeFormatter;

/**
 * Converter for {@link io.vertx.test.codegen.converter.IndividualPropertyValueDataObject}.
 * NOTE: This class has been automatically generated from the {@link io.vertx.test.codegen.converter.IndividualPropertyValueDataObject} original class using Vert.x codegen.
 */
public class IndividualPropertyValueDataObjectConverter {

  public static void fromJson(Iterable<java.util.Map.Entry<String, Object>> json, IndividualPropertyValueDataObject obj) {
    for (java.util.Map.Entry<String, Object> member : json) {
      switch (member.getKey()) {
        case "map-of-bar":
          if (member.getValue() instanceof JsonObject) {
            java.util.Map<String, java.lang.Object> map = new java.util.LinkedHashMap<>();
            ((Iterable<java.util.Map.Entry<String, Object>>)member.getValue()).forEach(entry -> {
              if (entry.getValue() instanceof Object)
                map.put(entry.getKey(), entry.getValue());
            });
            obj.setBarMap(map);
          }
          break;
        case "string_of_baz":
          if (member.getValue() instanceof String) {
            obj.setBazString((String)member.getValue());
          }
          break;
        case "listOfFoo":
          if (member.getValue() instanceof JsonArray) {
            java.util.ArrayList<java.lang.Object> list =  new java.util.ArrayList<>();
            ((Iterable<Object>)member.getValue()).forEach( item -> {
              if (item instanceof Object)
                list.add(item);
            });
            obj.setFooList(list);
          }
          break;
      }
    }
  }

  public static void toJson(IndividualPropertyValueDataObject obj, JsonObject json) {
    toJson(obj, json.getMap());
  }

  public static void toJson(IndividualPropertyValueDataObject obj, java.util.Map<String, Object> json) {
    if (obj.getBarMap() != null) {
      JsonObject map = new JsonObject();
      obj.getBarMap().forEach((key, value) -> map.put(key, value));
      json.put("map-of-bar", map);
    }
    if (obj.getBazString() != null) {
      json.put("string_of_baz", obj.getBazString());
    }
    if (obj.getFooList() != null) {
      JsonArray array = new JsonArray();
      obj.getFooList().forEach(item -> array.add(item));
      json.put("listOfFoo", array);
    }
  }
}
