package io.vertx.codegen.generators.dataobjecthelper.proto;

import io.vertx.codegen.PropertyInfo;
import io.vertx.codegen.PropertyKind;
import io.vertx.codegen.type.ClassKind;

public class ProtoProperty {
  private int fieldNumber;
  private int wireType;
  private int tag;
  private ProtoType protoType;
  private String message;

  public static ProtoProperty getProtoProperty(PropertyInfo prop, int fieldNumber) {
    ProtoProperty protoProperty = new ProtoProperty();
    ClassKind propKind = prop.getType().getKind();
    String message = null;
    ProtoType protoType;
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

    protoProperty.setFieldNumber(fieldNumber);
    protoProperty.setWireType(wireType);
    protoProperty.setTag(tag);
    protoProperty.setProtoType(protoType);
    protoProperty.setMessage(message);
    return protoProperty;
  }

  public static ProtoType determineProtoType(String javaDataType) {
    if ("java.lang.Integer".equals(javaDataType)) {
      return ProtoType.INT32;
    } else if ("java.lang.Long".equals(javaDataType)) {
      return ProtoType.INT64;
    } else if ("java.lang.Short".equals(javaDataType)) {
      return ProtoType.INT32;
    } else if ("java.lang.String".equals(javaDataType)) {
      return ProtoType.STRING;
    } else if ("java.lang.Float".equals(javaDataType)) {
      return ProtoType.FLOAT;
    } else if ("java.lang.Double".equals(javaDataType)) {
      return ProtoType.DOUBLE;
    } else if ("java.lang.Boolean".equals(javaDataType)) {
      return ProtoType.BOOL;
    } else if ("java.lang.Character".equals(javaDataType)) {
      return ProtoType.INT32;
    } else {
      throw new UnsupportedOperationException("Unsupported data-type " + javaDataType);
    }
  }

  public int getFieldNumber() {
    return fieldNumber;
  }

  public void setFieldNumber(int fieldNumber) {
    this.fieldNumber = fieldNumber;
  }

  public int getWireType() {
    return wireType;
  }

  public void setWireType(int wireType) {
    this.wireType = wireType;
  }

  public int getTag() {
    return tag;
  }

  public void setTag(int tag) {
    this.tag = tag;
  }

  public ProtoType getProtoType() {
    return protoType;
  }

  public void setProtoType(ProtoType protoType) {
    this.protoType = protoType;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
