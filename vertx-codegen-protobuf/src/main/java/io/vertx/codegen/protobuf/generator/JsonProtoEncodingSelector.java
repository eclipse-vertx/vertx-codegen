package io.vertx.codegen.protobuf.generator;

import io.vertx.codegen.DataObjectModel;
import io.vertx.codegen.protobuf.annotations.JsonProtoEncoding;
import io.vertx.codegen.protobuf.annotations.ProtobufGen;

import java.util.Optional;

// Extract JsonProtoEncoding from Annotation attribute ProtobufGen.jsonProtoEncoding()
public class JsonProtoEncodingSelector {
  static public JsonProtoEncoding select(DataObjectModel model) {
    Optional<Object> opMember = model.getAnnotations()
      .stream()
      .filter(ann -> ann.getName().equals(ProtobufGen.class.getName()))
      .findFirst()
      .map(ann -> ann.getMember("jsonProtoEncoding"));

    return opMember
      .map(v -> JsonProtoEncoding.valueOf((String) v))
      .orElse(JsonProtoEncoding.VERTX_STRUCT); // Default to VERTX_STRUCT
  }
}
