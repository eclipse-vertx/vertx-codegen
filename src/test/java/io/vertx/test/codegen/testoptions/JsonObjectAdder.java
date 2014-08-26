package io.vertx.test.codegen.testoptions;

import io.vertx.codegen.annotations.Options;
import io.vertx.core.json.JsonObject;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@Options
public interface JsonObjectAdder {

  public static JsonObjectAdder options() {
    throw new UnsupportedOperationException();
  }

  public static JsonObjectAdder optionsFromJson(JsonObject obj) {
    throw new UnsupportedOperationException();
  }

  JsonObjectAdder addJsonObject(JsonObject s);

}
