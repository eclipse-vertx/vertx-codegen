package io.vertx.test.codegen.testoptions;

import io.vertx.codegen.annotations.Options;
import io.vertx.core.json.JsonObject;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@Options
public interface SetterWithInvalidReturnType {

  public static SetterWithInvalidReturnType options() {
    throw new UnsupportedOperationException();
  }

  public static SetterWithInvalidReturnType optionsFromJson(JsonObject obj) {
    throw new UnsupportedOperationException();
  }

  String setString(String locale);

}
