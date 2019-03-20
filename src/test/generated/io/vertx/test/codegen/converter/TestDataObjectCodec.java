package io.vertx.test.codegen.converter;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonDecoder;

public class TestDataObjectCodec implements JsonDecoder<TestDataObject, JsonObject> {

  private static class TestDataObjectCodecHolder {
    static final TestDataObjectCodec INSTANCE = new TestDataObjectCodec();
  }

  public static TestDataObjectCodec getInstance() { return TestDataObjectCodecHolder.INSTANCE; }

  @Override public TestDataObject decode(JsonObject value) { return new TestDataObject(value); }

}