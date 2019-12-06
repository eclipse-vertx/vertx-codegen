package io.vertx.test.codegen.testjsoncodecs.enclosedcodec;

import io.vertx.core.spi.json.JsonCodec;
import io.vertx.core.json.JsonObject;

public class MyPojo {

  public static class MyPojoCodec implements JsonCodec<MyPojo, JsonObject> {

    public static final MyPojoCodec INSTANCE = new MyPojoCodec();

    @Override
    public MyPojo decode(JsonObject value) throws IllegalArgumentException {
      return null;
    }

    @Override
    public JsonObject encode(MyPojo value) throws IllegalArgumentException {
      return null;
    }

    @Override
    public Class<MyPojo> getTargetClass() {
      return MyPojo.class;
    }
  }

}
