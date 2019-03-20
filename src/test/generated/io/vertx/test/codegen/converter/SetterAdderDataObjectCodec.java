package io.vertx.test.codegen.converter;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonDecoder;

public class SetterAdderDataObjectCodec implements JsonDecoder<SetterAdderDataObject, JsonObject> {

  private static class SetterAdderDataObjectCodecHolder {
    static final SetterAdderDataObjectCodec INSTANCE = new SetterAdderDataObjectCodec();
  }

  public static SetterAdderDataObjectCodec getInstance() { return SetterAdderDataObjectCodecHolder.INSTANCE; }

  @Override public SetterAdderDataObject decode(JsonObject value) { return new SetterAdderDataObject (value); }

}