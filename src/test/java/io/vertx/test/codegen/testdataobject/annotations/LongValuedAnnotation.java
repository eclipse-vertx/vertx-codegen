package io.vertx.test.codegen.testdataobject.annotations;

/**
 * @author <a href="mailto:cafeinoman@openaliasbox.org>Francois Delalleau</a>
 */
public @interface LongValuedAnnotation {

  long value();

  long[] array();

  long defaultValue() default 1L;
}
