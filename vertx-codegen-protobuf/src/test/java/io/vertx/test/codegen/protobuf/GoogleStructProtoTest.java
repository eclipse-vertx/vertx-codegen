package io.vertx.test.codegen.protobuf;

import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.NullValue;
import com.google.protobuf.Struct;
import com.google.protobuf.Value;
import io.vertx.codegen.protobuf.converters.GoogleStructProtoConverter;
import io.vertx.core.json.JsonObject;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GoogleStructProtoTest {
  @Test
  public void TestJsonObject() throws IOException {
    JsonObject subJson = new JsonObject();
    subJson.put("SubString", "SubStringValue");
    subJson.put("SubInteger", 100);

    JsonObject jsonObject = new JsonObject();
    jsonObject.put("StringField", "StringValue");
    jsonObject.put("IntegerField", 15);
    jsonObject.put("BooleanField", true);
    jsonObject.put("LongField", 1000L);
    jsonObject.put("ShortField", (short) 8);
    jsonObject.put("DoubleField", 33333.33);
    jsonObject.put("FloatField", 666.66f);
    jsonObject.put("NullField", null);
    jsonObject.put("JsonObjectField", subJson);

    // Vertx Encode
    byte[] encoded = vertxEncode(jsonObject);

    // Decode using Google's protoc plugin
    com.google.protobuf.Struct structObject = com.google.protobuf.Struct.parseFrom(encoded);

    com.google.protobuf.Value stringValue = structObject.getFieldsMap().get("StringField");
    assertEquals("StringValue", stringValue.getStringValue());
    assertEquals(com.google.protobuf.Value.KindCase.STRING_VALUE, stringValue.getKindCase());

    com.google.protobuf.Value integerValue = structObject.getFieldsMap().get("IntegerField");
    assertEquals(15.0, integerValue.getNumberValue(), 0.0);
    assertEquals(Value.KindCase.NUMBER_VALUE, integerValue.getKindCase());

    com.google.protobuf.Value booleanValue = structObject.getFieldsMap().get("BooleanField");
    assertTrue(booleanValue.getBoolValue());
    assertEquals(Value.KindCase.BOOL_VALUE, booleanValue.getKindCase());

    com.google.protobuf.Value longValue = structObject.getFieldsMap().get("LongField");
    assertEquals(1000.0, longValue.getNumberValue(), 0.0);
    assertEquals(Value.KindCase.NUMBER_VALUE, longValue.getKindCase());

    com.google.protobuf.Value shortValue = structObject.getFieldsMap().get("ShortField");
    assertEquals(8.0, shortValue.getNumberValue(), 0.0);
    assertEquals(Value.KindCase.NUMBER_VALUE, shortValue.getKindCase());

    com.google.protobuf.Value doubleValue = structObject.getFieldsMap().get("DoubleField");
    assertEquals(33333.33, doubleValue.getNumberValue(), 0.0);
    assertEquals(Value.KindCase.NUMBER_VALUE, doubleValue.getKindCase());

    com.google.protobuf.Value floatValue = structObject.getFieldsMap().get("FloatField");
    assertEquals(666.66, floatValue.getNumberValue(), 0.01);
    assertEquals(Value.KindCase.NUMBER_VALUE, floatValue.getKindCase());

    com.google.protobuf.Value nullValue = structObject.getFieldsMap().get("NullField");
    assertEquals(NullValue.NULL_VALUE, nullValue.getNullValue());
    assertEquals(Value.KindCase.NULL_VALUE, nullValue.getKindCase());

    com.google.protobuf.Value jsonObjectValue = structObject.getFieldsMap().get("JsonObjectField");
    assertEquals(Value.KindCase.STRUCT_VALUE, jsonObjectValue.getKindCase());
    com.google.protobuf.Struct subStruct = jsonObjectValue.getStructValue();

    com.google.protobuf.Value subStringValue = subStruct.getFieldsMap().get("SubString");
    assertEquals(com.google.protobuf.Value.KindCase.STRING_VALUE, subStringValue.getKindCase());
    assertEquals("SubStringValue", subStringValue.getStringValue());

    com.google.protobuf.Value subIntegerValue = subStruct.getFieldsMap().get("SubInteger");
    assertEquals(com.google.protobuf.Value.KindCase.NUMBER_VALUE, subIntegerValue.getKindCase());
    assertEquals(100.0, subIntegerValue.getNumberValue(), 0.0);

    // Encode using Google's protoc plugin
    byte[] protocEncoded = protocEncode(structObject);
    assertArrayEquals(protocEncoded, encoded);

    // Vertx Decode
    CodedInputStream input = CodedInputStream.newInstance(protocEncoded);
    JsonObject decoded = GoogleStructProtoConverter.fromProto(input);

    // NOTE: JsonObject no longer the same due to problem with float
    // assertEquals(jsonObject.getMap(), decoded.getMap());

    assertEquals(jsonObject.getMap().get("StringField"), decoded.getMap().get("StringField"));
    assertEquals(Double.valueOf((Integer) jsonObject.getMap().get("IntegerField")),
      (Double) decoded.getMap().get("IntegerField"), 0.0);
    assertEquals(jsonObject.getMap().get("BooleanField"), decoded.getMap().get("BooleanField"));
    assertEquals(Double.valueOf((Long) jsonObject.getMap().get("LongField")),
      (Double) decoded.getMap().get("LongField"), 0.0);
    assertEquals(Double.valueOf((Short) jsonObject.getMap().get("ShortField")),
      (Double) decoded.getMap().get("ShortField"), 0.0);
    assertEquals((Double) jsonObject.getMap().get("DoubleField"),
      (Double) decoded.getMap().get("DoubleField"), 0.0);
    assertEquals(Double.valueOf((Float) jsonObject.getMap().get("FloatField")),
      (Double) decoded.getMap().get("FloatField"), 0.0);
    assertEquals(jsonObject.getMap().get("NullField"), decoded.getMap().get("NullField"));

    // Verify ComputeSize
    Assert.assertEquals(encoded.length, GoogleStructProtoConverter.computeSize(jsonObject));
  }

  @Test
  public void TestBed() throws IOException {
    JsonObject subJson = new JsonObject();
    subJson.put("SubString", "StringValue");
    subJson.put("SubInteger", 100);

    JsonObject jsonObject = new JsonObject();
//    jsonObject.put("StringField", "StringValue");
//    jsonObject.put("IntegerField", 15);
//    jsonObject.put("ShortField", 15);
//    jsonObject.put("LongField", 1000L);
//    jsonObject.put("DoubleField", 3.142d);
//    jsonObject.put("FloatField", 3.142f);
//    jsonObject.put("BooleanField", true);
    jsonObject.put("JsonObjectField", subJson);

    // Vertx Encode
    byte[] encoded = vertxEncode(jsonObject);

    // Decode using Google's protoc plugin
//    io.vertx.protobuf.JsonObject protoJsonObject = io.vertx.protobuf.JsonObject.parseFrom(encoded);

    com.google.protobuf.Struct struct = Struct.newBuilder()
//      .putFields("StringField", com.google.protobuf.Value.newBuilder().setStringValue("StringValue").build())
//      .putFields("IntegerField", com.google.protobuf.Value.newBuilder().setNumberValue(15).build())
//      .putFields("ShortField", com.google.protobuf.Value.newBuilder().setNumberValue(15).build())
//      .putFields("LongField", com.google.protobuf.Value.newBuilder().setNumberValue(1000L).build())
//      .putFields("DoubleField", com.google.protobuf.Value.newBuilder().setNumberValue(3.142d).build())
//      .putFields("FloatField", com.google.protobuf.Value.newBuilder().setNumberValue(3.142f).build())
//      .putFields("BooleanField", com.google.protobuf.Value.newBuilder().setBoolValue(true).build())
      .putFields("JsonObjectField", com.google.protobuf.Value.newBuilder()
        .setStructValue(com.google.protobuf.Struct.newBuilder()
          .putFields("SubString", com.google.protobuf.Value.newBuilder().setStringValue("StringValue").build())
          .putFields("SubInteger", com.google.protobuf.Value.newBuilder().setNumberValue(100).build())
          .build()).build())
      .build();

    byte[] protocEncoded = protocEncode(struct);

    assertArrayEquals(protocEncoded, encoded);

    // Verify ComputeSize
    Assert.assertEquals(encoded.length, GoogleStructProtoConverter.computeSize(jsonObject));
  }

  private byte[] vertxEncode(JsonObject obj) throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    CodedOutputStream output = CodedOutputStream.newInstance(baos);
    GoogleStructProtoConverter.toProto(obj, output);
    output.flush();
    byte[] encoded = baos.toByteArray();
    TestUtils.debug("Vertx encoded", encoded);
    return encoded;
  }

  private byte[] protocEncode(com.google.protobuf.Struct obj) throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    CodedOutputStream output = CodedOutputStream.newInstance(baos);
    obj.writeTo(output);
    output.flush();
    byte[] encoded = baos.toByteArray();
    TestUtils.debug("Protoc encoded", encoded);
    return encoded;
  }
}
