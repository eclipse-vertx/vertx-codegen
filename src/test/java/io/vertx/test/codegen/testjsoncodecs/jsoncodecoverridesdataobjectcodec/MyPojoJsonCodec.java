package io.vertx.test.codegen.testjsoncodecs.jsoncodecoverridesdataobjectcodec;

import io.vertx.core.json.JsonCodec;
import io.vertx.core.json.JsonObject;

public class MyPojoJsonCodec implements JsonCodec<MyPojo, JsonObject> {

  public static final MyPojoJsonCodec INSTANCE = new MyPojoJsonCodec();

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
