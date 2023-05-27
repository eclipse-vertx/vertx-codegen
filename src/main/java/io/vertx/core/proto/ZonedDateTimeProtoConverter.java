package io.vertx.core.proto;

import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class ZonedDateTimeProtoConverter {

  public static ZonedDateTime fromProto(CodedInputStream input) throws IOException {
    long seconds = 0;
    int nanos = 0;
    String zoneId = "";
    int tag;
    while ((tag = input.readTag()) != 0) {
      switch (tag) {
        case 8: {
          nanos = input.readInt32();
          break;
        }
        case 16: {
          seconds = input.readInt64();
          break;
        }
        case 26: {
          zoneId = input.readString();
          break;
        }
      }
    }
    return (Instant.ofEpochSecond(seconds, nanos)).atZone(ZoneId.of(zoneId));
  }

  public static void toProto(ZonedDateTime obj, CodedOutputStream output) throws IOException {
    Instant instant = obj.toInstant();
    output.writeInt32(1, instant.getNano());
    output.writeInt64(2, instant.getEpochSecond());
    output.writeString(3, obj.getZone().toString());
  }

  public static int computeSize(ZonedDateTime obj) {
    int size = 0;
    Instant instant = obj.toInstant();
    size += CodedOutputStream.computeInt32Size(1, instant.getNano());
    size += CodedOutputStream.computeInt64Size(2, instant.getEpochSecond());
    size += CodedOutputStream.computeStringSize(3, obj.getZone().toString());
    return size;
  }
}
