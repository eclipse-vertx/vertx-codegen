package io.vertx.test.codegen.converter;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;
import java.util.Objects;

/**
 * Converter for {@link io.vertx.test.codegen.converter.ChildInheritingDataObject}.
 * NOTE: This class has been automatically generated from the {@link "io.vertx.test.codegen.converter.ChildInheritingDataObject} original class using Vert.x codegen.
 */
public class ChildInheritingDataObjectConverter {

  public static void fromJson(Iterable<java.util.Map.Entry<String, Object>> json, ChildInheritingDataObject obj) {
    for (java.util.Map.Entry<String, Object> member : json) {
      switch (member.getKey()) {
        case "childProperty":
          if (member.getValue() instanceof String) {
            obj.setChildProperty((String)member.getValue());
          }
          break;
        case "parentProperty":
          if (member.getValue() instanceof String) {
            obj.setParentProperty((String)member.getValue());
          }
          break;
      }
    }
  }

  public static void toJson(ChildInheritingDataObject obj, JsonObject json) {
    toJson(obj, json.getMap());
  }

  public static void toJson(ChildInheritingDataObject obj, java.util.Map<String, Object> json) {
    if (obj.getChildProperty() != null) {
      json.put("childProperty", obj.getChildProperty());
    }
    if (obj.getParentProperty() != null) {
      json.put("parentProperty", obj.getParentProperty());
    }
  }

    public static boolean equals(ChildInheritingDataObject lhs, ChildInheritingDataObject rhs) {
        if (lhs == rhs) {
          return true;
        }

        return Objects.equals(lhs.getChildProperty(), rhs.getChildProperty()) &&
            Objects.equals(lhs.getParentProperty(), rhs.getParentProperty());
    }


    public static int hashCode(ChildInheritingDataObject o) {
        return Objects.hash(
                o.getChildProperty(),
                o.getParentProperty());
    }
}
