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
 * Converter and mapper for {@link io.vertx.test.codegen.converter.SetterAdderDataObject}.
 * NOTE: This class has been automatically generated from the {@link io.vertx.test.codegen.converter.SetterAdderDataObject} original class using Vert.x codegen.
 */
public class SetterAdderDataObjectConverter {


  private static final Base64.Decoder BASE64_DECODER = JsonUtil.BASE64_DECODER;
  private static final Base64.Encoder BASE64_ENCODER = JsonUtil.BASE64_ENCODER;

  public static void fromJson(Iterable<java.util.Map.Entry<String, Object>> json, SetterAdderDataObject obj) {
    for (java.util.Map.Entry<String, Object> member : json) {
      switch (member.getKey()) {
        case "values":
          if (member.getValue() instanceof JsonArray) {
            java.util.ArrayList<java.lang.String> list =  new java.util.ArrayList<>();
            ((Iterable<Object>)member.getValue()).forEach( item -> {
              if (item instanceof String)
                list.add((String)item);
            });
            obj.setValues(list);
          }
          break;
      }
    }
  }

  public static void toJson(SetterAdderDataObject obj, JsonObject json) {
    toJson(obj, json.getMap());
  }

  public static void toJson(SetterAdderDataObject obj, java.util.Map<String, Object> json) {
    if (obj.getValues() != null) {
      JsonArray array = new JsonArray();
      obj.getValues().forEach(item -> array.add(item));
      json.put("values", array);
    }
  }
}
