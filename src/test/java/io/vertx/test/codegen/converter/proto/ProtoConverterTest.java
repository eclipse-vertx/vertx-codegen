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
    user.setDoubleField(5.5);
    user.setLongField(1000L);
    user.setBoolField(true);
    user.setShortField((short) 10);
    user.setCharField((char) 1);

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

    // Assert decoded is same with original
    Assert.assertEquals(user.getUserName(), decoded.getUserName());
    Assert.assertEquals(user.getAge(), decoded.getAge());
    //Assert.assertEquals(user.getAddress(), decoded.getAddress()); // need equal and hash
    Assert.assertEquals(user.getAddress().getLatitude(), decoded.getAddress().getLatitude());
    Assert.assertEquals(user.getAddress().getLongitude(), decoded.getAddress().getLongitude());
    Assert.assertArrayEquals(user.getIntegerListField().toArray(), decoded.getIntegerListField().toArray());
    Assert.assertEquals(user.getDoubleField(), decoded.getDoubleField());
    Assert.assertEquals(user.getLongField(), decoded.getLongField());
    Assert.assertEquals(user.getBoolField(), decoded.getBoolField());
    Assert.assertEquals(user.getShortField(), decoded.getShortField());
    Assert.assertEquals(user.getCharField(), decoded.getCharField());

    // Assert total size is equal to computed size
    Assert.assertEquals(encoded.length, UserProtoConverter.computeSize(user));
  }
}
