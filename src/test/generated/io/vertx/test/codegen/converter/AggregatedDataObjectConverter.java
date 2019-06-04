package io.vertx.test.codegen.converter;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import io.vertx.core.spi.json.JsonCodec;

/**
 * Converter and Codec for {@link io.vertx.test.codegen.converter.AggregatedDataObject}.
 * NOTE: This class has been automatically generated from the {@link io.vertx.test.codegen.converter.AggregatedDataObject} original class using Vert.x codegen.
 */
public class AggregatedDataObjectConverter implements JsonCodec<AggregatedDataObject, JsonObject> {

  public static final AggregatedDataObjectConverter INSTANCE = new AggregatedDataObjectConverter();

  @Override public JsonObject encode(AggregatedDataObject value) { return (value != null) ? value.toJson() : null; }

  @Override public AggregatedDataObject decode(JsonObject value) { return (value != null) ? new AggregatedDataObject(value) : null; }

  @Override public Class<AggregatedDataObject> getTargetClass() { return AggregatedDataObject.class; }
}
