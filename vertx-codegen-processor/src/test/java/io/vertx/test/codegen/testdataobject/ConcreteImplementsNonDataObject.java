package io.vertx.test.codegen.testdataobject;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@DataObject
public class ConcreteImplementsNonDataObject implements NonDataObject {

  public ConcreteImplementsNonDataObject() {
  }

  public ConcreteImplementsNonDataObject(ConcreteImplementsNonDataObject other) {
  }

  public ConcreteImplementsNonDataObject(JsonObject json) {
  }
}
