package io.vertx.test.codegen.converter;

import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.CodedInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;

public class RecursiveItemProtoConverter {

  public static void fromProto(CodedInputStream input, RecursiveItem obj) throws IOException {
    int tag;
    while ((tag = input.readTag()) != 0) {
      switch (tag) {
        case 10: {
          int length = input.readUInt32();
          int oldLimit = input.pushLimit(length);
          RecursiveItem nested = new RecursiveItem();
          RecursiveItemProtoConverter.fromProto(input, nested);
          obj.setChildA(nested);
          input.popLimit(oldLimit);
          break;
        }
        case 18: {
          int length = input.readUInt32();
          int oldLimit = input.pushLimit(length);
          RecursiveItem nested = new RecursiveItem();
          RecursiveItemProtoConverter.fromProto(input, nested);
          obj.setChildB(nested);
          input.popLimit(oldLimit);
          break;
        }
        case 26: {
          int length = input.readUInt32();
          int oldLimit = input.pushLimit(length);
          RecursiveItem nested = new RecursiveItem();
          RecursiveItemProtoConverter.fromProto(input, nested);
          obj.setChildC(nested);
          input.popLimit(oldLimit);
          break;
        }
        case 34: {
          obj.setId(input.readString());
          break;
        }
      }
    }
  }

  public static void toProto(RecursiveItem obj, CodedOutputStream output) throws IOException {
    if (obj.getChildA() != null) {
      output.writeUInt32NoTag(10);
      output.writeUInt32NoTag(RecursiveItemProtoConverter.computeSize(obj.getChildA()));
      RecursiveItemProtoConverter.toProto(obj.getChildA(), output);
    }
    if (obj.getChildB() != null) {
      output.writeUInt32NoTag(18);
      output.writeUInt32NoTag(RecursiveItemProtoConverter.computeSize(obj.getChildB()));
      RecursiveItemProtoConverter.toProto(obj.getChildB(), output);
    }
    if (obj.getChildC() != null) {
      output.writeUInt32NoTag(26);
      output.writeUInt32NoTag(RecursiveItemProtoConverter.computeSize(obj.getChildC()));
      RecursiveItemProtoConverter.toProto(obj.getChildC(), output);
    }
    if (obj.getId() != null) {
      output.writeString(4, obj.getId());
    }
  }

  public static int computeSize(RecursiveItem obj) {
    int size = 0;
    if (obj.getChildA() != null) {
      size += CodedOutputStream.computeUInt32SizeNoTag(10);
      int dataSize = RecursiveItemProtoConverter.computeSize(obj.getChildA());
      size += CodedOutputStream.computeUInt32SizeNoTag(dataSize);
      size += dataSize;
    }
    if (obj.getChildB() != null) {
      size += CodedOutputStream.computeUInt32SizeNoTag(18);
      int dataSize = RecursiveItemProtoConverter.computeSize(obj.getChildB());
      size += CodedOutputStream.computeUInt32SizeNoTag(dataSize);
      size += dataSize;
    }
    if (obj.getChildC() != null) {
      size += CodedOutputStream.computeUInt32SizeNoTag(26);
      int dataSize = RecursiveItemProtoConverter.computeSize(obj.getChildC());
      size += CodedOutputStream.computeUInt32SizeNoTag(dataSize);
      size += dataSize;
    }
    if (obj.getId() != null) {
      size += CodedOutputStream.computeStringSize(4, obj.getId());
    }
    return size;
  }

  public static void toProto2(RecursiveItem obj, CodedOutputStream output) throws IOException {
    int[] cache = new int[100];
    RecursiveItemProtoConverter.computeSize2(obj, cache, 0);
    RecursiveItemProtoConverter.toProto2(obj, output, cache, 0);
  }

  public static int toProto2(RecursiveItem obj, CodedOutputStream output, int[] cache, int index) throws IOException {
    index = index + 1;
    if (obj.getChildA() != null) {
      output.writeUInt32NoTag(10);
      output.writeUInt32NoTag(cache[index]);
      index = RecursiveItemProtoConverter.toProto2(obj.getChildA(), output, cache, index);
    }
    if (obj.getChildB() != null) {
      output.writeUInt32NoTag(18);
      output.writeUInt32NoTag(cache[index]);
      index = RecursiveItemProtoConverter.toProto2(obj.getChildB(), output, cache, index);
    }
    if (obj.getChildC() != null) {
      output.writeUInt32NoTag(26);
      output.writeUInt32NoTag(cache[index]);
      index = RecursiveItemProtoConverter.toProto2(obj.getChildC(), output, cache, index);
    }
    if (obj.getId() != null) {
      output.writeString(4, obj.getId());
    }
    return index;
  }

  public static int computeSize2(RecursiveItem obj) {
    int[] cache = new int[100];
    RecursiveItemProtoConverter.computeSize2(obj, cache, 0);
    return cache[0];
  }

  public static int computeSize2(RecursiveItem obj, int[] cache, final int baseIndex) {
    System.out.println("computing size 2 for " + obj);
    int size = 0;
    int index = baseIndex + 1;
    if (obj.getChildA() != null) {
      size += CodedOutputStream.computeUInt32SizeNoTag(10);
      int savedIndex = index;
      index = RecursiveItemProtoConverter.computeSize2(obj.getChildA(), cache, index);
      int dataSize = cache[savedIndex];
      size += CodedOutputStream.computeUInt32SizeNoTag(dataSize);
      size += dataSize;
    }
    if (obj.getChildB() != null) {
      size += CodedOutputStream.computeUInt32SizeNoTag(18);
      int savedIndex = index;
      index = RecursiveItemProtoConverter.computeSize2(obj.getChildB(), cache, index);
      int dataSize = cache[savedIndex];
      size += CodedOutputStream.computeUInt32SizeNoTag(dataSize);
      size += dataSize;
    }
    if (obj.getChildC() != null) {
      size += CodedOutputStream.computeUInt32SizeNoTag(26);
      int savedIndex = index;
      index = RecursiveItemProtoConverter.computeSize2(obj.getChildC(), cache, index);
      int dataSize = cache[savedIndex];
      size += CodedOutputStream.computeUInt32SizeNoTag(dataSize);
      size += dataSize;
    }
    if (obj.getId() != null) {
      size += CodedOutputStream.computeStringSize(4, obj.getId());
    }
    cache[baseIndex] = size;
    return index;
  }

}
