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
import io.vertx.core.proto.*;

public class UserProtoConverter {

  public static void fromProto(CodedInputStream input, User obj) throws IOException {
    int tag;
    while ((tag = input.readTag()) != 0) {
      switch (tag) {
        case 10: {
          int length = input.readUInt32();
          int limit = input.pushLimit(length);
          Address nested = new Address();
          AddressProtoConverter.fromProto(input, nested);
          obj.setAddress(nested);
          input.popLimit(limit);
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
          int length = input.readUInt32();
          int limit = input.pushLimit(length);
          obj.setInstantField(InstantProtoConverter.fromProto(input));
          input.popLimit(limit);
          break;
        }
        case 58: {
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
        case 66: {
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
        case 74: {
          int length = input.readUInt32();
          int limit = input.pushLimit(length);
          obj.setJsonObjectField(JsonObjectProtoConverter.fromProto(input));
          input.popLimit(limit);
          break;
        }
        case 80: {
          obj.setLongField(input.readInt64());
          break;
        }
        case 88: {
          obj.setPrimitiveInt(input.readInt32());
          break;
        }
        case 96: {
          obj.setShortField((short) input.readInt32());
          break;
        }
        case 106: {
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
        case 114: {
          int length = input.readUInt32();
          int limit = input.pushLimit(length);
          Address nested = new Address();
          AddressProtoConverter.fromProto(input, nested);
          if (obj.getStructListField() == null) {
            obj.setStructListField(new ArrayList<>());
          }
          obj.getStructListField().add(nested);
          input.popLimit(limit);
          break;
        }
        case 122: {
          int length = input.readUInt32();
          int limit = input.pushLimit(length);
          Map<String, Address> map = obj.getStructValueMap();
          if (map == null) {
            map = new HashMap<>();
          }
          input.readTag();
          String key = input.readString();
          input.readTag();
          int vlength = input.readUInt32();
          int vlimit = input.pushLimit(vlength);
          Address value = new Address();
          AddressProtoConverter.fromProto(input, value);
          map.put(key, value);
          obj.setStructValueMap(map);
          input.popLimit(vlimit);
          input.popLimit(limit);
          break;
        }
        case 130: {
          obj.setUserName(input.readString());
          break;
        }
        case 138: {
          int length = input.readUInt32();
          int limit = input.pushLimit(length);
          obj.setZonedDateTimeField(ZonedDateTimeProtoConverter.fromProto(input));
          input.popLimit(limit);
          break;
        }
        case 146: {
          int length = input.readUInt32();
          int limit = input.pushLimit(length);
          if (obj.getZonedDateTimeListField() == null) {
            obj.setZonedDateTimeListField(new ArrayList<>());
          }
          obj.getZonedDateTimeListField().add(ZonedDateTimeProtoConverter.fromProto(input));
          input.popLimit(limit);
          break;
        }
        case 154: {
          int length = input.readUInt32();
          int limit = input.pushLimit(length);
          Map<String, ZonedDateTime> map = obj.getZonedDateTimeValueMap();
          if (map == null) {
            map = new HashMap<>();
          }
          input.readTag();
          String key = input.readString();
          input.readTag();
          int vlength = input.readUInt32();
          int vlimit = input.pushLimit(vlength);
          map.put(key, ZonedDateTimeProtoConverter.fromProto(input));
          obj.setZonedDateTimeValueMap(map);
          input.popLimit(vlimit);
          input.popLimit(limit);
          break;
        }
      }
    }
  }

  public static void toProto(User obj, CodedOutputStream output) throws IOException {
    int[] cache = new int[100];
    UserProtoConverter.computeSize(obj, cache, 0);
    UserProtoConverter.toProto(obj, output, cache, 0);
  }

  public static int toProto(User obj, CodedOutputStream output, int[] cache, int index) throws IOException {
    index = index + 1;
    if (obj.getAddress() != null) {
      output.writeUInt32NoTag(10);
      output.writeUInt32NoTag(cache[index]);
      index = AddressProtoConverter.toProto(obj.getAddress(), output, cache, index);
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
    if (obj.getInstantField() != null) {
      output.writeUInt32NoTag(50);
      output.writeUInt32NoTag(InstantProtoConverter.computeSize(obj.getInstantField()));
      InstantProtoConverter.toProto(obj.getInstantField(), output);
    }
    if (obj.getIntegerListField() != null) {
      // list | tag | data size | value[0] | value[1] | value[2] |
      if (obj.getIntegerListField().size() > 0) {
        output.writeUInt32NoTag(58);
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
      // map[0] | tag | data size | key | value |
      // map[1] | tag | data size | key | value |
      for (Map.Entry<String, Integer> entry : obj.getIntegerValueMap().entrySet()) {
        output.writeUInt32NoTag(66);
        // calculate data size
        int dataSize = 0;
        dataSize += CodedOutputStream.computeStringSize(1, entry.getKey());
        dataSize += CodedOutputStream.computeInt32Size(2, entry.getValue());
        // key
        output.writeUInt32NoTag(dataSize);
        // value
        output.writeString(1, entry.getKey());
        output.writeInt32(2, entry.getValue());
      }
    }
    if (obj.getJsonObjectField() != null) {
      output.writeUInt32NoTag(74);
      output.writeUInt32NoTag(JsonObjectProtoConverter.computeSize(obj.getJsonObjectField()));
      JsonObjectProtoConverter.toProto(obj.getJsonObjectField(), output);
    }
    if (obj.getLongField() != null) {
      output.writeInt64(10, obj.getLongField());
    }
    if (obj.getPrimitiveInt() != 0){
      output.writeInt32(11, obj.getPrimitiveInt());
    }
    if (obj.getShortField() != null) {
      output.writeInt32(12, obj.getShortField());
    }
    if (obj.getStringValueMap() != null) {
      // map[0] | tag | data size | key | value |
      // map[1] | tag | data size | key | value |
      for (Map.Entry<String, String> entry : obj.getStringValueMap().entrySet()) {
        output.writeUInt32NoTag(106);
        // calculate data size
        int dataSize = 0;
        dataSize += CodedOutputStream.computeStringSize(1, entry.getKey());
        dataSize += CodedOutputStream.computeStringSize(2, entry.getValue());
        // key
        output.writeUInt32NoTag(dataSize);
        // value
        output.writeString(1, entry.getKey());
        output.writeString(2, entry.getValue());
      }
    }
    if (obj.getStructListField() != null) {
      // list[0] | tag | data size | value |
      // list[1] | tag | data size | value |
      for (Address element: obj.getStructListField()) {
        output.writeUInt32NoTag(114);
        output.writeUInt32NoTag(cache[index]);
        index = AddressProtoConverter.toProto(element, output, cache, index);
      }
    }
    if (obj.getStructValueMap() != null) {
      // map[0] | tag | data size | key | value |
      // map[1] | tag | data size | key | value |
      for (Map.Entry<String, Address> entry : obj.getStructValueMap().entrySet()) {
        output.writeUInt32NoTag(122);
        // calculate data size
        int elementSize = cache[index];
        int dataSize = 0;
        dataSize += CodedOutputStream.computeStringSize(1, entry.getKey());
        dataSize += CodedOutputStream.computeInt32SizeNoTag(18);
        dataSize += CodedOutputStream.computeInt32SizeNoTag(elementSize);
        dataSize += elementSize;
        // key
        output.writeUInt32NoTag(dataSize);
        // value
        output.writeString(1, entry.getKey());
        output.writeUInt32NoTag(18);
        output.writeUInt32NoTag(elementSize);
        index = AddressProtoConverter.toProto(entry.getValue(), output, cache, index);
      }
    }
    if (obj.getUserName() != null) {
      output.writeString(16, obj.getUserName());
    }
    if (obj.getZonedDateTimeField() != null) {
      output.writeUInt32NoTag(138);
      output.writeUInt32NoTag(ZonedDateTimeProtoConverter.computeSize(obj.getZonedDateTimeField()));
      ZonedDateTimeProtoConverter.toProto(obj.getZonedDateTimeField(), output);
    }
    if (obj.getZonedDateTimeListField() != null) {
      // list[0] | tag | data size | value |
      // list[1] | tag | data size | value |
      for (ZonedDateTime element: obj.getZonedDateTimeListField()) {
        output.writeUInt32NoTag(146);
        output.writeUInt32NoTag(ZonedDateTimeProtoConverter.computeSize(element));
        ZonedDateTimeProtoConverter.toProto(element, output);
      }
    }
    if (obj.getZonedDateTimeValueMap() != null) {
      // map[0] | tag | data size | key | value |
      // map[1] | tag | data size | key | value |
      for (Map.Entry<String, ZonedDateTime> entry : obj.getZonedDateTimeValueMap().entrySet()) {
        output.writeUInt32NoTag(154);
        // calculate data size
        int elementSize = ZonedDateTimeProtoConverter.computeSize(entry.getValue());
        int dataSize = 0;
        dataSize += CodedOutputStream.computeStringSize(1, entry.getKey());
        dataSize += CodedOutputStream.computeInt32SizeNoTag(18);
        dataSize += CodedOutputStream.computeInt32SizeNoTag(elementSize);
        dataSize += elementSize;
        // key
        output.writeUInt32NoTag(dataSize);
        // value
        output.writeString(1, entry.getKey());
        output.writeUInt32NoTag(18);
        output.writeUInt32NoTag(elementSize);
        ZonedDateTimeProtoConverter.toProto(entry.getValue(), output);
      }
    }
    return index;
  }

  public static int computeSize(User obj) {
    int[] cache = new int[100];
    UserProtoConverter.computeSize(obj, cache, 0);
    return cache[0];
  }

  public static int computeSize(User obj, int[] cache, final int baseIndex) {
    int size = 0;
    int index = baseIndex + 1;
    if (obj.getAddress() != null) {
      size += CodedOutputStream.computeUInt32SizeNoTag(10);
      int savedIndex = index;
      index = AddressProtoConverter.computeSize(obj.getAddress(), cache, index);
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
    if (obj.getInstantField() != null) {
      size += CodedOutputStream.computeUInt32SizeNoTag(50);
      int dataSize = InstantProtoConverter.computeSize(obj.getInstantField());
      size += CodedOutputStream.computeUInt32SizeNoTag(dataSize);
      size += dataSize;
    }
    if (obj.getIntegerListField() != null) {
      // list | tag | data size | value[0] | value[1] | value[2] |
      if (obj.getIntegerListField().size() > 0) {
        size += CodedOutputStream.computeUInt32SizeNoTag(58);
        int dataSize = 0;
        for (Integer element: obj.getIntegerListField()) {
          dataSize += CodedOutputStream.computeInt32SizeNoTag(element);
        }
        size += CodedOutputStream.computeUInt32SizeNoTag(dataSize);
        size += dataSize;
      }
    }
    if (obj.getIntegerValueMap() != null) {
      // map[0] | tag | data size | key | value |
      // map[1] | tag | data size | key | value |
      for (Map.Entry<String, Integer> entry : obj.getIntegerValueMap().entrySet()) {
        size += CodedOutputStream.computeUInt32SizeNoTag(66);
        int dataSize = 0;
        dataSize += CodedOutputStream.computeStringSize(1, entry.getKey());
        dataSize += CodedOutputStream.computeInt32Size(2, entry.getValue());
        size += CodedOutputStream.computeUInt32SizeNoTag(dataSize);
        size += dataSize;
      }
    }
    if (obj.getJsonObjectField() != null) {
      size += CodedOutputStream.computeUInt32SizeNoTag(74);
      int dataSize = JsonObjectProtoConverter.computeSize(obj.getJsonObjectField());
      size += CodedOutputStream.computeUInt32SizeNoTag(dataSize);
      size += dataSize;
    }
    if (obj.getLongField() != null) {
      size += CodedOutputStream.computeInt64Size(10, obj.getLongField());
    }
    if (obj.getPrimitiveInt() != 0){
      size += CodedOutputStream.computeInt32Size(11, obj.getPrimitiveInt());
    }
    if (obj.getShortField() != null) {
      size += CodedOutputStream.computeInt32Size(12, obj.getShortField());
    }
    if (obj.getStringValueMap() != null) {
      // map[0] | tag | data size | key | value |
      // map[1] | tag | data size | key | value |
      for (Map.Entry<String, String> entry : obj.getStringValueMap().entrySet()) {
        size += CodedOutputStream.computeUInt32SizeNoTag(106);
        int dataSize = 0;
        dataSize += CodedOutputStream.computeStringSize(1, entry.getKey());
        dataSize += CodedOutputStream.computeStringSize(2, entry.getValue());
        size += CodedOutputStream.computeUInt32SizeNoTag(dataSize);
        size += dataSize;
      }
    }
    if (obj.getStructListField() != null) {
      // list[0] | tag | data size | value |
      // list[1] | tag | data size | value |
      if (obj.getStructListField().size() > 0) {
        for (Address element: obj.getStructListField()) {
          size += CodedOutputStream.computeUInt32SizeNoTag(114);
          int savedIndex = index;
          index = AddressProtoConverter.computeSize(element, cache, index);
          int dataSize = cache[savedIndex];
          size += CodedOutputStream.computeUInt32SizeNoTag(dataSize);
          size += dataSize;
        }
      }
    }
    if (obj.getStructValueMap() != null) {
        // map[0] | tag | data size | key | value |
        // map[1] | tag | data size | key | value |
      for (Map.Entry<String, Address> entry : obj.getStructValueMap().entrySet()) {
        size += CodedOutputStream.computeUInt32SizeNoTag(122);
        // calculate data size
        int dataSize = 0;
        // key
        dataSize += CodedOutputStream.computeStringSize(1, entry.getKey());
        // value
        int savedIndex = index;
        index = AddressProtoConverter.computeSize(entry.getValue(), cache, index);
        int elementSize = cache[savedIndex];
        dataSize += CodedOutputStream.computeInt32SizeNoTag(18);
        dataSize += CodedOutputStream.computeInt32SizeNoTag(elementSize);
        dataSize += elementSize;
        // data size
        size += CodedOutputStream.computeUInt32SizeNoTag(dataSize);
        size += dataSize;
      }
    }
    if (obj.getUserName() != null) {
      size += CodedOutputStream.computeStringSize(16, obj.getUserName());
    }
    if (obj.getZonedDateTimeField() != null) {
      size += CodedOutputStream.computeUInt32SizeNoTag(138);
      int dataSize = ZonedDateTimeProtoConverter.computeSize(obj.getZonedDateTimeField());
      size += CodedOutputStream.computeUInt32SizeNoTag(dataSize);
      size += dataSize;
    }
    if (obj.getZonedDateTimeListField() != null) {
      // list[0] | tag | data size | value |
      // list[1] | tag | data size | value |
      if (obj.getZonedDateTimeListField().size() > 0) {
        for (ZonedDateTime element: obj.getZonedDateTimeListField()) {
          size += CodedOutputStream.computeUInt32SizeNoTag(146);
          int dataSize = ZonedDateTimeProtoConverter.computeSize(element);
          size += CodedOutputStream.computeUInt32SizeNoTag(dataSize);
          size += dataSize;
        }
      }
    }
    if (obj.getZonedDateTimeValueMap() != null) {
        // map[0] | tag | data size | key | value |
        // map[1] | tag | data size | key | value |
      for (Map.Entry<String, ZonedDateTime> entry : obj.getZonedDateTimeValueMap().entrySet()) {
        size += CodedOutputStream.computeUInt32SizeNoTag(154);
        // calculate data size
        int dataSize = 0;
        // key
        dataSize += CodedOutputStream.computeStringSize(1, entry.getKey());
        // value
        int elementSize = ZonedDateTimeProtoConverter.computeSize(entry.getValue());
        dataSize += CodedOutputStream.computeInt32SizeNoTag(18);
        dataSize += CodedOutputStream.computeInt32SizeNoTag(elementSize);
        dataSize += elementSize;
        // data size
        size += CodedOutputStream.computeUInt32SizeNoTag(dataSize);
        size += dataSize;
      }
    }
    cache[baseIndex] = size;
    return index;
  }

}
