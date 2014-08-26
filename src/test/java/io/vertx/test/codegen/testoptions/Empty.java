package io.vertx.test.codegen.testoptions;

import io.vertx.codegen.annotations.Options;
import io.vertx.core.json.JsonObject;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@Options
public interface Empty {

  public static Empty options() {
    throw new UnsupportedOperationException();
  }

  public static Empty optionsFromJson(JsonObject obj) {
    throw new UnsupportedOperationException();
  }
}
