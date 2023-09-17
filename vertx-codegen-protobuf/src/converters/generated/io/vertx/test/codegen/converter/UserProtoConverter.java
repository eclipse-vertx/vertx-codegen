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

public class UserProtoConverter {

  public static void fromProto(CodedInputStream input, User obj) throws IOException {
    int tag;
    while ((tag = input.readTag()) != 0) {
      switch (tag) {
        case 10: {
          obj.setUserName(input.readString());
          break;
        }
        case 16: {
          obj.setAge(input.readInt32());
          break;
        }
        case 26: {
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
        case 34: {
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
        case 42: {
          int length = input.readUInt32();
          int limit = input.pushLimit(length);
          if (obj.getZonedDateTimeListField() == null) {
            obj.setZonedDateTimeListField(new ArrayList<>());
          }
          obj.getZonedDateTimeListField().add(ZonedDateTimeProtoConverter.fromProto(input));
          input.popLimit(limit);
          break;
        }
        case 50: {
          int length = input.readUInt32();
          int limit = input.pushLimit(length);
          if (obj.getJsonListField() == null) {
            obj.setJsonListField(new ArrayList<>());
          }
          obj.getJsonListField().add(VertxStructProtoConverter.fromProto(input));
          input.popLimit(limit);
          break;
        }
        case 58: {
          int length = input.readUInt32();
          int limit = input.pushLimit(length);
          Address nested = new Address();
          AddressProtoConverter.fromProto(input, nested);
          obj.setAddress(nested);
          input.popLimit(limit);
          break;
        }
        case 64: {
          obj.setByteField((byte) input.readInt32());
          break;
        }
        case 73: {
          obj.setDoubleField(input.readDouble());
          break;
        }
        case 85: {
          obj.setFloatField(input.readFloat());
          break;
        }
        case 88: {
          obj.setLongField(input.readInt64());
          break;
        }
        case 96: {
          obj.setBoolField(input.readBool());
          break;
        }
        case 104: {
          obj.setShortField((short) input.readInt32());
          break;
        }
        case 112: {
          obj.setCharField((char) input.readInt32());
          break;
        }
        case 122: {
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
        case 130: {
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
        case 138: {
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
        case 146: {
          int length = input.readUInt32();
          int limit = input.pushLimit(length);
          Map<String, JsonObject> map = obj.getJsonValueMap();
          if (map == null) {
            map = new HashMap<>();
          }
          input.readTag();
          String key = input.readString();
          input.readTag();
          int vlength = input.readUInt32();
          int vlimit = input.pushLimit(vlength);
          map.put(key, VertxStructProtoConverter.fromProto(input));
          obj.setJsonValueMap(map);
          input.popLimit(vlimit);
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
        case 162: {
          int length = input.readUInt32();
          int limit = input.pushLimit(length);
          obj.setZonedDateTimeField(ZonedDateTimeProtoConverter.fromProto(input));
          input.popLimit(limit);
          break;
        }
        case 170: {
          int length = input.readUInt32();
          int limit = input.pushLimit(length);
          obj.setInstantField(InstantProtoConverter.fromProto(input));
          input.popLimit(limit);
          break;
        }
        case 178: {
          int length = input.readUInt32();
          int limit = input.pushLimit(length);
          obj.setJsonObjectField(VertxStructProtoConverter.fromProto(input));
          input.popLimit(limit);
          break;
        }
        case 186: {
          int length = input.readUInt32();
          int limit = input.pushLimit(length);
          obj.setJsonArrayField(VertxStructListProtoConverter.fromProto(input));
          input.popLimit(limit);
          break;
        }
        case 192: {
          obj.setPrimitiveBoolean(input.readBool());
          break;
        }
        case 200: {
          obj.setPrimitiveByte((byte) input.readInt32());
          break;
        }
        case 208: {
          obj.setPrimitiveShort((short) input.readInt32());
          break;
        }
        case 216: {
          obj.setPrimitiveInt(input.readInt32());
          break;
        }
        case 224: {
          obj.setPrimitiveLong(input.readInt64());
          break;
        }
        case 237: {
          obj.setPrimitiveFloat(input.readFloat());
          break;
        }
        case 241: {
          obj.setPrimitiveDouble(input.readDouble());
          break;
        }
        case 248: {
          obj.setPrimitiveChar((char) input.readInt32());
          break;
        }
        case 256: {
          switch (input.readEnum()) {
            case 0:
              obj.setEnumType(EnumType.A);
              break;
            case 1:
              obj.setEnumType(EnumType.B);
              break;
            case 2:
              obj.setEnumType(EnumType.C);
              break;
          }
          break;
        }
      }
    }
  }

  public static void toProto(User obj, CodedOutputStream output) throws IOException {
    ExpandableIntArray cache = new ExpandableIntArray(16);
    UserProtoConverter.computeSize(obj, cache, 0);
    UserProtoConverter.toProto(obj, output, cache, 0);
  }

  public static int toProto(User obj, CodedOutputStream output, ExpandableIntArray cache, int index) throws IOException {
    index = index + 1;
    if (obj.getUserName() != null) {
      output.writeString(1, obj.getUserName());
    }
    if (obj.getAge() != null) {
      output.writeInt32(2, obj.getAge());
    }
    if (obj.getIntegerListField() != null) {
      // list | tag | data size | value[0] | value[1] | value[2] |
      if (obj.getIntegerListField().size() > 0) {
        output.writeUInt32NoTag(26);
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
    if (obj.getStructListField() != null) {
      // list[0] | tag | data size | value |
      // list[1] | tag | data size | value |
      for (Address element: obj.getStructListField()) {
        output.writeUInt32NoTag(34);
        output.writeUInt32NoTag(cache.get(index));
        index = AddressProtoConverter.toProto(element, output, cache, index);
      }
    }
    if (obj.getZonedDateTimeListField() != null) {
      // list[0] | tag | data size | value |
      // list[1] | tag | data size | value |
      for (ZonedDateTime element: obj.getZonedDateTimeListField()) {
        output.writeUInt32NoTag(42);
        output.writeUInt32NoTag(ZonedDateTimeProtoConverter.computeSize(element));
        ZonedDateTimeProtoConverter.toProto(element, output);
      }
    }
    if (obj.getJsonListField() != null) {
      // list[0] | tag | data size | value |
      // list[1] | tag | data size | value |
      for (JsonObject element: obj.getJsonListField()) {
        output.writeUInt32NoTag(50);
        output.writeUInt32NoTag(VertxStructProtoConverter.computeSize(element));
        VertxStructProtoConverter.toProto(element, output);
      }
    }
    if (obj.getAddress() != null) {
      output.writeUInt32NoTag(58);
      output.writeUInt32NoTag(cache.get(index));
      index = AddressProtoConverter.toProto(obj.getAddress(), output, cache, index);
    }
    if (obj.getByteField() != null) {
      output.writeInt32(8, obj.getByteField());
    }
    if (obj.getDoubleField() != null) {
      output.writeDouble(9, obj.getDoubleField());
    }
    if (obj.getFloatField() != null) {
      output.writeFloat(10, obj.getFloatField());
    }
    if (obj.getLongField() != null) {
      output.writeInt64(11, obj.getLongField());
    }
    if (obj.getBoolField() != null) {
      output.writeBool(12, obj.getBoolField());
    }
    if (obj.getShortField() != null) {
      output.writeInt32(13, obj.getShortField());
    }
    if (obj.getCharField() != null) {
      output.writeInt32(14, obj.getCharField());
    }
    if (obj.getStringValueMap() != null) {
      // map[0] | tag | data size | key | value |
      // map[1] | tag | data size | key | value |
      for (Map.Entry<String, String> entry : obj.getStringValueMap().entrySet()) {
        output.writeUInt32NoTag(122);
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
    if (obj.getIntegerValueMap() != null) {
      // map[0] | tag | data size | key | value |
      // map[1] | tag | data size | key | value |
      for (Map.Entry<String, Integer> entry : obj.getIntegerValueMap().entrySet()) {
        output.writeUInt32NoTag(130);
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
    if (obj.getStructValueMap() != null) {
      // map[0] | tag | data size | key | value |
      // map[1] | tag | data size | key | value |
      for (Map.Entry<String, Address> entry : obj.getStructValueMap().entrySet()) {
        output.writeUInt32NoTag(138);
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
    if (obj.getJsonValueMap() != null) {
      // map[0] | tag | data size | key | value |
      // map[1] | tag | data size | key | value |
      for (Map.Entry<String, JsonObject> entry : obj.getJsonValueMap().entrySet()) {
        output.writeUInt32NoTag(146);
        // calculate data size
        int elementSize = VertxStructProtoConverter.computeSize(entry.getValue());
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
        VertxStructProtoConverter.toProto(entry.getValue(), output);
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
    if (obj.getZonedDateTimeField() != null) {
      output.writeUInt32NoTag(162);
      output.writeUInt32NoTag(ZonedDateTimeProtoConverter.computeSize(obj.getZonedDateTimeField()));
      ZonedDateTimeProtoConverter.toProto(obj.getZonedDateTimeField(), output);
    }
    if (obj.getInstantField() != null) {
      output.writeUInt32NoTag(170);
      output.writeUInt32NoTag(InstantProtoConverter.computeSize(obj.getInstantField()));
      InstantProtoConverter.toProto(obj.getInstantField(), output);
    }
    if (obj.getJsonObjectField() != null) {
      output.writeUInt32NoTag(178);
      output.writeUInt32NoTag(VertxStructProtoConverter.computeSize(obj.getJsonObjectField()));
      VertxStructProtoConverter.toProto(obj.getJsonObjectField(), output);
    }
    if (obj.getJsonArrayField() != null) {
      output.writeUInt32NoTag(186);
      output.writeUInt32NoTag(VertxStructListProtoConverter.computeSize(obj.getJsonArrayField()));
      VertxStructListProtoConverter.toProto(obj.getJsonArrayField(), output);
    }
    if (obj.isPrimitiveBoolean()) {
      output.writeBool(24, obj.isPrimitiveBoolean());
    }
    if (obj.getPrimitiveByte() != 0) {
      output.writeInt32(25, obj.getPrimitiveByte());
    }
    if (obj.getPrimitiveShort() != 0) {
      output.writeInt32(26, obj.getPrimitiveShort());
    }
    if (obj.getPrimitiveInt() != 0) {
      output.writeInt32(27, obj.getPrimitiveInt());
    }
    if (obj.getPrimitiveLong() != 0) {
      output.writeInt64(28, obj.getPrimitiveLong());
    }
    if (obj.getPrimitiveFloat() != 0) {
      output.writeFloat(29, obj.getPrimitiveFloat());
    }
    if (obj.getPrimitiveDouble() != 0) {
      output.writeDouble(30, obj.getPrimitiveDouble());
    }
    if (obj.getPrimitiveChar() != 0) {
      output.writeInt32(31, obj.getPrimitiveChar());
    }
    if (obj.getEnumType() != null) {
      switch (obj.getEnumType()) {
        case A:
          output.writeEnum(32, 0);
          break;
        case B:
          output.writeEnum(32, 1);
          break;
        case C:
          output.writeEnum(32, 2);
          break;
      }
    }
    return index;
  }

  public static int computeSize(User obj) {
    ExpandableIntArray cache = new ExpandableIntArray(16);
    UserProtoConverter.computeSize(obj, cache, 0);
    return cache.get(0);
  }

  public static int computeSize(User obj, ExpandableIntArray cache, final int baseIndex) {
    int size = 0;
    int index = baseIndex + 1;
    if (obj.getUserName() != null) {
      size += CodedOutputStream.computeStringSize(1, obj.getUserName());
    }
    if (obj.getAge() != null) {
      size += CodedOutputStream.computeInt32Size(2, obj.getAge());
    }
    if (obj.getIntegerListField() != null) {
      // list | tag | data size | value[0] | value[1] | value[2] |
      if (obj.getIntegerListField().size() > 0) {
        size += CodedOutputStream.computeUInt32SizeNoTag(26);
        int dataSize = 0;
        for (Integer element: obj.getIntegerListField()) {
          dataSize += CodedOutputStream.computeInt32SizeNoTag(element);
        }
        size += CodedOutputStream.computeUInt32SizeNoTag(dataSize);
        size += dataSize;
      }
    }
    if (obj.getStructListField() != null) {
      // list[0] | tag | data size | value |
      // list[1] | tag | data size | value |
      if (obj.getStructListField().size() > 0) {
        for (Address element: obj.getStructListField()) {
          size += CodedOutputStream.computeUInt32SizeNoTag(34);
          int savedIndex = index;
          index = AddressProtoConverter.computeSize(element, cache, index);
          int dataSize = cache.get(savedIndex);
          size += CodedOutputStream.computeUInt32SizeNoTag(dataSize);
          size += dataSize;
        }
      }
    }
    if (obj.getZonedDateTimeListField() != null) {
      // list[0] | tag | data size | value |
      // list[1] | tag | data size | value |
      if (obj.getZonedDateTimeListField().size() > 0) {
        for (ZonedDateTime element: obj.getZonedDateTimeListField()) {
          size += CodedOutputStream.computeUInt32SizeNoTag(42);
          int dataSize = ZonedDateTimeProtoConverter.computeSize(element);
          size += CodedOutputStream.computeUInt32SizeNoTag(dataSize);
          size += dataSize;
        }
      }
    }
    if (obj.getJsonListField() != null) {
      // list[0] | tag | data size | value |
      // list[1] | tag | data size | value |
      if (obj.getJsonListField().size() > 0) {
        for (JsonObject element: obj.getJsonListField()) {
          size += CodedOutputStream.computeUInt32SizeNoTag(50);
          int dataSize = VertxStructProtoConverter.computeSize(element);
          size += CodedOutputStream.computeUInt32SizeNoTag(dataSize);
          size += dataSize;
        }
      }
    }
    if (obj.getAddress() != null) {
      size += CodedOutputStream.computeUInt32SizeNoTag(58);
      int savedIndex = index;
      index = AddressProtoConverter.computeSize(obj.getAddress(), cache, index);
      int dataSize = cache.get(savedIndex);
      size += CodedOutputStream.computeUInt32SizeNoTag(dataSize);
      size += dataSize;
    }
    if (obj.getByteField() != null) {
      size += CodedOutputStream.computeInt32Size(8, obj.getByteField());
    }
    if (obj.getDoubleField() != null) {
      size += CodedOutputStream.computeDoubleSize(9, obj.getDoubleField());
    }
    if (obj.getFloatField() != null) {
      size += CodedOutputStream.computeFloatSize(10, obj.getFloatField());
    }
    if (obj.getLongField() != null) {
      size += CodedOutputStream.computeInt64Size(11, obj.getLongField());
    }
    if (obj.getBoolField() != null) {
      size += CodedOutputStream.computeBoolSize(12, obj.getBoolField());
    }
    if (obj.getShortField() != null) {
      size += CodedOutputStream.computeInt32Size(13, obj.getShortField());
    }
    if (obj.getCharField() != null) {
      size += CodedOutputStream.computeInt32Size(14, obj.getCharField());
    }
    if (obj.getStringValueMap() != null) {
      // map[0] | tag | data size | key | value |
      // map[1] | tag | data size | key | value |
      for (Map.Entry<String, String> entry : obj.getStringValueMap().entrySet()) {
        size += CodedOutputStream.computeUInt32SizeNoTag(122);
        int dataSize = 0;
        dataSize += CodedOutputStream.computeStringSize(1, entry.getKey());
        dataSize += CodedOutputStream.computeStringSize(2, entry.getValue());
        size += CodedOutputStream.computeUInt32SizeNoTag(dataSize);
        size += dataSize;
      }
    }
    if (obj.getIntegerValueMap() != null) {
      // map[0] | tag | data size | key | value |
      // map[1] | tag | data size | key | value |
      for (Map.Entry<String, Integer> entry : obj.getIntegerValueMap().entrySet()) {
        size += CodedOutputStream.computeUInt32SizeNoTag(130);
        int dataSize = 0;
        dataSize += CodedOutputStream.computeStringSize(1, entry.getKey());
        dataSize += CodedOutputStream.computeInt32Size(2, entry.getValue());
        size += CodedOutputStream.computeUInt32SizeNoTag(dataSize);
        size += dataSize;
      }
    }
    if (obj.getStructValueMap() != null) {
        // map[0] | tag | data size | key | value |
        // map[1] | tag | data size | key | value |
      for (Map.Entry<String, Address> entry : obj.getStructValueMap().entrySet()) {
        size += CodedOutputStream.computeUInt32SizeNoTag(138);
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
    if (obj.getJsonValueMap() != null) {
        // map[0] | tag | data size | key | value |
        // map[1] | tag | data size | key | value |
      for (Map.Entry<String, JsonObject> entry : obj.getJsonValueMap().entrySet()) {
        size += CodedOutputStream.computeUInt32SizeNoTag(146);
        // calculate data size
        int dataSize = 0;
        // key
        dataSize += CodedOutputStream.computeStringSize(1, entry.getKey());
        // value
        int elementSize = VertxStructProtoConverter.computeSize(entry.getValue());
        dataSize += CodedOutputStream.computeInt32SizeNoTag(18);
        dataSize += CodedOutputStream.computeInt32SizeNoTag(elementSize);
        dataSize += elementSize;
        // data size
        size += CodedOutputStream.computeUInt32SizeNoTag(dataSize);
        size += dataSize;
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
    if (obj.getZonedDateTimeField() != null) {
      size += CodedOutputStream.computeUInt32SizeNoTag(162);
      int dataSize = ZonedDateTimeProtoConverter.computeSize(obj.getZonedDateTimeField());
      size += CodedOutputStream.computeUInt32SizeNoTag(dataSize);
      size += dataSize;
    }
    if (obj.getInstantField() != null) {
      size += CodedOutputStream.computeUInt32SizeNoTag(170);
      int dataSize = InstantProtoConverter.computeSize(obj.getInstantField());
      size += CodedOutputStream.computeUInt32SizeNoTag(dataSize);
      size += dataSize;
    }
    if (obj.getJsonObjectField() != null) {
      size += CodedOutputStream.computeUInt32SizeNoTag(178);
      int dataSize = VertxStructProtoConverter.computeSize(obj.getJsonObjectField());
      size += CodedOutputStream.computeUInt32SizeNoTag(dataSize);
      size += dataSize;
    }
    if (obj.getJsonArrayField() != null) {
      size += CodedOutputStream.computeUInt32SizeNoTag(186);
      int dataSize = VertxStructListProtoConverter.computeSize(obj.getJsonArrayField());
      size += CodedOutputStream.computeUInt32SizeNoTag(dataSize);
      size += dataSize;
    }
    if (obj.isPrimitiveBoolean()) {
      size += CodedOutputStream.computeBoolSize(24, obj.isPrimitiveBoolean());
    }
    if (obj.getPrimitiveByte() != 0) {
      size += CodedOutputStream.computeInt32Size(25, obj.getPrimitiveByte());
    }
    if (obj.getPrimitiveShort() != 0) {
      size += CodedOutputStream.computeInt32Size(26, obj.getPrimitiveShort());
    }
    if (obj.getPrimitiveInt() != 0) {
      size += CodedOutputStream.computeInt32Size(27, obj.getPrimitiveInt());
    }
    if (obj.getPrimitiveLong() != 0) {
      size += CodedOutputStream.computeInt64Size(28, obj.getPrimitiveLong());
    }
    if (obj.getPrimitiveFloat() != 0) {
      size += CodedOutputStream.computeFloatSize(29, obj.getPrimitiveFloat());
    }
    if (obj.getPrimitiveDouble() != 0) {
      size += CodedOutputStream.computeDoubleSize(30, obj.getPrimitiveDouble());
    }
    if (obj.getPrimitiveChar() != 0) {
      size += CodedOutputStream.computeInt32Size(31, obj.getPrimitiveChar());
    }
    if (obj.getEnumType() != null) {
      switch (obj.getEnumType()) {
        case A:
          size += CodedOutputStream.computeEnumSize(32, 0);
          break;
        case B:
          size += CodedOutputStream.computeEnumSize(32, 1);
          break;
        case C:
          size += CodedOutputStream.computeEnumSize(32, 2);
          break;
      }
    }
    cache.set(baseIndex, size);
    return index;
  }

}
