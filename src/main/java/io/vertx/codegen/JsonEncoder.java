package io.vertx.codegen;

@FunctionalInterface
public interface JsonEncoder<T> {
  Object encode(T value);
}
