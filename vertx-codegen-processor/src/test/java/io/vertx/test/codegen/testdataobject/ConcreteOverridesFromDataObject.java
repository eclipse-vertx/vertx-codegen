package io.vertx.test.codegen.testdataobject;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@DataObject
public class ConcreteOverridesFromDataObject extends DataObjectWithProperty {

  public ConcreteOverridesFromDataObject() {
  }

  public ConcreteOverridesFromDataObject(ConcreteOverridesFromDataObject other) {
  }

  public ConcreteOverridesFromDataObject(JsonObject json) {
  }

  @Override
  public ConcreteOverridesFromDataObject setDataObjectProperty(String value) {
    return this;
  }
}
