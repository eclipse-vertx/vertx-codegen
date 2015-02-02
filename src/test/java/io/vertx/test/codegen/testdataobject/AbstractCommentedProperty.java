package io.vertx.test.codegen.testdataobject;

import io.vertx.core.json.JsonObject;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class AbstractCommentedProperty {

  public AbstractCommentedProperty() {
  }

  public AbstractCommentedProperty(JsonObject json) {
  }

  public AbstractCommentedProperty(AbstractCommentedProperty other) {
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
