package io.vertx.test.codegen.converter;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;
import java.time.Instant;
import java.time.format.DateTimeFormatter;

/**
 * Converter for {@link io.vertx.test.codegen.converter.SetterAdderDataObject}.
 * NOTE: This class has been automatically generated from the {@link io.vertx.test.codegen.converter.SetterAdderDataObject} original class using Vert.x codegen.
 */
public class SetterAdderDataObjectConverter {

  public static void fromJson(Iterable<java.util.Map.Entry<String, Object>> json, SetterAdderDataObject obj) {
    for (java.util.Map.Entry<String, Object> member : json) {
      switch (member.getKey()) {
        case "values":
          if (member.getValue() instanceof JsonArray) {
            java.util.ArrayList<java.lang.String> list =  new java.util.ArrayList<>();
            ((Iterable<Object>)member.getValue()).forEach( item -> {
              if (item instanceof String)
                list.add((String)item);
            });
            obj.setValues(list);
          }
          break;
      }
    }
  }

  public static void toJson(SetterAdderDataObject obj, JsonObject json) {
    toJson(obj, json.getMap());
  }

  public static void toJson(SetterAdderDataObject obj, java.util.Map<String, Object> json) {
    if (obj.getValues() != null) {
      JsonArray array = new JsonArray();
      obj.getValues().forEach(item -> array.add(item));
      json.put("values", array);
    }
  }
}
