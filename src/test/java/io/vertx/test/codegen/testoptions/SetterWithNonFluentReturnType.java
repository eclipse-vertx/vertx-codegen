package io.vertx.test.codegen.testoptions;

import io.vertx.codegen.annotations.Options;
import io.vertx.core.json.JsonObject;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@Options
public interface SetterWithNonFluentReturnType {

  public static SetterWithNonFluentReturnType options() {
    throw new UnsupportedOperationException();
  }

  public static SetterWithNonFluentReturnType optionsFromJson(JsonObject obj) {
    throw new UnsupportedOperationException();
  }

  void setPrimitiveBoolean(boolean b);
  String setString(String locale);

}
