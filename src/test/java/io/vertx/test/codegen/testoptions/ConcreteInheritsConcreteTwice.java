package io.vertx.test.codegen.testoptions;

import io.vertx.codegen.annotations.Options;
import io.vertx.core.json.JsonObject;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@Options
public interface ConcreteInheritsConcreteTwice extends Concrete, Concrete2 {

  public static ConcreteInheritsConcreteTwice options() {
    throw new UnsupportedOperationException();
  }

  public static ConcreteInheritsConcreteTwice optionsFromJson(JsonObject obj) {
    throw new UnsupportedOperationException();
  }

}
