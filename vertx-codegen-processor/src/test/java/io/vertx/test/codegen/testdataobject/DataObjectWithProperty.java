package io.vertx.test.codegen.testdataobject;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@DataObject
public class DataObjectWithProperty {

  public DataObjectWithProperty() {
  }

  public DataObjectWithProperty(JsonObject json) {
  }

  public DataObjectWithProperty(DataObjectWithProperty that) {
  }

  public DataObjectWithProperty setDataObjectProperty(String value) {
    return this;
  }
}
