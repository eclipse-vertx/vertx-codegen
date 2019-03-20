package io.vertx.test.codegen.converter;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonCodec;

public class AggregatedDataObjectCodec implements JsonCodec<AggregatedDataObject, JsonObject> {

  private static class AggregatedDataObjectCodecHolder {
    static final AggregatedDataObjectCodec INSTANCE = new AggregatedDataObjectCodec();
  }

  public static AggregatedDataObjectCodec getInstance() { return AggregatedDataObjectCodecHolder.INSTANCE; }

  @Override public JsonObject encode(AggregatedDataObject value) { return value.toJson(); }

  @Override public AggregatedDataObject decode(JsonObject value) { return new AggregatedDataObject (value); }

}