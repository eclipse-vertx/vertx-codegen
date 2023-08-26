package io.vertx.codegen.protobuf.annotations;

public enum JsonProtoEncoding {
  VERTX_STRUCT, // Utilizes Vert.x optimized encoding
  GOOGLE_STRUCT    // Utilizes the .proto definition from Google's struct.proto to encode JsonObject into Protobuf
}
