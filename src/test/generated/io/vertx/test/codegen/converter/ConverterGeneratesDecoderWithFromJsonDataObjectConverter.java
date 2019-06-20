package io.vertx.test.codegen.converter;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import io.vertx.core.spi.json.JsonEncoder;

/**
 * Converter and Codec for {@link io.vertx.test.codegen.converter.ConverterGeneratesDecoderWithFromJsonDataObject}.
 * NOTE: This class has been automatically generated from the {@link io.vertx.test.codegen.converter.ConverterGeneratesDecoderWithFromJsonDataObject} original class using Vert.x codegen.
 */
public class ConverterGeneratesDecoderWithFromJsonDataObjectConverter implements JsonEncoder<ConverterGeneratesDecoderWithFromJsonDataObject, JsonObject> {

  public static final ConverterGeneratesDecoderWithFromJsonDataObjectConverter INSTANCE = new ConverterGeneratesDecoderWithFromJsonDataObjectConverter();

  @Override public JsonObject encode(ConverterGeneratesDecoderWithFromJsonDataObject value) { return (value != null) ? value.toJson() : null; }

  @Override public Class<ConverterGeneratesDecoderWithFromJsonDataObject> getTargetClass() { return ConverterGeneratesDecoderWithFromJsonDataObject.class; }

  public static void fromJson(Iterable<java.util.Map.Entry<String, Object>> json, ConverterGeneratesDecoderWithFromJsonDataObject obj) {
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

  public static void toJson(ConverterGeneratesDecoderWithFromJsonDataObject obj, JsonObject json) {
    toJson(obj, json.getMap());
  }

  public static void toJson(ConverterGeneratesDecoderWithFromJsonDataObject obj, java.util.Map<String, Object> json) {
    json.put("a", obj.getA());
  }
}
