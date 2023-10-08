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
  public void testVertxConverter() throws IOException {
    // Populate a POJO
    SimplePojo pojo = new SimplePojo();
    pojo.setNullInteger(null);
    pojo.setNullBoolean(null);
    pojo.setNullString(null);
    pojo.setZeroInteger(0);
    pojo.setZeroBoolean(false);
    pojo.setZeroString("");
    pojo.setPrimitiveInteger(0);
    pojo.setPrimitiveBoolean(false);

    // Vertx Encode
    byte[] encoded = vertxEncode(pojo);

    // Vertx Decode
    SimplePojo decoded = vertxDecode(encoded, false);

    // Decoded is exactly the same with original pojo
    Assert.assertEquals(pojo, decoded);

    Assert.assertEquals(null, decoded.getNullInteger());
    Assert.assertEquals(null, decoded.getNullBoolean());
    Assert.assertEquals(null, decoded.getNullString());
    Assert.assertEquals((Integer) 0, decoded.getZeroInteger());
    Assert.assertEquals(false, decoded.getZeroBoolean());
    Assert.assertEquals("", decoded.getZeroString());
    Assert.assertEquals(0, decoded.getPrimitiveInteger());
    Assert.assertEquals(false, decoded.isPrimitiveBoolean());

    // Assert total size is equal to computed size
    Assert.assertEquals(encoded.length, SimplePojoProtoConverter.computeSize(pojo));
  }

  @Test
  public void testProtocDecode() throws IOException {
    // Populate a POJO
    SimplePojo pojo = new SimplePojo();
    pojo.setNullInteger(null);
    pojo.setNullBoolean(null);
    pojo.setNullString(null);
    pojo.setZeroInteger(0);
    pojo.setZeroBoolean(false);
    pojo.setZeroString("");
    pojo.setPrimitiveInteger(0);
    pojo.setPrimitiveBoolean(false);

    // Vertx Encode
    byte[] encoded = vertxEncode(pojo);

    // Protoc Decode
    io.vertx.protobuf.generated.SimplePojo decoded = protocDecode(encoded);

    Assert.assertEquals(0, decoded.getNullInteger()); // Integer in Google Protoc is not nullable
    Assert.assertEquals(false, decoded.getNullBoolean()); // Boolean in Google Protoc is not nullable
    Assert.assertEquals("", decoded.getNullString()); // String in Google Protoc is not nullable
    Assert.assertEquals(0, decoded.getZeroInteger());
    Assert.assertEquals(false, decoded.getZeroBoolean());
    Assert.assertEquals("", decoded.getZeroString());
    Assert.assertEquals(0, decoded.getPrimitiveInteger());
    Assert.assertEquals(false, decoded.getPrimitiveBoolean());
  }

    @Test
  public void testProtocEncode() throws IOException {
    // Populate a Protoc generated POJO
    io.vertx.protobuf.generated.SimplePojo pojo = io.vertx.protobuf.generated.SimplePojo.newBuilder()
      .setNullInteger(0) // cannot set null integer, won't compile
      .setNullBoolean(false) // cannot set null boolean, won't compile
      .setNullString("") // cannot set null string, will throw NPE
      .setZeroInteger(0)
      .setZeroBoolean(false)
      .setZeroString("")
      .setPrimitiveInteger(0)
      .setPrimitiveBoolean(false)
      .build();

    // Protoc Encode
    byte[] encoded = protocEncode(pojo);

    // Vertx Decode
    SimplePojo decoded = vertxDecode(encoded, true);

    Assert.assertEquals((Integer)0, decoded.getNullInteger());
    Assert.assertEquals(false, decoded.getNullBoolean());
    Assert.assertEquals("", decoded.getNullString());
    Assert.assertEquals((Integer)0, decoded.getZeroInteger());
    Assert.assertEquals(false, decoded.getZeroBoolean());
    Assert.assertEquals("", decoded.getZeroString());
    Assert.assertEquals(0, decoded.getPrimitiveInteger());
    Assert.assertEquals(false, decoded.isPrimitiveBoolean());
  }

  private byte[] vertxEncode(SimplePojo obj) throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    CodedOutputStream output = CodedOutputStream.newInstance(baos);
    SimplePojoProtoConverter.toProto(obj, output);
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
