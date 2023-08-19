package io.vertx.codegen.protobuf.converters;

import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.io.IOException;
import java.util.Map;

import static com.google.protobuf.WireFormat.WIRETYPE_LENGTH_DELIMITED;

public class GoogleStructProtoConverter {
  public static final int TOP_LEVEL_FIELD_NUMBER = 1;

  public static final int MAP_KEY_FIELD_NUMBER = 1;
  public static final int MAP_VALUE_FIELD_NUMBER = 2;

  public static final int NULL_FIELD_NUMBER = 1;
  public static final int NUMBER_FIELD_NUMBER = 2;
  public static final int STRING_FIELD_NUMBER = 3;
  public static final int BOOLEAN_FIELD_NUMBER = 4;
  public static final int STRUCT_FIELD_NUMBER = 5;
  public static final int LIST_FIELD_NUMBER = 6;

  // int tag = (fieldNumber << 3) | wireType;
  public static final int TOP_LEVEL_TAG = 0xa;      //    1|010

  public static final int NULL_TAG = 0x8;           //    1|000
  public static final int NUMBER_TAG = 0x12;        //   10|010
  public static final int STRING_TAG = 0x1a;        //   11|010
  public static final int BOOLEAN_TAG = 0x20;       //  100|000
  public static final int STRUCT_TAG = 0x2a;        //  101|010
  public static final int LIST_TAG = 0x30;          //  110|000

  public static JsonObject fromProto(CodedInputStream input) throws IOException {
    JsonObject obj = new JsonObject();
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

      if (value == null) {
        valueLength = CodedOutputStream.computeEnumSize(NULL_FIELD_NUMBER, 0);
      } else if (value instanceof Integer){
        valueLength = CodedOutputStream.computeDoubleSize(NUMBER_FIELD_NUMBER, (Integer) value);
      } else if (value instanceof String) {
        valueLength = CodedOutputStream.computeStringSize(STRING_FIELD_NUMBER, (String) value);
      } else if (value instanceof Boolean) {
        valueLength = CodedOutputStream.computeBoolSize(BOOLEAN_FIELD_NUMBER, (Boolean) value);
      } else if (value instanceof JsonObject) {
        structSize = GoogleStructProtoConverter.computeSize((JsonObject) value);
        valueLength += CodedOutputStream.computeTagSize(STRUCT_FIELD_NUMBER);
        valueLength += CodedOutputStream.computeUInt32SizeNoTag(structSize);
        valueLength += structSize;
      } else if (value instanceof JsonArray) {
        // TODO
      } else {
        throw new UnsupportedOperationException("Unsupported type " + value.getClass().getTypeName());
      }
      dataSize += CodedOutputStream.computeStringSize(MAP_KEY_FIELD_NUMBER, key);
      dataSize += CodedOutputStream.computeTagSize(MAP_VALUE_FIELD_NUMBER);           // value tag
      dataSize += CodedOutputStream.computeUInt32SizeNoTag(valueLength);              // value length
      dataSize += valueLength;                                                        // value

      // top level
      output.writeTag(TOP_LEVEL_FIELD_NUMBER, WIRETYPE_LENGTH_DELIMITED);             // top-level tag, always 0xa
      output.writeUInt32NoTag(dataSize);                                              // top-level length

      // key
      output.writeString(MAP_KEY_FIELD_NUMBER, key);

      // value
      output.writeTag(MAP_VALUE_FIELD_NUMBER, WIRETYPE_LENGTH_DELIMITED);             // value tag, always 0x12
      if (value == null) {
        output.writeUInt32NoTag(valueLength);                                         // value length
        output.writeEnum(NULL_FIELD_NUMBER, 0);                                 // value
      } else if (value instanceof Integer) {
        output.writeUInt32NoTag(valueLength);                                         // value length
        output.writeDouble(NUMBER_FIELD_NUMBER, (Integer) value);                     // value
      } else if (value instanceof String) {
        output.writeUInt32NoTag(valueLength);                                         // value length
        output.writeString(STRING_FIELD_NUMBER, (String) value);                      // value
      } else if (value instanceof Boolean) {
        output.writeUInt32NoTag(valueLength);                                         // value length
        output.writeBool(BOOLEAN_FIELD_NUMBER, (Boolean) value);                      // value
      } else if (value instanceof JsonObject) {
        output.writeUInt32NoTag(valueLength);                                         // value length
        output.writeTag(STRUCT_FIELD_NUMBER, WIRETYPE_LENGTH_DELIMITED);              // value
        output.writeUInt32NoTag(structSize);                                          //
        GoogleStructProtoConverter.toProto((JsonObject) value, output);               //
      } else if (value instanceof JsonArray) {
        // TODO
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
      if (value == null) {
        valueLength = CodedOutputStream.computeEnumSize(NULL_FIELD_NUMBER, 0);
      } else if (value instanceof Integer) {
        valueLength = CodedOutputStream.computeDoubleSize(NUMBER_FIELD_NUMBER, (Integer) value);
      } else if (value instanceof String) {
        valueLength = CodedOutputStream.computeStringSize(STRING_FIELD_NUMBER, (String) value);
      } else if (value instanceof Boolean) {
        valueLength = CodedOutputStream.computeBoolSize(BOOLEAN_FIELD_NUMBER, (Boolean) value);
      } else if (value instanceof JsonObject) {
        int structSize = GoogleStructProtoConverter.computeSize((JsonObject) value);
        valueLength += CodedOutputStream.computeTagSize(STRUCT_FIELD_NUMBER);
        valueLength += CodedOutputStream.computeUInt32SizeNoTag(structSize);
        valueLength += structSize;
      } else if (value instanceof JsonArray) {
        // TODO
      } else {
        throw new UnsupportedOperationException("Unsupported type " + value.getClass().getTypeName());
      }
      dataSize += CodedOutputStream.computeStringSize(MAP_KEY_FIELD_NUMBER, key);
      dataSize += CodedOutputStream.computeTagSize(MAP_VALUE_FIELD_NUMBER);         // value tag
      dataSize += CodedOutputStream.computeUInt32SizeNoTag(valueLength);            // value length
      dataSize += valueLength;                                                      // value

      totalSize += CodedOutputStream.computeTagSize(TOP_LEVEL_FIELD_NUMBER);        // top-level tag
      totalSize += CodedOutputStream.computeUInt32SizeNoTag(dataSize);              // top-level length
      totalSize += dataSize;                                                        // key and value
    }
    return totalSize;
  }
}
