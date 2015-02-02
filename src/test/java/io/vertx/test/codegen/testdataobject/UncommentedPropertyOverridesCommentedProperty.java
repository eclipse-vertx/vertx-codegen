package io.vertx.test.codegen.testdataobject;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@DataObject
public class UncommentedPropertyOverridesCommentedProperty extends AbstractCommentedProperty {

  public UncommentedPropertyOverridesCommentedProperty() {
  }

  public UncommentedPropertyOverridesCommentedProperty(JsonObject json) {
  }

  public UncommentedPropertyOverridesCommentedProperty(UncommentedPropertyOverridesCommentedProperty other) {
  }

  @Override
  public String getTheProperty() {
    return super.getTheProperty();
  }

  @Override
  public void setTheProperty(String value) {
    super.setTheProperty(value);
  }
}
