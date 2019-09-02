package io.vertx.test.codegen.testjsonmapper.illegaljsontypeinmapper;

import io.vertx.core.spi.json.JsonMapper;

import java.time.Instant;
import java.time.ZonedDateTime;

public class ZonedDateTimeMapper implements JsonMapper<ZonedDateTime, Instant> {

  public static final ZonedDateTimeMapper INSTANCE = new ZonedDateTimeMapper();

  @Override
  public ZonedDateTime deserialize(Instant value) throws IllegalArgumentException {
    return null;
  }

  @Override
  public Instant serialize(ZonedDateTime value) throws IllegalArgumentException {
    return null;
  }

  @Override
  public Class<ZonedDateTime> getTargetClass() {
    return ZonedDateTime.class;
  }
}
