package io.vertx.test.codegen.converter;

import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.CodedInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;

public class UserProtoConverter {

  public static void fromProto(CodedInputStream input, User obj) throws IOException {
    int tag;
    while ((tag = input.readTag()) != 0) {
      switch (tag) {
        case 10: {
          int length = input.readUInt32();
          int oldLimit = input.pushLimit(length);
          Address nested = new Address();
          AddressProtoConverter.fromProto(input, nested);
          obj.setAddress(nested);
          input.popLimit(oldLimit);
          break;
        }
        case 16: {
          obj.setAge(input.readInt32());
          break;
        }
        case 24: {
          obj.setBoolField(input.readBool());
          break;
        }
        case 32: {
          obj.setCharField((char) input.readInt32());
          break;
        }
        case 41: {
          obj.setDoubleField(input.readDouble());
          break;
        }
        case 50: {
          int length = input.readRawVarint32();
          int limit = input.pushLimit(length);
          List<Integer> list = new ArrayList<>();
          while (input.getBytesUntilLimit() > 0) {
            list.add(input.readInt32());
          }
          obj.setIntegerListField(list);
          input.popLimit(limit);
          break;
        }
        case 58: {
          int length = input.readRawVarint32();
          int limit = input.pushLimit(length);
          Map<String, Integer> map = obj.getIntegerValueMap();
          if (map == null) {
            map = new HashMap<>();
          }
          input.readTag();
          String key = input.readString();
          input.readTag();
          Integer value = input.readInt32();
          map.put(key, value);
          obj.setIntegerValueMap(map);
          input.popLimit(limit);
          break;
        }
        case 64: {
          obj.setLongField(input.readInt64());
          break;
        }
        case 72: {
          obj.setShortField((short) input.readInt32());
          break;
        }
        case 82: {
          int length = input.readRawVarint32();
          int limit = input.pushLimit(length);
          Map<String, String> map = obj.getStringValueMap();
          if (map == null) {
            map = new HashMap<>();
          }
          input.readTag();
          String key = input.readString();
          input.readTag();
          String value = input.readString();
          map.put(key, value);
          obj.setStringValueMap(map);
          input.popLimit(limit);
          break;
        }
        case 90: {
          obj.setUserName(input.readString());
          break;
        }
      }
    }
  }

  public static void toProto(User obj, CodedOutputStream output) throws IOException {
    if (obj.getAddress() != null) {
      output.writeUInt32NoTag(10);
      output.writeUInt32NoTag(AddressProtoConverter.computeSize(obj.getAddress()));
      AddressProtoConverter.toProto(obj.getAddress(), output);
    }
    if (obj.getAge() != null) {
      output.writeInt32(2, obj.getAge());
    }
    if (obj.getBoolField() != null) {
      output.writeBool(3, obj.getBoolField());
    }
    if (obj.getCharField() != null) {
      output.writeInt32(4, obj.getCharField());
    }
    if (obj.getDoubleField() != null) {
      output.writeDouble(5, obj.getDoubleField());
    }
    if (obj.getIntegerListField() != null) {
      if (obj.getIntegerListField().size() > 0) {
        output.writeUInt32NoTag(50);
        int dataSize = 0;
        for (Integer element: obj.getIntegerListField()) {
          dataSize += CodedOutputStream.computeInt32SizeNoTag(element);
        }
        output.writeUInt32NoTag(dataSize);
        for (Integer element: obj.getIntegerListField()) {
          output.writeInt32NoTag(element);
        }
      }
    }
    if (obj.getIntegerValueMap() != null) {
      for (Map.Entry<String, Integer> entry : obj.getIntegerValueMap().entrySet()) {
        output.writeUInt32NoTag(58);
        int dataSize = 0;
        dataSize += CodedOutputStream.computeStringSize(1, entry.getKey());
        dataSize += CodedOutputStream.computeInt32Size(2, entry.getValue());
        output.writeUInt32NoTag(dataSize);
        output.writeString(1, entry.getKey());
        output.writeInt32(2, entry.getValue());
      }
    }
    if (obj.getLongField() != null) {
      output.writeInt64(8, obj.getLongField());
    }
    if (obj.getShortField() != null) {
      output.writeInt32(9, obj.getShortField());
    }
    if (obj.getStringValueMap() != null) {
      for (Map.Entry<String, String> entry : obj.getStringValueMap().entrySet()) {
        output.writeUInt32NoTag(82);
        int dataSize = 0;
        dataSize += CodedOutputStream.computeStringSize(1, entry.getKey());
        dataSize += CodedOutputStream.computeStringSize(2, entry.getValue());
        output.writeUInt32NoTag(dataSize);
        output.writeString(1, entry.getKey());
        output.writeString(2, entry.getValue());
      }
    }
    if (obj.getUserName() != null) {
      output.writeString(11, obj.getUserName());
    }
  }

