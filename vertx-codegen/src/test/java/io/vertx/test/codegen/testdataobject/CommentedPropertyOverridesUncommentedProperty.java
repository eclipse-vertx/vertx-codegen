package io.vertx.test.codegen.testdataobject;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@DataObject
public class CommentedPropertyOverridesUncommentedProperty extends AbstractUncommentedProperty {

  public CommentedPropertyOverridesUncommentedProperty() {
  }

  public CommentedPropertyOverridesUncommentedProperty(JsonObject json) {
  }

  public CommentedPropertyOverridesUncommentedProperty(CommentedPropertyOverridesUncommentedProperty other) {
  }

  @Override
  public String getTheProperty() {
    return super.getTheProperty();
  }

  /**
   * The overriden property description.
   */
  @Override
  public void setTheProperty(String value) {
    super.setTheProperty(value);
  }
}
