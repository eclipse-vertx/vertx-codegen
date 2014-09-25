package io.vertx.test.codegen.testoptions;

import io.vertx.codegen.annotations.Options;
import io.vertx.core.json.JsonObject;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@Options
public class ConcreteInheritsAbstract implements Abstract {
  public ConcreteInheritsAbstract() {
  }

  public ConcreteInheritsAbstract(ConcreteInheritsAbstract other) {
  }

  public ConcreteInheritsAbstract(JsonObject json) {
  }
}
