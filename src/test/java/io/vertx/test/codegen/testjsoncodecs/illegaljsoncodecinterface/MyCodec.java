package io.vertx.test.codegen.testjsoncodecs.illegaljsoncodecinterface;

import io.vertx.core.json.JsonCodec;

import java.time.ZonedDateTime;

public interface MyCodec extends JsonCodec<ZonedDateTime> { }
