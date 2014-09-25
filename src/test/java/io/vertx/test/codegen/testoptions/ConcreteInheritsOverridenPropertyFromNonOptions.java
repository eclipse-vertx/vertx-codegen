package io.vertx.test.codegen.testoptions;

import io.vertx.codegen.annotations.Options;
import io.vertx.core.json.JsonObject;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@Options
public class ConcreteInheritsOverridenPropertyFromNonOptions implements NonOptionsWithProperty<ConcreteInheritsOverridenPropertyFromNonOptions> {

  public ConcreteInheritsOverridenPropertyFromNonOptions() {
  }

  public ConcreteInheritsOverridenPropertyFromNonOptions(ConcreteInheritsOverridenPropertyFromNonOptions other) {
  }

  public ConcreteInheritsOverridenPropertyFromNonOptions(JsonObject json) {
  }

  @Override
  public ConcreteInheritsOverridenPropertyFromNonOptions setNonOptionsProperty(String value) {
    return this;
  }
}
