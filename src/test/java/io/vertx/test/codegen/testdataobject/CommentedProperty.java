package io.vertx.test.codegen.testdataobject;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@DataObject
public class CommentedProperty {

  public CommentedProperty() {
  }

  public CommentedProperty(JsonObject json) {
  }

  public CommentedProperty(CommentedProperty other) {
  }

  public String getTheProperty() {
    throw new UnsupportedOperationException();
  }

  /**
   * The property description.
   */
  public void setTheProperty(String value) {
    throw new UnsupportedOperationException();
  }
}
