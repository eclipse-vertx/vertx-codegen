package io.vertx.test.codegen.testdataobject.annotations;

/**
 * @author <a href="mailto:cafeinoman@openaliasbox.org>Francois Delalleau</a>
 */
public @interface IntegerValuedAnnotation {

  int value();

  int[] array();

  int defaultValue() default 1;
}
