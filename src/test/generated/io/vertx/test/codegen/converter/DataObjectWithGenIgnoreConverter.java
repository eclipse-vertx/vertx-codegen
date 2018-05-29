package io.vertx.test.codegen.converter;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;
import java.util.Objects;

/**
 * Converter for {@link io.vertx.test.codegen.converter.DataObjectWithGenIgnore}.
 * NOTE: This class has been automatically generated from the {@link "io.vertx.test.codegen.converter.DataObjectWithGenIgnore} original class using Vert.x codegen.
 */
public class DataObjectWithGenIgnoreConverter {

  public static void fromJson(Iterable<java.util.Map.Entry<String, Object>> json, DataObjectWithGenIgnore obj) {
    for (java.util.Map.Entry<String, Object> member : json) {
      switch (member.getKey()) {
          case "firstName":
            if (member.getValue() instanceof String) {
              obj.setFirstName((String)member.getValue());
            }
            break;
          case "lastName":
            if (member.getValue() instanceof String) {
              obj.setLastName((String)member.getValue());
            }
            break;
      }
    }
  }

  public static void toJson(DataObjectWithGenIgnore obj, JsonObject json) {
    toJson(obj, json.getMap());
  }

  public static void toJson(DataObjectWithGenIgnore obj, java.util.Map<String, Object> json) {
    if (obj.getFirstName() != null) {
      json.put("firstName", obj.getFirstName());
    }
    if (obj.getLastName() != null) {
      json.put("lastName", obj.getLastName());
    }
  }

    public static boolean equals(DataObjectWithGenIgnore lhs, DataObjectWithGenIgnore rhs) {
        if (lhs == rhs) return true;
        return 
            Objects.equals(lhs.getFirstName(), rhs.getFirstName()) &&
            Objects.equals(lhs.getLastName(), rhs.getLastName());
    }


    public static int hashCode(DataObjectWithGenIgnore o) {
        return Objects.hash(
                o.getFirstName(),
                o.getLastName());
    }
}
