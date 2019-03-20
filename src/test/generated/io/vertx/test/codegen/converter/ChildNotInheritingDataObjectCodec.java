package io.vertx.test.codegen.converter;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonDecoder;

public class ChildNotInheritingDataObjectCodec implements JsonDecoder<ChildNotInheritingDataObject, JsonObject> {

  private static class ChildNotInheritingDataObjectCodecHolder {
    static final ChildNotInheritingDataObjectCodec INSTANCE = new ChildNotInheritingDataObjectCodec();
  }

  public static ChildNotInheritingDataObjectCodec getInstance() { return ChildNotInheritingDataObjectCodecHolder.INSTANCE; }

  @Override public ChildNotInheritingDataObject decode(JsonObject value) { return new ChildNotInheritingDataObject(value); }

}