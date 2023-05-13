package io.vertx.test.codegen.converter;

import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.CodedInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;

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
      }
    }
  }

  public static void toProto(Address obj, CodedOutputStream output) throws IOException {
    if (obj.getLatitude() != null) {
      output.writeFloat(1, obj.getLatitude());
    }
    if (obj.getLongitude() != null) {
      output.writeFloat(2, obj.getLongitude());
    }
  }

  public static int computeSize(Address obj) {
    int size = 0;
    if (obj.getLatitude() != null) {
      size += CodedOutputStream.computeFloatSize(1, obj.getLatitude());
    }
    if (obj.getLongitude() != null) {
      size += CodedOutputStream.computeFloatSize(2, obj.getLongitude());
    }
    return size;
  }

  public static int toProto2(Address obj, CodedOutputStream output, int[] cache, int index) throws IOException {
    index = index + 1;
    if (obj.getLatitude() != null) {
      output.writeFloat(1, obj.getLatitude());
    }
    if (obj.getLongitude() != null) {
      output.writeFloat(2, obj.getLongitude());
    }
    return index;
  }

  public static int computeSize2(Address obj, int[] cache, final int baseIndex) {
    System.out.println("computing size 2 for " + obj);
    int size = 0;
    int index = baseIndex + 1;
    if (obj.getLatitude() != null) {
      size += CodedOutputStream.computeFloatSize(1, obj.getLatitude());
    }
    if (obj.getLongitude() != null) {
      size += CodedOutputStream.computeFloatSize(2, obj.getLongitude());
    }
    cache[baseIndex] = size;
    return index;
  }

}
