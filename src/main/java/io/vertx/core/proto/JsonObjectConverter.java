package io.vertx.core.proto;

import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import io.vertx.core.json.JsonObject;

import java.io.IOException;
import java.util.Map;

import static com.google.protobuf.WireFormat.WIRETYPE_LENGTH_DELIMITED;

public class JsonObjectConverter {
  public static final int STRUCT_FIELDS_FIELD_NUMBER = 1;

  public static final int MAP_KEY_FIELD_NUMBER = 1;
  public static final int MAP_VALUE_FIELD_NUMBER = 2;

  public static final int NULL_FIELD_NUMBER = 1;
  public static final int STRUCT_FIELD_NUMBER = 2;
  public static final int LIST_FIELD_NUMBER = 3;
  public static final int BOOLEAN_FIELD_NUMBER = 4;
  public static final int STRING_FIELD_NUMBER = 5;
  public static final int INTEGER_FIELD_NUMBER = 6;
  public static final int LONG_FIELD_NUMBER = 7;
  public static final int DOUBLE_FIELD_NUMBER = 8;
  public static final int FLOAT_FIELD_NUMBER = 9;

  // int tag = (fieldNumber << 3) | wireType;
  public static final int STRUCT_FIELDS_TAG = 0xa;  //    1|010

  public static final int NULL_TAG = 0x8;           //    1|000
  public static final int STRUCT_TAG = 0x12;        //   10|010
  //public static final int LIST_TAG = ?
  public static final int BOOLEAN_TAG = 0x20;       //  100|000
  public static final int STRING_TAG = 0x2a;        //  101|010
  public static final int INTEGER_TAG = 0x30;       //  110|000
  public static final int LONG_TAG = 0x38;          //  111|000
  public static final int DOUBLE_TAG = 0x41;        // 1000|001
  public static final int FLOAT_TAG = 0x4d;         // 1001|101

