package io.vertx.test.codegen.testapi.jsonmapper;

import io.vertx.core.spi.json.JsonMapper;

public class MyPojoJsonMapper implements JsonMapper<MyPojo, Number> {

  public static final MyPojoJsonMapper INSTANCE = new MyPojoJsonMapper();

  @Override
  public MyPojo deserialize(Number value) {
    return new MyPojo().setA(value.intValue());
  }

  @Override
  public Number serialize(MyPojo value) {
    return value.getA();
  }

  @Override
  public Class<MyPojo> getTargetClass() {
    return MyPojo.class;
  }
}
