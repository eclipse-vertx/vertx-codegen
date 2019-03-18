package io.vertx.test.codegen.testjsoncodecs.illegaljsoncodecnoemptyconstructor;

import io.vertx.core.json.JsonCodec;

import java.time.ZonedDateTime;

public class MyCodec implements JsonCodec<ZonedDateTime, String> {

  public MyCodec(Integer aValue) {}

  @Override
  public String encode(ZonedDateTime value) {
    return null;
  }

  @Override
  public ZonedDateTime decode(String value) {
    return null;
  }
}
