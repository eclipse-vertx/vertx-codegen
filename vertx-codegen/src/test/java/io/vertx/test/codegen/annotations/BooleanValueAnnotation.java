package io.vertx.test.codegen.annotations;

/**
 * @author <a href="mailto:cafeinoman@openaliasbox.org>Francois Delalleau</a>
 */
public @interface BooleanValueAnnotation {

  boolean value();

  boolean[] array();

  boolean defaultValue() default true;
}
