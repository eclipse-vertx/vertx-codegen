package io.vertx.test.codegen.testdataobject;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@DataObject
public class CommentedPropertyOverridesCommentedProperty extends AbstractCommentedProperty {

  public CommentedPropertyOverridesCommentedProperty() {
  }

  public CommentedPropertyOverridesCommentedProperty(JsonObject json) {
  }

  public CommentedPropertyOverridesCommentedProperty(CommentedPropertyOverridesCommentedProperty other) {
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
