package io.vertx.test.codegen.testdataobject.annotations;

/**
 * @author <a href="mailto:cafeinoman@openaliasbox.org>Francois Delalleau</a>
 */
public @interface ClassValueAnnotation {

  Class<String> value();

  Class<String>[] array();

  Class<String> defaultValue() default String.class;
}
