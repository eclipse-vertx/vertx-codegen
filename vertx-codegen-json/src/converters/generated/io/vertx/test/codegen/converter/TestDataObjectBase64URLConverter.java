package io.vertx.test.codegen.converter;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.impl.JsonUtil;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

/**
 * Converter and mapper for {@link io.vertx.test.codegen.converter.TestDataObjectBase64URL}.
 * NOTE: This class has been automatically generated from the {@link io.vertx.test.codegen.converter.TestDataObjectBase64URL} original class using Vert.x codegen.
 */
public class TestDataObjectBase64URLConverter {


  private static final Base64.Decoder BASE64_DECODER = Base64.getUrlDecoder();
  private static final Base64.Encoder BASE64_ENCODER = Base64.getUrlEncoder().withoutPadding();

  public static void fromJson(Iterable<java.util.Map.Entry<String, Object>> json, TestDataObjectBase64URL obj) {
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

  public static void toJson(TestDataObjectBase64URL obj, JsonObject json) {
    toJson(obj, json.getMap());
  }

  public static void toJson(TestDataObjectBase64URL obj, java.util.Map<String, Object> json) {
    if (obj.getData() != null) {
      json.put("data", BASE64_ENCODER.encodeToString(obj.getData().getBytes()));
    }
  }
}
