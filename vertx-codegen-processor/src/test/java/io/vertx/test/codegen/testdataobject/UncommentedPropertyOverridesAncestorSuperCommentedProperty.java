package io.vertx.test.codegen.testdataobject;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@DataObject
public class UncommentedPropertyOverridesAncestorSuperCommentedProperty extends UncommentedPropertyOverridesSuperCommentedProperty {

  public UncommentedPropertyOverridesAncestorSuperCommentedProperty() {
  }

  public UncommentedPropertyOverridesAncestorSuperCommentedProperty(JsonObject json) {
  }

  public UncommentedPropertyOverridesAncestorSuperCommentedProperty(UncommentedPropertyOverridesAncestorSuperCommentedProperty other) {
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
