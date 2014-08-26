package io.vertx.test.codegen.testoptions;

import io.vertx.codegen.annotations.Options;
import io.vertx.core.json.JsonObject;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@Options
public interface ApiAdder {

  public static ApiAdder options() {
    throw new UnsupportedOperationException();
  }

  public static ApiAdder optionsFromJson(JsonObject obj) {
    throw new UnsupportedOperationException();
  }

  ApiAdder addApiObject(ApiObject s);

}
