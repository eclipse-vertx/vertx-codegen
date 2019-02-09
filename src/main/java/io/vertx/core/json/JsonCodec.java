package io.vertx.core.json;

public interface JsonCodec <T> {
  T decode(Object value);
  Object encode(T value);
}
