package io.vertx.test.codegen.converter;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.impl.JsonUtil;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

/**
 * Converter and mapper for {@link io.vertx.test.codegen.converter.ConverterGeneratesDeserializerWithFromJsonDataObject}.
 * NOTE: This class has been automatically generated from the {@link io.vertx.test.codegen.converter.ConverterGeneratesDeserializerWithFromJsonDataObject} original class using Vert.x codegen.
 */
public class ConverterGeneratesDeserializerWithFromJsonDataObjectConverter {


  private static final Base64.Decoder BASE64_DECODER;
  private static final Base64.Encoder BASE64_ENCODER;

  static {
    BASE64_DECODER = JsonUtil.BASE64_DECODER;
    BASE64_ENCODER = JsonUtil.BASE64_ENCODER;
  }

  public static void fromJson(Iterable<java.util.Map.Entry<String, Object>> json, ConverterGeneratesDeserializerWithFromJsonDataObject obj) {
    for (java.util.Map.Entry<String, Object> member : json) {
      switch (member.getKey()) {
        case "a":
          if (member.getValue() instanceof Number) {
            obj.setA(((Number)member.getValue()).intValue());
          }
          break;
      }
    }
  }

  public static void toJson(ConverterGeneratesDeserializerWithFromJsonDataObject obj, JsonObject json) {
    toJson(obj, json.getMap());
  }

  public static void toJson(ConverterGeneratesDeserializerWithFromJsonDataObject obj, java.util.Map<String, Object> json) {
    json.put("a", obj.getA());
  }
}
