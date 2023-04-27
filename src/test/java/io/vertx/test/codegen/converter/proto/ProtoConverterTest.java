package io.vertx.test.codegen.converter.proto;

import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import io.vertx.test.codegen.converter.Address;
import io.vertx.test.codegen.converter.User;
import io.vertx.test.codegen.converter.UserProtoConverter;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

public class ProtoConverterTest {
  @Test
  public void testPrimitiveFields() throws IOException {
    Address address = new Address();
    address.setLatitude(3.333F);
    address.setLongitude(4.444F);
    User user = new User();
    user.setUserName("jviet");
    user.setAge(21);
    user.setAddress(address);
    user.setIntegerListField(Collections.unmodifiableList(Arrays.asList(100, 101)));

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
    //Assert.assertEquals(user.getAddress(), decoded.getAddress()); // need equal and hash
    Assert.assertEquals(user.getAddress().getLatitude(), decoded.getAddress().getLatitude());
    Assert.assertEquals(user.getAddress().getLongitude(), decoded.getAddress().getLongitude());
    Assert.assertArrayEquals(user.getIntegerListField().toArray(), decoded.getIntegerListField().toArray());
  }
}
