package io.vertx.codegen.testmodel;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.impl.JsonUtil;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

/**
 * Converter and mapper for {@link io.vertx.codegen.testmodel.DataObjectWithNestedBuffer}.
 * NOTE: This class has been automatically generated from the {@link io.vertx.codegen.testmodel.DataObjectWithNestedBuffer} original class using Vert.x codegen.
 */
public class DataObjectWithNestedBufferConverter {


  private static final Base64.Decoder BASE64_DECODER = JsonUtil.BASE64_DECODER;
  private static final Base64.Encoder BASE64_ENCODER = JsonUtil.BASE64_ENCODER;

  public static void fromJson(Iterable<java.util.Map.Entry<String, Object>> json, DataObjectWithNestedBuffer obj) {
    for (java.util.Map.Entry<String, Object> member : json) {
      switch (member.getKey()) {
        case "buffer":
          if (member.getValue() instanceof String) {
            obj.setBuffer(io.vertx.core.buffer.Buffer.buffer(BASE64_DECODER.decode((String)member.getValue())));
          }
          break;
        case "buffers":
          if (member.getValue() instanceof JsonArray) {
            java.util.ArrayList<io.vertx.core.buffer.Buffer> list =  new java.util.ArrayList<>();
            ((Iterable<Object>)member.getValue()).forEach( item -> {
              if (item instanceof String)
                list.add(io.vertx.core.buffer.Buffer.buffer(BASE64_DECODER.decode((String)item)));
            });
            obj.setBuffers(list);
          }
          break;
        case "nested":
          if (member.getValue() instanceof JsonObject) {
            obj.setNested(new io.vertx.codegen.testmodel.DataObjectWithBuffer((io.vertx.core.json.JsonObject)member.getValue()));
          }
          break;
      }
    }
  }

  public static void toJson(DataObjectWithNestedBuffer obj, JsonObject json) {
    toJson(obj, json.getMap());
  }

  public static void toJson(DataObjectWithNestedBuffer obj, java.util.Map<String, Object> json) {
    if (obj.getBuffer() != null) {
      json.put("buffer", BASE64_ENCODER.encodeToString(obj.getBuffer().getBytes()));
    }
    if (obj.getBuffers() != null) {
      JsonArray array = new JsonArray();
      obj.getBuffers().forEach(item -> array.add(BASE64_ENCODER.encodeToString(item.getBytes())));
      json.put("buffers", array);
    }
    if (obj.getNested() != null) {
      json.put("nested", obj.getNested().toJson());
    }
  }
}
