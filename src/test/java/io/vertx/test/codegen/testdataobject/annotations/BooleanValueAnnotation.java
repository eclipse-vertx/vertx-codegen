package io.vertx.test.codegen.testdataobject.annotations;

/**
 * @author <a href="mailto:cafeinoman@openaliasbox.org>Francois Delalleau</a>
 */
public @interface BooleanValueAnnotation {

  boolean value();

  boolean[] array();

  boolean defaultValue() default true;
}
