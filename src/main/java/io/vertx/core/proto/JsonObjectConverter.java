package io.vertx.core.proto;

import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import io.vertx.core.json.JsonObject;

import java.io.IOException;
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
              obj.put(key, input.readEnum());
              break;
            case 0x11:
              obj.put(key, input.readDouble());
              break;
            case 0x1a:
              obj.put(key, input.readString());
              break;
            case 0x20:
              obj.put(key, input.readBool());
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
      int valueLength = 0;
      if (value instanceof String) {
        valueLength = CodedOutputStream.computeStringSize(3, (String) value);
      } else if (value instanceof Integer){
        valueLength = CodedOutputStream.computeEnumSize(1, (Integer) value);
      } else if (value instanceof Boolean) {
        valueLength = CodedOutputStream.computeBoolSize(4, (Boolean) value);
      } else if (value instanceof Double) {
        valueLength = CodedOutputStream.computeDoubleSize(2, (Double) value);
      } else {
        throw new UnsupportedOperationException("Unsupported type " + value.getClass().getTypeName());
      }
      dataSize += CodedOutputStream.computeTagSize(2);         // value tag
      dataSize += CodedOutputStream.computeUInt32SizeNoTag(valueLength);  // value length
      dataSize += valueLength;                                            // value

      // struct header
      output.writeTag(1, 2);        // struct tag, always 0xa
      output.writeUInt32NoTag(dataSize);  // struct length

      // key
      output.writeString(1, key);

      // value
      output.writeTag(2, 2); // value tag, always 0x12
      if (value instanceof String) {
        output.writeUInt32NoTag(valueLength);           // value length
        output.writeString(3, (String) value);        // value
      } else if (value instanceof Integer){
        output.writeUInt32NoTag(valueLength);            // value length
        output.writeEnum(1, (Integer) value); // value
      } else if (value instanceof Boolean){
        output.writeUInt32NoTag(valueLength);             // value length
        output.writeBool(4, (Boolean) value);           // value
      } else if (value instanceof Double) {
        output.writeUInt32NoTag(valueLength);               // value length
        output.writeDouble(2, (Double) value);   // value
      } else {
        throw new UnsupportedOperationException("Unsupported type " + value.getClass().getTypeName());
      }
    }
  }

  public static int computeSize(JsonObject obj) {
    int totalSize = 0;
    Map<String, Object> fields = obj.getMap();
    for (Map.Entry<String, Object> entry : fields.entrySet()) {
      String key = entry.getKey();
      Object value = entry.getValue();

      int dataSize = 0;
      dataSize += CodedOutputStream.computeStringSize(1, key);
      int valueLength = 0;
      if (value instanceof String) {
        valueLength = CodedOutputStream.computeStringSize(3, (String) value);
      } else if (value instanceof Integer) {
        valueLength = CodedOutputStream.computeEnumSize(1, (Integer) value);
      } else if (value instanceof Boolean) {
        valueLength = CodedOutputStream.computeBoolSize(4, (Boolean) value);
      } else if (value instanceof Double) {
        valueLength = CodedOutputStream.computeDoubleSize(2, (Double) value);
      } else {
        throw new UnsupportedOperationException("Unsupported type " + value.getClass().getTypeName());
      }
      dataSize += CodedOutputStream.computeTagSize(2);         // value tag
      dataSize += CodedOutputStream.computeUInt32SizeNoTag(valueLength);  // value length
      dataSize += valueLength;                                            // value

      totalSize += CodedOutputStream.computeTagSize(1);       // struct tag
      totalSize += CodedOutputStream.computeUInt32SizeNoTag(dataSize);   // struct length
      totalSize += dataSize;                                             // struct
    }
    return totalSize;
  }

}
