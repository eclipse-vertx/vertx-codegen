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
import io.vertx.core.ExpandableArray;
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
          obj.setByteField((byte) input.readInt32());
          break;
        }
        case 40: {
          obj.setCharField((char) input.readInt32());
          break;
        }
        case 49: {
          obj.setDoubleField(input.readDouble());
          break;
        }
        case 58: {
          int length = input.readUInt32();
          int limit = input.pushLimit(length);
          obj.setInstantField(InstantProtoConverter.fromProto(input));
          input.popLimit(limit);
          break;
        }
        case 66: {
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
        case 74: {
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
        case 82: {
          int length = input.readUInt32();
          int limit = input.pushLimit(length);
          obj.setJsonObjectField(JsonObjectProtoConverter.fromProto(input));
          input.popLimit(limit);
          break;
        }
        case 88: {
          obj.setLongField(input.readInt64());
          break;
        }
        case 96: {
          obj.setPrimitiveBoolean(input.readBool());
          break;
        }
        case 104: {
          obj.setPrimitiveByte((byte) input.readInt32());
          break;
        }
        case 112: {
          obj.setPrimitiveChar((char) input.readInt32());
          break;
        }
        case 121: {
          obj.setPrimitiveDouble(input.readDouble());
          break;
        }
        case 133: {
          obj.setPrimitiveFloat(input.readFloat());
          break;
        }
        case 136: {
          obj.setPrimitiveInt(input.readInt32());
          break;
        }
        case 144: {
          obj.setPrimitiveLong(input.readInt64());
          break;
        }
        case 152: {
          obj.setPrimitiveShort((short) input.readInt32());
          break;
        }
        case 160: {
          obj.setShortField((short) input.readInt32());
          break;
        }
        case 170: {
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
        case 178: {
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
        case 186: {
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
        case 194: {
          obj.setUserName(input.readString());
          break;
        }
        case 202: {
          int length = input.readUInt32();
          int limit = input.pushLimit(length);
          obj.setZonedDateTimeField(ZonedDateTimeProtoConverter.fromProto(input));
          input.popLimit(limit);
          break;
        }
        case 210: {
          int length = input.readUInt32();
          int limit = input.pushLimit(length);
          if (obj.getZonedDateTimeListField() == null) {
            obj.setZonedDateTimeListField(new ArrayList<>());
          }
          obj.getZonedDateTimeListField().add(ZonedDateTimeProtoConverter.fromProto(input));
          input.popLimit(limit);
          break;
        }
        case 218: {
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
    ExpandableArray cache = new ExpandableArray(16);
    UserProtoConverter.computeSize(obj, cache, 0);
    UserProtoConverter.toProto(obj, output, cache, 0);
  }

  public static int toProto(User obj, CodedOutputStream output, ExpandableArray cache, int index) throws IOException {
    index = index + 1;
    if (obj.getAddress() != null) {
      output.writeUInt32NoTag(10);
      output.writeUInt32NoTag(cache.get(index));
      index = AddressProtoConverter.toProto(obj.getAddress(), output, cache, index);
    }
    if (obj.getAge() != null) {
      output.writeInt32(2, obj.getAge());
    }
    if (obj.getBoolField() != null) {
      output.writeBool(3, obj.getBoolField());
    }
    if (obj.getByteField() != null) {
      output.writeInt32(4, obj.getByteField());
    }
    if (obj.getCharField() != null) {
      output.writeInt32(5, obj.getCharField());
    }
    if (obj.getDoubleField() != null) {
      output.writeDouble(6, obj.getDoubleField());
    }
    if (obj.getInstantField() != null) {
      output.writeUInt32NoTag(58);
      output.writeUInt32NoTag(InstantProtoConverter.computeSize(obj.getInstantField()));
      InstantProtoConverter.toProto(obj.getInstantField(), output);
    }
    if (obj.getIntegerListField() != null) {
      // list | tag | data size | value[0] | value[1] | value[2] |
      if (obj.getIntegerListField().size() > 0) {
        output.writeUInt32NoTag(66);
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
        output.writeUInt32NoTag(74);
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
      output.writeUInt32NoTag(82);
      output.writeUInt32NoTag(JsonObjectProtoConverter.computeSize(obj.getJsonObjectField()));
      JsonObjectProtoConverter.toProto(obj.getJsonObjectField(), output);
    }
    if (obj.getLongField() != null) {
      output.writeInt64(11, obj.getLongField());
    }
    if (obj.isPrimitiveBoolean()) {
      output.writeBool(12, obj.isPrimitiveBoolean());
    }
    if (obj.getPrimitiveByte() != 0) {
      output.writeInt32(13, obj.getPrimitiveByte());
    }
    if (obj.getPrimitiveChar() != 0) {
      output.writeInt32(14, obj.getPrimitiveChar());
    }
    if (obj.getPrimitiveDouble() != 0) {
      output.writeDouble(15, obj.getPrimitiveDouble());
    }
    if (obj.getPrimitiveFloat() != 0) {
      output.writeFloat(16, obj.getPrimitiveFloat());
    }
    if (obj.getPrimitiveInt() != 0) {
      output.writeInt32(17, obj.getPrimitiveInt());
    }
    if (obj.getPrimitiveLong() != 0) {
      output.writeInt64(18, obj.getPrimitiveLong());
    }
    if (obj.getPrimitiveShort() != 0) {
      output.writeInt32(19, obj.getPrimitiveShort());
    }
    if (obj.getShortField() != null) {
      output.writeInt32(20, obj.getShortField());
    }
    if (obj.getStringValueMap() != null) {
      // map[0] | tag | data size | key | value |
      // map[1] | tag | data size | key | value |
      for (Map.Entry<String, String> entry : obj.getStringValueMap().entrySet()) {
        output.writeUInt32NoTag(170);
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
        output.writeUInt32NoTag(178);
        output.writeUInt32NoTag(cache.get(index));
        index = AddressProtoConverter.toProto(element, output, cache, index);
      }
    }
    if (obj.getStructValueMap() != null) {
      // map[0] | tag | data size | key | value |
      // map[1] | tag | data size | key | value |
      for (Map.Entry<String, Address> entry : obj.getStructValueMap().entrySet()) {
        output.writeUInt32NoTag(186);
        // calculate data size
        int elementSize = cache.get(index);
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
      output.writeString(24, obj.getUserName());
    }
    if (obj.getZonedDateTimeField() != null) {
      output.writeUInt32NoTag(202);
      output.writeUInt32NoTag(ZonedDateTimeProtoConverter.computeSize(obj.getZonedDateTimeField()));
      ZonedDateTimeProtoConverter.toProto(obj.getZonedDateTimeField(), output);
    }
    if (obj.getZonedDateTimeListField() != null) {
      // list[0] | tag | data size | value |
      // list[1] | tag | data size | value |
      for (ZonedDateTime element: obj.getZonedDateTimeListField()) {
        output.writeUInt32NoTag(210);
        output.writeUInt32NoTag(ZonedDateTimeProtoConverter.computeSize(element));
        ZonedDateTimeProtoConverter.toProto(element, output);
      }
    }
    if (obj.getZonedDateTimeValueMap() != null) {
      // map[0] | tag | data size | key | value |
      // map[1] | tag | data size | key | value |
      for (Map.Entry<String, ZonedDateTime> entry : obj.getZonedDateTimeValueMap().entrySet()) {
        output.writeUInt32NoTag(218);
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
    ExpandableArray cache = new ExpandableArray(16);
    UserProtoConverter.computeSize(obj, cache, 0);
    return cache.get(0);
  }

  public static int computeSize(User obj, ExpandableArray cache, final int baseIndex) {
    int size = 0;
    int index = baseIndex + 1;
    if (obj.getAddress() != null) {
      size += CodedOutputStream.computeUInt32SizeNoTag(10);
      int savedIndex = index;
      index = AddressProtoConverter.computeSize(obj.getAddress(), cache, index);
      int dataSize = cache.get(savedIndex);
      size += CodedOutputStream.computeUInt32SizeNoTag(dataSize);
      size += dataSize;
    }
    if (obj.getAge() != null) {
      size += CodedOutputStream.computeInt32Size(2, obj.getAge());
    }
    if (obj.getBoolField() != null) {
      size += CodedOutputStream.computeBoolSize(3, obj.getBoolField());
    }
    if (obj.getByteField() != null) {
      size += CodedOutputStream.computeInt32Size(4, obj.getByteField());
    }
    if (obj.getCharField() != null) {
      size += CodedOutputStream.computeInt32Size(5, obj.getCharField());
    }
    if (obj.getDoubleField() != null) {
      size += CodedOutputStream.computeDoubleSize(6, obj.getDoubleField());
    }
    if (obj.getInstantField() != null) {
      size += CodedOutputStream.computeUInt32SizeNoTag(58);
      int dataSize = InstantProtoConverter.computeSize(obj.getInstantField());
      size += CodedOutputStream.computeUInt32SizeNoTag(dataSize);
      size += dataSize;
    }
    if (obj.getIntegerListField() != null) {
      // list | tag | data size | value[0] | value[1] | value[2] |
      if (obj.getIntegerListField().size() > 0) {
        size += CodedOutputStream.computeUInt32SizeNoTag(66);
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
        size += CodedOutputStream.computeUInt32SizeNoTag(74);
        int dataSize = 0;
        dataSize += CodedOutputStream.computeStringSize(1, entry.getKey());
        dataSize += CodedOutputStream.computeInt32Size(2, entry.getValue());
        size += CodedOutputStream.computeUInt32SizeNoTag(dataSize);
        size += dataSize;
      }
    }
    if (obj.getJsonObjectField() != null) {
      size += CodedOutputStream.computeUInt32SizeNoTag(82);
      int dataSize = JsonObjectProtoConverter.computeSize(obj.getJsonObjectField());
      size += CodedOutputStream.computeUInt32SizeNoTag(dataSize);
      size += dataSize;
    }
    if (obj.getLongField() != null) {
      size += CodedOutputStream.computeInt64Size(11, obj.getLongField());
    }
    if (obj.isPrimitiveBoolean()) {
      size += CodedOutputStream.computeBoolSize(12, obj.isPrimitiveBoolean());
    }
    if (obj.getPrimitiveByte() != 0) {
      size += CodedOutputStream.computeInt32Size(13, obj.getPrimitiveByte());
    }
    if (obj.getPrimitiveChar() != 0) {
      size += CodedOutputStream.computeInt32Size(14, obj.getPrimitiveChar());
    }
    if (obj.getPrimitiveDouble() != 0) {
      size += CodedOutputStream.computeDoubleSize(15, obj.getPrimitiveDouble());
    }
    if (obj.getPrimitiveFloat() != 0) {
      size += CodedOutputStream.computeFloatSize(16, obj.getPrimitiveFloat());
    }
    if (obj.getPrimitiveInt() != 0) {
      size += CodedOutputStream.computeInt32Size(17, obj.getPrimitiveInt());
    }
    if (obj.getPrimitiveLong() != 0) {
      size += CodedOutputStream.computeInt64Size(18, obj.getPrimitiveLong());
    }
    if (obj.getPrimitiveShort() != 0) {
      size += CodedOutputStream.computeInt32Size(19, obj.getPrimitiveShort());
    }
    if (obj.getShortField() != null) {
      size += CodedOutputStream.computeInt32Size(20, obj.getShortField());
    }
    if (obj.getStringValueMap() != null) {
      // map[0] | tag | data size | key | value |
      // map[1] | tag | data size | key | value |
      for (Map.Entry<String, String> entry : obj.getStringValueMap().entrySet()) {
        size += CodedOutputStream.computeUInt32SizeNoTag(170);
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
          size += CodedOutputStream.computeUInt32SizeNoTag(178);
          int savedIndex = index;
          index = AddressProtoConverter.computeSize(element, cache, index);
          int dataSize = cache.get(savedIndex);
          size += CodedOutputStream.computeUInt32SizeNoTag(dataSize);
          size += dataSize;
        }
      }
    }
    if (obj.getStructValueMap() != null) {
        // map[0] | tag | data size | key | value |
        // map[1] | tag | data size | key | value |
      for (Map.Entry<String, Address> entry : obj.getStructValueMap().entrySet()) {
        size += CodedOutputStream.computeUInt32SizeNoTag(186);
        // calculate data size
        int dataSize = 0;
        // key
        dataSize += CodedOutputStream.computeStringSize(1, entry.getKey());
        // value
        int savedIndex = index;
        index = AddressProtoConverter.computeSize(entry.getValue(), cache, index);
        int elementSize = cache.get(savedIndex);
        dataSize += CodedOutputStream.computeInt32SizeNoTag(18);
        dataSize += CodedOutputStream.computeInt32SizeNoTag(elementSize);
        dataSize += elementSize;
        // data size
        size += CodedOutputStream.computeUInt32SizeNoTag(dataSize);
        size += dataSize;
      }
    }
    if (obj.getUserName() != null) {
      size += CodedOutputStream.computeStringSize(24, obj.getUserName());
    }
    if (obj.getZonedDateTimeField() != null) {
      size += CodedOutputStream.computeUInt32SizeNoTag(202);
      int dataSize = ZonedDateTimeProtoConverter.computeSize(obj.getZonedDateTimeField());
      size += CodedOutputStream.computeUInt32SizeNoTag(dataSize);
      size += dataSize;
    }
    if (obj.getZonedDateTimeListField() != null) {
      // list[0] | tag | data size | value |
      // list[1] | tag | data size | value |
      if (obj.getZonedDateTimeListField().size() > 0) {
        for (ZonedDateTime element: obj.getZonedDateTimeListField()) {
          size += CodedOutputStream.computeUInt32SizeNoTag(210);
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
        size += CodedOutputStream.computeUInt32SizeNoTag(218);
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
    cache.set(baseIndex, size);
    return index;
  }

}
