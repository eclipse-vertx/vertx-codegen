package io.vertx.test.codegen.converter;

import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.CodedInputStream;
import java.io.IOException;

public class UserProtoConverter {

  public static void fromProto(CodedInputStream input, User obj) throws IOException {
    int tag;
    while ((tag = input.readTag()) != 0) {
      switch (tag) {
      case 8:
        obj.setAge(input.readInt32());
        break;
      case 18:
        obj.setUserName(input.readString());
        break;
      }
    }
  }

  public static void toProto(User obj, CodedOutputStream output) throws IOException {
    if (obj.getAge() != null) {
      output.writeInt32(1, obj.getAge());
    }
    if (obj.getUserName() != null) {
      output.writeString(2, obj.getUserName());
    }
  }

  public static int computeSize(User obj) {
    int size = 0;
    if (obj.getAge() != null) {
      size += CodedOutputStream.computeInt32Size(1, obj.getAge());
    }
    if (obj.getUserName() != null) {
      size += CodedOutputStream.computeStringSize(2, obj.getUserName());
    }
    return size;
  }

}
