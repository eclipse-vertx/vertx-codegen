package io.vertx.core.proto;

import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import io.vertx.core.json.JsonObject;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.Map;

public class JsonObjectConverter {
  public static JsonObject fromProto(CodedInputStream input) throws IOException {
    JsonObject obj = new JsonObject();
    int tag;
    while ((tag = input.readTag()) != 0) {
      switch (tag) {
        case 0xa: {
          int length = input.readUInt32();
          int limit = input.pushLimit(length);

          input.readTag();
          String key = input.readString();
          input.readTag();
          int vlength = input.readUInt32();
          int vlimit = input.pushLimit(vlength);

          int fieldType = input.readTag();
          switch (fieldType) {
            case 0x8:
              int intValue = input.readEnum();
              obj.put(key, intValue);
              break;
            case 0x1a:
              String strValue = input.readString();
              obj.put(key, strValue);
              break;
            default:
              throw new UnsupportedOperationException("Unsupported field type " + fieldType);
          }

          input.popLimit(vlimit);
          input.popLimit(limit);
          break;
        }
        default:
          throw new UnsupportedOperationException("Unsupported tag " + tag);
      }
    }
    return obj;
  }

  public static void toProto(JsonObject obj, CodedOutputStream output) throws IOException {
    Map<String, Object> fields = obj.getMap();
    for (Map.Entry<String, Object> entry : fields.entrySet()) {
      String key = entry.getKey();
      Object value = entry.getValue();

      // calculate dataSize
      int dataSize = 0;
      dataSize += CodedOutputStream.computeStringSize(1, key);
      if (value instanceof String) {
        String strValue = (String) value;
        int valueLength = CodedOutputStream.computeStringSize(1, strValue);

        dataSize += CodedOutputStream.computeTagSize(2);        // value tag
        dataSize += CodedOutputStream.computeUInt32SizeNoTag(valueLength); // value length
        dataSize += valueLength;

      } else if (value instanceof Integer){
        int intValue = (Integer) value;
        int valueLength = CodedOutputStream.computeEnumSize(1, intValue);

        dataSize += CodedOutputStream.computeTagSize(2);        // value tag
        dataSize += CodedOutputStream.computeUInt32SizeNoTag(valueLength); // value length
        dataSize += valueLength;

      } else {
        // TODO implement
      }

      // struct header
      output.writeTag(1, 2);        // struct tag, always 0xa
      output.writeUInt32NoTag(dataSize);  // struct length

      // key
      output.writeString(1, key);

      // value
      if (value instanceof String) {
        String strValue = (String) value;
        output.writeTag(2, 2); // value tag, always 0x12
        output.writeUInt32NoTag(CodedOutputStream.computeStringSize(3, strValue));  // value length
        output.writeString(3, strValue); // value
      } else if (value instanceof Integer){
        int intValue = (Integer) value;
        output.writeTag(2, 2);    // value tag, always 0x12
        output.writeUInt32NoTag(CodedOutputStream.computeEnumSize(1, intValue));  // value length
        output.writeEnum(1, intValue); // value
      } else {
        // TODO implement
      }
    }
  }

  public static int computeSize(ZonedDateTime obj) {
    return 0;
  }

}
