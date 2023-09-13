package io.vertx.test.codegen.protobuf;

import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.InvalidProtocolBufferException;
import io.vertx.core.json.JsonObject;
import io.vertx.test.codegen.converter.Address;
import io.vertx.test.codegen.converter.EnumType;
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

    // Boxed type fields
    User user = new User();
    user.setUserName("jviet");
    user.setAge(21);
    user.setByteField((byte)8);
    user.setDoubleField(5055.5d);
    user.setFloatField(55.5f);
    user.setLongField(1000L);
    user.setBoolField(true);
    user.setShortField((short) 10);
    user.setCharField((char) 1);

    // Primitive type fields
    user.setPrimitiveBoolean(true);
    user.setPrimitiveByte((byte)3);
    user.setPrimitiveShort((short)300);
    user.setPrimitiveInt(3000);
    user.setPrimitiveLong(300000L);
    user.setPrimitiveFloat(3.3f);
    user.setPrimitiveDouble(3003.33d);
    user.setPrimitiveChar((char)30);

    // Nested Object fields
    user.setAddress(address1);

    // Built-in Object fields
    JsonObject jsonObject5 = new JsonObject();
    jsonObject5.put("IntField", 105);
    jsonObject5.put("StringField", "StringValue-5");
    user.setJsonObjectField(jsonObject5);

    // Enum fields
    user.setEnumType(EnumType.C);

    // List fields
    user.setStructListField(Collections.unmodifiableList(Arrays.asList(address2, address3)));
    user.setIntegerListField(Collections.unmodifiableList(Arrays.asList(100, 101)));

    JsonObject jsonObject1 = new JsonObject();
    jsonObject1.put("IntField", 101);
    jsonObject1.put("StringField", "StringValue-1");
    JsonObject jsonObject2 = new JsonObject();
    jsonObject2.put("IntField", 102);
    jsonObject2.put("StringField", "StringValue-2");
    user.setJsonListField(Collections.unmodifiableList(Arrays.asList(jsonObject1, jsonObject2)));

    // Map fields
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

    JsonObject jsonObject3 = new JsonObject();
    jsonObject3.put("IntField", 103);
    jsonObject3.put("StringField", "StringValue-3");
    JsonObject jsonObject4 = new JsonObject();
    jsonObject4.put("IntField", 104);
    jsonObject4.put("StringField", "StringValue-4");
    Map<String, JsonObject> jsonValueMap = new HashMap<>();
    jsonValueMap.put("key1", jsonObject3);
    jsonValueMap.put("key2", jsonObject4);
    user.setJsonValueMap(jsonValueMap);

    // Vertx Encode
    byte[] encoded = vertxEncode(user);

    // Decode using Google's protoc plugin
    io.vertx.protobuf.generated.User protocObj = protocDecode(encoded);

    // Encode using Google's protoc plugin
    byte[] protocEncoded = protocEncode(protocObj);
    assertArrayEquals(protocEncoded, encoded);

    // Vertx Decode
    User decoded = vertxDecode(protocEncoded);

    // Assert decoded is same with original
    // Boxed field types
    assertEquals(user.getUserName(), decoded.getUserName());
    assertEquals(user.getAge(), decoded.getAge());
    assertEquals(user.getByteField(), decoded.getByteField());
    assertEquals(user.getDoubleField(), decoded.getDoubleField());
    assertEquals(user.getFloatField(), decoded.getFloatField());
    assertEquals(user.getLongField(), decoded.getLongField());
    assertEquals(user.getBoolField(), decoded.getBoolField());
    assertEquals(user.getShortField(), decoded.getShortField());
    assertEquals(user.getCharField(), decoded.getCharField());

    // Built-in Object fields
    assertEquals(user.getJsonObjectField(), decoded.getJsonObjectField());

    // Enum fields
    assertEquals(protocObj.getEnumType().getNumber(), 2); // EnumType.C

    // Nested object field
    Assert.assertEquals(user.getAddress(), decoded.getAddress());
    assertEquals(user.getAddress().getLatitude(), decoded.getAddress().getLatitude());
    assertEquals(user.getAddress().getLongitude(), decoded.getAddress().getLongitude());

    // List fields
    Assert.assertArrayEquals(user.getStructListField().toArray(), decoded.getStructListField().toArray());
    Assert.assertArrayEquals(user.getIntegerListField().toArray(), decoded.getIntegerListField().toArray());
    Assert.assertArrayEquals(user.getJsonListField().toArray(), decoded.getJsonListField().toArray());

    // Primitive field types
    assertEquals(user.isPrimitiveBoolean(), decoded.isPrimitiveBoolean());
    assertEquals(user.getPrimitiveByte(), decoded.getPrimitiveByte());
    assertEquals(user.getPrimitiveShort(), decoded.getPrimitiveShort());
    assertEquals(user.getPrimitiveInt(), decoded.getPrimitiveInt());
    assertEquals(user.getPrimitiveLong(), decoded.getPrimitiveLong());
    assertEquals(user.getPrimitiveFloat(), decoded.getPrimitiveFloat(), 0.0);
    assertEquals(user.getPrimitiveDouble(), decoded.getPrimitiveDouble(), 0.0);
    assertEquals(user.getPrimitiveChar(), decoded.getPrimitiveChar());

    // Map fields
    assertEquals(user.getStringValueMap(), decoded.getStringValueMap());
    assertEquals(user.getIntegerValueMap(), decoded.getIntegerValueMap());
    assertEquals(user.getStructValueMap(), decoded.getStructValueMap());
    assertEquals(user.getJsonValueMap(), decoded.getJsonValueMap());

    // Assert total size is equal to computed size
    Assert.assertEquals(encoded.length, UserProtoConverter.computeSize(user));
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
    io.vertx.protobuf.generated.User protocObj = protocDecode(encoded);
    assertEquals(protocObj.getAddress().getName(), user.getAddress().getName());

    // Encode using Google's protoc plugin
    byte[] protocEncoded = protocEncode(protocObj);
    assertArrayEquals(protocEncoded, encoded);

    // Vertx Decode
    User decoded = vertxDecode(protocEncoded);
    assertEquals(user, decoded);

    // Assert total size is equal to computed size
    Assert.assertEquals(encoded.length, UserProtoConverter.computeSize(user));
  }

  @Test
  public void testIntegerField() throws IOException {
    User user = new User();
    user.setAge(18);
    testEncodeDecode(user, User::getAge, io.vertx.protobuf.generated.User::getAge);
  }

  @Test
  public void testBoolField() throws IOException {
    User user = new User();
    user.setBoolField(true);
    testEncodeDecode(user, User::getBoolField, io.vertx.protobuf.generated.User::getBoolField);
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
    testEncodeDecode(user, User::getDoubleField, io.vertx.protobuf.generated.User::getDoubleField);
  }

  @Test
  public void testIntegerListField() throws IOException {
    User user = new User();
    user.setIntegerListField(Collections.unmodifiableList(Arrays.asList(1, 2)));
    testEncodeDecode(user, User::getIntegerListField, io.vertx.protobuf.generated.User::getIntegerListFieldList);
  }

  @Test
  public void testIntegerMapField() throws IOException {
    User user = new User();
    Map<String, Integer> integerValueMap = new HashMap<>();
    integerValueMap.put("key1", 1);
    integerValueMap.put("key2", 2);
    user.setIntegerValueMap(integerValueMap);
    testEncodeDecode(user, User::getIntegerValueMap, io.vertx.protobuf.generated.User::getIntegerValueMapMap);
  }

  @Test
  public void testLongField() throws IOException {
    User user = new User();
    user.setLongField(100001L);
    testEncodeDecode(user, User::getLongField, io.vertx.protobuf.generated.User::getLongField);
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
    testEncodeDecode(user, User::getStringValueMap, io.vertx.protobuf.generated.User::getStringValueMapMap);
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
    io.vertx.protobuf.generated.User protocObj = protocDecode(encoded);
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
    io.vertx.protobuf.generated.User protocObj = protocDecode(encoded);
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
    io.vertx.protobuf.generated.User protocObj = protocDecode(encoded);
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
    testEncodeDecode(user, User::getUserName, io.vertx.protobuf.generated.User::getUserName);
  }

  @Test
  public void testInstantField() throws IOException {
    ZonedDateTime date = ZonedDateTime.of(2023, 6, 19, 12, 33, 12, 10, ZoneId.of("UTC"));
    User user = new User();
    user.setInstantField(date.toInstant());

    // Vertx Encode
    byte[] encoded = vertxEncode(user);

    // Decode using Google's protoc plugin
    io.vertx.protobuf.generated.User protocObj = protocDecode(encoded);
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
    io.vertx.protobuf.generated.User protocObj = protocDecode(encoded);
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
    io.vertx.protobuf.generated.User protocObj = protocDecode(encoded);
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
    Function<io.vertx.protobuf.generated.User, T> protocGetter) throws IOException {
    // Vertx Encode
    byte[] encoded = vertxEncode(obj);

    // Decode using Google's protoc plugin
    io.vertx.protobuf.generated.User protocObj = protocDecode(encoded);
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
    TestUtils.debug("Vertx encoded", encoded);
    return encoded;
  }

  private byte[] protocEncode(io.vertx.protobuf.generated.User obj) throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    CodedOutputStream output = CodedOutputStream.newInstance(baos);
    obj.writeTo(output);
    output.flush();
    byte[] encoded = baos.toByteArray();
    TestUtils.debug("Protoc encoded", encoded);
    return encoded;
  }

  private io.vertx.protobuf.generated.User protocDecode(byte[] arr) throws InvalidProtocolBufferException {
    return io.vertx.protobuf.generated.User.parseFrom(arr);
  }

  private User vertxDecode(byte[] arr) throws IOException {
    CodedInputStream input = CodedInputStream.newInstance(arr);
    User obj = new User();
    UserProtoConverter.fromProto(input, obj);
    return obj;
  }
}
