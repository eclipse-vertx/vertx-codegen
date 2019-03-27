package io.vertx.test.codegen.converter;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import io.vertx.core.json.JsonDecoder;

/**
 * Converter and Codec for {@link io.vertx.test.codegen.converter.ChildInheritingDataObject}.
 * NOTE: This class has been automatically generated from the {@link io.vertx.test.codegen.converter.ChildInheritingDataObject} original class using Vert.x codegen.
 */
public class ChildInheritingDataObjectConverter implements JsonDecoder<ChildInheritingDataObject, JsonObject> {

  public static ChildInheritingDataObjectConverter INSTANCE = new ChildInheritingDataObjectConverter();


  @Override public ChildInheritingDataObject decode(JsonObject value) { return (value != null) ? new ChildInheritingDataObject(value) : null; }


  public static void fromJson(Iterable<java.util.Map.Entry<String, Object>> json, ChildInheritingDataObject obj) {
    for (java.util.Map.Entry<String, Object> member : json) {
      switch (member.getKey()) {
        case "childProperty":
          if (member.getValue() instanceof String) {
            obj.setChildProperty((String)member.getValue());
          }
          break;
        case "parentProperty":
          if (member.getValue() instanceof String) {
            obj.setParentProperty((String)member.getValue());
          }
          break;
      }
    }
  }

  public static void toJson(ChildInheritingDataObject obj, JsonObject json) {
    toJson(obj, json.getMap());
  }

  public static void toJson(ChildInheritingDataObject obj, java.util.Map<String, Object> json) {
    if (obj.getChildProperty() != null) {
      json.put("childProperty", obj.getChildProperty());
    }
    if (obj.getParentProperty() != null) {
      json.put("parentProperty", obj.getParentProperty());
    }
  }
}
