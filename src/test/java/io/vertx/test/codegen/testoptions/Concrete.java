package io.vertx.test.codegen.testoptions;

import io.vertx.codegen.annotations.Options;
import io.vertx.core.json.JsonObject;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@Options(concrete = true)
public interface Concrete {

  public static Concrete options() {
    throw new UnsupportedOperationException();
  }

  public static Concrete optionsFromJson(JsonObject obj) {
    throw new UnsupportedOperationException();
  }
}
