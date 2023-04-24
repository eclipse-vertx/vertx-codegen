package io.vertx.test.codegen.converter.proto;

import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import io.vertx.test.codegen.converter.User;
import io.vertx.test.codegen.converter.UserProtoConverter;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ProtoConverterTest {
  @Test
  public void testPrimitiveFields() throws IOException {
    User user = new User();
    user.setUserName("jviet");
    user.setAge(21);
    // Encode
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    CodedOutputStream output = CodedOutputStream.newInstance(baos);
    UserProtoConverter.toProto(user, output);
    output.flush();
    byte[] encoded = baos.toByteArray();
    // Decode
    User decoded = new User();
    CodedInputStream input = CodedInputStream.newInstance(encoded);
    UserProtoConverter.fromProto(input, decoded);
    Assert.assertEquals(user.getUserName(), decoded.getUserName());
    Assert.assertEquals(user.getAge(), decoded.getAge());
  }
}
