package io.vertx.test.codegen.converter.proto;

import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.InvalidProtocolBufferException;
import io.vertx.test.codegen.converter.Address;
import io.vertx.test.codegen.converter.RecursiveItem;
import io.vertx.test.codegen.converter.RecursiveItemProtoConverter;
import io.vertx.test.codegen.converter.TestUtils;
import io.vertx.test.codegen.converter.User;
import io.vertx.test.codegen.converter.UserProtoConverter;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class ProtoConverterTest {
  @Test
  public void testAllFields() throws IOException {
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
    UserProtoConverter.toProto(user, output);
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
    Assert.assertEquals(encoded.length, UserProtoConverter.computeSize(user));
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

    RecursiveItemProtoConverter.toProto(root, output);
    output.flush();
    byte[] encoded = baos.toByteArray();

    ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
    CodedOutputStream output2 = CodedOutputStream.newInstance(baos2);
    RecursiveItemProtoConverter.toProto(root, output2);
    output2.flush();
    byte[] encoded2 = baos2.toByteArray();

    Assert.assertArrayEquals(encoded2, encoded);
  }

  @Test
  public void testNestedField() throws IOException {
    Address address = new Address();
    address.setName("Address-01");

    User user = new User();
    user.setAddress(address);

    // Vertx Encode
    byte[] encoded = vertxEncode(user);

    // Decode using Google's protoc plugin
    io.vertx.test.protoc.gen.User protocObj = protocDecode(encoded);
    assertEquals(protocObj.getAddress().getName(), user.getAddress().getName());

    // Encode using Google's protoc plugin
    byte[] protocEncoded = protocEncode(protocObj);
    assertArrayEquals(protocEncoded, encoded);

    // Vertx Decode
    User decoded = vertxDecode(protocEncoded);
    assertEquals(user, decoded);
  }

  @Test
  public void testIntegerField() throws IOException {
    User user = new User();
    user.setAge(18);
    testEncodeDecode(user, User::getAge, io.vertx.test.protoc.gen.User::getAge);
  }

  @Test
  public void testBoolField() throws IOException {
    User user = new User();
    user.setBoolField(true);
    testEncodeDecode(user, User::getBoolField, io.vertx.test.protoc.gen.User::getBoolField);
  }

  @Test
  public void testCharField() throws IOException {
    User user = new User();
    user.setCharField('Z');
    testEncodeDecode(user, User::getCharField, protocObject -> (char) protocObject.getCharField());
  }

  @Test
  public void testDoubleField() throws IOException {
    User user = new User();
    user.setDoubleField(3.142);
    testEncodeDecode(user, User::getDoubleField, io.vertx.test.protoc.gen.User::getDoubleField);
  }

  @Test
  public void testIntegerListField() throws IOException {
    User user = new User();
    user.setIntegerListField(Collections.unmodifiableList(Arrays.asList(1, 2)));
    testEncodeDecode(user, User::getIntegerListField, io.vertx.test.protoc.gen.User::getIntegerListFieldList);
  }

  @Test
  public void testIntegerMapField() throws IOException {
    User user = new User();
    Map<String, Integer> integerValueMap = new HashMap<>();
    integerValueMap.put("key1", 1);
    integerValueMap.put("key2", 2);
    user.setIntegerValueMap(integerValueMap);
    testEncodeDecode(user, User::getIntegerValueMap, io.vertx.test.protoc.gen.User::getIntegerValueMapMap);
  }

  @Test
  public void testLongField() throws IOException {
    User user = new User();
    user.setLongField(100001L);
    testEncodeDecode(user, User::getLongField, io.vertx.test.protoc.gen.User::getLongField);
  }

  @Test
  public void testShortField() throws IOException {
    User user = new User();
    user.setShortField((short)20);
    testEncodeDecode(user, User::getShortField, protocObj -> (short) protocObj.getShortField());
  }

  @Test
  public void testStringMapField() throws IOException {
    User user = new User();
    Map<String, String> stringValueMap = new HashMap<>();
    stringValueMap.put("key1", "value1");
    stringValueMap.put("key2", "value2");
    user.setStringValueMap(stringValueMap);
    testEncodeDecode(user, User::getStringValueMap, io.vertx.test.protoc.gen.User::getStringValueMapMap);
  }

  @Test
  public void testNestedListField() throws IOException {
    Address address1 = new Address();
    address1.setName("Address-1");
    Address address2 = new Address();
    address2.setName("Address-2");
    User user = new User();
    user.setStructListField(Collections.unmodifiableList(Arrays.asList(address1, address2)));

    // Vertx Encode
    byte[] encoded = vertxEncode(user);

    // Decode using Google's protoc plugin
    io.vertx.test.protoc.gen.User protocObj = protocDecode(encoded);
    assertEquals(protocObj.getStructListFieldList().size(), user.getStructListField().size());
    assertEquals(protocObj.getStructListField(0).getName(), user.getStructListField().get(0).getName());
    assertEquals(protocObj.getStructListField(1).getName(), user.getStructListField().get(1).getName());

    // Encode using Google's protoc plugin
    byte[] protocEncoded = protocEncode(protocObj);
    assertArrayEquals(protocEncoded, encoded);

    // Vertx Decode
    User decoded = vertxDecode(protocEncoded);
    assertEquals(user, decoded);
  }

  @Test
  public void testZonedDateTimeListField() throws IOException {
    User user = new User();
    List<ZonedDateTime> list = new ArrayList<>();
    list.add(ZonedDateTime.of(2023, 5, 27, 21, 23, 58, 15, ZoneId.of("UTC")));
    list.add(ZonedDateTime.of(2023, 6, 5, 20, 56, 15, 11, ZoneId.of("UTC")));
    user.setZonedDateTimeListField(list);

    // Vertx Encode
    byte[] encoded = vertxEncode(user);

    // Decode using Google's protoc plugin
    io.vertx.test.protoc.gen.User protocObj = protocDecode(encoded);
    assertEquals(user.getZonedDateTimeListField().get(0).toInstant().getEpochSecond(), protocObj.getZonedDateTimeListField(0).getSeconds());
    assertEquals(user.getZonedDateTimeListField().get(0).toInstant().getNano(), protocObj.getZonedDateTimeListField(0).getNanos());
    assertEquals(user.getZonedDateTimeListField().get(0).getZone().toString(), protocObj.getZonedDateTimeListField(0).getZoneId());
    assertEquals(user.getZonedDateTimeListField().get(1).toInstant().getEpochSecond(), protocObj.getZonedDateTimeListField(1).getSeconds());
    assertEquals(user.getZonedDateTimeListField().get(1).toInstant().getNano(), protocObj.getZonedDateTimeListField(1).getNanos());
    assertEquals(user.getZonedDateTimeListField().get(1).getZone().toString(), protocObj.getZonedDateTimeListField(1).getZoneId());

    // Encode using Google's protoc plugin
    byte[] protocEncoded = protocEncode(protocObj);
    assertArrayEquals(protocEncoded, encoded);

    // Vertx Decode
    User decoded = vertxDecode(protocEncoded);
    assertEquals(user, decoded);

    // Test computeSize
    Assert.assertEquals(encoded.length, UserProtoConverter.computeSize(user));
  }

  @Test
  public void testNestedMapField() throws IOException {
    Address address1 = new Address();
    address1.setName("Address-1");
    Address address2 = new Address();
    address2.setName("Address-2");
    User user = new User();
    Map<String, Address> structValueMap = new HashMap<>();
    structValueMap.put("key1", address1);
    structValueMap.put("key2", address2);
    user.setStructValueMap(structValueMap);

    // Vertx Encode
    byte[] encoded = vertxEncode(user);

    // Decode using Google's protoc plugin
    io.vertx.test.protoc.gen.User protocObj = protocDecode(encoded);
    assertEquals(user.getStructValueMap().get("key1").getName(), protocObj.getStructValueMapMap().get("key1").getName());
    assertEquals(user.getStructValueMap().get("key2").getName(), protocObj.getStructValueMapMap().get("key2").getName());

    // Encode using Google's protoc plugin
    byte[] protocEncoded = protocEncode(protocObj);
    assertArrayEquals(protocEncoded, encoded);

    // Vertx Decode
    User decoded = vertxDecode(protocEncoded);
    assertEquals(user, decoded);
  }

  @Test
  public void testStringField() throws IOException {
    User user = new User();
    user.setUserName("user-01");
    testEncodeDecode(user, User::getUserName, io.vertx.test.protoc.gen.User::getUsername);
  }

  @Test
  public void testInstantField() throws IOException {
    ZonedDateTime date = ZonedDateTime.of(2023, 6, 19, 12, 33, 12, 10, ZoneId.of("UTC"));
    User user = new User();
    user.setInstantField(date.toInstant());

    // Vertx Encode
    byte[] encoded = vertxEncode(user);

    // Decode using Google's protoc plugin
    io.vertx.test.protoc.gen.User protocObj = protocDecode(encoded);
    assertEquals(user.getInstantField().getEpochSecond(), protocObj.getInstantField().getSeconds());
    assertEquals(user.getInstantField().getNano(), protocObj.getInstantField().getNanos());

    // Encode using Google's protoc plugin
    byte[] protocEncoded = protocEncode(protocObj);
    assertArrayEquals(protocEncoded, encoded);

    // Vertx Decode
    User decoded = vertxDecode(protocEncoded);
    assertEquals(user, decoded);

    // Test computeSize
    Assert.assertEquals(encoded.length, UserProtoConverter.computeSize(user));
  }

  @Test
  public void testZonedDateTimeField() throws IOException {
    User user = new User();
    user.setZonedDateTimeField(ZonedDateTime.of(2023, 5, 27, 21, 23, 58, 15, ZoneId.of("UTC")));

    // Vertx Encode
    byte[] encoded = vertxEncode(user);

    // Decode using Google's protoc plugin
    io.vertx.test.protoc.gen.User protocObj = protocDecode(encoded);
    assertEquals(user.getZonedDateTimeField().toInstant().getEpochSecond(), protocObj.getZonedDateTimeField().getSeconds());
    assertEquals(user.getZonedDateTimeField().toInstant().getNano(), protocObj.getZonedDateTimeField().getNanos());
    assertEquals(user.getZonedDateTimeField().getZone().toString(), protocObj.getZonedDateTimeField().getZoneId());

    // Encode using Google's protoc plugin
    byte[] protocEncoded = protocEncode(protocObj);
    assertArrayEquals(protocEncoded, encoded);

    // Vertx Decode
    User decoded = vertxDecode(protocEncoded);
    assertEquals(user, decoded);

    // Test computeSize
    Assert.assertEquals(encoded.length, UserProtoConverter.computeSize(user));
  }

  @Test
  public void testZonedDateTimeMapField() throws IOException {
    User user = new User();
    Map<String, ZonedDateTime> mapField = new HashMap<>();
    mapField.put("Key1", ZonedDateTime.of(2023, 5, 27, 21, 23, 58, 15, ZoneId.of("UTC")));
    mapField.put("Key2", ZonedDateTime.of(2023, 6, 6, 10, 50, 6, 6, ZoneId.of("UTC")));
    user.setZonedDateTimeValueMap(mapField);

    // Vertx Encode
    byte[] encoded = vertxEncode(user);

    // Decode using Google's protoc plugin
    io.vertx.test.protoc.gen.User protocObj = protocDecode(encoded);
    assertEquals(user.getZonedDateTimeValueMap().get("Key1").toInstant().getEpochSecond(), protocObj.getZonedDateTimeValueMapMap().get("Key1").getSeconds());
    assertEquals(user.getZonedDateTimeValueMap().get("Key1").toInstant().getNano(), protocObj.getZonedDateTimeValueMapMap().get("Key1").getNanos());
    assertEquals(user.getZonedDateTimeValueMap().get("Key1").getZone().toString(), protocObj.getZonedDateTimeValueMapMap().get("Key1").getZoneId());
    assertEquals(user.getZonedDateTimeValueMap().get("Key2").toInstant().getEpochSecond(), protocObj.getZonedDateTimeValueMapMap().get("Key2").getSeconds());
    assertEquals(user.getZonedDateTimeValueMap().get("Key2").toInstant().getNano(), protocObj.getZonedDateTimeValueMapMap().get("Key2").getNanos());
    assertEquals(user.getZonedDateTimeValueMap().get("Key2").getZone().toString(), protocObj.getZonedDateTimeValueMapMap().get("Key2").getZoneId());

    // Encode using Google's protoc plugin
    byte[] protocEncoded = protocEncode(protocObj);
    assertArrayEquals(protocEncoded, encoded);

    // Vertx Decode
    User decoded = vertxDecode(protocEncoded);
    assertEquals(user, decoded);

    // Test computeSize
    Assert.assertEquals(encoded.length, UserProtoConverter.computeSize(user));
  }

  private <T> void testEncodeDecode(
    User obj,
    Function<User, T> pojoGetter,
    Function<io.vertx.test.protoc.gen.User, T> protocGetter) throws IOException {
    // Vertx Encode
    byte[] encoded = vertxEncode(obj);

    // Decode using Google's protoc plugin
    io.vertx.test.protoc.gen.User protocObj = protocDecode(encoded);
    assertEquals(pojoGetter.apply(obj), protocGetter.apply(protocObj));

    // Encode using Google's protoc plugin
    byte[] protocEncoded = protocEncode(protocObj);
    assertArrayEquals(protocEncoded, encoded);

    // Vertx Decode
    User decoded = vertxDecode(protocEncoded);
    assertEquals(obj, decoded);
  }

  private byte[] vertxEncode(User obj) throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    CodedOutputStream output = CodedOutputStream.newInstance(baos);
    UserProtoConverter.toProto(obj, output);
    output.flush();
    byte[] encoded = baos.toByteArray();
    System.out.println("vertx encoded:\n" + TestUtils.prettyHexDump(encoded));
    return encoded;
  }

  private byte[] protocEncode(io.vertx.test.protoc.gen.User obj) throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    CodedOutputStream output = CodedOutputStream.newInstance(baos);
    obj.writeTo(output);
    output.flush();
    byte[] encoded = baos.toByteArray();
    System.out.println("protoc encoded:\n" + TestUtils.prettyHexDump(encoded));
    return encoded;
  }

  private io.vertx.test.protoc.gen.User protocDecode(byte[] arr) throws InvalidProtocolBufferException {
    return io.vertx.test.protoc.gen.User.parseFrom(arr);
  }

  private User vertxDecode(byte[] arr) throws IOException {
    CodedInputStream input = CodedInputStream.newInstance(arr);
    User obj = new User();
    UserProtoConverter.fromProto(input, obj);
    return obj;
  }
}
