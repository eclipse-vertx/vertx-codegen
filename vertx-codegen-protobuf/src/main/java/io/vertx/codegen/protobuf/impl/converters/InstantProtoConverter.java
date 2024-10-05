package io.vertx.codegen.protobuf.impl.converters;

import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;

import java.io.IOException;
import java.time.Instant;

public class InstantProtoConverter {
  public static final int NANOS_FIELD_NUMBER = 1;
  public static final int SECONDS_FIELD_NUMBER = 2;

  public static final int NANOS_TAG = 0x8;    //  1|000
  public static final int SECONDS_TAG = 0x10; // 10|000

  public static Instant fromProto(CodedInputStream input) throws IOException {
    long seconds = 0;
    int nanos = 0;
    int tag;
    while ((tag = input.readTag()) != 0) {
      switch (tag) {
        case NANOS_TAG: {
          nanos = input.readInt32();
          break;
        }
        case SECONDS_TAG: {
          seconds = input.readInt64();
          break;
        }
      }
    }
    return Instant.ofEpochSecond(seconds, nanos);
  }

  public static void toProto(Instant obj, CodedOutputStream output) throws IOException {
    output.writeInt32(NANOS_FIELD_NUMBER, obj.getNano());
    output.writeInt64(SECONDS_FIELD_NUMBER, obj.getEpochSecond());
  }

  public static int computeSize(Instant obj) {
    int size = 0;
    size += CodedOutputStream.computeInt32Size(NANOS_FIELD_NUMBER, obj.getNano());
    size += CodedOutputStream.computeInt64Size(SECONDS_FIELD_NUMBER, obj.getEpochSecond());
    return size;
  }
}
