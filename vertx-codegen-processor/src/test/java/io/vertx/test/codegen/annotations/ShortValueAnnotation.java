package io.vertx.test.codegen.annotations;

/**
 * @author <a href="mailto:cafeinoman@openaliasbox.org>Francois Delalleau</a>
 */
public @interface ShortValueAnnotation {

  short value();

  short[] array();

  short defaultValue() default 1;
}
