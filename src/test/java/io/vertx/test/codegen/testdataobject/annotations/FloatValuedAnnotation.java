package io.vertx.test.codegen.testdataobject.annotations;

/**
 * @author <a href="mailto:cafeinoman@openaliasbox.org>Francois Delalleau</a>
 */
public @interface FloatValuedAnnotation {

  float value();

  float[] array();

  float defaultValue() default 1.0f;
}
