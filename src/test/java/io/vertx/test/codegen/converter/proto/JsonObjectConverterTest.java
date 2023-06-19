package io.vertx.test.codegen.converter.proto;

import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.Struct;
import com.google.protobuf.Value;
import io.vertx.core.json.JsonObject;
import io.vertx.core.proto.JsonObjectConverter;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static io.vertx.test.codegen.converter.proto.ProtoConverterTest.prettyHexDump;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class JsonObjectConverterTest {

  @Test
  public void TestJsonObject() throws IOException {
    JsonObject jsonObject = new JsonObject();
    jsonObject.put("IntegerField1", 15);
    jsonObject.put("StringField2", "StringValue");
    jsonObject.put("BooleanField3", true);
    jsonObject.put("DoubleField4", 3.142);
    jsonObject.put("LongField8", 20000L);

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    CodedOutputStream output = CodedOutputStream.newInstance(baos);
    JsonObjectConverter.toProto(jsonObject, output);
    output.flush();

    // Vertx Encode
    byte[] encoded = baos.toByteArray();
    System.out.println("vertx encoded:\n" + prettyHexDump(encoded));

    // Decode using Google's protoc plugin
    Struct struct = Struct.parseFrom(encoded);

    Value intValue = struct.getFieldsMap().get("IntegerField1");
    assertEquals(15, intValue.getNullValueValue());
    assertEquals(Value.KindCase.NULL_VALUE, intValue.getKindCase());

    Value longValue = struct.getFieldsMap().get("LongField8");
    assertEquals(20000L, longValue.getLongValue());
    assertEquals(Value.KindCase.LONG_VALUE, longValue.getKindCase());

    Value stringValue = struct.getFieldsMap().get("StringField2");
    assertEquals("StringValue", stringValue.getStringValue());
    assertEquals(Value.KindCase.STRING_VALUE, stringValue.getKindCase());

    Value boolValue = struct.getFieldsMap().get("BooleanField3");
    assertTrue(boolValue.getBoolValue());
    assertEquals(Value.KindCase.BOOL_VALUE, boolValue.getKindCase());

    Value doubleValue = struct.getFieldsMap().get("DoubleField4");
    assertEquals(3.142, doubleValue.getNumberValue(), 0.0);
    assertEquals(Value.KindCase.NUMBER_VALUE, doubleValue.getKindCase());

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
    jsonObjectField.put("IntegerField1", 100);
    jsonObjectField.put("StringField2", "sub-string");
    jsonObject.put("structField5", jsonObjectField);

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    CodedOutputStream output = CodedOutputStream.newInstance(baos);
    JsonObjectConverter.toProto(jsonObject, output);
    output.flush();

    // Vertx Encode
    byte[] encoded = baos.toByteArray();
    System.out.println("vertx encoded:\n" + prettyHexDump(encoded));

    // Decode using Google's protoc plugin
    Struct struct = Struct.parseFrom(encoded);

    Value structValue = struct.getFieldsMap().get("structField5");
    assertEquals(Value.KindCase.STRUCT_VALUE, structValue.getKindCase());
    Struct subStruct = structValue.getStructValue();

    Value intValue = subStruct.getFieldsMap().get("IntegerField1");
    assertEquals(100, intValue.getNullValueValue());
    assertEquals(Value.KindCase.NULL_VALUE, intValue.getKindCase());

    Value stringValue = subStruct.getFieldsMap().get("StringField2");
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
    System.out.println("protoc encoded:\n" + prettyHexDump(encoded));
    return encoded;
  }
}
