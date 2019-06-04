package io.vertx.test.codegen.testjsoncodecs.missinginstancefield;

import io.vertx.core.spi.json.JsonCodec;

import java.time.ZonedDateTime;

public class ZonedDateTimeCodec implements JsonCodec<ZonedDateTime, String> {

  @Override
  public String encode(ZonedDateTime value) {
    return value.toString();
  }

  @Override
  public Class<ZonedDateTime> getTargetClass() {
    return ZonedDateTime.class;
  }

  @Override
  public ZonedDateTime decode(String value) {
    return ZonedDateTime.parse(value);
  }
}
