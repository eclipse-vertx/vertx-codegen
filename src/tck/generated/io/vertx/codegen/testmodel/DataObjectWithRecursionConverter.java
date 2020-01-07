package io.vertx.codegen.testmodel;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;
import java.time.Instant;
import java.time.format.DateTimeFormatter;

/**
 * Converter and mapper for {@link io.vertx.codegen.testmodel.DataObjectWithRecursion}.
 * NOTE: This class has been automatically generated from the {@link io.vertx.codegen.testmodel.DataObjectWithRecursion} original class using Vert.x codegen.
 */
public class DataObjectWithRecursionConverter {


  public static void fromJson(Iterable<java.util.Map.Entry<String, Object>> json, DataObjectWithRecursion obj) {
    for (java.util.Map.Entry<String, Object> member : json) {
      switch (member.getKey()) {
        case "data":
          if (member.getValue() instanceof String) {
            obj.setData((String)member.getValue());
          }
          break;
        case "next":
          if (member.getValue() instanceof JsonObject) {
            obj.setNext(new io.vertx.codegen.testmodel.DataObjectWithRecursion((JsonObject)member.getValue()));
          }
          break;
      }
    }
  }

  public static void toJson(DataObjectWithRecursion obj, JsonObject json) {
    toJson(obj, json.getMap());
  }

  public static void toJson(DataObjectWithRecursion obj, java.util.Map<String, Object> json) {
    if (obj.getData() != null) {
      json.put("data", obj.getData());
    }
    if (obj.getNext() != null) {
      json.put("next", obj.getNext().toJson());
    }
  }
}
