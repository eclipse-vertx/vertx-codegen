package io.vertx.codegen.protobuf.generator;

import io.vertx.codegen.PropertyInfo;
import io.vertx.codegen.PropertyKind;
import io.vertx.codegen.type.ClassKind;

// This class store the protobuf properties of a given field
public class ProtoProperty {
  private int fieldNumber;
  private int wireType;
  private int tag;
  private ProtoType protoType;
  private boolean isNullable;
  private String message;       // Indicate the name of the message of the nested field

  public static ProtoProperty getProtoProperty(PropertyInfo prop, int fieldNumber) {
    ProtoProperty protoProperty = new ProtoProperty();
    ClassKind propKind = prop.getType().getKind();
    ProtoType protoType;
    boolean isNullable = determineIsNullable(prop.getType().getName());
    String message = null;
    int wireType;
    if (propKind.basic) {
      protoType = determineProtoType(prop.getType().getName());
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
      protoType = null;
      message = prop.getType().getSimpleName();
      wireType = 2;
    }

    // Override wire type if property is a list, map or set
    if (prop.getKind() == PropertyKind.LIST ||
      prop.getKind() == PropertyKind.MAP ||
      prop.getKind() == PropertyKind.SET) {
      wireType = 2;
    }

    int tag = (fieldNumber << 3) | wireType;

    protoProperty.fieldNumber = fieldNumber;
    protoProperty.wireType = wireType;
    protoProperty.tag = tag;
    protoProperty.protoType = protoType;
    protoProperty.isNullable = isNullable;
    protoProperty.message = message;
    return protoProperty;
  }

  private static ProtoType determineProtoType(String javaDataType) {
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
      throw new UnsupportedOperationException("Unsupported data-type " + javaDataType);
    }
  }

  // Anything other than java primitive type should be nullable
  private static boolean determineIsNullable(String javaDataType) {
    if ("int".equals(javaDataType)) {
      return false;
    } else if ("long".equals(javaDataType)) {
      return false;
    } else if ("short".equals(javaDataType)) {
      return false;
    } else if ("float".equals(javaDataType)) {
      return false;
    } else if ("double".equals(javaDataType)) {
      return false;
    } else if ("boolean".equals(javaDataType)) {
      return false;
    } else if ("byte".equals(javaDataType)) {
      return false;
    } else if ("char".equals(javaDataType)) {
      return false;
    } else {
      return true;
    }
  }

  // Find out if the data type are io.vertx.protobuf builtin type
  public static String getBuiltInType(PropertyInfo prop) {
    String javaDataType = prop.getType().getName();
    if ("java.time.ZonedDateTime".equals(javaDataType)) {
      return "ZonedDateTime";
    } else if ("java.time.Instant".equals(javaDataType)) {
      return "Instant";
    } else if ("io.vertx.core.json.JsonObject".equals(javaDataType)) {
      return "JsonObject";
    } else {
      return null;
    }
  }

  public static String getProtoConverter(String builtInType) {
    if ("ZonedDateTime".equals(builtInType)) {
      return "ZonedDateTimeProtoConverter";
    } else if ("Instant".equals(builtInType)) {
      return "InstantProtoConverter";
    } else if ("JsonObject".equals(builtInType)) {
      return "VertxStructProtoConverter";
    } else {
      return null;
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

  public String getMessage() {
    return message;
  }
}
