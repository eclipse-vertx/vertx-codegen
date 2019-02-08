package io.vertx.codegen;

@FunctionalInterface
public interface JsonDecoder<T> {
  T decode(Object value);
}
