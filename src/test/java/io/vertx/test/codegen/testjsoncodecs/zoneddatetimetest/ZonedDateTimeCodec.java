package io.vertx.test.codegen.testjsoncodecs.zoneddatetimetest;

import io.vertx.core.json.JsonCodec;

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
