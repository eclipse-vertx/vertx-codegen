package io.vertx.test.codegen.testdataobject;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@DataObject
public class ConcreteInheritsOverridenPropertyFromDataObject implements DataObjectWithProperty {

  public ConcreteInheritsOverridenPropertyFromDataObject() {
  }

  public ConcreteInheritsOverridenPropertyFromDataObject(ConcreteInheritsOverridenPropertyFromDataObject other) {
  }

  public ConcreteInheritsOverridenPropertyFromDataObject(JsonObject json) {
  }

  @Override
  public DataObjectWithProperty setNonDataObjectProperty(String value) {
    return this;
  }
}
