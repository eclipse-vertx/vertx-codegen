package io.vertx.core.json;

public interface JsonEncoder<TARGET_TYPE, JSON_TYPE> {
  JSON_TYPE encode(TARGET_TYPE value);
}
