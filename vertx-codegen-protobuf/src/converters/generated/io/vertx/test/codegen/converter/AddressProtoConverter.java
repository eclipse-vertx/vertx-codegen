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

public class AddressProtoConverter {

  public static void fromProto(CodedInputStream input, Address obj) throws IOException {
    fromProto(input, obj, false);
  }

  public static void fromProto(CodedInputStream input, Address obj, boolean compatibleMode) throws IOException {
    if (compatibleMode) {
      obj.setName("");
      obj.setLongitude(0f);
      obj.setLatitude(0f);
    }
    int tag;
    while ((tag = input.readTag()) != 0) {
      switch (tag) {
        case 10: {
          obj.setName(input.readString());
          break;
        }
        case 21: {
          obj.setLongitude(input.readFloat());
          break;
        }
        case 29: {
          obj.setLatitude(input.readFloat());
          break;
        }
      }
    } // while loop
  }

  public static void toProto(Address obj, CodedOutputStream output) throws IOException {
    toProto(obj, output, false);
  }

  public static void toProto(Address obj, CodedOutputStream output, boolean compatibleMode) throws IOException {
    ExpandableIntArray cache = new ExpandableIntArray(16);
    AddressProtoConverter.computeSize(obj, cache, 0, compatibleMode);
    AddressProtoConverter.toProto(obj, output, cache, 0, compatibleMode);
  }

  static int toProto(Address obj, CodedOutputStream output, ExpandableIntArray cache, int index, boolean compatibleMode) throws IOException {
    index = index + 1;
    // name
    if (compatibleMode && obj.getName() == null) {
      throw new IllegalArgumentException("Null values are not allowed for boxed types in compatibility mode");
    }
    if ((!compatibleMode && obj.getName() != null) || (compatibleMode && !obj.getName().isEmpty())) {
      output.writeString(1, obj.getName());
    }
    // longitude
    if (compatibleMode && obj.getLongitude() == null) {
      throw new IllegalArgumentException("Null values are not allowed for boxed types in compatibility mode");
    }
    if ((!compatibleMode && obj.getLongitude() != null) || (compatibleMode && obj.getLongitude() != 0f)) {
      output.writeFloat(2, obj.getLongitude());
    }
    // latitude
    if (compatibleMode && obj.getLatitude() == null) {
      throw new IllegalArgumentException("Null values are not allowed for boxed types in compatibility mode");
    }
    if ((!compatibleMode && obj.getLatitude() != null) || (compatibleMode && obj.getLatitude() != 0f)) {
      output.writeFloat(3, obj.getLatitude());
    }
    return index;
  }

  public static int computeSize(Address obj) {
    return computeSize(obj, false);
  }

  public static int computeSize(Address obj, boolean compatibleMode) {
    ExpandableIntArray cache = new ExpandableIntArray(16);
    AddressProtoConverter.computeSize(obj, cache, 0, compatibleMode);
    return cache.get(0);
  }

  static int computeSize(Address obj, ExpandableIntArray cache, final int baseIndex, boolean compatibleMode) {
    int size = 0;
    int index = baseIndex + 1;
    if (obj.getName() != null) {
      size += CodedOutputStream.computeStringSize(1, obj.getName());
    }
    if (obj.getLongitude() != null) {
      size += CodedOutputStream.computeFloatSize(2, obj.getLongitude());
    }
    if (obj.getLatitude() != null) {
      size += CodedOutputStream.computeFloatSize(3, obj.getLatitude());
    }
    cache.set(baseIndex, size);
    return index;
  }

}
