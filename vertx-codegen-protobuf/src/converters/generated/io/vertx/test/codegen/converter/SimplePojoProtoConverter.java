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
import io.vertx.core.json.JsonObject;
import io.vertx.codegen.protobuf.utils.ExpandableIntArray;
import io.vertx.codegen.protobuf.converters.*;

public class SimplePojoProtoConverter {

  public static void fromProto(CodedInputStream input, SimplePojo obj) throws IOException {
    int tag;
    while ((tag = input.readTag()) != 0) {
      switch (tag) {
        case 8: {
          obj.setNullInteger(input.readInt32());
          break;
        }
        case 16: {
          obj.setZeroInteger(input.readInt32());
          break;
        }
        case 24: {
          obj.setNullBoolean(input.readBool());
          break;
        }
        case 32: {
          obj.setZeroBoolean(input.readBool());
          break;
        }
        case 42: {
          obj.setNullString(input.readString());
          break;
        }
        case 50: {
          obj.setZeroString(input.readString());
          break;
        }
        case 56: {
          obj.setPrimitiveInteger(input.readInt32());
          break;
        }
        case 64: {
          obj.setPrimitiveBoolean(input.readBool());
          break;
        }
      }
    }
  }

  public static void toProto(SimplePojo obj, CodedOutputStream output) throws IOException {
    ExpandableIntArray cache = new ExpandableIntArray(16);
    SimplePojoProtoConverter.computeSize(obj, cache, 0);
    SimplePojoProtoConverter.toProto(obj, output, cache, 0);
  }

  public static int toProto(SimplePojo obj, CodedOutputStream output, ExpandableIntArray cache, int index) throws IOException {
    index = index + 1;
    if (obj.getNullInteger() != null) {
      output.writeInt32(1, obj.getNullInteger());
    }
    if (obj.getZeroInteger() != null) {
      output.writeInt32(2, obj.getZeroInteger());
    }
    if (obj.getNullBoolean() != null) {
      output.writeBool(3, obj.getNullBoolean());
    }
    if (obj.getZeroBoolean() != null) {
      output.writeBool(4, obj.getZeroBoolean());
    }
    if (obj.getNullString() != null) {
      output.writeString(5, obj.getNullString());
    }
    if (obj.getZeroString() != null) {
      output.writeString(6, obj.getZeroString());
    }
    if (obj.getPrimitiveInteger() != 0) {
      output.writeInt32(7, obj.getPrimitiveInteger());
    }
    if (obj.isPrimitiveBoolean()) {
      output.writeBool(8, obj.isPrimitiveBoolean());
    }
    return index;
  }

  public static int computeSize(SimplePojo obj) {
    ExpandableIntArray cache = new ExpandableIntArray(16);
    SimplePojoProtoConverter.computeSize(obj, cache, 0);
    return cache.get(0);
  }

  public static int computeSize(SimplePojo obj, ExpandableIntArray cache, final int baseIndex) {
    int size = 0;
    int index = baseIndex + 1;
    if (obj.getNullInteger() != null) {
      size += CodedOutputStream.computeInt32Size(1, obj.getNullInteger());
    }
    if (obj.getZeroInteger() != null) {
      size += CodedOutputStream.computeInt32Size(2, obj.getZeroInteger());
    }
    if (obj.getNullBoolean() != null) {
      size += CodedOutputStream.computeBoolSize(3, obj.getNullBoolean());
    }
    if (obj.getZeroBoolean() != null) {
      size += CodedOutputStream.computeBoolSize(4, obj.getZeroBoolean());
    }
    if (obj.getNullString() != null) {
      size += CodedOutputStream.computeStringSize(5, obj.getNullString());
    }
    if (obj.getZeroString() != null) {
      size += CodedOutputStream.computeStringSize(6, obj.getZeroString());
    }
    if (obj.getPrimitiveInteger() != 0) {
      size += CodedOutputStream.computeInt32Size(7, obj.getPrimitiveInteger());
    }
    if (obj.isPrimitiveBoolean()) {
      size += CodedOutputStream.computeBoolSize(8, obj.isPrimitiveBoolean());
    }
    cache.set(baseIndex, size);
    return index;
  }

}
