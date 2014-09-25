package io.vertx.test.codegen.testoptions;

import io.vertx.codegen.annotations.Options;
import io.vertx.core.json.JsonObject;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@Options
public class ConcreteInheritsPropertyFromOptions implements OptionsWithProperty {

  public ConcreteInheritsPropertyFromOptions() {
  }

  public ConcreteInheritsPropertyFromOptions(ConcreteInheritsPropertyFromOptions other) {
  }

  public ConcreteInheritsPropertyFromOptions(JsonObject json) {
  }

  @Override
  public OptionsWithProperty setNonOptionsProperty(String value) {
    return this;
  }
}
