package io.vertx.test.codegen.converter.proto;

import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.InvalidProtocolBufferException;
import io.vertx.test.codegen.converter.Address;
import io.vertx.test.codegen.converter.RecursiveItem;
import io.vertx.test.codegen.converter.RecursiveItemProtoConverter;
import io.vertx.test.codegen.converter.User;
import io.vertx.test.codegen.converter.UserProtoConverter;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class ProtoConverterTest {
  @Test
  public void testIntegerField() throws IOException {
    User user = new User();
    user.setAge(18);

    // Encode to byte array
    byte[] encoded = encode(user);

    // Decode using Google's protoc plugin
    io.vertx.test.protoc.gen.User protocObj = protocDecode(encoded);
    assertEquals(protocObj.getAge(), (int) user.getAge());

    // Encode using Google's protoc plugin
    byte[] protocEncoded = protocEncode(protocObj);
    assertArrayEquals(protocEncoded, encoded);

    // Decode
    User decoded = decode(protocEncoded);
    assertEquals(user, decoded);
  }

  @Test
  public void testBoolField() throws IOException {
    User user = new User();
    user.setBoolField(true);

    // Encode to byte array
    byte[] encoded = encode(user);

    // Decode using Google's protoc plugin
    io.vertx.test.protoc.gen.User protocObj = protocDecode(encoded);
    assertEquals(protocObj.getBoolField(), user.getBoolField());

    // Encode using Google's protoc plugin
    byte[] protocEncoded = protocEncode(protocObj);
    assertArrayEquals(protocEncoded, encoded);

    // Decode
    User decoded = decode(protocEncoded);
    assertEquals(user, decoded);
  }

  @Test
  public void testNestedField() throws IOException {
    Address address = new Address();
    address.setName("Address-01");

    User user = new User();
    user.setAddress(address);

    // Encode to byte array
    byte[] encoded = encode(user);

    // Decode using Google's protoc plugin
    io.vertx.test.protoc.gen.User protocObj = protocDecode(encoded);
    assertEquals(protocObj.getAddress().getName(), user.getAddress().getName());

    // Encode using Google's protoc plugin
    byte[] protocEncoded = protocEncode(protocObj);
    assertArrayEquals(protocEncoded, encoded);

    // Decode
    User decoded = decode(protocEncoded);
    assertEquals(user, decoded);
  }

  @Test
  public void testPrimitiveFields() throws IOException {
    Address address1 = new Address();
    address1.setName("Addr-1");
    address1.setLatitude(3.301f);
    address1.setLongitude(4.401f);

    Address address2 = new Address();
    address2.setName("Addr-2");
    address2.setLatitude(3.302F);
    address2.setLongitude(4.402F);

    Address address3 = new Address();
    address3.setName("Addr-3");
    address3.setLatitude(3.303F);
    address3.setLongitude(4.403F);

    User user = new User();
    user.setUserName("jviet");
    user.setAge(21);
    user.setAddress(address1);
    user.setStructListField(Collections.unmodifiableList(Arrays.asList(address2, address3)));
    user.setIntegerListField(Collections.unmodifiableList(Arrays.asList(100, 101)));
    user.setDoubleField(5.5);
    user.setLongField(1000L);
    user.setBoolField(true);
    user.setShortField((short) 10);
    user.setCharField((char) 1);
    Map<String, String> stringValueMap = new HashMap<>();
    stringValueMap.put("key1", "value1");
    stringValueMap.put("key2", "value2");
    user.setStringValueMap(stringValueMap);
    Map<String, Integer> integerValueMap = new HashMap<>();
    integerValueMap.put("key1", 1);
    integerValueMap.put("key2", 2);
    user.setIntegerValueMap(integerValueMap);

    Map<String, Address> structValueMap = new HashMap<>();
    structValueMap.put("key1", address1);
    structValueMap.put("key2", address2);
    user.setStructValueMap(structValueMap);

    // Encode
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    CodedOutputStream output = CodedOutputStream.newInstance(baos);
    UserProtoConverter.toProto2(user, output);
    output.flush();
    byte[] encoded = baos.toByteArray();
    // Decode
    User decoded = new User();
    CodedInputStream input = CodedInputStream.newInstance(encoded);
    UserProtoConverter.fromProto(input, decoded);

    // Assert decoded is same with original
    assertEquals(user.getUserName(), decoded.getUserName());
    assertEquals(user.getAge(), decoded.getAge());
    Assert.assertEquals(user.getAddress(), decoded.getAddress());
    assertEquals(user.getAddress().getLatitude(), decoded.getAddress().getLatitude());
    assertEquals(user.getAddress().getLongitude(), decoded.getAddress().getLongitude());
    Assert.assertArrayEquals(user.getStructListField().toArray(), decoded.getStructListField().toArray());
    Assert.assertArrayEquals(user.getIntegerListField().toArray(), decoded.getIntegerListField().toArray());
    assertEquals(user.getDoubleField(), decoded.getDoubleField());
    assertEquals(user.getLongField(), decoded.getLongField());
    assertEquals(user.getBoolField(), decoded.getBoolField());
    assertEquals(user.getShortField(), decoded.getShortField());
    assertEquals(user.getCharField(), decoded.getCharField());
    assertEquals(user.getStringValueMap(), decoded.getStringValueMap());
    assertEquals(user.getIntegerValueMap(), decoded.getIntegerValueMap());
    assertEquals(user.getStructValueMap(), decoded.getStructValueMap());

    // Assert total size is equal to computed size
    assertEquals(encoded.length, UserProtoConverter.computeSize2(user));
  }

  @Test
  public void testCachedComputeSize() throws IOException {
    RecursiveItem root = new RecursiveItem("root");
    RecursiveItem a = new RecursiveItem("a");
    RecursiveItem a_a = new RecursiveItem("a_a");
    RecursiveItem a_b = new RecursiveItem("a_b");
    RecursiveItem a_c = new RecursiveItem("a_c");
    RecursiveItem b = new RecursiveItem("b");
    RecursiveItem b_a = new RecursiveItem("b_a");
    RecursiveItem b_b = new RecursiveItem("b_b");
    RecursiveItem c = new RecursiveItem("c");
    RecursiveItem c_b = new RecursiveItem("c_b");
    RecursiveItem c_c = new RecursiveItem("c_c");

    RecursiveItem a_a_a = new RecursiveItem("a_a_a");
    RecursiveItem a_a_b = new RecursiveItem("a_a_b");
    RecursiveItem a_a_c = new RecursiveItem("a_a_c");

    RecursiveItem a_a_a_a = new RecursiveItem("a_a_a_a");
    RecursiveItem a_a_a_b = new RecursiveItem("a_a_a_b");
    RecursiveItem a_a_a_c = new RecursiveItem("a_a_a_c");

    root.setChildA(a);

    a.setChildA(a_a);
    a.setChildB(a_b);
    a.setChildC(a_c);

    a_a.setChildA(a_a_a);
    a_a.setChildB(a_a_b);
    a_a.setChildC(a_a_c);

    a_a_a.setChildA(a_a_a_a);
    a_a_a.setChildB(a_a_a_b);
    a_a_a.setChildC(a_a_a_c);

    root.setChildB(b);
    b.setChildA(b_a);
    b.setChildB(b_b);

    root.setChildC(c);
    c.setChildB(c_b);
    c.setChildC(c_c);

    // Encode
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    CodedOutputStream output = CodedOutputStream.newInstance(baos);

    RecursiveItemProtoConverter.toProto2(root, output);
    output.flush();

    byte[] encoded = baos.toByteArray();
    System.out.println("encoded 2 \n" + prettyHexDump(encoded));

    ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
    CodedOutputStream output2 = CodedOutputStream.newInstance(baos2);
    RecursiveItemProtoConverter.toProto(root, output2);
    output2.flush();
    byte[] encoded2 = baos2.toByteArray();
    System.out.println("encoded 1 \n" + prettyHexDump(encoded2));

    Assert.assertArrayEquals(encoded2, encoded);
  }

  private byte[] encode(User obj) throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    CodedOutputStream output = CodedOutputStream.newInstance(baos);
    UserProtoConverter.toProto2(obj, output);
    output.flush();
    byte[] encoded = baos.toByteArray();
    System.out.println("vertx encoded:\n" + prettyHexDump(encoded));
    return encoded;
  }

  private byte[] protocEncode(io.vertx.test.protoc.gen.User obj) throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    CodedOutputStream output = CodedOutputStream.newInstance(baos);
    obj.writeTo(output);
    output.flush();
    byte[] encoded = baos.toByteArray();
    System.out.println("protoc encoded:\n" + prettyHexDump(encoded));
    return encoded;
  }

  private io.vertx.test.protoc.gen.User protocDecode(byte[] arr) throws InvalidProtocolBufferException {
    return io.vertx.test.protoc.gen.User.parseFrom(arr);
  }

  private User decode(byte[] arr) throws IOException {
    CodedInputStream input = CodedInputStream.newInstance(arr);
    User obj = new User();
    UserProtoConverter.fromProto(input, obj);
    return obj;
  }

  public static String prettyHexDump(byte[] bytes) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < bytes.length; i++) {
      if (i % 16 == 0) {
        sb.append(String.format("%05x: ", i));
      }
      sb.append(String.format("%02x ", bytes[i]));
      if (i % 4 == 3) {
        sb.append(" ");
      }
      if (i % 16 == 15 || i == bytes.length - 1) {
        for (int j = i + 1; j % 16 != 0; j++) {
          sb.append("   ");
          if (j % 4 == 3) {
            sb.append(" ");
          }
        }
        int start = (i / 16) * 16;
        sb.append("  ");
        for (int j = start; j <= i; j++) {
          if (bytes[j] >= 32 && bytes[j] < 127) {
            sb.append((char) bytes[j]);
          } else {
            sb.append(".");
          }
          if ((j - start) % 4 == 3 && j < i) {
            sb.append(" ");
          }
        }
        sb.append("\n");
      }
    }
    return sb.toString();
  }
}
