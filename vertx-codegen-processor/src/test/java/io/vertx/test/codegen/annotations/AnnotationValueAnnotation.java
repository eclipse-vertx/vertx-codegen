package io.vertx.test.codegen.annotations;

/**
 * @author <a href="mailto:cafeinoman@openaliasbox.org>Francois Delalleau</a>
 */
public @interface AnnotationValueAnnotation {

  StringValuedAnnotation value();

  StringValuedAnnotation[] array();

}
