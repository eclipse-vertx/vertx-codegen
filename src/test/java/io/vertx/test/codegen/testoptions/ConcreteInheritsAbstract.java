package io.vertx.test.codegen.testoptions;

import io.vertx.codegen.annotations.Options;
import io.vertx.core.json.JsonObject;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@Options
public interface ConcreteInheritsAbstract extends Abstract {

  public static ConcreteInheritsAbstract options() {
    throw new UnsupportedOperationException();
  }

  public static ConcreteInheritsAbstract optionsFromJson(JsonObject obj) {
    throw new UnsupportedOperationException();
  }

}
