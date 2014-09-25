package io.vertx.test.codegen.testoptions;

import io.vertx.codegen.annotations.Options;
import io.vertx.core.json.JsonObject;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@Options
public class ConcreteInheritsNonOptions implements NonOptions {

  public ConcreteInheritsNonOptions() {
  }

  public ConcreteInheritsNonOptions(ConcreteInheritsNonOptions other) {
  }

  public ConcreteInheritsNonOptions(JsonObject json) {
  }
}
