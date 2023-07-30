package io.vertx.test.codegen.testdataobject;

import io.vertx.core.json.JsonObject;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class NonDataObjectWithProperty {

  public NonDataObjectWithProperty() {
  }

  public NonDataObjectWithProperty(JsonObject json) {
  }

  public NonDataObjectWithProperty(NonDataObjectWithProperty that) {
  }

  public NonDataObjectWithProperty setNonDataObjectProperty(String value) {
    return this;
  }
}
