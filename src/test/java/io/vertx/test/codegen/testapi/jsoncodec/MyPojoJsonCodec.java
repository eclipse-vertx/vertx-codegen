package io.vertx.test.codegen.testapi.jsoncodec;

import io.vertx.core.json.JsonCodec;

public class MyPojoJsonCodec implements JsonCodec<MyPojo, Integer> {

  public static MyPojoJsonCodec getInstance() { return new MyPojoJsonCodec(); }

  @Override
  public MyPojo decode(Integer value) {
    return new MyPojo().setA(value);
  }

  @Override
  public Integer encode(MyPojo value) {
    return value.getA();
  }
}
