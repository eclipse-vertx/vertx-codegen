package io.vertx.test.codegen.annotations;

/**
 * @author <a href="mailto:cafeinoman@openaliasbox.org>Francois Delalleau</a>
 */
public @interface ByteValueAnnotation {

  byte value();

  byte[] array();

  byte defaultValue() default 1;
}
