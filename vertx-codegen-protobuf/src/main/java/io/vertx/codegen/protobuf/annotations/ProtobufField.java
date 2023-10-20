package io.vertx.codegen.protobuf.annotations;

/**
 * Specifies a protobuf <em>field number</em> of a property in a data class that uses {@link ProtobufGen}.
 * See {@link ProtobufGen#fieldNumberStrategy()} for more information about how field numbers
 * may be assigned.
 * <p>
 * Note that field numbers should <strong>never</strong> be changed or reused.
 * See the <a href="https://protobuf.dev/programming-guides/proto3/">Protocol Buffers Language Guide</a>
 * for more information.
 * <p>
 * Currently, this annotation is ignored on enum values. That may change in the future.
 */
public @interface ProtobufField {
  int value();
}
