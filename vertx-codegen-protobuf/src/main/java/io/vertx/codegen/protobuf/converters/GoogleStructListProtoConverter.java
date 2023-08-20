package io.vertx.codegen.protobuf.converters;

import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import io.vertx.core.json.JsonArray;

import java.io.IOException;

import static com.google.protobuf.WireFormat.WIRETYPE_LENGTH_DELIMITED;
import static io.vertx.codegen.protobuf.converters.GoogleStructProtoConverter.NUMBER_FIELD_NUMBER;
import static io.vertx.codegen.protobuf.converters.GoogleStructProtoConverter.NUMBER_TAG;
import static io.vertx.codegen.protobuf.converters.GoogleStructProtoConverter.STRING_FIELD_NUMBER;
import static io.vertx.codegen.protobuf.converters.GoogleStructProtoConverter.STRING_TAG;
import static io.vertx.codegen.protobuf.converters.GoogleStructProtoConverter.TOP_LEVEL_FIELD_NUMBER;
import static io.vertx.codegen.protobuf.converters.GoogleStructProtoConverter.TOP_LEVEL_TAG;

public class GoogleStructListProtoConverter {
  public static JsonArray fromProto(CodedInputStream input) throws IOException {
    JsonArray array = new JsonArray();
    int tag;
    while ((tag = input.readTag()) != 0) {
      if (tag != TOP_LEVEL_TAG) {
        throw new UnsupportedOperationException("Unsupported tag " + tag);
      }

      int length = input.readUInt32();
      int limit = input.pushLimit(length);

      int fieldType = input.readTag();
      switch (fieldType) {
        case STRING_TAG:
          array.add(input.readString());
          break;
        case NUMBER_TAG:
          array.add(input.readDouble());
          break;
        default:
          throw new UnsupportedOperationException("Unsupported field type " + fieldType);
      }

      input.popLimit(limit);
    }
    return array;
  }

  public static void toProto(JsonArray array, CodedOutputStream output) throws IOException {
    for (Object value : array.getList()) {
      // Calculate value length
      int valueLength = 0;
      if (value == null) {
        throw new UnsupportedOperationException("Unsupported null type");
      } else if (value instanceof String) {
        valueLength = CodedOutputStream.computeStringSize(STRING_FIELD_NUMBER, (String) value);
      } else if (value instanceof Integer){
        valueLength = CodedOutputStream.computeDoubleSize(NUMBER_FIELD_NUMBER, (Integer) value);
      } else if (value instanceof Long) {
        valueLength = CodedOutputStream.computeDoubleSize(NUMBER_FIELD_NUMBER, (Long) value);
      } else {
        throw new UnsupportedOperationException("Unsupported type " + value.getClass().getTypeName());
      }

      output.writeTag(TOP_LEVEL_FIELD_NUMBER, WIRETYPE_LENGTH_DELIMITED);   // top-level tag, always 0xa
      output.writeUInt32NoTag(valueLength);                                 // value length

      if (value instanceof String) {
        output.writeString(STRING_FIELD_NUMBER, (String) value);
      } else if (value instanceof Integer){
        output.writeDouble(NUMBER_FIELD_NUMBER, (Integer) value);
      } else if (value instanceof Long) {
        output.writeDouble(NUMBER_FIELD_NUMBER, (Long) value);
      } else {
        throw new UnsupportedOperationException("Unsupported type " + value.getClass().getTypeName());
      }
    }
  }

  public static int computeSize(JsonArray array) {
    int totalSize = 0;
    for (Object value : array) {
      // Calculate value length
      int valueLength = 0;
      if (value == null) {
        throw new UnsupportedOperationException("Unsupported null type");
      } else if (value instanceof String) {
        valueLength = CodedOutputStream.computeStringSize(STRING_FIELD_NUMBER, (String) value);
      } else if (value instanceof Integer){
        valueLength = CodedOutputStream.computeDoubleSize(NUMBER_FIELD_NUMBER, (Integer) value);
      } else if (value instanceof Long) {
        valueLength = CodedOutputStream.computeDoubleSize(NUMBER_FIELD_NUMBER, (Long) value);
      } else {
        throw new UnsupportedOperationException("Unsupported type " + value.getClass().getTypeName());
      }

      totalSize += CodedOutputStream.computeTagSize(TOP_LEVEL_FIELD_NUMBER);      // top-level tag
      totalSize += CodedOutputStream.computeUInt32SizeNoTag(valueLength);         // value length
      totalSize += valueLength;                                                   // value
    }
    return totalSize;
  }
}
