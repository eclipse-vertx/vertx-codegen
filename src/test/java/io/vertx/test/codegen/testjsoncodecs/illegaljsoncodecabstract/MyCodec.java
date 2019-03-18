package io.vertx.test.codegen.testjsoncodecs.illegaljsoncodecabstract;

import io.vertx.core.json.JsonCodec;

import java.time.ZonedDateTime;

public abstract class MyCodec implements JsonCodec<ZonedDateTime, String> { }
