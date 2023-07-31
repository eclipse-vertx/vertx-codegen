package io.vertx.test.codegen.testdataobject;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@DataObject
public class ConcreteExtendsConcrete extends Concrete {
  public ConcreteExtendsConcrete() {
  }

  public ConcreteExtendsConcrete(ConcreteExtendsConcrete other) {
  }

  public ConcreteExtendsConcrete(JsonObject json) {
  }
}
