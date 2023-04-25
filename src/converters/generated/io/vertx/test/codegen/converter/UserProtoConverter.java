package io.vertx.test.codegen.converter;

import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.CodedInputStream;
import java.io.IOException;

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
        case 26: {
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
    if (obj.getUserName() != null) {
      output.writeString(3, obj.getUserName());
    }
  }

  public static int computeSize(User obj) {
    int size = 0;
    if (obj.getAddress() != null) {
      size += AddressProtoConverter.computeSize(obj.getAddress());
    }
    if (obj.getAge() != null) {
      size += CodedOutputStream.computeInt32Size(2, obj.getAge());
    }
    if (obj.getUserName() != null) {
      size += CodedOutputStream.computeStringSize(3, obj.getUserName());
    }
    return size;
  }

}
