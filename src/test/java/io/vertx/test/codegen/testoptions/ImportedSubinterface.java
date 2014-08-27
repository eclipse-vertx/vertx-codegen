package io.vertx.test.codegen.testoptions;

import io.vertx.codegen.annotations.Options;
import io.vertx.core.json.JsonObject;
import io.vertx.test.codegen.testoptions.imported.Imported;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@Options
public interface ImportedSubinterface extends Imported {

  public static ImportedSubinterface options() {
    throw new UnsupportedOperationException();
  }

  public static ImportedSubinterface optionsFromJson(JsonObject obj) {
    throw new UnsupportedOperationException();
  }

}
