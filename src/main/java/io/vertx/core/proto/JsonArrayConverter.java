package io.vertx.core.proto;

import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import io.vertx.core.json.JsonArray;

import java.io.IOException;

import static com.google.protobuf.WireFormat.WIRETYPE_LENGTH_DELIMITED;
import static io.vertx.core.proto.JsonObjectConverter.INTEGER_FIELD_NUMBER;
import static io.vertx.core.proto.JsonObjectConverter.INTEGER_TAG;
import static io.vertx.core.proto.JsonObjectConverter.TOP_LEVEL_FIELD_NUMBER;
import static io.vertx.core.proto.JsonObjectConverter.TOP_LEVEL_TAG;

public class JsonArrayConverter {
  public static JsonArray fromProto(CodedInputStream input) throws IOException {
    JsonArray array = new JsonArray();
    int tag;
    while ((tag = input.readTag()) != 0) {
      switch (tag) {
        case TOP_LEVEL_TAG: {
          int length = input.readUInt32();
          int limit = input.pushLimit(length);

          int fieldType = input.readTag();
          switch (fieldType) {
            case INTEGER_TAG:
              array.add(input.readInt32());
              break;
            default:
              throw new UnsupportedOperationException("Unsupported field type " + fieldType);
          }

          input.popLimit(limit);
          break;
        }
        default:
          throw new UnsupportedOperationException("Unsupported tag " + tag);
      }
    }
    return array;
  }

  public static void toProto(JsonArray array, CodedOutputStream output) throws IOException {
    for (Object value : array) {
      // Calculate value length
      int valueLength = 0;
      if (value == null) {
        throw new UnsupportedOperationException("Unsupported null type");
      } else if (value instanceof String) {
        throw new UnsupportedOperationException("Unsupported type " + value.getClass().getTypeName());
      } else if (value instanceof Integer){
        valueLength = CodedOutputStream.computeInt32Size(INTEGER_FIELD_NUMBER, (Integer) value);
      } else {
        throw new UnsupportedOperationException("Unsupported type " + value.getClass().getTypeName());
      }

      output.writeTag(TOP_LEVEL_FIELD_NUMBER, WIRETYPE_LENGTH_DELIMITED);   // top-level tag, always 0xa
      output.writeUInt32NoTag(valueLength);                                 // value length
      output.writeInt32(INTEGER_FIELD_NUMBER, (Integer) value);             // value
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
        throw new UnsupportedOperationException("Unsupported type " + value.getClass().getTypeName());
      } else if (value instanceof Integer){
        valueLength = CodedOutputStream.computeInt32Size(INTEGER_FIELD_NUMBER, (Integer) value);
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
