package io.vertx.test.codegen.converter;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import io.vertx.core.spi.json.JsonDecoder;

/**
 * Converter and Codec for {@link io.vertx.test.codegen.converter.NoConverterDataObject}.
 * NOTE: This class has been automatically generated from the {@link io.vertx.test.codegen.converter.NoConverterDataObject} original class using Vert.x codegen.
 */
public class NoConverterDataObjectConverter implements JsonDecoder<NoConverterDataObject, JsonObject> {

  public static final NoConverterDataObjectConverter INSTANCE = new NoConverterDataObjectConverter();

  @Override public NoConverterDataObject decode(JsonObject value) { return (value != null) ? new NoConverterDataObject(value) : null; }

  @Override public Class<NoConverterDataObject> getTargetClass() { return NoConverterDataObject.class; }
}
