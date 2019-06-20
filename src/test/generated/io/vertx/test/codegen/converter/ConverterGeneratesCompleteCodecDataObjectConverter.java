package io.vertx.test.codegen.converter;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import io.vertx.core.spi.json.JsonEncoder;

/**
 * Converter and Codec for {@link io.vertx.test.codegen.converter.ConverterGeneratesCompleteCodecDataObject}.
 * NOTE: This class has been automatically generated from the {@link io.vertx.test.codegen.converter.ConverterGeneratesCompleteCodecDataObject} original class using Vert.x codegen.
 */
public class ConverterGeneratesCompleteCodecDataObjectConverter implements JsonEncoder<ConverterGeneratesCompleteCodecDataObject, JsonObject> {

  public static final ConverterGeneratesCompleteCodecDataObjectConverter INSTANCE = new ConverterGeneratesCompleteCodecDataObjectConverter();

  @Override
  public JsonObject encode(ConverterGeneratesCompleteCodecDataObject value) {
    if (value == null) return null;
    JsonObject json = new JsonObject();
    toJson(value, json);
    return json;
  }

  @Override public Class<ConverterGeneratesCompleteCodecDataObject> getTargetClass() { return ConverterGeneratesCompleteCodecDataObject.class; }

  public static void fromJson(Iterable<java.util.Map.Entry<String, Object>> json, ConverterGeneratesCompleteCodecDataObject obj) {
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

  public static void toJson(ConverterGeneratesCompleteCodecDataObject obj, JsonObject json) {
    toJson(obj, json.getMap());
  }

  public static void toJson(ConverterGeneratesCompleteCodecDataObject obj, java.util.Map<String, Object> json) {
    json.put("a", obj.getA());
  }
}
