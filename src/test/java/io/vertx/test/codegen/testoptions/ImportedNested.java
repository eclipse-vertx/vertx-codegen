package io.vertx.test.codegen.testoptions;

import io.vertx.codegen.annotations.Options;
import io.vertx.core.json.JsonObject;
import io.vertx.test.codegen.testoptions.imported.Imported;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@Options
public interface ImportedNested {

  public static ImportedNested options() {
    throw new UnsupportedOperationException();
  }

  public static ImportedNested optionsFromJson(JsonObject obj) {
    throw new UnsupportedOperationException();
  }

  ImportedNested setImported(Imported imported);

}
