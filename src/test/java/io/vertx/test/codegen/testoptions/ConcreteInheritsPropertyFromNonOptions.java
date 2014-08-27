package io.vertx.test.codegen.testoptions;

import io.vertx.codegen.annotations.Options;
import io.vertx.core.json.JsonObject;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@Options
public interface ConcreteInheritsPropertyFromNonOptions extends NonOptionsWithProperty<ConcreteInheritsPropertyFromNonOptions> {

  public static ConcreteInheritsPropertyFromNonOptions options() {
    throw new UnsupportedOperationException();
  }

  public static ConcreteInheritsPropertyFromNonOptions optionsFromJson(JsonObject obj) {
    throw new UnsupportedOperationException();
  }
}
