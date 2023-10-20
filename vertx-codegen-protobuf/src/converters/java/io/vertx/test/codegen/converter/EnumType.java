package io.vertx.test.codegen.converter;

import io.vertx.codegen.annotations.VertxGen;
import io.vertx.codegen.protobuf.annotations.FieldNumberStrategy;
import io.vertx.codegen.protobuf.annotations.ProtobufGen;

@VertxGen
@ProtobufGen(fieldNumberStrategy = FieldNumberStrategy.COMPACT)
public enum EnumType {
  A,
  B,
  C
}
