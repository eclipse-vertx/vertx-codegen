package io.vertx.test.codegen.annotations;

/**
 * @author <a href="mailto:cafeinoman@openaliasbox.org>Francois Delalleau</a>
 */
public @interface CharValueAnnotation {

  char value();

  char[] array();

  char defaultValue() default 'a';
}
