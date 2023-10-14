package io.vertx.test.codegen.protobuf;

import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.InvalidProtocolBufferException;
import io.vertx.test.codegen.converter.SimplePojo;
import io.vertx.test.codegen.converter.SimplePojoProtoConverter;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;


public class CompatibilityTest {
  @Test
  public void testVertxNullable() throws IOException {
    // Populate a POJO
    SimplePojo pojo = new SimplePojo();
    pojo.setIntegerField(null);
    pojo.setLongField(null);
    pojo.setBooleanField(null);
    pojo.setStringField(null);

    // Vertx Encode
    byte[] encoded = vertxEncode(pojo, false);

    // Vertx Decode
    SimplePojo decoded = vertxDecode(encoded, false);

    // Decoded is exactly the same with original pojo
    Assert.assertEquals(pojo, decoded);

    Assert.assertNull(decoded.getIntegerField());
    Assert.assertNull(decoded.getLongField());
    Assert.assertNull(decoded.getStringField());
    Assert.assertNull(decoded.getBooleanField());

    // Assert total size is equal to computed size
    Assert.assertEquals(encoded.length, SimplePojoProtoConverter.computeSize(pojo));
  }

  @Test
  public void testEncodeCompatibleMode() throws IOException {
    // Populate a POJO
    SimplePojo pojo = new SimplePojo();
    pojo.setIntegerField(0);
    pojo.setLongField(0L);
    pojo.setBooleanField(false);
    pojo.setStringField("");

    // Vertx Encode
    byte[] encoded = vertxEncode(pojo, true);

    // Protoc Decode
    io.vertx.protobuf.generated.SimplePojo decoded = protocDecode(encoded);

    Assert.assertEquals(0, decoded.getIntegerField());
    Assert.assertEquals(0, decoded.getLongField());
    Assert.assertFalse(decoded.getBooleanField());
    Assert.assertEquals("", decoded.getStringField());
  }

  @Test
  public void testDecodeCompatibleMode() throws IOException {
    // Populate a Protoc generated POJO
    io.vertx.protobuf.generated.SimplePojo pojo = io.vertx.protobuf.generated.SimplePojo.newBuilder()
      .setIntegerField(0)
      .setLongField(0L)
      .setBooleanField(false)
      .setStringField("")
      .build();

    // Protoc Encode
    byte[] encoded = protocEncode(pojo);

    // Vertx Decode
    SimplePojo decoded = vertxDecode(encoded, true);

    Assert.assertEquals((Integer)0, decoded.getIntegerField());
    Assert.assertEquals((Long)0L, decoded.getLongField());
    Assert.assertEquals(false, decoded.getBooleanField());
    Assert.assertEquals("", decoded.getStringField());
  }

  // This test case demonstrates what happens when we decode data from Google Protocol Buffers without using compatible mode
  @Test
  public void testDecodeProtocUsingVertxNullable() throws IOException {
    // Populate a Protoc generated POJO
    io.vertx.protobuf.generated.SimplePojo pojo = io.vertx.protobuf.generated.SimplePojo.newBuilder()
      .setIntegerField(0)
      .setLongField(0L)
      .setBooleanField(false)
      .setStringField("")
      .build();

    // Protoc Encode
    byte[] encoded = protocEncode(pojo);

    // Vertx Decode
    SimplePojo decoded = vertxDecode(encoded, false);

    Assert.assertNull(decoded.getIntegerField()); // Should be 0, but become null due to wrong mode being used
    Assert.assertNull(decoded.getLongField()); // Should be 0, but become null due to wrong mode being used
    Assert.assertNull(decoded.getBooleanField()); // Should be false, but become null due to wrong mode being used
    Assert.assertNull(decoded.getStringField()); // Should be empty string, but become null due to wrong mode being used
  }

  @Test
  public void testEncodeNullFieldInCompatibleMode() throws IOException {
    // Populate a POJO
    SimplePojo pojo = new SimplePojo();
    pojo.setIntegerField(null);
    pojo.setLongField(null);
    pojo.setBooleanField(null);
    pojo.setStringField(null);

    // Null field are not allowed in compatible mode
    try {
      vertxEncode(pojo, true);
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Null values are not allowed for boxed types in compatibility mode", e.getMessage());
    }
  }

  private byte[] vertxEncode(SimplePojo obj, boolean compatibleMode) throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    CodedOutputStream output = CodedOutputStream.newInstance(baos);
    SimplePojoProtoConverter.toProto(obj, output, compatibleMode);
    output.flush();
    byte[] encoded = baos.toByteArray();
    TestUtils.debug("Vertx encoded", encoded);
    return encoded;
  }

  private byte[] protocEncode(io.vertx.protobuf.generated.SimplePojo obj) throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    CodedOutputStream output = CodedOutputStream.newInstance(baos);
    obj.writeTo(output);
    output.flush();
    byte[] encoded = baos.toByteArray();
    TestUtils.debug("Protoc encoded", encoded);
    return encoded;
  }

  private io.vertx.protobuf.generated.SimplePojo protocDecode(byte[] arr) throws InvalidProtocolBufferException {
    return io.vertx.protobuf.generated.SimplePojo.parseFrom(arr);
  }

  private SimplePojo vertxDecode(byte[] arr, boolean compatibleMode) throws IOException {
    CodedInputStream input = CodedInputStream.newInstance(arr);
    SimplePojo obj = new SimplePojo();
    SimplePojoProtoConverter.fromProto(input, obj, compatibleMode);
    return obj;
  }
}
