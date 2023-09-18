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

public class PersonProtoConverter {

  public static void fromProto(CodedInputStream input, Person obj) throws IOException {
    int tag;
    while ((tag = input.readTag()) != 0) {
      switch (tag) {
        case 18: {
          obj.setName(input.readString());
          break;
        }
        case 32: {
          obj.setAge(input.readInt32());
          break;
        }
      }
    }
  }

  public static void toProto(Person obj, CodedOutputStream output) throws IOException {
    ExpandableIntArray cache = new ExpandableIntArray(16);
    PersonProtoConverter.computeSize(obj, cache, 0);
    PersonProtoConverter.toProto(obj, output, cache, 0);
  }

  public static int toProto(Person obj, CodedOutputStream output, ExpandableIntArray cache, int index) throws IOException {
    index = index + 1;
    if (obj.getName() != null) {
      output.writeString(2, obj.getName());
    }
    if (obj.getAge() != 0) {
      output.writeInt32(4, obj.getAge());
    }
    return index;
  }

  public static int computeSize(Person obj) {
    ExpandableIntArray cache = new ExpandableIntArray(16);
    PersonProtoConverter.computeSize(obj, cache, 0);
    return cache.get(0);
  }

  public static int computeSize(Person obj, ExpandableIntArray cache, final int baseIndex) {
    int size = 0;
    int index = baseIndex + 1;
    if (obj.getName() != null) {
      size += CodedOutputStream.computeStringSize(2, obj.getName());
    }
    if (obj.getAge() != 0) {
      size += CodedOutputStream.computeInt32Size(4, obj.getAge());
    }
    cache.set(baseIndex, size);
    return index;
  }

}
