package io.vertx.test.codegen.testapi;

import io.vertx.codegen.annotations.VertxGen;
import io.vertx.test.codegen.annotations.EmptyAnnotation;
import io.vertx.test.codegen.annotations.StringValuedAnnotation;

/**
 * @author <a href="mailto:cafeinoman@openaliasbox.org>Francois Delalleau</a>
 */
@VertxGen
@EmptyAnnotation
public interface Annotated {

  @StringValuedAnnotation(value = "aString", array = {"one", "two"})
  String stringAnnotated();
}
