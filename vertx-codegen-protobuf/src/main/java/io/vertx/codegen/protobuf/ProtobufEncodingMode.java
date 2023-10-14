package io.vertx.codegen.protobuf;

/**
 * Enumeration representing different encoding modes for Protocol Buffers encoding.
 */
public enum ProtobufEncodingMode {
  /**
   * In this encoding mode, the converter uses a non-standard protobuf encoding to allow boxed types
   * (e.g., Integer, Double) and String to be nullable. This encoding mode is intended for use when
   * communicating with another Vert.x converter that supports nullable values.
   */
  VERTX_NULLABLE,

  /**
   * In this encoding mode, the converter uses the standard protobuf encoding, which is compatible with
   * Google's protobuf decoder. Null values are not allowed for boxed types and String in this mode.
   */
  GOOGLE_COMPATIBLE,
}
