package io.vertx.test.codegen.testoptions;

import io.vertx.codegen.annotations.Options;
import io.vertx.core.json.JsonObject;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@Options(concrete = false)
public interface AbstractInheritsConcrete extends Concrete {

  public static AbstractInheritsConcrete options() {
    throw new UnsupportedOperationException();
  }

  public static AbstractInheritsConcrete optionsFromJson(JsonObject obj) {
    throw new UnsupportedOperationException();
  }
}
