package io.vertx.test.codegen.testdataobject.annotations;

/**
 * @author <a href="mailto:cafeinoman@openaliasbox.org>Francois Delalleau</a>
 */
public @interface AnnotationValueAnnotation {

  StringValuedAnnotation value();

  StringValuedAnnotation[] array();

}
