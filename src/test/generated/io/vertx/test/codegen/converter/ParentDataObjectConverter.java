package io.vertx.test.codegen.converter;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import io.vertx.core.spi.json.JsonDeserializer;

/**
 * Converter and mapper for {@link io.vertx.test.codegen.converter.ParentDataObject}.
 * NOTE: This class has been automatically generated from the {@link io.vertx.test.codegen.converter.ParentDataObject} original class using Vert.x codegen.
 */
public class ParentDataObjectConverter implements JsonDeserializer<ParentDataObject, JsonObject> {

  public static final ParentDataObjectConverter INSTANCE = new ParentDataObjectConverter();

  @Override public ParentDataObject deserialize(JsonObject value) { return (value != null) ? new ParentDataObject(value) : null; }

  @Override public Class<ParentDataObject> getTargetClass() { return ParentDataObject.class; }

  public static void fromJson(Iterable<java.util.Map.Entry<String, Object>> json, ParentDataObject obj) {
    for (java.util.Map.Entry<String, Object> member : json) {
      switch (member.getKey()) {
        case "parentProperty":
          if (member.getValue() instanceof String) {
            obj.setParentProperty((String)member.getValue());
          }
          break;
      }
    }
  }

  public static void toJson(ParentDataObject obj, JsonObject json) {
    toJson(obj, json.getMap());
  }

  public static void toJson(ParentDataObject obj, java.util.Map<String, Object> json) {
    if (obj.getParentProperty() != null) {
      json.put("parentProperty", obj.getParentProperty());
    }
  }
}
