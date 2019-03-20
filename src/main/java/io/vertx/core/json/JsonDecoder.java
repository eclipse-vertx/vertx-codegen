package io.vertx.core.json;

public interface JsonDecoder<TARGET_TYPE, JSON_TYPE> {
  TARGET_TYPE decode(JSON_TYPE value);
}
