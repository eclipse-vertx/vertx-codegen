package io.vertx.test.codegen.testoptions;

import io.vertx.codegen.annotations.Options;
import io.vertx.core.json.JsonObject;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@Options
public class ConcreteInheritsOverridenPropertyFromOptions implements OptionsWithProperty {

  public ConcreteInheritsOverridenPropertyFromOptions() {
  }

  public ConcreteInheritsOverridenPropertyFromOptions(ConcreteInheritsOverridenPropertyFromOptions other) {
  }

  public ConcreteInheritsOverridenPropertyFromOptions(JsonObject json) {
  }

  @Override
  public OptionsWithProperty setNonOptionsProperty(String value) {
    return this;
  }
}
