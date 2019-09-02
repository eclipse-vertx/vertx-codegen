package io.vertx.test.codegen.testjsonmapper.interfacetest;

import io.vertx.core.spi.json.JsonMapper;

import java.time.ZonedDateTime;

public interface MyMapper extends JsonMapper<ZonedDateTime, String> {

  MyMapper INSTANCE = new MyMapper() {
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
