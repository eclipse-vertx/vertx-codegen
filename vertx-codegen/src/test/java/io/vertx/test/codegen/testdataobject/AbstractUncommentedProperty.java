package io.vertx.test.codegen.testdataobject;

import io.vertx.core.json.JsonObject;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class AbstractUncommentedProperty {

  public AbstractUncommentedProperty() {
  }

  public AbstractUncommentedProperty(JsonObject json) {
  }

  public AbstractUncommentedProperty(AbstractUncommentedProperty other) {
  }

  public String getTheProperty() {
    throw new UnsupportedOperationException();
  }

  public void setTheProperty(String value) {
    throw new UnsupportedOperationException();
  }
}
