package io.vertx.test.codegen.testdataobject;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@DataObject
public class ConcreteImplementsFromNonDataObject implements NonDataObjectInterfaceWithProperty<ConcreteImplementsFromNonDataObject> {

  public ConcreteImplementsFromNonDataObject() {
  }

  public ConcreteImplementsFromNonDataObject(ConcreteImplementsFromNonDataObject other) {
  }

  public ConcreteImplementsFromNonDataObject(JsonObject json) {
  }

  @Override
  public ConcreteImplementsFromNonDataObject setNonDataObjectProperty(String value) {
    return this;
  }
}
