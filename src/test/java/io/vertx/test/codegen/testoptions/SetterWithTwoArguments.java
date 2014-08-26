package io.vertx.test.codegen.testoptions;

import io.vertx.codegen.annotations.Options;
import io.vertx.core.json.JsonObject;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@Options
public interface SetterWithTwoArguments {

  public static SetterWithTwoArguments options() {
    throw new UnsupportedOperationException();
  }

  public static SetterWithTwoArguments optionsFromJson(JsonObject obj) {
    throw new UnsupportedOperationException();
  }

  SetterWithTwoArguments setString(String s1, String s2);

}
