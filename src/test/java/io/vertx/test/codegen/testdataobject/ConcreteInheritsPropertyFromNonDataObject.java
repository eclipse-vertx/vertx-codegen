package io.vertx.test.codegen.testdataobject;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@DataObject
public class ConcreteInheritsPropertyFromNonDataObject implements NonDataObjectWithProperty<ConcreteInheritsPropertyFromNonDataObject> {

  public ConcreteInheritsPropertyFromNonDataObject() {
  }

  public ConcreteInheritsPropertyFromNonDataObject(ConcreteInheritsPropertyFromNonDataObject other) {
  }

  public ConcreteInheritsPropertyFromNonDataObject(JsonObject json) {
  }

  @Override
  public ConcreteInheritsPropertyFromNonDataObject setNonDataObjectProperty(String value) {
    return this;
  }
}
