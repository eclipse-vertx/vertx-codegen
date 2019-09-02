package io.vertx.test.codegen.testjsonmapper.abstractclasstest;

import io.vertx.core.spi.json.JsonMapper;

import java.time.ZonedDateTime;

public abstract class MyMapper implements JsonMapper<ZonedDateTime, String> {

  public static final MyMapper INSTANCE = new MyMapper() {
    @Override
    public ZonedDateTime deserialize(String value) {
      return null;
    }

    @Override
    public String serialize(ZonedDateTime value) {
      return null;
    }

    @Override
    public Class<ZonedDateTime> getTargetClass() {
      return ZonedDateTime.class;
    }
  };

}
