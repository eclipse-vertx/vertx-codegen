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

  public String getSetterProperty() {
    throw new UnsupportedOperationException();
  }

  /**
   * Setter setter property description.
   */
  public void setSetterProperty(String value) {
    throw new UnsupportedOperationException();
  }

  /**
   * Getter getter property description.
   */
  public String getGetterProperty() {
    throw new UnsupportedOperationException();
  }

  public void setGetterProperty(String value) {
    throw new UnsupportedOperationException();
  }

  /**
   * GetterAndSetter getter property description.
   */
  public String getGetterAndSetterProperty() {
    throw new UnsupportedOperationException();
  }

  /**
   * GetterAndSetter setter property description.
   */
  public void setGetterAndSetterProperty(String value) {
    throw new UnsupportedOperationException();
  }
}
