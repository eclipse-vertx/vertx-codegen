package io.vertx.test.codegen.converter;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.impl.JsonUtil;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

/**
 * Converter and mapper for {@link io.vertx.test.codegen.converter.DataObjectPropertyFormattedDataObject}.
 * NOTE: This class has been automatically generated from the {@link io.vertx.test.codegen.converter.DataObjectPropertyFormattedDataObject} original class using Vert.x codegen.
 */
public class DataObjectPropertyFormattedDataObjectConverter {


  private static final Base64.Decoder BASE64_DECODER = JsonUtil.BASE64_DECODER;
  private static final Base64.Encoder BASE64_ENCODER = JsonUtil.BASE64_ENCODER;

  public static void fromJson(Iterable<java.util.Map.Entry<String, Object>> json, DataObjectPropertyFormattedDataObject obj) {
    for (java.util.Map.Entry<String, Object> member : json) {
      switch (member.getKey()) {
        case "id":
          if (member.getValue() instanceof String) {
            obj.setId((String)member.getValue());
          }
          break;
        case ":ref":
          if (member.getValue() instanceof String) {
            obj.setRef((String)member.getValue());
          }
          break;
      }
    }
  }

  public static void toJson(DataObjectPropertyFormattedDataObject obj, JsonObject json) {
    toJson(obj, json.getMap());
  }

  public static void toJson(DataObjectPropertyFormattedDataObject obj, java.util.Map<String, Object> json) {
    if (obj.getId() != null) {
      json.put("id", obj.getId());
    }
    if (obj.getRef() != null) {
      json.put(":ref", obj.getRef());
    }
  }
}
