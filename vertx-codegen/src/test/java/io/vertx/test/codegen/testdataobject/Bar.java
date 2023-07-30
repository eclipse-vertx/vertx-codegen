package io.vertx.test.codegen.testdataobject;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;
import io.vertx.test.codegen.annotations.ClassValueAnnotation;

/**
 * @author <a href="mailto:cafeinoman@openaliasbox.org>Francois Delalleau</a>
 */
@DataObject
@ClassValueAnnotation(value = Foo.class, array = {String.class})
public class Bar {
  public Bar(JsonObject json) {
  }
}
