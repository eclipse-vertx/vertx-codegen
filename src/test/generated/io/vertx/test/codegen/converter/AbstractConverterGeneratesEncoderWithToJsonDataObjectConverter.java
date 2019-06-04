package io.vertx.test.codegen.converter;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import io.vertx.core.spi.json.JsonCodec;

/**
 * Converter and Codec for {@link io.vertx.test.codegen.converter.AbstractConverterGeneratesEncoderWithToJsonDataObject}.
 * NOTE: This class has been automatically generated from the {@link io.vertx.test.codegen.converter.AbstractConverterGeneratesEncoderWithToJsonDataObject} original class using Vert.x codegen.
 */
public class AbstractConverterGeneratesEncoderWithToJsonDataObjectConverter implements JsonCodec<AbstractConverterGeneratesEncoderWithToJsonDataObject, JsonObject> {

  public static final AbstractConverterGeneratesEncoderWithToJsonDataObjectConverter INSTANCE = new AbstractConverterGeneratesEncoderWithToJsonDataObjectConverter();

  @Override
  public JsonObject encode(AbstractConverterGeneratesEncoderWithToJsonDataObject value) {
    if (value == null) return null;
    JsonObject json = new JsonObject();
    toJson(value, json);
    return json;
  }

  @Override public AbstractConverterGeneratesEncoderWithToJsonDataObject decode(JsonObject value) { return (value != null) ? AbstractConverterGeneratesEncoderWithToJsonDataObject.decode(value) : null; }

  @Override public Class<AbstractConverterGeneratesEncoderWithToJsonDataObject> getTargetClass() { return AbstractConverterGeneratesEncoderWithToJsonDataObject.class; }

  public static void fromJson(Iterable<java.util.Map.Entry<String, Object>> json, AbstractConverterGeneratesEncoderWithToJsonDataObject obj) {
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

  public static void toJson(AbstractConverterGeneratesEncoderWithToJsonDataObject obj, JsonObject json) {
    toJson(obj, json.getMap());
  }

  public static void toJson(AbstractConverterGeneratesEncoderWithToJsonDataObject obj, java.util.Map<String, Object> json) {
    json.put("a", obj.getA());
  }
}
