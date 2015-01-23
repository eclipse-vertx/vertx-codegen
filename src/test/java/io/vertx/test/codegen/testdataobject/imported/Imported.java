package io.vertx.test.codegen.testdataobject.imported;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@DataObject
public interface Imported {

  public static Imported dataObject() {
    throw new UnsupportedOperationException();
  }

  public static Imported dataObjectFromJson(JsonObject obj) {
    throw new UnsupportedOperationException();
  }
}
