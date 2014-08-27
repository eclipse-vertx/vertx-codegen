package io.vertx.test.codegen.testoptions;

import io.vertx.codegen.annotations.Options;
import io.vertx.core.json.JsonObject;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@Options
public interface ConcreteInheritsOverridenPropertyFromOptions extends OptionsWithProperty {

  public static ConcreteInheritsOverridenPropertyFromOptions options() {
    throw new UnsupportedOperationException();
  }

  public static ConcreteInheritsOverridenPropertyFromOptions optionsFromJson(JsonObject obj) {
    throw new UnsupportedOperationException();
  }

  @Override
  OptionsWithProperty setNonOptionsProperty(String value);
}
