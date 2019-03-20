package io.vertx.test.codegen.converter;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonDecoder;

public class NoConverterDataObjectCodec implements JsonDecoder<NoConverterDataObject, JsonObject> {

  private static class NoConverterDataObjectCodecHolder {
    static final NoConverterDataObjectCodec INSTANCE = new NoConverterDataObjectCodec();
  }

  public static NoConverterDataObjectCodec getInstance() { return NoConverterDataObjectCodecHolder.INSTANCE; }

  @Override public NoConverterDataObject decode(JsonObject value) { return new NoConverterDataObject(value); }

}