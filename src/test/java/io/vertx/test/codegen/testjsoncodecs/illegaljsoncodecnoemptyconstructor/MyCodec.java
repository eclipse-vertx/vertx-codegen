package io.vertx.test.codegen.testjsoncodecs.illegaljsoncodecnoemptyconstructor;

import io.vertx.codegen.JsonCodec;

import java.time.ZonedDateTime;

public class MyCodec implements JsonCodec<ZonedDateTime> {

  public MyCodec(Integer aValue) {}

  @Override
  public Object decode(ZonedDateTime value) {
    return null;
  }

  @Override
  public ZonedDateTime encode(Object value) {
    return null;
  }
}