  public static int computeSize(User obj) {
    int size = 0;
    if (obj.getAddress() != null) {
      size += CodedOutputStream.computeUInt32SizeNoTag(10);
      int dataSize = AddressProtoConverter.computeSize(obj.getAddress());
      size += CodedOutputStream.computeUInt32SizeNoTag(dataSize);
      size += dataSize;
    }
    if (obj.getAge() != null) {
      size += CodedOutputStream.computeInt32Size(2, obj.getAge());
    }
    if (obj.getBoolField() != null) {
      size += CodedOutputStream.computeBoolSize(3, obj.getBoolField());
    }
    if (obj.getCharField() != null) {
      size += CodedOutputStream.computeInt32Size(4, obj.getCharField());
    }
    if (obj.getDoubleField() != null) {
      size += CodedOutputStream.computeDoubleSize(5, obj.getDoubleField());
    }
    if (obj.getIntegerListField() != null) {
      if (obj.getIntegerListField().size() > 0) {
        size += CodedOutputStream.computeUInt32SizeNoTag(50);
        int dataSize = 0;
        for (Integer element: obj.getIntegerListField()) {
          dataSize += CodedOutputStream.computeInt32SizeNoTag(element);
        }
        size += CodedOutputStream.computeUInt32SizeNoTag(dataSize);
        size += dataSize;
      }
    }
    if (obj.getIntegerValueMap() != null) {
      for (Map.Entry<String, Integer> entry : obj.getIntegerValueMap().entrySet()) {
        size += CodedOutputStream.computeUInt32SizeNoTag(58);
        int dataSize = 0;
        dataSize += CodedOutputStream.computeStringSize(1, entry.getKey());
        dataSize += CodedOutputStream.computeInt32Size(2, entry.getValue());
        size += CodedOutputStream.computeUInt32SizeNoTag(dataSize);
        size += dataSize;
      }
    }
    if (obj.getLongField() != null) {
      size += CodedOutputStream.computeInt64Size(8, obj.getLongField());
    }
    if (obj.getShortField() != null) {
      size += CodedOutputStream.computeInt32Size(9, obj.getShortField());
    }
    if (obj.getStringValueMap() != null) {
      for (Map.Entry<String, String> entry : obj.getStringValueMap().entrySet()) {
        size += CodedOutputStream.computeUInt32SizeNoTag(82);
        int dataSize = 0;
        dataSize += CodedOutputStream.computeStringSize(1, entry.getKey());
        dataSize += CodedOutputStream.computeStringSize(2, entry.getValue());
        size += CodedOutputStream.computeUInt32SizeNoTag(dataSize);
        size += dataSize;
      }
    }
    if (obj.getUserName() != null) {
      size += CodedOutputStream.computeStringSize(11, obj.getUserName());
    }
    return size;
  }

  public static void toProto2(User obj, CodedOutputStream output, int[] cache, int baseIndex) throws IOException {
    if (obj.getAddress() != null) {
      output.writeUInt32NoTag(10);
      int index = AddressProtoConverter.computeSize2(obj.getAddress(), cache, baseIndex);
      System.out.println("cache of address " + Arrays.toString(cache));
      System.out.println("index of address " + index);
      System.out.println("length of address " + cache[baseIndex]);
      output.writeUInt32NoTag(cache[baseIndex]);
      baseIndex += index;
      AddressProtoConverter.toProto(obj.getAddress(), output);
    }
    if (obj.getAge() != null) {
      output.writeInt32(2, obj.getAge());
    }
    if (obj.getBoolField() != null) {
      output.writeBool(3, obj.getBoolField());
    }
    if (obj.getCharField() != null) {
      output.writeInt32(4, obj.getCharField());
    }
    if (obj.getDoubleField() != null) {
      output.writeDouble(5, obj.getDoubleField());
    }
    if (obj.getIntegerListField() != null) {
    }
    if (obj.getIntegerValueMap() != null) {
    }
    if (obj.getLongField() != null) {
      output.writeInt64(8, obj.getLongField());
    }
    if (obj.getShortField() != null) {
      output.writeInt32(9, obj.getShortField());
    }
    if (obj.getStringValueMap() != null) {
    }
    if (obj.getUserName() != null) {
      output.writeString(11, obj.getUserName());
    }
  }

  public static int computeSize2(User obj, int[] cache, final int baseIndex) {
    System.out.println("computing size for " + obj);
    int size = 0;
    int index = baseIndex + 1;
    if (obj.getAddress() != null) {
      size += CodedOutputStream.computeUInt32SizeNoTag(10);
      int savedIndex = index;
      index = AddressProtoConverter.computeSize2(obj.getAddress(), cache, index);
      int dataSize = cache[savedIndex];
      size += CodedOutputStream.computeUInt32SizeNoTag(dataSize);
      size += dataSize;
    }
    if (obj.getAge() != null) {
      size += CodedOutputStream.computeInt32Size(2, obj.getAge());
    }
    if (obj.getBoolField() != null) {
      size += CodedOutputStream.computeBoolSize(3, obj.getBoolField());
    }
    if (obj.getCharField() != null) {
      size += CodedOutputStream.computeInt32Size(4, obj.getCharField());
    }
    if (obj.getDoubleField() != null) {
      size += CodedOutputStream.computeDoubleSize(5, obj.getDoubleField());
    }
    if (obj.getIntegerListField() != null) {
    }
    if (obj.getIntegerValueMap() != null) {
    }
    if (obj.getLongField() != null) {
      size += CodedOutputStream.computeInt64Size(8, obj.getLongField());
    }
    if (obj.getShortField() != null) {
      size += CodedOutputStream.computeInt32Size(9, obj.getShortField());
    }
    if (obj.getStringValueMap() != null) {
    }
    if (obj.getUserName() != null) {
      size += CodedOutputStream.computeStringSize(11, obj.getUserName());
    }
    cache[baseIndex] = size;
    return index;
  }

}
