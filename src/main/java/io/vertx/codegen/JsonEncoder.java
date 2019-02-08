package io.vertx.codegen;

@FunctionalInterface
public interface JsonEncoder<T> {
   T encode(Object value);
}
