package io.vertx.test.codegen.testdataobject;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@DataObject
public abstract class AbstractInheritsConcrete extends Concrete {
  public AbstractInheritsConcrete() {
  }

  public AbstractInheritsConcrete(AbstractInheritsConcrete other) {
  }

  public AbstractInheritsConcrete(JsonObject json) {
  }
}
