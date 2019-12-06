package io.vertx.test.codegen.testjsoncodecs.abstractclasstest;

import io.vertx.core.spi.json.JsonCodec;

import java.time.ZonedDateTime;

public abstract class MyCodec implements JsonCodec<ZonedDateTime, String> {

  public static final MyCodec INSTANCE = new MyCodec() {
    @Override
    public ZonedDateTime decode(String value) {
      return null;
    }

    @Override
    public String encode(ZonedDateTime value) {
      return null;
    }

    @Override
    public Class<ZonedDateTime> getTargetClass() {
      return ZonedDateTime.class;
    }
  };

}
