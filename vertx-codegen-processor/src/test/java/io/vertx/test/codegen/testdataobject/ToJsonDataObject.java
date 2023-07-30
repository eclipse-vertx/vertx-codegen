package io.vertx.test.codegen.testdataobject;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@DataObject
public class ToJsonDataObject {

  public ToJsonDataObject() {
  }

  public ToJsonDataObject(ToJsonDataObject other) {
  }

  public ToJsonDataObject(JsonObject json) {
  }

  public JsonObject toJson() {
    throw new UnsupportedOperationException();
  }
}
