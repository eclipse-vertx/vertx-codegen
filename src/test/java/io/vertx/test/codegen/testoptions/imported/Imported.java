package io.vertx.test.codegen.testoptions.imported;

import io.vertx.codegen.annotations.Options;
import io.vertx.core.json.JsonObject;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@Options
public interface Imported {

  public static Imported options() {
    throw new UnsupportedOperationException();
  }

  public static Imported optionsFromJson(JsonObject obj) {
    throw new UnsupportedOperationException();
  }
}
