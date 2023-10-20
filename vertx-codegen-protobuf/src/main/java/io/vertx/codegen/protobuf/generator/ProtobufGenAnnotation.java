package io.vertx.codegen.protobuf.generator;

import io.vertx.codegen.DataObjectModel;
import io.vertx.codegen.Model;
import io.vertx.codegen.protobuf.annotations.FieldNumberStrategy;
import io.vertx.codegen.protobuf.annotations.JsonProtoEncoding;
import io.vertx.codegen.protobuf.annotations.ProtobufGen;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

class ProtobufGenAnnotation {
  static JsonProtoEncoding jsonProtoEncoding(DataObjectModel model) {
    return model.getAnnotation(ProtobufGen.class)
      .map(ann -> ann.getMember("jsonProtoEncoding"))
      .map(v -> JsonProtoEncoding.valueOf((String) v))
      .orElse(JsonProtoEncoding.VERTX_STRUCT); // Default to VERTX_STRUCT
  }

  static FieldNumberStrategy fieldNumberStrategy(Model model) {
    return model.getAnnotation(ProtobufGen.class)
      .map(ann -> (String) ann.getMember("fieldNumberStrategy"))
      .map(FieldNumberStrategy::valueOf)
      .orElseThrow(NoSuchElementException::new); // the annotation member is mandatory, so this should never happen
  }

  static Set<Integer> reservedFieldNumbers(Model model) {
    return model.getAnnotation(ProtobufGen.class)
      .map(ann -> (List<Integer>) ann.getMember("reservedFieldNumbers"))
      .map(HashSet::new)
      .orElseGet(HashSet::new);
  }

  static Set<String> reservedFieldNames(Model model) {
    return model.getAnnotation(ProtobufGen.class)
      .map(ann -> (List<String>) ann.getMember("reservedFieldNames"))
      .map(HashSet::new)
      .orElseGet(HashSet::new);
  }
}
