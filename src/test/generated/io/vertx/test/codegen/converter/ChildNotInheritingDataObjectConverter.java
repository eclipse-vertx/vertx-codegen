package io.vertx.test.codegen.converter;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import io.vertx.core.json.JsonDecoder;

/**
 * Converter and Codec for {@link io.vertx.test.codegen.converter.ChildNotInheritingDataObject}.
 * NOTE: This class has been automatically generated from the {@link io.vertx.test.codegen.converter.ChildNotInheritingDataObject} original class using Vert.x codegen.
 */
public class ChildNotInheritingDataObjectConverter implements JsonDecoder<ChildNotInheritingDataObject, JsonObject> {

  private static class ChildNotInheritingDataObjectConverterHolder {
    static final ChildNotInheritingDataObjectConverter INSTANCE = new ChildNotInheritingDataObjectConverter();
  }

  public static ChildNotInheritingDataObjectConverter getInstance() { return ChildNotInheritingDataObjectConverterHolder.INSTANCE; }

  @Override public ChildNotInheritingDataObject decode(JsonObject value) { return new ChildNotInheritingDataObject(value); }


  public static void fromJson(Iterable<java.util.Map.Entry<String, Object>> json, ChildNotInheritingDataObject obj) {
    for (java.util.Map.Entry<String, Object> member : json) {
      switch (member.getKey()) {
        case "childProperty":
          if (member.getValue() instanceof String) {
            obj.setChildProperty((String)member.getValue());
          }
          break;
      }
    }
  }

  public static void toJson(ChildNotInheritingDataObject obj, JsonObject json) {
    toJson(obj, json.getMap());
  }

  public static void toJson(ChildNotInheritingDataObject obj, java.util.Map<String, Object> json) {
    if (obj.getChildProperty() != null) {
      json.put("childProperty", obj.getChildProperty());
    }
  }
}
