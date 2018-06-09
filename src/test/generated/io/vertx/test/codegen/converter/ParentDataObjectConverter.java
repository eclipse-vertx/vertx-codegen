package io.vertx.test.codegen.converter;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;
import java.util.Objects;

/**
 * Converter for {@link io.vertx.test.codegen.converter.ParentDataObject}.
 * NOTE: This class has been automatically generated from the {@link "io.vertx.test.codegen.converter.ParentDataObject} original class using Vert.x codegen.
 */
public class ParentDataObjectConverter {

  public static void fromJson(Iterable<java.util.Map.Entry<String, Object>> json, ParentDataObject obj) {
    for (java.util.Map.Entry<String, Object> member : json) {
      switch (member.getKey()) {
        case "parentProperty":
          if (member.getValue() instanceof String) {
            obj.setParentProperty((String)member.getValue());
          }
          break;
      }
    }
  }

  public static void toJson(ParentDataObject obj, JsonObject json) {
    toJson(obj, json.getMap());
  }

  public static void toJson(ParentDataObject obj, java.util.Map<String, Object> json) {
    if (obj.getParentProperty() != null) {
      json.put("parentProperty", obj.getParentProperty());
    }
  }

    public static boolean equals(ParentDataObject lhs, ParentDataObject rhs) {
        if (lhs == rhs) {
          return true;
        }

        return Objects.equals(lhs.getParentProperty(), rhs.getParentProperty());
    }


    public static int hashCode(ParentDataObject o) {
        return Objects.hash(
                o.getParentProperty());
    }
}
