package io.vertx.test.codegen.testjsoncodecs.interfacetest;

import io.vertx.core.spi.json.JsonCodec;

import java.time.ZonedDateTime;

public interface MyCodec extends JsonCodec<ZonedDateTime, String> {

  MyCodec INSTANCE = new MyCodec() {
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
