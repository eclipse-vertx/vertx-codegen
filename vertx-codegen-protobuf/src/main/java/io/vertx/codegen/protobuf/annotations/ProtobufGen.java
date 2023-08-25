package io.vertx.codegen.protobuf.annotations;

/**
 * The ProtobufGen annotation is used to mark data classes that should be processed to generate protobuf converters.
 * <p>
 * This annotation can optionally specify the protobuf encoding type for JSON objects via the {@code jsonObjectEncodingType} attribute.
 * The default value is {@code JsonObjectProtoEncodingType.VERTX_OPTIMISED}.
 * Available options are:
 * <ul>
 *   <li>{@code VERTX_OPTIMISED} - Utilizes Vert.x optimized encoding.</li>
 *   <li>{@code GOOGLE_STRUCT} - Utilizes the .proto definition from <a href="https://github.com/protocolbuffers/protobuf/blob/main/src/google/protobuf/struct.proto">Google's struct.proto</a> to encode JsonObject into Protobuf. Note that this stores all numeric types as double, which will be less efficient and lose the original numeric type (e.g., integer, short, double).</li>
 * </ul>
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
 * Note: This annotation only works with the {@literal @}DataObject annotation.
 *
 * @author <a href="https://github.com/lwlee2608">Jason Lee</a>
 */
public @interface ProtobufGen {
  JsonObjectProtoEncodingType jsonObjectEncodingType() default JsonObjectProtoEncodingType.VERTX_OPTIMISED;
}
