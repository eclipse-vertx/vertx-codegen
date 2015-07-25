package io.vertx.test.codegen.testdataobject;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@DataObject
public abstract class AbstractDataObjectWithProperty {

  public AbstractDataObjectWithProperty() {
  }

  public AbstractDataObjectWithProperty(JsonObject json) {
  }

  public AbstractDataObjectWithProperty(AbstractDataObjectWithProperty that) {
  }

  public AbstractDataObjectWithProperty setInheritedProperty(String value) {
    return this;
  }

  public AbstractDataObjectWithProperty setOverriddenProperty(String value) {
    return this;
  }

  public abstract AbstractDataObjectWithProperty setAbstractProperty(String value);
}
