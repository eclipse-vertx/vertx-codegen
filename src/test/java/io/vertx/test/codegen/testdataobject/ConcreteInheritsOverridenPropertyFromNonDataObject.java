package io.vertx.test.codegen.testdataobject;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@DataObject
public class ConcreteInheritsOverridenPropertyFromNonDataObject implements NonDataObjectWithProperty<ConcreteInheritsOverridenPropertyFromNonDataObject> {

  public ConcreteInheritsOverridenPropertyFromNonDataObject() {
  }

  public ConcreteInheritsOverridenPropertyFromNonDataObject(ConcreteInheritsOverridenPropertyFromNonDataObject other) {
  }

  public ConcreteInheritsOverridenPropertyFromNonDataObject(JsonObject json) {
  }

  @Override
  public ConcreteInheritsOverridenPropertyFromNonDataObject setNonDataObjectProperty(String value) {
    return this;
  }
}
