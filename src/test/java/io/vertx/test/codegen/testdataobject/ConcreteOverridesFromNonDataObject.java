package io.vertx.test.codegen.testdataobject;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@DataObject
public class ConcreteOverridesFromNonDataObject extends NonDataObjectWithProperty {

  public ConcreteOverridesFromNonDataObject() {
  }

  public ConcreteOverridesFromNonDataObject(ConcreteOverridesFromNonDataObject other) {
  }

  public ConcreteOverridesFromNonDataObject(JsonObject json) {
  }

  @Override
  public ConcreteOverridesFromNonDataObject setNonDataObjectProperty(String value) {
    return this;
  }
}
