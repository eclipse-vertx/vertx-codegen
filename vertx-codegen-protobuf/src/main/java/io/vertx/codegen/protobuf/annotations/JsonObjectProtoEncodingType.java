package io.vertx.codegen.protobuf.annotations;

public enum JsonObjectProtoEncodingType {
  VERTX_OPTIMISED, // Utilizes Vert.x optimized encoding
  GOOGLE_STRUCT    // Utilizes the .proto definition from Google's struct.proto to encode JsonObject into Protobuf
}
