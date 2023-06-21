package io.vertx.test.codegen.converter.proto;

import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.proto.JsonObjectConverter;
import io.vertx.protobuf.Value;
import io.vertx.test.codegen.converter.TestUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.Instant;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class JsonObjectConverterTest {

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
    io.vertx.protobuf.JsonObject protoJsonObject = io.vertx.protobuf.JsonObject.parseFrom(encoded);

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
    JsonObject decoded = JsonObjectConverter.fromProto(input);

    assertEquals(jsonObject.getMap(), decoded.getMap());

    // Verify ComputeSize
    Assert.assertEquals(encoded.length, JsonObjectConverter.computeSize(jsonObject));
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
    io.vertx.protobuf.JsonObject protoJsonObject = io.vertx.protobuf.JsonObject.parseFrom(encoded);

    Value jsonObjectValue = protoJsonObject.getFieldsMap().get("jsonObjectField");
    assertEquals(Value.KindCase.JSON_OBJECT_VALUE, jsonObjectValue.getKindCase());
    io.vertx.protobuf.JsonObject subJsonObject = jsonObjectValue.getJsonObjectValue();

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
    JsonObject decoded = JsonObjectConverter.fromProto(input);

    assertEquals(jsonObject.getMap(), decoded.getMap());

    // Verify ComputeSize
    Assert.assertEquals(encoded.length, JsonObjectConverter.computeSize(jsonObject));
  }

  @Test
  public void TestIntegerJsonArray() throws IOException {
    JsonObject jsonObject = new JsonObject();
    JsonArray jsonArray = new JsonArray();
    jsonArray.add(1);
    jsonArray.add(2);
    jsonArray.add(3);
    jsonObject.put("intList", jsonArray);

    byte[] encoded = vertxEncode(jsonObject);

    // Decode using Google's protoc plugin
    io.vertx.protobuf.JsonObject protoJsonObject = io.vertx.protobuf.JsonObject.parseFrom(encoded);

    Value jsonObjectValue = protoJsonObject.getFieldsMap().get("intList");
    assertEquals(Value.KindCase.JSON_ARRAY_VALUE, jsonObjectValue.getKindCase());
    io.vertx.protobuf.JsonArray subJsonArray = jsonObjectValue.getJsonArrayValue();
    List<Value> valueList = subJsonArray.getValuesList();
    assertEquals(3, valueList.size());

    Value intValue1 = valueList.get(0);
    assertEquals(1, intValue1.getIntegerValue());
    assertEquals(Value.KindCase.INTEGER_VALUE, intValue1.getKindCase());

    Value intValue2 = valueList.get(1);
    assertEquals(2, intValue2.getIntegerValue());
    assertEquals(Value.KindCase.INTEGER_VALUE, intValue2.getKindCase());

    Value intValue3 = valueList.get(2);
    assertEquals(3, intValue3.getIntegerValue());
    assertEquals(Value.KindCase.INTEGER_VALUE, intValue3.getKindCase());

    // Encode using Google's protoc plugin
    byte[] protocEncoded = protocEncode(protoJsonObject);
    assertArrayEquals(protocEncoded, encoded);

    // Vertx Decode
    CodedInputStream input = CodedInputStream.newInstance(protocEncoded);
    JsonObject decoded = JsonObjectConverter.fromProto(input);

    assertEquals(jsonObject.getMap(), decoded.getMap());

    // Verify ComputeSize
    Assert.assertEquals(encoded.length, JsonObjectConverter.computeSize(jsonObject));
  }
  @Test
  public void TestStringJsonArray() throws IOException {
    JsonObject jsonObject = new JsonObject();
    JsonArray jsonArray = new JsonArray();
    jsonArray.add("One");
    jsonArray.add("Two");
    jsonArray.add("Three");
    jsonObject.put("strList", jsonArray);

    byte[] encoded = vertxEncode(jsonObject);

    // Decode using Google's protoc plugin
    io.vertx.protobuf.JsonObject protoJsonObject = io.vertx.protobuf.JsonObject.parseFrom(encoded);

    Value jsonObjectValue = protoJsonObject.getFieldsMap().get("strList");
    assertEquals(Value.KindCase.JSON_ARRAY_VALUE, jsonObjectValue.getKindCase());
    io.vertx.protobuf.JsonArray subJsonArray = jsonObjectValue.getJsonArrayValue();
    List<Value> valueList = subJsonArray.getValuesList();
    assertEquals(3, valueList.size());

    Value strValue1 = valueList.get(0);
    assertEquals("One", strValue1.getStringValue());
    assertEquals(Value.KindCase.STRING_VALUE, strValue1.getKindCase());

    Value strValue2 = valueList.get(1);
    assertEquals("Two", strValue2.getStringValue());
    assertEquals(Value.KindCase.STRING_VALUE, strValue2.getKindCase());

    Value strValue3 = valueList.get(2);
    assertEquals("Three", strValue3.getStringValue());
    assertEquals(Value.KindCase.STRING_VALUE, strValue3.getKindCase());

    // Encode using Google's protoc plugin
    byte[] protocEncoded = protocEncode(protoJsonObject);
    assertArrayEquals(protocEncoded, encoded);

    // Vertx Decode
    CodedInputStream input = CodedInputStream.newInstance(protocEncoded);
    JsonObject decoded = JsonObjectConverter.fromProto(input);

    assertEquals(jsonObject.getMap(), decoded.getMap());

    // Verify ComputeSize
    Assert.assertEquals(encoded.length, JsonObjectConverter.computeSize(jsonObject));
  }

  private byte[] protocEncode(io.vertx.protobuf.JsonObject obj) throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    CodedOutputStream output = CodedOutputStream.newInstance(baos);
    obj.writeTo(output);
    output.flush();
    byte[] encoded = baos.toByteArray();
    System.out.println("protoc encoded:\n" + TestUtils.prettyHexDump(encoded));
    return encoded;
  }

  private byte[] vertxEncode(JsonObject obj) throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    CodedOutputStream output = CodedOutputStream.newInstance(baos);
    JsonObjectConverter.toProto(obj, output);
    output.flush();
    byte[] encoded = baos.toByteArray();
    System.out.println("vertx encoded:\n" + TestUtils.prettyHexDump(encoded));
    return encoded;
  }
}
