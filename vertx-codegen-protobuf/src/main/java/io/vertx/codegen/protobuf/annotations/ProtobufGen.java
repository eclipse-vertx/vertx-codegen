package io.vertx.codegen.protobuf.annotations;

/**
 * The ProtobufGen annotation is used to mark data classes that should be processed to generate protobuf converters.
 * <p>
 * This annotation can optionally specify the protobuf encoding type for JSON via the {@code jsonProtoEncoding} attribute.
 * The default value is {@code JsonProtoEncoding.VERTX_STRUCT}.
 * Available options are:
 * <ul>
 *   <li>{@code VERTX_STRUCT} - Utilizes Vert.x optimized encoding.</li>
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
 * Note: This annotation only works with the {@literal @DataObject} annotation.
 *
 * @author <a href="https://github.com/lwlee2608">Jason Lee</a>
 */
public @interface ProtobufGen {

  /**
   * @return whether the generated converter should be public or package private
   */
  boolean publicConverter() default true;

  JsonProtoEncoding jsonProtoEncoding() default JsonProtoEncoding.VERTX_STRUCT;

  /**
   * The strategy of field number assignment. Use {@link FieldNumberStrategy#MANUAL},
   * {@link FieldNumberStrategy#COMPACT}, or {@link FieldNumberStrategy#SEGMENTED}.
   * <p>
   * See the <a href="https://protobuf.dev/programming-guides/proto3/">Protocol Buffers Language Guide</a>
   * for more information about protobuf schema evolution.
   * <p>
   * Note that this setting is ignored for {@code enum}s, where protobuf enum constants are always
   * assigned automatically. Coincidentally, the protobuf enum constants are identical to
   * {@linkplain Enum#ordinal() enum ordinals}. Extra <strong>care must be taken when changing
   * {@code enum}s</strong> annotated {@code @ProtobufGen}. In the future, this setting may become
   * relevant even for enums, together with {@link ProtobufField @ProtobufField}.
   */
  FieldNumberStrategy fieldNumberStrategy();

  /**
   * The set of reserved field numbers. It is an error for a data object property to be explicitly assigned
   * a field number from the reserved set. Automatically assigned field numbers will skip the reserved numbers.
   */
  int[] reservedFieldNumbers() default {};

  /**
   * The set of reserved field names. It is an error for a data object property to have a name that is
   * present in the reserved set.
   */
  String[] reservedFieldNames() default {};
}