  public static JsonObject fromProto(CodedInputStream input) throws IOException {
    JsonObject obj = new JsonObject();
    int tag;
    while ((tag = input.readTag()) != 0) {
      switch (tag) {
        case STRUCT_FIELDS_TAG: {
          int length = input.readUInt32();
          int limit = input.pushLimit(length);

          input.readTag();
          String key = input.readString();
          input.readTag();
          int vlength = input.readUInt32();
          int vlimit = input.pushLimit(vlength);

          int fieldType = input.readTag();
          switch (fieldType) {
            case STRING_TAG:
              obj.put(key, input.readString());
              break;
            case INTEGER_TAG:
              obj.put(key, input.readInt32());
              break;
            case LONG_TAG:
              obj.put(key, input.readInt64());
              break;
            case DOUBLE_TAG:
              obj.put(key, input.readDouble());
              break;
            case FLOAT_TAG:
              obj.put(key, input.readFloat());
              break;
            case BOOLEAN_TAG:
              obj.put(key, input.readBool());
              break;
            case STRUCT_TAG:
              int structLength = input.readUInt32();
              int structLimit = input.pushLimit(structLength);
              JsonObject subObj = JsonObjectConverter.fromProto(input);
              obj.put(key, subObj);
              input.popLimit(structLimit);
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
      int structSize = 0;
      int dataSize = 0;
      int valueLength = 0;
      if (value instanceof String) {
        valueLength = CodedOutputStream.computeStringSize(STRING_FIELD_NUMBER, (String) value);
      } else if (value instanceof Integer){
        valueLength = CodedOutputStream.computeInt32Size(INTEGER_FIELD_NUMBER, (Integer) value);
      } else if (value instanceof Long){
        valueLength = CodedOutputStream.computeInt64Size(LONG_FIELD_NUMBER, (Long) value);
      } else if (value instanceof Boolean) {
        valueLength = CodedOutputStream.computeBoolSize(BOOLEAN_FIELD_NUMBER, (Boolean) value);
      } else if (value instanceof Double) {
        valueLength = CodedOutputStream.computeDoubleSize(DOUBLE_FIELD_NUMBER, (Double) value);
      } else if (value instanceof Float) {
        valueLength = CodedOutputStream.computeFloatSize(FLOAT_FIELD_NUMBER, (Float) value);
      } else if (value instanceof JsonObject) {
        structSize = JsonObjectConverter.computeSize((JsonObject) value);
        valueLength += CodedOutputStream.computeTagSize(STRUCT_FIELD_NUMBER);
        valueLength += CodedOutputStream.computeUInt32SizeNoTag(structSize);
        valueLength += structSize;
      } else {
        throw new UnsupportedOperationException("Unsupported type " + value.getClass().getTypeName());
      }
      dataSize += CodedOutputStream.computeStringSize(MAP_KEY_FIELD_NUMBER, key);
      dataSize += CodedOutputStream.computeTagSize(MAP_VALUE_FIELD_NUMBER);       // value tag
      dataSize += CodedOutputStream.computeUInt32SizeNoTag(valueLength);          // value length
      dataSize += valueLength;                                                    // value

      // struct header
      output.writeTag(STRUCT_FIELDS_FIELD_NUMBER, WIRETYPE_LENGTH_DELIMITED);     // struct tag, always 0xa
      output.writeUInt32NoTag(dataSize);                                          // struct length

      // key
      output.writeString(MAP_KEY_FIELD_NUMBER, key);

      // value
      output.writeTag(MAP_VALUE_FIELD_NUMBER, WIRETYPE_LENGTH_DELIMITED);         // value tag, always 0x12
      if (value instanceof String) {
        output.writeUInt32NoTag(valueLength);                                     // value length
        output.writeString(STRING_FIELD_NUMBER, (String) value);                  // value
      } else if (value instanceof Integer){
        output.writeUInt32NoTag(valueLength);                                     // value length
        output.writeInt32(INTEGER_FIELD_NUMBER, (Integer) value);                 // value
      } else if (value instanceof Long){
        output.writeUInt32NoTag(valueLength);                                     // value length
        output.writeInt64(LONG_FIELD_NUMBER, (Long) value);                       // value
      } else if (value instanceof Boolean){
        output.writeUInt32NoTag(valueLength);                                     // value length
        output.writeBool(BOOLEAN_FIELD_NUMBER, (Boolean) value);                  // value
      } else if (value instanceof Double) {
        output.writeUInt32NoTag(valueLength);                                     // value length
        output.writeDouble(DOUBLE_FIELD_NUMBER, (Double) value);                  // value
      } else if (value instanceof Float) {
        output.writeUInt32NoTag(valueLength);                                     // value length
        output.writeFloat(FLOAT_FIELD_NUMBER, (Float) value);                     // value
      } else if (value instanceof JsonObject) {
        output.writeUInt32NoTag(valueLength);                                     // value length
        output.writeTag(STRUCT_FIELD_NUMBER, WIRETYPE_LENGTH_DELIMITED);          // 0x2a
        output.writeUInt32NoTag(structSize);                                      // value length
        JsonObjectConverter.toProto((JsonObject) value, output);                  // value
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
      int valueLength = 0;
      if (value instanceof String) {
        valueLength = CodedOutputStream.computeStringSize(STRUCT_FIELD_NUMBER, (String) value);
      } else if (value instanceof Integer) {
        valueLength = CodedOutputStream.computeInt32Size(INTEGER_FIELD_NUMBER, (Integer) value);
      } else if (value instanceof Long) {
        valueLength = CodedOutputStream.computeInt64Size(LONG_FIELD_NUMBER, (Long) value);
      } else if (value instanceof Boolean) {
        valueLength = CodedOutputStream.computeBoolSize(BOOLEAN_FIELD_NUMBER, (Boolean) value);
      } else if (value instanceof Double) {
        valueLength = CodedOutputStream.computeDoubleSize(DOUBLE_FIELD_NUMBER, (Double) value);
      } else if (value instanceof Float) {
        valueLength = CodedOutputStream.computeFloatSize(FLOAT_FIELD_NUMBER, (Float) value);
      } else if (value instanceof JsonObject) {
        int structSize = JsonObjectConverter.computeSize((JsonObject) value);
        valueLength += CodedOutputStream.computeTagSize(STRUCT_FIELD_NUMBER);
        valueLength += CodedOutputStream.computeUInt32SizeNoTag(structSize);
        valueLength += structSize;
      } else {
        throw new UnsupportedOperationException("Unsupported type " + value.getClass().getTypeName());
      }
      dataSize += CodedOutputStream.computeStringSize(MAP_KEY_FIELD_NUMBER, key);
      dataSize += CodedOutputStream.computeTagSize(MAP_VALUE_FIELD_NUMBER);         // value tag
      dataSize += CodedOutputStream.computeUInt32SizeNoTag(valueLength);            // value length
      dataSize += valueLength;                                                      // value

      totalSize += CodedOutputStream.computeTagSize(STRUCT_FIELDS_FIELD_NUMBER);    // struct tag
      totalSize += CodedOutputStream.computeUInt32SizeNoTag(dataSize);              // struct length
      totalSize += dataSize;                                                        // struct
    }
    return totalSize;
  }

}
