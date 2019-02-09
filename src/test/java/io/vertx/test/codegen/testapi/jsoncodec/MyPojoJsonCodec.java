package io.vertx.test.codegen.testapi.jsoncodec;


import io.vertx.codegen.JsonCodec;

public class MyPojoJsonCodec implements JsonCodec<MyPojo> {


  @Override
  public MyPojo decode(Object value) {
    return new MyPojo().setA((Integer)value);
  }

  @Override
  public Object encode(MyPojo value) {
    return value.getA();
  }
}
