package io.vertx.test.codegen.converter;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import io.vertx.core.spi.json.JsonCodec;

/**
 * Converter and Codec for {@link io.vertx.test.codegen.converter.KebabCaseDataObject}.
 * NOTE: This class has been automatically generated from the {@link io.vertx.test.codegen.converter.KebabCaseDataObject} original class using Vert.x codegen.
 */
public class KebabCaseDataObjectConverter implements JsonCodec<KebabCaseDataObject, JsonObject> {

  public static final KebabCaseDataObjectConverter INSTANCE = new KebabCaseDataObjectConverter();

  @Override
  public JsonObject encode(KebabCaseDataObject value) {
    if (value == null) return null;
    JsonObject json = new JsonObject();
    toJson(value, json);
    return json;
  }

  @Override public KebabCaseDataObject decode(JsonObject value) { return (value != null) ? new KebabCaseDataObject(value) : null; }

  @Override public Class<KebabCaseDataObject> getTargetClass() { return KebabCaseDataObject.class; }

  public static void fromJson(Iterable<java.util.Map.Entry<String, Object>> json, KebabCaseDataObject obj) {
    for (java.util.Map.Entry<String, Object> member : json) {
      switch (member.getKey()) {
        case "list-name":
          if (member.getValue() instanceof JsonArray) {
            java.util.ArrayList<java.lang.Object> list =  new java.util.ArrayList<>();
            ((Iterable<Object>)member.getValue()).forEach( item -> {
              if (item instanceof Object)
                list.add(item);
            });
            obj.setListName(list);
          }
          break;
        case "map-name":
          if (member.getValue() instanceof JsonObject) {
            java.util.Map<String, java.lang.Object> map = new java.util.LinkedHashMap<>();
            ((Iterable<java.util.Map.Entry<String, Object>>)member.getValue()).forEach(entry -> {
              if (entry.getValue() instanceof Object)
                map.put(entry.getKey(), entry.getValue());
            });
            obj.setMapName(map);
          }
          break;
        case "string-name":
          if (member.getValue() instanceof String) {
            obj.setStringName((String)member.getValue());
          }
          break;
      }
    }
  }

  public static void toJson(KebabCaseDataObject obj, JsonObject json) {
    toJson(obj, json.getMap());
  }

  public static void toJson(KebabCaseDataObject obj, java.util.Map<String, Object> json) {
    if (obj.getListName() != null) {
      JsonArray array = new JsonArray();
      obj.getListName().forEach(item -> array.add(item));
      json.put("list-name", array);
    }
    if (obj.getMapName() != null) {
      JsonObject map = new JsonObject();
      obj.getMapName().forEach((key, value) -> map.put(key, value));
      json.put("map-name", map);
    }
    if (obj.getStringName() != null) {
      json.put("string-name", obj.getStringName());
    }
  }
}
