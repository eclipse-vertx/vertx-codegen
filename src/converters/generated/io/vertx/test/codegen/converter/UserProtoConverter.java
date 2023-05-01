package io.vertx.test.codegen.converter;

import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.CodedInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserProtoConverter {

  public static void fromProto(CodedInputStream input, User obj) throws IOException {
    int tag;
    while ((tag = input.readTag()) != 0) {
      switch (tag) {
        case 10: {
          int length = input.readUInt32();
          int oldLimit = input.pushLimit(length);
          Address address = new Address();
          AddressProtoConverter.fromProto(input, address);
          obj.setAddress(address);
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
        case 56: {
          obj.setLongField(input.readInt64());
          break;
        }
        case 64: {
          obj.setShortField((short) input.readInt32());
          break;
        }
        case 74: {
          obj.setUserName(input.readString());
          break;
        }
      }
    }
  }

  public static void toProto(User obj, CodedOutputStream output) throws IOException {
    if (obj.getAddress() != null) {
      output.writeTag(1, 2);
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
    if (obj.getLongField() != null) {
      output.writeInt64(7, obj.getLongField());
    }
    if (obj.getShortField() != null) {
      output.writeInt32(8, obj.getShortField());
    }
    if (obj.getUserName() != null) {
      output.writeString(9, obj.getUserName());
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
    if (obj.getLongField() != null) {
      size += CodedOutputStream.computeInt64Size(7, obj.getLongField());
    }
    if (obj.getShortField() != null) {
      size += CodedOutputStream.computeInt32Size(8, obj.getShortField());
    }
    if (obj.getUserName() != null) {
      size += CodedOutputStream.computeStringSize(9, obj.getUserName());
    }
    return size;
  }

}
