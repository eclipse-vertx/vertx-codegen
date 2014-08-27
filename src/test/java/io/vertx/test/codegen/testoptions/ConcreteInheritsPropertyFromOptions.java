package io.vertx.test.codegen.testoptions;

import io.vertx.codegen.annotations.Options;
import io.vertx.core.json.JsonObject;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@Options
public interface ConcreteInheritsPropertyFromOptions extends OptionsWithProperty {

  public static ConcreteInheritsPropertyFromOptions options() {
    throw new UnsupportedOperationException();
  }

  public static ConcreteInheritsPropertyFromOptions optionsFromJson(JsonObject obj) {
    throw new UnsupportedOperationException();
  }
}
