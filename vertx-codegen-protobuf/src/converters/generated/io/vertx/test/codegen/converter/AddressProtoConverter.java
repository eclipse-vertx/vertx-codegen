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
import io.vertx.codegen.protobuf.utils.ExpandableIntArray;
import io.vertx.core.json.JsonObject;
import io.vertx.codegen.protobuf.converters.*;

public class AddressProtoConverter {

  public static void fromProto(CodedInputStream input, Address obj) throws IOException {
    int tag;
    while ((tag = input.readTag()) != 0) {
      switch (tag) {
        case 13: {
          obj.setLatitude(input.readFloat());
          break;
        }
        case 21: {
          obj.setLongitude(input.readFloat());
          break;
        }
        case 26: {
          obj.setName(input.readString());
          break;
        }
      }
    }
  }

  public static void toProto(Address obj, CodedOutputStream output) throws IOException {
    ExpandableIntArray cache = new ExpandableIntArray(16);
    AddressProtoConverter.computeSize(obj, cache, 0);
    AddressProtoConverter.toProto(obj, output, cache, 0);
  }

  public static int toProto(Address obj, CodedOutputStream output, ExpandableIntArray cache, int index) throws IOException {
    index = index + 1;
    if (obj.getLatitude() != null) {
      output.writeFloat(1, obj.getLatitude());
    }
    if (obj.getLongitude() != null) {
      output.writeFloat(2, obj.getLongitude());
    }
    if (obj.getName() != null) {
      output.writeString(3, obj.getName());
    }
    return index;
  }

  public static int computeSize(Address obj) {
    ExpandableIntArray cache = new ExpandableIntArray(16);
    AddressProtoConverter.computeSize(obj, cache, 0);
    return cache.get(0);
  }

  public static int computeSize(Address obj, ExpandableIntArray cache, final int baseIndex) {
    int size = 0;
    int index = baseIndex + 1;
    if (obj.getLatitude() != null) {
      size += CodedOutputStream.computeFloatSize(1, obj.getLatitude());
    }
    if (obj.getLongitude() != null) {
      size += CodedOutputStream.computeFloatSize(2, obj.getLongitude());
    }
    if (obj.getName() != null) {
      size += CodedOutputStream.computeStringSize(3, obj.getName());
    }
    cache.set(baseIndex, size);
    return index;
  }

}
