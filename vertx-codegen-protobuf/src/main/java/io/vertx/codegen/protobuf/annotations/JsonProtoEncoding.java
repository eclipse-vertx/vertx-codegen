package io.vertx.codegen.protobuf.annotations;

/**
 * Enumerates the available JSON elements to Protocol Buffers encoding strategies.
 * <p>
 * This enum is used to determine the encoding method for converting JSON data to Protocol Buffers format.
 * </p>
 */
public enum JsonProtoEncoding {

  /**
   * This encoding method uses the Vert.x vertx-struct.proto to convert JSON elements into Protocol Buffers format.
   * <p>
   * Employs optimizations to Vert.x to efficiently convert JSON data.
   * </p>
   */
  VERTX_STRUCT,

  /**
   * This encoding method uses the Google struct.proto to convert JSON elements into Protocol Buffers format.
   */
  GOOGLE_STRUCT
}
