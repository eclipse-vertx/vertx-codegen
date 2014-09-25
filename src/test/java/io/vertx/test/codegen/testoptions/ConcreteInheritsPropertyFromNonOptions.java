package io.vertx.test.codegen.testoptions;

import io.vertx.codegen.annotations.Options;
import io.vertx.core.json.JsonObject;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@Options
public class ConcreteInheritsPropertyFromNonOptions implements NonOptionsWithProperty<ConcreteInheritsPropertyFromNonOptions> {

  public ConcreteInheritsPropertyFromNonOptions() {
  }

  public ConcreteInheritsPropertyFromNonOptions(ConcreteInheritsPropertyFromNonOptions other) {
  }

  public ConcreteInheritsPropertyFromNonOptions(JsonObject json) {
  }

  @Override
  public ConcreteInheritsPropertyFromNonOptions setNonOptionsProperty(String value) {
    return this;
  }
}
