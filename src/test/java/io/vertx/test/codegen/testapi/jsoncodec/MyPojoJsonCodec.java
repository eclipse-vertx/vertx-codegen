package io.vertx.test.codegen.testapi.jsoncodec;

import io.vertx.core.spi.json.JsonCodec;

public class MyPojoJsonCodec implements JsonCodec<MyPojo, Number> {

  public static final MyPojoJsonCodec INSTANCE = new MyPojoJsonCodec();

  @Override
  public MyPojo decode(Number value) {
    return new MyPojo().setA(value.intValue());
  }

  @Override
  public Number encode(MyPojo value) {
    return value.getA();
  }

  @Override
  public Class<MyPojo> getTargetClass() {
    return MyPojo.class;
  }
}
