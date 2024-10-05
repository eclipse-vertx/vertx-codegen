package io.vertx.test.codegen.converter;

import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.CodedInputStream;
import java.io.IOException;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;
import io.vertx.codegen.protobuf.ProtobufEncodingMode;
import io.vertx.core.json.JsonObject;
import io.vertx.codegen.protobuf.utils.ExpandableIntArray;
import io.vertx.codegen.protobuf.impl.converters.*;

public class BookProtoConverter {

  public static void fromProto(CodedInputStream input, Book obj) throws IOException {
    fromProto(input, obj, ProtobufEncodingMode.VERTX);
  }

  public static void fromProto(CodedInputStream input, Book obj, ProtobufEncodingMode encodingMode) throws IOException {
    boolean compatibleMode = encodingMode == ProtobufEncodingMode.GOOGLE_COMPATIBLE;
    if (compatibleMode) {
      obj.setName("");
      obj.setAuthor("");
      obj.setIsbn("");
      obj.setGenre("");
    }
    int tag;
    while ((tag = input.readTag()) != 0) {
      switch (tag) {
        case 10: {
          obj.setName(input.readString());
          break;
        }
        case 26: {
          obj.setAuthor(input.readString());
          break;
        }
        case 82: {
          obj.setIsbn(input.readString());
          break;
        }
        case 162: {
          obj.setGenre(input.readString());
          break;
        }
      }
    } // while loop
  }

  public static void toProto(Book obj, CodedOutputStream output) throws IOException {
    toProto(obj, output, ProtobufEncodingMode.VERTX);
  }

  public static void toProto(Book obj, CodedOutputStream output, ProtobufEncodingMode encodingMode) throws IOException {
    ExpandableIntArray cache = new ExpandableIntArray(16);
    BookProtoConverter.computeSize(obj, cache, 0, encodingMode);
    BookProtoConverter.toProto(obj, output, cache, 0, encodingMode);
  }

  static int toProto(Book obj, CodedOutputStream output, ExpandableIntArray cache, int index, ProtobufEncodingMode encodingMode) throws IOException {
    boolean compatibleMode = encodingMode == ProtobufEncodingMode.GOOGLE_COMPATIBLE;
    index = index + 1;
    // name
    if (compatibleMode && obj.getName() == null) {
      throw new IllegalArgumentException("Null values are not allowed for boxed types in compatibility mode");
    }
    if ((!compatibleMode && obj.getName() != null) || (compatibleMode && !obj.getName().isEmpty())) {
      output.writeString(1, obj.getName());
    }
    // author
    if (compatibleMode && obj.getAuthor() == null) {
      throw new IllegalArgumentException("Null values are not allowed for boxed types in compatibility mode");
    }
    if ((!compatibleMode && obj.getAuthor() != null) || (compatibleMode && !obj.getAuthor().isEmpty())) {
      output.writeString(3, obj.getAuthor());
    }
    // isbn
    if (compatibleMode && obj.getIsbn() == null) {
      throw new IllegalArgumentException("Null values are not allowed for boxed types in compatibility mode");
    }
    if ((!compatibleMode && obj.getIsbn() != null) || (compatibleMode && !obj.getIsbn().isEmpty())) {
      output.writeString(10, obj.getIsbn());
    }
    // genre
    if (compatibleMode && obj.getGenre() == null) {
      throw new IllegalArgumentException("Null values are not allowed for boxed types in compatibility mode");
    }
    if ((!compatibleMode && obj.getGenre() != null) || (compatibleMode && !obj.getGenre().isEmpty())) {
      output.writeString(20, obj.getGenre());
    }
    return index;
  }

  public static int computeSize(Book obj) {
    return computeSize(obj, ProtobufEncodingMode.VERTX);
  }

  public static int computeSize(Book obj, ProtobufEncodingMode encodingMode) {
    ExpandableIntArray cache = new ExpandableIntArray(16);
    BookProtoConverter.computeSize(obj, cache, 0, encodingMode);
    return cache.get(0);
  }

  static int computeSize(Book obj, ExpandableIntArray cache, final int baseIndex, ProtobufEncodingMode encodingMode) {
    int size = 0;
    int index = baseIndex + 1;
    if (obj.getName() != null) {
      size += CodedOutputStream.computeStringSize(1, obj.getName());
    }
    if (obj.getAuthor() != null) {
      size += CodedOutputStream.computeStringSize(3, obj.getAuthor());
    }
    if (obj.getIsbn() != null) {
      size += CodedOutputStream.computeStringSize(10, obj.getIsbn());
    }
    if (obj.getGenre() != null) {
      size += CodedOutputStream.computeStringSize(20, obj.getGenre());
    }
    cache.set(baseIndex, size);
    return index;
  }

}
