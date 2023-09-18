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
import io.vertx.core.json.JsonObject;
import io.vertx.codegen.protobuf.utils.ExpandableIntArray;
import io.vertx.codegen.protobuf.converters.*;

public class BookProtoConverter {

  public static void fromProto(CodedInputStream input, Book obj) throws IOException {
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
    }
  }

  public static void toProto(Book obj, CodedOutputStream output) throws IOException {
    ExpandableIntArray cache = new ExpandableIntArray(16);
    BookProtoConverter.computeSize(obj, cache, 0);
    BookProtoConverter.toProto(obj, output, cache, 0);
  }

  public static int toProto(Book obj, CodedOutputStream output, ExpandableIntArray cache, int index) throws IOException {
    index = index + 1;
    if (obj.getName() != null) {
      output.writeString(1, obj.getName());
    }
    if (obj.getAuthor() != null) {
      output.writeString(3, obj.getAuthor());
    }
    if (obj.getIsbn() != null) {
      output.writeString(10, obj.getIsbn());
    }
    if (obj.getGenre() != null) {
      output.writeString(20, obj.getGenre());
    }
    return index;
  }

  public static int computeSize(Book obj) {
    ExpandableIntArray cache = new ExpandableIntArray(16);
    BookProtoConverter.computeSize(obj, cache, 0);
    return cache.get(0);
  }

  public static int computeSize(Book obj, ExpandableIntArray cache, final int baseIndex) {
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
