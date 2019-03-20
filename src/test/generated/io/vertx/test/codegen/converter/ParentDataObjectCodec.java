package io.vertx.test.codegen.converter;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonDecoder;

public class ParentDataObjectCodec implements JsonDecoder<ParentDataObject, JsonObject> {

  private static class ParentDataObjectCodecHolder {
    static final ParentDataObjectCodec INSTANCE = new ParentDataObjectCodec();
  }

  public static ParentDataObjectCodec getInstance() { return ParentDataObjectCodecHolder.INSTANCE; }

  @Override public ParentDataObject decode(JsonObject value) { return new ParentDataObject (value); }

}