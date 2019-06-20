package io.vertx.test.codegen.converter;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import io.vertx.core.spi.json.JsonCodec;

/**
 * Converter and Codec for {@link io.vertx.test.codegen.converter.ConverterGeneratesEncoderWithToJsonDataObject}.
 * NOTE: This class has been automatically generated from the {@link io.vertx.test.codegen.converter.ConverterGeneratesEncoderWithToJsonDataObject} original class using Vert.x codegen.
 */
public class ConverterGeneratesEncoderWithToJsonDataObjectConverter implements JsonCodec<ConverterGeneratesEncoderWithToJsonDataObject, JsonObject> {

  public static final ConverterGeneratesEncoderWithToJsonDataObjectConverter INSTANCE = new ConverterGeneratesEncoderWithToJsonDataObjectConverter();

  @Override
  public JsonObject encode(ConverterGeneratesEncoderWithToJsonDataObject value) {
    if (value == null) return null;
    JsonObject json = new JsonObject();
    toJson(value, json);
    return json;
  }

  @Override public ConverterGeneratesEncoderWithToJsonDataObject decode(JsonObject value) { return (value != null) ? new ConverterGeneratesEncoderWithToJsonDataObject(value) : null; }

  @Override public Class<ConverterGeneratesEncoderWithToJsonDataObject> getTargetClass() { return ConverterGeneratesEncoderWithToJsonDataObject.class; }

  public static void fromJson(Iterable<java.util.Map.Entry<String, Object>> json, ConverterGeneratesEncoderWithToJsonDataObject obj) {
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

  public static void toJson(ConverterGeneratesEncoderWithToJsonDataObject obj, JsonObject json) {
    toJson(obj, json.getMap());
  }

  public static void toJson(ConverterGeneratesEncoderWithToJsonDataObject obj, java.util.Map<String, Object> json) {
    json.put("a", obj.getA());
  }
}
