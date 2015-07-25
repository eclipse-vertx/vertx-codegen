package io.vertx.test.codegen.testdataobject;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@DataObject
public class ConcreteImplementsFromDataObject implements DataObjectInterfaceWithProperty {

  public ConcreteImplementsFromDataObject() {
  }

  public ConcreteImplementsFromDataObject(ConcreteImplementsFromDataObject other) {
  }

  public ConcreteImplementsFromDataObject(JsonObject json) {
  }

  @Override
  public DataObjectInterfaceWithProperty setDataObjectProperty(String value) {
    return this;
  }
}
