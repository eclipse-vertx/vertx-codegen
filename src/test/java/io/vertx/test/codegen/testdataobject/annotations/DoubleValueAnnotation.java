package io.vertx.test.codegen.testdataobject.annotations;

/**
 * @author <a href="mailto:cafeinoman@openaliasbox.org>Francois Delalleau</a>
 */
public @interface DoubleValueAnnotation {

  double value();

  double[] array();

  double defaultValue() default 1.0;
}
