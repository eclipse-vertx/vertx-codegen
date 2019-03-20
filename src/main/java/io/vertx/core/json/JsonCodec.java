package io.vertx.core.json;

public interface JsonCodec <TARGET_TYPE, JSON_TYPE> extends JsonEncoder<TARGET_TYPE, JSON_TYPE>, JsonDecoder<TARGET_TYPE, JSON_TYPE>{ }
