package io.vertx.codegen.protobuf.annotations;

/**
 * The ProtobufGen annotation is a marker for data classes that should have corresponding .proto files and converters generated.
 * <p>
 * When a data class is annotated with ProtobufGen, during the build process a .proto file is created, along with a set of Protobuf converters.
 * <p>
 * Example:
 * <pre>
 * {@literal @}DataObject
 * {@literal @}ProtobufGen
 * class Example {
 *   ...
 * }
 * </pre>
 * Generated:
 * <pre>
 * public class UserProtoConverter {
 *   public static void fromProto(...);
 *   public static void toProto(...);
 * }
 * </pre>
 * @author <a href="https://github.com/lwlee2608">Jason Lee</a>
 */
public @interface ProtobufGen {
}
