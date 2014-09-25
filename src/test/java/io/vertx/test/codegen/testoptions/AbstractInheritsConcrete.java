package io.vertx.test.codegen.testoptions;

import io.vertx.codegen.annotations.Options;
import io.vertx.core.json.JsonObject;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@Options
public abstract class AbstractInheritsConcrete extends Concrete {
  public AbstractInheritsConcrete() {
  }

  public AbstractInheritsConcrete(AbstractInheritsConcrete other) {
  }

  public AbstractInheritsConcrete(JsonObject json) {
  }
}
