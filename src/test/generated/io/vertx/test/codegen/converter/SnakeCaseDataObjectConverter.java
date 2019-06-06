package io.vertx.test.codegen.converter;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import io.vertx.core.spi.json.JsonCodec;

/**
 * Converter and Codec for {@link io.vertx.test.codegen.converter.SnakeCaseDataObject}.
 * NOTE: This class has been automatically generated from the {@link io.vertx.test.codegen.converter.SnakeCaseDataObject} original class using Vert.x codegen.
 */
public class SnakeCaseDataObjectConverter implements JsonCodec<SnakeCaseDataObject, JsonObject> {

  public static final SnakeCaseDataObjectConverter INSTANCE = new SnakeCaseDataObjectConverter();

  @Override
  public JsonObject encode(SnakeCaseDataObject value) {
    if (value == null) return null;
    JsonObject json = new JsonObject();
    toJson(value, json);
    return json;
  }

  @Override public SnakeCaseDataObject decode(JsonObject value) { return (value != null) ? new SnakeCaseDataObject(value) : null; }

  @Override public Class<SnakeCaseDataObject> getTargetClass() { return SnakeCaseDataObject.class; }

  public static void fromJson(Iterable<java.util.Map.Entry<String, Object>> json, SnakeCaseDataObject obj) {
    for (java.util.Map.Entry<String, Object> member : json) {
      switch (member.getKey()) {
        case "list_name":
          if (member.getValue() instanceof JsonArray) {
            java.util.ArrayList<java.lang.Object> list =  new java.util.ArrayList<>();
            ((Iterable<Object>)member.getValue()).forEach( item -> {
              if (item instanceof Object)
                list.add(item);
            });
            obj.setListName(list);
          }
          break;
        case "map_name":
          if (member.getValue() instanceof JsonObject) {
            java.util.Map<String, java.lang.Object> map = new java.util.LinkedHashMap<>();
            ((Iterable<java.util.Map.Entry<String, Object>>)member.getValue()).forEach(entry -> {
              if (entry.getValue() instanceof Object)
                map.put(entry.getKey(), entry.getValue());
            });
            obj.setMapName(map);
          }
          break;
        case "string_name":
          if (member.getValue() instanceof String) {
            obj.setStringName((String)member.getValue());
          }
          break;
      }
    }
  }

  public static void toJson(SnakeCaseDataObject obj, JsonObject json) {
    toJson(obj, json.getMap());
  }

  public static void toJson(SnakeCaseDataObject obj, java.util.Map<String, Object> json) {
    if (obj.getListName() != null) {
      JsonArray array = new JsonArray();
      obj.getListName().forEach(item -> array.add(item));
      json.put("list_name", array);
    }
    if (obj.getMapName() != null) {
      JsonObject map = new JsonObject();
      obj.getMapName().forEach((key, value) -> map.put(key, value));
      json.put("map_name", map);
    }
    if (obj.getStringName() != null) {
      json.put("string_name", obj.getStringName());
    }
  }
}
