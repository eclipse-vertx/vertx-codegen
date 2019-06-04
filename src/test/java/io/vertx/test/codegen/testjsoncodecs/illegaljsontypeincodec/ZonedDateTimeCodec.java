package io.vertx.test.codegen.testjsoncodecs.illegaljsontypeincodec;

import io.vertx.core.spi.json.JsonCodec;

import java.time.Instant;
import java.time.ZonedDateTime;

public class ZonedDateTimeCodec implements JsonCodec<ZonedDateTime, Instant> {

  public static final ZonedDateTimeCodec INSTANCE = new ZonedDateTimeCodec();

  @Override
  public ZonedDateTime decode(Instant value) throws IllegalArgumentException {
    return null;
  }

  @Override
  public Instant encode(ZonedDateTime value) throws IllegalArgumentException {
    return null;
  }

  @Override
  public Class<ZonedDateTime> getTargetClass() {
    return ZonedDateTime.class;
  }
}
