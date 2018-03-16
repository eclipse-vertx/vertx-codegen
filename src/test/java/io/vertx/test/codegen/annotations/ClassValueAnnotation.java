package io.vertx.test.codegen.annotations;

/**
 * @author <a href="mailto:cafeinoman@openaliasbox.org>Francois Delalleau</a>
 */
public @interface ClassValueAnnotation {

  Class<?> value();

  Class<?>[] array();

  Class<?> defaultValue() default String.class;
}
