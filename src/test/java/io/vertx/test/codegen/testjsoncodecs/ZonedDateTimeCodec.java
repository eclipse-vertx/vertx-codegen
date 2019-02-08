package io.vertx.test.codegen.testjsoncodecs;

import io.vertx.codegen.JsonCodec;

import java.time.ZonedDateTime;

public class ZonedDateTimeCodec implements JsonCodec<ZonedDateTime> {
  @Override
  public Object decode(ZonedDateTime value) {
    return value.toString();
  }

  @Override
  public ZonedDateTime encode(Object value) {
    return ZonedDateTime.parse((String)value);
  }
}
