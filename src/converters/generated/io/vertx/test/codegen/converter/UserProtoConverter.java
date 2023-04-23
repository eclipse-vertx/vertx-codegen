package io.vertx.test.codegen.converter;

import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.CodedInputStream;

public class UserProtoConverter {

  public static void fromProto(CodedInputStream input, User obj) {
  // setAge
  // setUserName
  }

  public static void toProto(User obj, CodedOutputStream output) {
  // getAge
  // getUserName
  }

  public static int computeSize(User obj) {
    int size = 0;
    if (obj.getAge() != null) {
      // size += CodedOutputStream.computeSize(fieldNumber, obj.getAge());
    }
    if (obj.getUserName() != null) {
      // size += CodedOutputStream.computeSize(fieldNumber, obj.getUserName());
    }
    return size;
  }

}
