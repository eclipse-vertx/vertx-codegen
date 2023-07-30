package io.vertx.codegen.protobuf.annotations;

/**
 * The ProtobufGen annotation is used to mark data classes that should be processed to generate protobuf converters.
 * <p>
 * Example usage:
 * <pre>
 * {@literal @}DataObject
 * {@literal @}ProtobufGen
 * class Example {
 *   ...
 * }
 * </pre>
 * Generated Converter:
 * <pre>
 * public class UserProtoConverter {
 *   public static void fromProto(...);
 *   public static void toProto(...);
 * }
 * </pre>
 * Note: This annotation only works with the `@DataObject` annotation.
 *
 * @author <a href="https://github.com/lwlee2608">Jason Lee</a>
 */
public @interface ProtobufGen {
}
