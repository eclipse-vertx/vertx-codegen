package io.vertx.test.codegen.converter;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;
import java.util.Objects;

/**
 * Converter for {@link io.vertx.test.codegen.converter.DataObjectWithGenIgnore}.
 * NOTE: This class has been automatically generated from the {@link "io.vertx.test.codegen.converter.DataObjectWithGenIgnore} original class using Vert.x codegen.
 */
public class DataObjectWithGenIgnoreHelper {

    public static boolean equals(DataObjectWithGenIgnore lhs, DataObjectWithGenIgnore rhs) {
        if (lhs == rhs) {
          return true;
        }

        return Objects.equals(lhs.getFirstName(), rhs.getFirstName()) &&
            Objects.equals(lhs.getLastName(), rhs.getLastName());
    }

    public static int hashCode(DataObjectWithGenIgnore o) {
        return Objects.hash(
                o.getFirstName(),
                o.getLastName());
    }

}
