package io.vertx.codegen.protobuf.impl.converters;

import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class ZonedDateTimeProtoConverter {
  public static final int NANOS_FIELD_NUMBER = 1;
  public static final int SECONDS_FIELD_NUMBER = 2;
  public static final int ZONE_FIELD_NUMBER = 3;

  public static final int NANOS_TAG = 0x8;    //  1|000
  public static final int SECONDS_TAG = 0x10; // 10|000
  public static final int ZONE_TAG = 0x1a;    // 11|010

  public static ZonedDateTime fromProto(CodedInputStream input) throws IOException {
    long seconds = 0;
    int nanos = 0;
    String zoneId = "";
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
        case ZONE_TAG: {
          zoneId = input.readString();
          break;
        }
      }
    }
    return (Instant.ofEpochSecond(seconds, nanos)).atZone(ZoneId.of(zoneId));
  }

  public static void toProto(ZonedDateTime obj, CodedOutputStream output) throws IOException {
    Instant instant = obj.toInstant();
    output.writeInt32(NANOS_FIELD_NUMBER, instant.getNano());
    output.writeInt64(SECONDS_FIELD_NUMBER, instant.getEpochSecond());
    output.writeString(ZONE_FIELD_NUMBER, obj.getZone().toString());
  }

  public static int computeSize(ZonedDateTime obj) {
    int size = 0;
    Instant instant = obj.toInstant();
    size += CodedOutputStream.computeInt32Size(NANOS_FIELD_NUMBER, instant.getNano());
    size += CodedOutputStream.computeInt64Size(SECONDS_FIELD_NUMBER, instant.getEpochSecond());
    size += CodedOutputStream.computeStringSize(ZONE_FIELD_NUMBER, obj.getZone().toString());
    return size;
  }
}
