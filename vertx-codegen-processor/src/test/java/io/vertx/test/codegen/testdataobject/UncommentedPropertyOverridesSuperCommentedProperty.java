package io.vertx.test.codegen.testdataobject;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@DataObject
public class UncommentedPropertyOverridesSuperCommentedProperty extends AbstractCommentedProperty {

  public UncommentedPropertyOverridesSuperCommentedProperty() {
  }

  public UncommentedPropertyOverridesSuperCommentedProperty(JsonObject json) {
  }

  public UncommentedPropertyOverridesSuperCommentedProperty(UncommentedPropertyOverridesSuperCommentedProperty other) {
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
