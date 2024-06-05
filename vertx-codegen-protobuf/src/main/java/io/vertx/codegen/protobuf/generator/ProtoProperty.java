package io.vertx.codegen.protobuf.generator;

import io.vertx.codegen.processor.PropertyInfo;
import io.vertx.codegen.processor.PropertyKind;
import io.vertx.codegen.protobuf.annotations.JsonProtoEncoding;
import io.vertx.codegen.processor.type.ClassKind;

// This class store the protobuf properties of a given field
public class ProtoProperty {
  private int fieldNumber;
  private int wireType;
  private int tag;
  private ProtoType protoType;
  // Indicate if field is nullable.
  private boolean isNullable;
  // Indicate if field is Java boxed type
  private boolean isBoxedType; // TODO maybe do not belong here

  private String defaultValue;

  // Indicate the name of the message of the nested field
  private String message;
  // Indicate the name of the enumeration type
  private String enumType;
  // Built-in types are predefined complex proto types
  // Examples: datetime.proto, struct.proto, vertx-struct.proto
  private String builtInType;

  public static ProtoProperty getProtoProperty(PropertyInfo prop, int fieldNumber) {
    ProtoProperty protoProperty = new ProtoProperty();
    ClassKind propKind = prop.getType().getKind();
    ProtoType protoType = null;
    boolean isNullable = determineIsNullable(prop.getType().getName());
    boolean isBoxedType = determineIsBoxedType(prop.getType().getName());
    String defaultValue = determineDefaultValue(prop.getType().getName());
    String message = null;
    String enumType = null;
    String builtInProtoType = null;
    int wireType;
    if (prop.getType().getKind() == ClassKind.ENUM) {
      enumType = prop.getType().getSimpleName();
      wireType = 0;
    } else { // Not Enum
      if (propKind.basic) {
        protoType = determinePrimitiveProtoType(prop.getType().getName());
        switch (protoType) {
          case BOOL:
          case INT64:
          case INT32:
            wireType = 0;
            break;
          case DOUBLE:
            wireType = 1;
            break;
          case STRING:
            wireType = 2;
            break;
          case FLOAT:
            wireType = 5;
            break;
          default:
            throw new UnsupportedOperationException("Unsupported proto-type " + protoType);
        }
      } else {
        message = prop.getType().getSimpleName();
        wireType = 2;
        builtInProtoType = determineBuiltInType(prop);
      }
    } // Not Enum

    // Override wire type if property is a list, map or set
    if (prop.getKind() == PropertyKind.LIST ||
      prop.getKind() == PropertyKind.MAP ||
      prop.getKind() == PropertyKind.SET) {
      wireType = 2;
    }

    checkFieldNumber(fieldNumber);

    int tag = (fieldNumber << 3) | wireType;

    protoProperty.fieldNumber = fieldNumber;
    protoProperty.wireType = wireType;
    protoProperty.tag = tag;
    protoProperty.protoType = protoType;
    protoProperty.isNullable = isNullable;
    protoProperty.isBoxedType = isBoxedType;
    protoProperty.defaultValue = defaultValue;
    protoProperty.enumType = enumType;
    protoProperty.message = message;
    protoProperty.builtInType = builtInProtoType;
    return protoProperty;
  }

  private static void checkFieldNumber(int fieldNumber) {
    // see https://protobuf.dev/programming-guides/proto3/#assigning
    if (fieldNumber < 1 || fieldNumber > 536_870_911) {
      throw new IllegalArgumentException("Field number " + fieldNumber + " is invalid");
    }
    if (fieldNumber >= 19_000 && fieldNumber <= 19_999) {
      throw new IllegalArgumentException("Field number " + fieldNumber + " is reserved for Protobuf implementations");
    }
  }

  private static ProtoType determinePrimitiveProtoType(String javaDataType) {
    if ("java.lang.Integer".equals(javaDataType) || "int".equals(javaDataType)) {
      return ProtoType.INT32;
    } else if ("java.lang.Long".equals(javaDataType) || "long".equals(javaDataType)) {
      return ProtoType.INT64;
    } else if ("java.lang.Short".equals(javaDataType) || "short".equals(javaDataType)) {
      return ProtoType.INT32;
    } else if ("java.lang.String".equals(javaDataType)) {
      return ProtoType.STRING;
    } else if ("java.lang.Float".equals(javaDataType) || "float".equals(javaDataType)) {
      return ProtoType.FLOAT;
    } else if ("java.lang.Double".equals(javaDataType) || "double".equals(javaDataType)) {
      return ProtoType.DOUBLE;
    } else if ("java.lang.Boolean".equals(javaDataType) || "boolean".equals(javaDataType)) {
      return ProtoType.BOOL;
    } else if ("java.lang.Byte".equals(javaDataType) || "byte".equals(javaDataType)) {
      return ProtoType.INT32;
    } else if ("java.lang.Character".equals(javaDataType) || "char".equals(javaDataType)) {
      return ProtoType.INT32;
    } else {
      throw new UnsupportedOperationException("Unsupported primitive data-type " + javaDataType);
    }
  }

