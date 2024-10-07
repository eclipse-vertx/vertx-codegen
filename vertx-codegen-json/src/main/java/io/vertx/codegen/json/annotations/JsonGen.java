package io.vertx.codegen.json.annotations;

import io.vertx.codegen.format.Case;
import io.vertx.codegen.format.LowerCamelCase;

/**
 * Annotation for {@link io.vertx.codegen.annotations.DataObject} annotated class that triggers
 * the generation of a converter class that performs from/to JSON conversion based on the
 * data object properties.
 *
 * <p>The name of this converter is the name of the data object with the {@literal Converter} suffix.
 *
 * <p>The converter has a {@code fromJson(JsonObject,T)} and a {@code toJson(T,JsonObject)} public static methods, such methods
 * can be used by the json constructor or the {@code toJson()} method to implement the conversion code.
 *
 * <p>By default, the generated methods only handle the conversion of the property of the data object and do not
 * handle the properties of the ancestors of this data object, {@link #inheritConverter()} can be set to
 * true to change this behavior and handle the conversion of the inherited properties as well.
 *
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public @interface JsonGen {

  /**
   * @return true if the converter should handle the state of the ancestors.
   */
  boolean inheritConverter() default false;

  /**
   * @return whether the generated converter should be public or package private
   */
  boolean publicConverter() default true;

  /**
   * @return todo
   */
  Class<? extends Case> jsonPropertyNameFormatter() default LowerCamelCase.class;

}
