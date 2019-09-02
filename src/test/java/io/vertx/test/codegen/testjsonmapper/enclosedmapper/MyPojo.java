package io.vertx.test.codegen.testjsonmapper.enclosedmapper;

import io.vertx.core.spi.json.JsonMapper;
import io.vertx.core.json.JsonObject;

public class MyPojo {

  public static class MyPojoMapper implements JsonMapper<MyPojo, JsonObject> {

    public static final MyPojoMapper INSTANCE = new MyPojoMapper();

    @Override
    public MyPojo deserialize(JsonObject value) throws IllegalArgumentException {
      return null;
    }

    @Override
    public JsonObject serialize(MyPojo value) throws IllegalArgumentException {
      return null;
    }

    @Override
    public Class<MyPojo> getTargetClass() {
      return MyPojo.class;
    }
  }

}