  // Anything other than java primitive type should be nullable
  private static boolean determineIsNullable(String javaDataType) {
    switch (javaDataType) {
      case "int":
      case "long":
      case "short":
      case "float":
      case "double":
      case "boolean":
      case "byte":
      case "char":
        return false;
      default:
        return true;
    }
  }

  private static boolean determineIsBoxedType(String javaDataType) {
    if ("java.lang.Integer".equals(javaDataType) || "Integer".equals(javaDataType) ||
      "java.lang.Short".equals(javaDataType) || "Short".equals(javaDataType) ||
      "java.lang.Long".equals(javaDataType) || "Long".equals(javaDataType) ||
      "java.lang.Float".equals(javaDataType) || "Float".equals(javaDataType) ||
      "java.lang.Double".equals(javaDataType) || "Double".equals(javaDataType) ||
      "java.lang.Boolean".equals(javaDataType) || "Boolean".equals(javaDataType) ||
      "java.lang.String".equals(javaDataType) || "String".equals(javaDataType)) {
      return true;
    } else {
      return false;
    }
  }

  private static String determineDefaultValue(String javaDataType) {
    switch (javaDataType) {
      case "java.lang.Integer":
      case "Integer":
      case "int":
        return "0";

      case "java.lang.Short":
      case "Short":
      case "short":
        return "(short)0";

      case "java.lang.Long":
      case "Long":
      case "long":
        return "0L";

      case "java.lang.Float":
      case "Float":
      case "float":
        return "0f";

      case "java.lang.Double":
      case "Double":
      case "double":
        return "0d";

      case "java.lang.Boolean":
      case "Boolean":
      case "boolean":
        return "false";

      case "java.lang.String":
      case "String":
        return "\"\"";

      default:
        return null;
    }
  }

  // Find out if the data type are io.vertx.protobuf builtin type, return Proto type
  private static String determineBuiltInType(PropertyInfo prop) {
    String javaDataType = prop.getType().getName();
    switch (javaDataType) {
      case "java.time.ZonedDateTime":
        return "ZonedDateTime";
      case "java.time.Instant":
        return "Instant";
      case "io.vertx.core.json.JsonObject":
        return "Struct";
      case "io.vertx.core.json.JsonArray":
        return "ListValue";
      default:
        return null;
    }
  }

  static String getBuiltInProtoConverter(String builtInType, JsonProtoEncoding jsonProtoEncoding) {
    switch (builtInType) {
      case "ZonedDateTime":
        return "ZonedDateTimeProtoConverter";
      case "Instant":
        return "InstantProtoConverter";
      case "JsonObject":
        switch (jsonProtoEncoding) {
          case VERTX_STRUCT:
            return "VertxStructProtoConverter";
          case GOOGLE_STRUCT:
            return "GoogleStructProtoConverter";
          default:
            throw new InternalError("Unknown built-it type " + builtInType);
        }
      case "JsonArray":
        switch (jsonProtoEncoding) {
          case VERTX_STRUCT:
            return "VertxStructListProtoConverter";
          case GOOGLE_STRUCT:
            return "GoogleStructListProtoConverter";
          default:
            throw new InternalError("Unknown built-it type " + builtInType);
        }
      default:
        throw new InternalError("Unknown built-it type " + builtInType);
    }
  }

  public int getFieldNumber() {
    return fieldNumber;
  }

  public int getWireType() {
    return wireType;
  }

  public int getTag() {
    return tag;
  }

  public ProtoType getProtoType() {
    return protoType;
  }

  public boolean isNullable() {
    return isNullable;
  }

  public boolean isBoxedType() {
    return isBoxedType;
  }

  public String getDefaultValue() {
    return defaultValue;
  }

  public String getEnumType() {
    return enumType;
  }

  public String getMessage() {
    return message;
  }

  public boolean isBuiltinType() {
    return builtInType != null;
  }

  public String getBuiltInType() {
    return builtInType;
  }
}
