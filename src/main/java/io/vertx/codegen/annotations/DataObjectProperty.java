package io.vertx.codegen.annotations;

import io.vertx.codegen.type.CaseFormat;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Declares what case the json converter will use.
 *
 * @author Richard Gomez
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface DataObjectProperty {
  /**
   * @return naming scheme for the converter to use
   */
  CaseFormat caseFormat() default CaseFormat.LOWER_CAMEL;
}
