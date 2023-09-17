package io.vertx.test.codegen.protobuf;

import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.codegen.protobuf.converters.VertxStructProtoConverter;
import io.vertx.protobuf.Value;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.Instant;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class VertxStructProtoTest {

  @Test
  public void TestJsonObject() throws IOException {
    JsonObject jsonObject = new JsonObject();
    jsonObject.put("IntegerField", 15);
    jsonObject.put("StringField", "StringValue");
    jsonObject.put("BooleanField", true);
    jsonObject.put("DoubleField", 3.142);
    jsonObject.put("LongField", 20000L);
    jsonObject.put("FloatField", 8.8888f);
    jsonObject.put("NullField", null);
    Instant now = Instant.now();
    jsonObject.put("InstantField", now);

    // Vertx Encode
    byte[] encoded = vertxEncode(jsonObject);

    // Decode using Google's protoc plugin
    io.vertx.protobuf.Struct protoJsonObject = io.vertx.protobuf.Struct.parseFrom(encoded);

    Value intValue = protoJsonObject.getFieldsMap().get("IntegerField");
    assertEquals(15, intValue.getIntegerValue());
    assertEquals(Value.KindCase.INTEGER_VALUE, intValue.getKindCase());

    Value longValue = protoJsonObject.getFieldsMap().get("LongField");
    assertEquals(20000L, longValue.getLongValue());
    assertEquals(Value.KindCase.LONG_VALUE, longValue.getKindCase());

    Value stringValue = protoJsonObject.getFieldsMap().get("StringField");
    assertEquals("StringValue", stringValue.getStringValue());
    assertEquals(Value.KindCase.STRING_VALUE, stringValue.getKindCase());

    Value boolValue = protoJsonObject.getFieldsMap().get("BooleanField");
    assertTrue(boolValue.getBoolValue());
    assertEquals(Value.KindCase.BOOL_VALUE, boolValue.getKindCase());

    Value doubleValue = protoJsonObject.getFieldsMap().get("DoubleField");
    assertEquals(3.142, doubleValue.getDoubleValue(), 0.0);
    assertEquals(Value.KindCase.DOUBLE_VALUE, doubleValue.getKindCase());

    Value floatValue = protoJsonObject.getFieldsMap().get("FloatField");
    assertEquals(8.8888f, floatValue.getFloatValue(), 0.0);
    assertEquals(Value.KindCase.FLOAT_VALUE, floatValue.getKindCase());

    Value instantValue = protoJsonObject.getFieldsMap().get("InstantField");
    assertEquals(now.getNano(), instantValue.getInstantValue().getNanos());
    assertEquals(now.getEpochSecond(), instantValue.getInstantValue().getSeconds());
    assertEquals(Value.KindCase.INSTANT_VALUE, instantValue.getKindCase());

    // Encode using Google's protoc plugin
    byte[] protocEncoded = protocEncode(protoJsonObject);
    assertArrayEquals(protocEncoded, encoded);

    // Vertx Decode
    CodedInputStream input = CodedInputStream.newInstance(protocEncoded);
    JsonObject decoded = VertxStructProtoConverter.fromProto(input);

    assertEquals(jsonObject.getMap(), decoded.getMap());

    // Verify ComputeSize
    Assert.assertEquals(encoded.length, VertxStructProtoConverter.computeSize(jsonObject));
  }

  @Test
  public void TestRecursiveJsonObject() throws IOException {
    JsonObject jsonObject = new JsonObject();

    JsonObject jsonObjectField = new JsonObject();
    jsonObjectField.put("IntegerField", 100);
    jsonObjectField.put("StringField", "sub-string");
    jsonObject.put("jsonObjectField", jsonObjectField);

    // Vertx Encode
    byte[] encoded = vertxEncode(jsonObject);

    // Decode using Google's protoc plugin
    io.vertx.protobuf.Struct protoJsonObject = io.vertx.protobuf.Struct.parseFrom(encoded);

    Value jsonObjectValue = protoJsonObject.getFieldsMap().get("jsonObjectField");
    assertEquals(Value.KindCase.JSON_OBJECT_VALUE, jsonObjectValue.getKindCase());
    io.vertx.protobuf.Struct subJsonObject = jsonObjectValue.getJsonObjectValue();

    Value intValue = subJsonObject.getFieldsMap().get("IntegerField");
    assertEquals(100, intValue.getIntegerValue());
    assertEquals(Value.KindCase.INTEGER_VALUE, intValue.getKindCase());

    Value stringValue = subJsonObject.getFieldsMap().get("StringField");
    assertEquals("sub-string", stringValue.getStringValue());
    assertEquals(Value.KindCase.STRING_VALUE, stringValue.getKindCase());

    // Encode using Google's protoc plugin
    byte[] protocEncoded = protocEncode(protoJsonObject);
    assertArrayEquals(protocEncoded, encoded);

    // Vertx Decode
    CodedInputStream input = CodedInputStream.newInstance(protocEncoded);
    JsonObject decoded = VertxStructProtoConverter.fromProto(input);

    assertEquals(jsonObject.getMap(), decoded.getMap());

    // Verify ComputeSize
    Assert.assertEquals(encoded.length, VertxStructProtoConverter.computeSize(jsonObject));
  }

  @Test
  public void TestJsonArray() throws IOException {
    JsonObject jsonObject = new JsonObject();

    JsonArray intJsonArray = new JsonArray();
    intJsonArray.add(1);
    intJsonArray.add(2);
    intJsonArray.add(3);
    jsonObject.put("intList", intJsonArray);

    JsonArray strJsonArray = new JsonArray();
    strJsonArray.add("One");
    strJsonArray.add("Two");
    strJsonArray.add("Three");
    jsonObject.put("strList", strJsonArray);

    JsonArray longJsonArray = new JsonArray();
    longJsonArray.add(1000L);
    longJsonArray.add(2000L);
    longJsonArray.add(3000L);
    jsonObject.put("longList", longJsonArray);

    byte[] encoded = vertxEncode(jsonObject);

    // Decode using Google's protoc plugin
    io.vertx.protobuf.Struct protoJsonObject = io.vertx.protobuf.Struct.parseFrom(encoded);

    // Integer
    Value intJsonArrayValue = protoJsonObject.getFieldsMap().get("intList");
    assertEquals(Value.KindCase.JSON_ARRAY_VALUE, intJsonArrayValue.getKindCase());
    List<Value> intValueList = intJsonArrayValue.getJsonArrayValue().getValuesList();
    assertEquals(3, intValueList.size());

    Value intValue1 = intValueList.get(0);
    assertEquals(1, intValue1.getIntegerValue());
    assertEquals(Value.KindCase.INTEGER_VALUE, intValue1.getKindCase());
    Value intValue2 = intValueList.get(1);
    assertEquals(2, intValue2.getIntegerValue());
    assertEquals(Value.KindCase.INTEGER_VALUE, intValue2.getKindCase());
    Value intValue3 = intValueList.get(2);
    assertEquals(3, intValue3.getIntegerValue());
    assertEquals(Value.KindCase.INTEGER_VALUE, intValue3.getKindCase());

    // String
    Value strJsonArrayValue = protoJsonObject.getFieldsMap().get("strList");
    assertEquals(Value.KindCase.JSON_ARRAY_VALUE, strJsonArrayValue.getKindCase());
    List<Value> strValueList = strJsonArrayValue.getJsonArrayValue().getValuesList();
    assertEquals(3, strValueList.size());

    Value strValue1 = strValueList.get(0);
    assertEquals("One", strValue1.getStringValue());
    assertEquals(Value.KindCase.STRING_VALUE, strValue1.getKindCase());
    Value strValue2 = strValueList.get(1);
    assertEquals("Two", strValue2.getStringValue());
    assertEquals(Value.KindCase.STRING_VALUE, strValue2.getKindCase());
    Value strValue3 = strValueList.get(2);
    assertEquals("Three", strValue3.getStringValue());
    assertEquals(Value.KindCase.STRING_VALUE, strValue3.getKindCase());

    // Long
    Value longJsonArrayValue = protoJsonObject.getFieldsMap().get("longList");
    assertEquals(Value.KindCase.JSON_ARRAY_VALUE, longJsonArrayValue.getKindCase());
    List<Value> longValueList = longJsonArrayValue.getJsonArrayValue().getValuesList();
    assertEquals(3, longValueList.size());

    Value longValue1 = longValueList.get(0);
    assertEquals(1000L, longValue1.getLongValue());
    assertEquals(Value.KindCase.LONG_VALUE, longValue1.getKindCase());
    Value longValue2 = longValueList.get(1);
    assertEquals(2000L, longValue2.getLongValue());
    assertEquals(Value.KindCase.LONG_VALUE, longValue2.getKindCase());
    Value longValue3 = longValueList.get(2);
    assertEquals(3000L, longValue3.getLongValue());
    assertEquals(Value.KindCase.LONG_VALUE, longValue3.getKindCase());

    // Encode using Google's protoc plugin
    byte[] protocEncoded = protocEncode(protoJsonObject);
    assertArrayEquals(protocEncoded, encoded);

    // Vertx Decode
    CodedInputStream input = CodedInputStream.newInstance(protocEncoded);
    JsonObject decoded = VertxStructProtoConverter.fromProto(input);

    assertEquals(jsonObject.getMap(), decoded.getMap());

    // Verify ComputeSize
    Assert.assertEquals(encoded.length, VertxStructProtoConverter.computeSize(jsonObject));
  }

  @Test
  public void TestBinaryField() throws IOException {
    byte[] binary = {0x1, 0x2, 0x3, 0x4, 0x5, 0x6};
    ByteString byteString = ByteString.copyFrom(binary);
    io.vertx.protobuf.Struct proto = io.vertx.protobuf.Struct.newBuilder()
      .putFields("BinaryField", Value.newBuilder().setBinaryValue(byteString)
        .build())
      .build();

    protocEncode(proto);

    JsonObject jsonObject = new JsonObject();
    jsonObject.put("BinaryField", binary);

    // Vertx Encode
    byte[] encoded = vertxEncode(jsonObject);

    // Decode using Google's protoc plugin
    io.vertx.protobuf.Struct protoJsonObject = io.vertx.protobuf.Struct.parseFrom(encoded);

    Value intValue = protoJsonObject.getFieldsMap().get("BinaryField");
    assertEquals(byteString, intValue.getBinaryValue());
    assertEquals(Value.KindCase.BINARY_VALUE, intValue.getKindCase());

    // Encode using Google's protoc plugin
    byte[] protocEncoded = protocEncode(protoJsonObject);
    assertArrayEquals(protocEncoded, encoded);

    // Vertx Decode
    CodedInputStream input = CodedInputStream.newInstance(protocEncoded);
    JsonObject decoded = VertxStructProtoConverter.fromProto(input);

    assertArrayEquals((byte[]) jsonObject.getMap().get("BinaryField"), (byte[]) decoded.getMap().get("BinaryField"));

    // Verify ComputeSize
    Assert.assertEquals(encoded.length, VertxStructProtoConverter.computeSize(jsonObject));
  }

  private byte[] protocEncode(io.vertx.protobuf.Struct obj) throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    CodedOutputStream output = CodedOutputStream.newInstance(baos);
    obj.writeTo(output);
    output.flush();
    byte[] encoded = baos.toByteArray();
    TestUtils.debug("Protoc encoded", encoded);
    return encoded;
  }

  private byte[] vertxEncode(JsonObject obj) throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    CodedOutputStream output = CodedOutputStream.newInstance(baos);
    VertxStructProtoConverter.toProto(obj, output);
    output.flush();
    byte[] encoded = baos.toByteArray();
    TestUtils.debug("Vertx encoded", encoded);
    return encoded;
  }
}
