package io.vertx.test.codegen.testdataobject;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@DataObject
public interface Concrete2 {

  public static Concrete2 dataObject() {
    throw new UnsupportedOperationException();
  }

  public static Concrete2 dataObjectFromJson(JsonObject obj) {
    throw new UnsupportedOperationException();
  }
}
