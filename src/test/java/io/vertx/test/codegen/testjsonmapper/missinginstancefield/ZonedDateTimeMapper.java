package io.vertx.test.codegen.testjsonmapper.missinginstancefield;

import io.vertx.core.spi.json.JsonMapper;

import java.time.ZonedDateTime;

public class ZonedDateTimeMapper implements JsonMapper<ZonedDateTime, String> {

  @Override
  public String serialize(ZonedDateTime value) {
    return value.toString();
  }

  @Override
  public Class<ZonedDateTime> getTargetClass() {
    return ZonedDateTime.class;
  }

  @Override
  public ZonedDateTime deserialize(String value) {
    return ZonedDateTime.parse(value);
  }
}
