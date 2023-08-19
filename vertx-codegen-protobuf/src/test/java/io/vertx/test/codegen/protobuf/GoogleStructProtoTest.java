package io.vertx.test.codegen.protobuf;

import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.Struct;
import com.google.protobuf.Value;
import io.vertx.codegen.protobuf.converters.GoogleStructProtoConverter;
import io.vertx.codegen.protobuf.converters.JsonObjectProtoConverter;
import io.vertx.core.json.JsonObject;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.Assert.assertArrayEquals;

public class GoogleStructProtoTest {
  @Test
  public void TestJsonObject() throws IOException {
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
