package io.vertx.test.codegen.converter.proto;

import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.InvalidProtocolBufferException;
import io.vertx.test.codegen.converter.RecursiveItem;
import io.vertx.test.codegen.converter.RecursiveItemProtoConverter;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class RecursiveProtoTest {
  @Test
  public void testRecursiveItem() throws IOException {
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

    // Vertx Encode
    byte[] encoded = vertxEncode(root);

    // Decode using Google's protoc plugin
    io.vertx.protobuf.generated.RecursiveItem protocObj = protocDecode(encoded);

    // Encode using Google's protoc plugin
    byte[] protocEncoded = protocEncode(protocObj);

    // Vertx Decode
    RecursiveItem decoded = vertxDecode(protocEncoded);

    // Assert decoded is same with original
    assertEquals(root, decoded);

    // Assert total size is equal to computed size
    Assert.assertEquals(encoded.length, RecursiveItemProtoConverter.computeSize(root));
  }

  private byte[] vertxEncode(RecursiveItem obj) throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    CodedOutputStream output = CodedOutputStream.newInstance(baos);
    RecursiveItemProtoConverter.toProto(obj, output);
    output.flush();
    byte[] encoded = baos.toByteArray();
    TestUtils.debug("Vertx encoded", encoded);
    return encoded;
  }

  private byte[] protocEncode(io.vertx.protobuf.generated.RecursiveItem obj) throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    CodedOutputStream output = CodedOutputStream.newInstance(baos);
    obj.writeTo(output);
    output.flush();
    byte[] encoded = baos.toByteArray();
    TestUtils.debug("Protoc encoded", encoded);
    return encoded;
  }

  private io.vertx.protobuf.generated.RecursiveItem protocDecode(byte[] arr) throws InvalidProtocolBufferException {
    return io.vertx.protobuf.generated.RecursiveItem.parseFrom(arr);
  }

  private RecursiveItem vertxDecode(byte[] arr) throws IOException {
    CodedInputStream input = CodedInputStream.newInstance(arr);
    RecursiveItem obj = new RecursiveItem();
    RecursiveItemProtoConverter.fromProto(input, obj);
    return obj;
  }
}
