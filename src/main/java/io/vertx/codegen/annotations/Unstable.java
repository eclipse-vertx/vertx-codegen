package io.vertx.codegen.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Indicates a Vert.x public API element is subject to change (e.g. type/name modification or removal).
 * Users should work with such API elements for evaluation purpose only.
 */
@Retention(SOURCE)
@Target({ANNOTATION_TYPE, CONSTRUCTOR, FIELD, METHOD, PACKAGE, TYPE})
@Documented
public @interface Unstable {

  /**
   * @return some details about why the annotated element is considered unstable
   */
  String value() default "";

}
