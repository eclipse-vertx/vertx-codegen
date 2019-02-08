package io.vertx.test.codegen.converter;

import io.vertx.codegen.JsonCodec;

import java.time.ZonedDateTime;

public class ZonedDateTimeCodec implements JsonCodec<ZonedDateTime> {
  @Override
  public Object encode(ZonedDateTime value) {
    return value.toString();
  }

  @Override
  public ZonedDateTime decode(Object value) {
    return ZonedDateTime.parse((String)value);
  }
}
