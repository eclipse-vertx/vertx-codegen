package io.vertx.test.codegen.converter;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonDecoder;

public class ChildInheritingDataObjectCodec implements JsonDecoder<ChildInheritingDataObject, JsonObject> {

  private static class ChildInheritingDataObjectCodecHolder {
    static final ChildInheritingDataObjectCodec INSTANCE = new ChildInheritingDataObjectCodec();
  }

  public static ChildInheritingDataObjectCodec getInstance() { return ChildInheritingDataObjectCodecHolder.INSTANCE; }

  @Override public ChildInheritingDataObject decode(JsonObject value) { return new ChildInheritingDataObject(value); }

}