package io.vertx.core.json;

public interface JsonCodec <TARGET_TYPE, JSON_TYPE> {
  TARGET_TYPE decode(JSON_TYPE value);
  JSON_TYPE encode(TARGET_TYPE value);
}
