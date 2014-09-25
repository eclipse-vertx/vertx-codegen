package io.vertx.test.codegen.testoptions;

import io.vertx.codegen.annotations.Options;
import io.vertx.core.json.JsonObject;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@Options
public interface Concrete2 {

  public static Concrete2 options() {
    throw new UnsupportedOperationException();
  }

  public static Concrete2 optionsFromJson(JsonObject obj) {
    throw new UnsupportedOperationException();
  }
}
