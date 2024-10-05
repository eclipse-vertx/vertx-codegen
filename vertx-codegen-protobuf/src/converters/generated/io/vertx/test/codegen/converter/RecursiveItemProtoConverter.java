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
import io.vertx.codegen.protobuf.impl.converters.*;

public class RecursiveItemProtoConverter {

  public static void fromProto(CodedInputStream input, RecursiveItem obj) throws IOException {
    fromProto(input, obj, ProtobufEncodingMode.VERTX);
  }

  public static void fromProto(CodedInputStream input, RecursiveItem obj, ProtobufEncodingMode encodingMode) throws IOException {
    boolean compatibleMode = encodingMode == ProtobufEncodingMode.GOOGLE_COMPATIBLE;
    if (compatibleMode) {
      obj.setId("");
    }
    int tag;
    while ((tag = input.readTag()) != 0) {
      switch (tag) {
        case 10: {
          obj.setId(input.readString());
          break;
        }
        case 18: {
          int length = input.readUInt32();
          int limit = input.pushLimit(length);
          RecursiveItem nested = new RecursiveItem();
          RecursiveItemProtoConverter.fromProto(input, nested);
          obj.setChildA(nested);
          input.popLimit(limit);
          break;
        }
        case 26: {
          int length = input.readUInt32();
          int limit = input.pushLimit(length);
          RecursiveItem nested = new RecursiveItem();
          RecursiveItemProtoConverter.fromProto(input, nested);
          obj.setChildB(nested);
          input.popLimit(limit);
          break;
        }
        case 34: {
          int length = input.readUInt32();
          int limit = input.pushLimit(length);
          RecursiveItem nested = new RecursiveItem();
          RecursiveItemProtoConverter.fromProto(input, nested);
          obj.setChildC(nested);
          input.popLimit(limit);
          break;
        }
      }
    } // while loop
  }

  public static void toProto(RecursiveItem obj, CodedOutputStream output) throws IOException {
    toProto(obj, output, ProtobufEncodingMode.VERTX);
  }

  public static void toProto(RecursiveItem obj, CodedOutputStream output, ProtobufEncodingMode encodingMode) throws IOException {
    ExpandableIntArray cache = new ExpandableIntArray(16);
    RecursiveItemProtoConverter.computeSize(obj, cache, 0, encodingMode);
    RecursiveItemProtoConverter.toProto(obj, output, cache, 0, encodingMode);
  }

  static int toProto(RecursiveItem obj, CodedOutputStream output, ExpandableIntArray cache, int index, ProtobufEncodingMode encodingMode) throws IOException {
    boolean compatibleMode = encodingMode == ProtobufEncodingMode.GOOGLE_COMPATIBLE;
    index = index + 1;
    // id
    if (compatibleMode && obj.getId() == null) {
      throw new IllegalArgumentException("Null values are not allowed for boxed types in compatibility mode");
    }
    if ((!compatibleMode && obj.getId() != null) || (compatibleMode && !obj.getId().isEmpty())) {
      output.writeString(1, obj.getId());
    }
    // childA
    if (obj.getChildA() != null) {
      output.writeUInt32NoTag(18);
      output.writeUInt32NoTag(cache.get(index));
      index = RecursiveItemProtoConverter.toProto(obj.getChildA(), output, cache, index, encodingMode);
    }
    // childB
    if (obj.getChildB() != null) {
      output.writeUInt32NoTag(26);
      output.writeUInt32NoTag(cache.get(index));
      index = RecursiveItemProtoConverter.toProto(obj.getChildB(), output, cache, index, encodingMode);
    }
    // childC
    if (obj.getChildC() != null) {
      output.writeUInt32NoTag(34);
      output.writeUInt32NoTag(cache.get(index));
      index = RecursiveItemProtoConverter.toProto(obj.getChildC(), output, cache, index, encodingMode);
    }
    return index;
  }

  public static int computeSize(RecursiveItem obj) {
    return computeSize(obj, ProtobufEncodingMode.VERTX);
  }

  public static int computeSize(RecursiveItem obj, ProtobufEncodingMode encodingMode) {
    ExpandableIntArray cache = new ExpandableIntArray(16);
    RecursiveItemProtoConverter.computeSize(obj, cache, 0, encodingMode);
    return cache.get(0);
  }

  static int computeSize(RecursiveItem obj, ExpandableIntArray cache, final int baseIndex, ProtobufEncodingMode encodingMode) {
    int size = 0;
    int index = baseIndex + 1;
    if (obj.getId() != null) {
      size += CodedOutputStream.computeStringSize(1, obj.getId());
    }
    if (obj.getChildA() != null) {
      size += CodedOutputStream.computeUInt32SizeNoTag(18);
      int savedIndex = index;
      index = RecursiveItemProtoConverter.computeSize(obj.getChildA(), cache, index, encodingMode);
      int dataSize = cache.get(savedIndex);
      size += CodedOutputStream.computeUInt32SizeNoTag(dataSize);
      size += dataSize;
    }
    if (obj.getChildB() != null) {
      size += CodedOutputStream.computeUInt32SizeNoTag(26);
      int savedIndex = index;
      index = RecursiveItemProtoConverter.computeSize(obj.getChildB(), cache, index, encodingMode);
      int dataSize = cache.get(savedIndex);
      size += CodedOutputStream.computeUInt32SizeNoTag(dataSize);
      size += dataSize;
    }
    if (obj.getChildC() != null) {
      size += CodedOutputStream.computeUInt32SizeNoTag(34);
      int savedIndex = index;
      index = RecursiveItemProtoConverter.computeSize(obj.getChildC(), cache, index, encodingMode);
      int dataSize = cache.get(savedIndex);
      size += CodedOutputStream.computeUInt32SizeNoTag(dataSize);
      size += dataSize;
    }
    cache.set(baseIndex, size);
    return index;
  }

}
