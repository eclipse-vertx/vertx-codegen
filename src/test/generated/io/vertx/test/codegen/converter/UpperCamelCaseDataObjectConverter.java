package io.vertx.test.codegen.converter;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;
import java.time.Instant;
import java.time.format.DateTimeFormatter;

/**
 * Converter for {@link io.vertx.test.codegen.converter.UpperCamelCaseDataObject}.
 * NOTE: This class has been automatically generated from the {@link io.vertx.test.codegen.converter.UpperCamelCaseDataObject} original class using Vert.x codegen.
 */
public class UpperCamelCaseDataObjectConverter {

  public static void fromJson(Iterable<java.util.Map.Entry<String, Object>> json, UpperCamelCaseDataObject obj) {
    for (java.util.Map.Entry<String, Object> member : json) {
      switch (member.getKey()) {
        case "ListName":
          if (member.getValue() instanceof JsonArray) {
            java.util.ArrayList<java.lang.Object> list =  new java.util.ArrayList<>();
            ((Iterable<Object>)member.getValue()).forEach( item -> {
              if (item instanceof Object)
                list.add(item);
            });
            obj.setListName(list);
          }
          break;
        case "MapName":
          if (member.getValue() instanceof JsonObject) {
            java.util.Map<String, java.lang.Object> map = new java.util.LinkedHashMap<>();
            ((Iterable<java.util.Map.Entry<String, Object>>)member.getValue()).forEach(entry -> {
              if (entry.getValue() instanceof Object)
                map.put(entry.getKey(), entry.getValue());
            });
            obj.setMapName(map);
          }
          break;
        case "StringName":
          if (member.getValue() instanceof String) {
            obj.setStringName((String)member.getValue());
          }
          break;
      }
    }
  }

  public static void toJson(UpperCamelCaseDataObject obj, JsonObject json) {
    toJson(obj, json.getMap());
  }

  public static void toJson(UpperCamelCaseDataObject obj, java.util.Map<String, Object> json) {
    if (obj.getListName() != null) {
      JsonArray array = new JsonArray();
      obj.getListName().forEach(item -> array.add(item));
      json.put("ListName", array);
    }
    if (obj.getMapName() != null) {
      JsonObject map = new JsonObject();
      obj.getMapName().forEach((key, value) -> map.put(key, value));
      json.put("MapName", map);
    }
    if (obj.getStringName() != null) {
      json.put("StringName", obj.getStringName());
    }
  }
}
