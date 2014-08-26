package io.vertx.test.codegen.testoptions;

import io.vertx.codegen.annotations.Options;
import io.vertx.core.json.JsonObject;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@Options
public interface JsonObjectSetter {

  public static JsonObjectSetter options() {
    throw new UnsupportedOperationException();
  }

  public static JsonObjectSetter optionsFromJson(JsonObject obj) {
    throw new UnsupportedOperationException();
  }

  JsonObjectSetter setJsonObject(JsonObject s);

}
