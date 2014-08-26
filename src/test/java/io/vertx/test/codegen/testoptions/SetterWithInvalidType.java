package io.vertx.test.codegen.testoptions;

import io.vertx.codegen.annotations.Options;
import io.vertx.core.json.JsonObject;

import java.util.Locale;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@Options
public interface SetterWithInvalidType {

  public static SetterWithInvalidType options() {
    throw new UnsupportedOperationException();
  }

  public static SetterWithInvalidType optionsFromJson(JsonObject obj) {
    throw new UnsupportedOperationException();
  }

  SetterWithInvalidType setString(Locale locale);

}
