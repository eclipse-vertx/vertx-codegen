package io.vertx.test.codegen.testdataobject;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@DataObject
public class ConcreteInheritsPropertyFromDataObject implements DataObjectWithProperty {

  public ConcreteInheritsPropertyFromDataObject() {
  }

  public ConcreteInheritsPropertyFromDataObject(ConcreteInheritsPropertyFromDataObject other) {
  }

  public ConcreteInheritsPropertyFromDataObject(JsonObject json) {
  }

  @Override
  public DataObjectWithProperty setNonDataObjectProperty(String value) {
    return this;
  }
}
