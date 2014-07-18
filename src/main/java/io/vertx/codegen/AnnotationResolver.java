package io.vertx.codegen;

import java.lang.annotation.Annotation;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@FunctionalInterface
interface AnnotationResolver {
  Annotation get(Class<? extends Annotation> annotationType);
}
