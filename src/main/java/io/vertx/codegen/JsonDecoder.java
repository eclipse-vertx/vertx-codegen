package io.vertx.codegen;

@FunctionalInterface
public interface JsonDecoder<T> {
  Object decode(T value);
}
