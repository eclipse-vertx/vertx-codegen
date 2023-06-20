package io.vertx.test.codegen.converter.proto;

import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.Struct;
import com.google.protobuf.Value;
import io.vertx.core.json.JsonObject;
import io.vertx.core.proto.JsonObjectConverter;
import io.vertx.test.codegen.converter.TestUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.Instant;

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
    Struct struct = Struct.parseFrom(encoded);

    Value intValue = struct.getFieldsMap().get("IntegerField");
    assertEquals(15, intValue.getIntegerValue());
    assertEquals(Value.KindCase.INTEGER_VALUE, intValue.getKindCase());

    Value longValue = struct.getFieldsMap().get("LongField");
    assertEquals(20000L, longValue.getLongValue());
    assertEquals(Value.KindCase.LONG_VALUE, longValue.getKindCase());

    Value stringValue = struct.getFieldsMap().get("StringField");
    assertEquals("StringValue", stringValue.getStringValue());
    assertEquals(Value.KindCase.STRING_VALUE, stringValue.getKindCase());

    Value boolValue = struct.getFieldsMap().get("BooleanField");
    assertTrue(boolValue.getBoolValue());
    assertEquals(Value.KindCase.BOOL_VALUE, boolValue.getKindCase());

    Value doubleValue = struct.getFieldsMap().get("DoubleField");
    assertEquals(3.142, doubleValue.getDoubleValue(), 0.0);
    assertEquals(Value.KindCase.DOUBLE_VALUE, doubleValue.getKindCase());

    Value floatValue = struct.getFieldsMap().get("FloatField");
    assertEquals(8.8888f, floatValue.getFloatValue(), 0.0);
    assertEquals(Value.KindCase.FLOAT_VALUE, floatValue.getKindCase());

    Value instantValue = struct.getFieldsMap().get("InstantField");
    assertEquals(now.getNano(), instantValue.getInstantValue().getNanos());
    assertEquals(now.getEpochSecond(), instantValue.getInstantValue().getSeconds());
    assertEquals(Value.KindCase.INSTANT_VALUE, instantValue.getKindCase());

    // Encode using Google's protoc plugin
    byte[] protocEncoded = protocEncode(struct);
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
    jsonObject.put("structField", jsonObjectField);

    // Vertx Encode
    byte[] encoded = vertxEncode(jsonObject);

    // Decode using Google's protoc plugin
    Struct struct = Struct.parseFrom(encoded);

    Value structValue = struct.getFieldsMap().get("structField");
    assertEquals(Value.KindCase.STRUCT_VALUE, structValue.getKindCase());
    Struct subStruct = structValue.getStructValue();

    Value intValue = subStruct.getFieldsMap().get("IntegerField");
    assertEquals(100, intValue.getIntegerValue());
    assertEquals(Value.KindCase.INTEGER_VALUE, intValue.getKindCase());

    Value stringValue = subStruct.getFieldsMap().get("StringField");
    assertEquals("sub-string", stringValue.getStringValue());
    assertEquals(Value.KindCase.STRING_VALUE, stringValue.getKindCase());

    // Encode using Google's protoc plugin
    byte[] protocEncoded = protocEncode(struct);
    assertArrayEquals(protocEncoded, encoded);

    // Vertx Decode
    CodedInputStream input = CodedInputStream.newInstance(protocEncoded);
    JsonObject decoded = JsonObjectConverter.fromProto(input);

    assertEquals(jsonObject.getMap(), decoded.getMap());

    // Verify ComputeSize
    Assert.assertEquals(encoded.length, JsonObjectConverter.computeSize(jsonObject));
  }

  private byte[] protocEncode(Struct obj) throws IOException {
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
