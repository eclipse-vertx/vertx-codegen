package io.vertx.test.codegen.testdataobject;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@DataObject
public class DataObjectWithObjectProperty {

  public DataObjectWithObjectProperty() {
  }

  public DataObjectWithObjectProperty(JsonObject json) {
  }

  public DataObjectWithObjectProperty(DataObjectWithObjectProperty that) {
  }

  public void setProperty(Object o) {
  }
}
