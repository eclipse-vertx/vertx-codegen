package io.vertx.test.codegen.converter;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.impl.JsonUtil;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

/**
 * Converter and mapper for {@link io.vertx.test.codegen.converter.ParentDataObject}.
 * NOTE: This class has been automatically generated from the {@link io.vertx.test.codegen.converter.ParentDataObject} original class using Vert.x codegen.
 */
public class ParentDataObjectConverter {


  private static final Base64.Decoder BASE64_DECODER = JsonUtil.BASE64_DECODER;
  private static final Base64.Encoder BASE64_ENCODER = JsonUtil.BASE64_ENCODER;

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
