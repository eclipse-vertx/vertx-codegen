package io.vertx.test.codegen.testdataobject;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@DataObject
public class UncommentedProperty {

  public UncommentedProperty() {
  }

  public UncommentedProperty(JsonObject json) {
  }

  public UncommentedProperty(UncommentedProperty other) {
  }

  public String getTheProperty() {
    throw new UnsupportedOperationException();
  }

  public void setTheProperty(String value) {
    throw new UnsupportedOperationException();
  }
}
