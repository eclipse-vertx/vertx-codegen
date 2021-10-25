package io.vertx.codegen.testmodel.base64;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.impl.JsonUtil;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

/**
 * Converter and mapper for {@link io.vertx.codegen.testmodel.base64.Base64Basic}.
 * NOTE: This class has been automatically generated from the {@link io.vertx.codegen.testmodel.base64.Base64Basic} original class using Vert.x codegen.
 */
public class Base64BasicConverter {


  private static final Base64.Decoder BASE64_DECODER = Base64.getDecoder();
  private static final Base64.Encoder BASE64_ENCODER = Base64.getEncoder();

  public static void fromJson(Iterable<java.util.Map.Entry<String, Object>> json, Base64Basic obj) {
    for (java.util.Map.Entry<String, Object> member : json) {
      switch (member.getKey()) {
        case "data":
          if (member.getValue() instanceof String) {
            obj.setData(io.vertx.core.buffer.Buffer.buffer(BASE64_DECODER.decode((String)member.getValue())));
          }
          break;
      }
    }
  }

  public static void toJson(Base64Basic obj, JsonObject json) {
    toJson(obj, json.getMap());
  }

  public static void toJson(Base64Basic obj, java.util.Map<String, Object> json) {
    if (obj.getData() != null) {
      json.put("data", BASE64_ENCODER.encodeToString(obj.getData().getBytes()));
    }
  }
}
