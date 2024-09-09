package io.vertx.test.codegen.converter;

import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.CodedInputStream;
import java.io.IOException;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;
import io.vertx.codegen.protobuf.ProtobufEncodingMode;
import io.vertx.core.json.JsonObject;
import io.vertx.codegen.protobuf.utils.ExpandableIntArray;
import io.vertx.codegen.protobuf.converters.*;

public class SimplePojoProtoConverter {

  public static void fromProto(CodedInputStream input, SimplePojo obj) throws IOException {
    fromProto(input, obj, ProtobufEncodingMode.VERTX);
  }

  public static void fromProto(CodedInputStream input, SimplePojo obj, ProtobufEncodingMode encodingMode) throws IOException {
    boolean compatibleMode = encodingMode == ProtobufEncodingMode.GOOGLE_COMPATIBLE;
    if (compatibleMode) {
      obj.setIntegerField(0);
      obj.setLongField(0L);
      obj.setBooleanField(false);
      obj.setStringField("");
    }
    int tag;
    while ((tag = input.readTag()) != 0) {
      switch (tag) {
        case 8: {
          obj.setIntegerField(input.readInt32());
          break;
        }
        case 16: {
          obj.setLongField(input.readInt64());
          break;
        }
        case 24: {
          obj.setBooleanField(input.readBool());
          break;
        }
        case 34: {
          obj.setStringField(input.readString());
          break;
        }
      }
    } // while loop
  }

  public static void toProto(SimplePojo obj, CodedOutputStream output) throws IOException {
    toProto(obj, output, ProtobufEncodingMode.VERTX);
  }

  public static void toProto(SimplePojo obj, CodedOutputStream output, ProtobufEncodingMode encodingMode) throws IOException {
    ExpandableIntArray cache = new ExpandableIntArray(16);
    SimplePojoProtoConverter.computeSize(obj, cache, 0, encodingMode);
    SimplePojoProtoConverter.toProto(obj, output, cache, 0, encodingMode);
  }

  static int toProto(SimplePojo obj, CodedOutputStream output, ExpandableIntArray cache, int index, ProtobufEncodingMode encodingMode) throws IOException {
    boolean compatibleMode = encodingMode == ProtobufEncodingMode.GOOGLE_COMPATIBLE;
    index = index + 1;
    // integerField
    if (compatibleMode && obj.getIntegerField() == null) {
      throw new IllegalArgumentException("Null values are not allowed for boxed types in compatibility mode");
    }
    if ((!compatibleMode && obj.getIntegerField() != null) || (compatibleMode && obj.getIntegerField() != 0)) {
      output.writeInt32(1, obj.getIntegerField());
    }
    // longField
    if (compatibleMode && obj.getLongField() == null) {
      throw new IllegalArgumentException("Null values are not allowed for boxed types in compatibility mode");
    }
    if ((!compatibleMode && obj.getLongField() != null) || (compatibleMode && obj.getLongField() != 0L)) {
      output.writeInt64(2, obj.getLongField());
    }
    // booleanField
    if (compatibleMode && obj.getBooleanField() == null) {
      throw new IllegalArgumentException("Null values are not allowed for boxed types in compatibility mode");
    }
    if ((!compatibleMode && obj.getBooleanField() != null) || (compatibleMode && !obj.getBooleanField())) {
      output.writeBool(3, obj.getBooleanField());
    }
    // stringField
    if (compatibleMode && obj.getStringField() == null) {
      throw new IllegalArgumentException("Null values are not allowed for boxed types in compatibility mode");
    }
    if ((!compatibleMode && obj.getStringField() != null) || (compatibleMode && !obj.getStringField().isEmpty())) {
      output.writeString(4, obj.getStringField());
    }
    return index;
  }

  public static int computeSize(SimplePojo obj) {
    return computeSize(obj, ProtobufEncodingMode.VERTX);
  }

  public static int computeSize(SimplePojo obj, ProtobufEncodingMode encodingMode) {
    ExpandableIntArray cache = new ExpandableIntArray(16);
    SimplePojoProtoConverter.computeSize(obj, cache, 0, encodingMode);
    return cache.get(0);
  }

  static int computeSize(SimplePojo obj, ExpandableIntArray cache, final int baseIndex, ProtobufEncodingMode encodingMode) {
    int size = 0;
    int index = baseIndex + 1;
    if (obj.getIntegerField() != null) {
      size += CodedOutputStream.computeInt32Size(1, obj.getIntegerField());
    }
    if (obj.getLongField() != null) {
      size += CodedOutputStream.computeInt64Size(2, obj.getLongField());
    }
    if (obj.getBooleanField() != null) {
      size += CodedOutputStream.computeBoolSize(3, obj.getBooleanField());
    }
    if (obj.getStringField() != null) {
      size += CodedOutputStream.computeStringSize(4, obj.getStringField());
    }
    cache.set(baseIndex, size);
    return index;
  }

}
