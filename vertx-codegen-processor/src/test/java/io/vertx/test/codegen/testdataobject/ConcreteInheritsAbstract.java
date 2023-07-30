package io.vertx.test.codegen.testdataobject;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@DataObject
public class ConcreteInheritsAbstract implements Abstract {
  public ConcreteInheritsAbstract() {
  }

  public ConcreteInheritsAbstract(ConcreteInheritsAbstract other) {
  }

  public ConcreteInheritsAbstract(JsonObject json) {
  }
}
