package io.vertx.test.codegen.testoptions;

import io.vertx.codegen.annotations.GenIgnore;
import io.vertx.codegen.annotations.Options;
import io.vertx.core.json.JsonObject;

import java.util.Locale;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@Options
public interface IgnoreMethods {

  public static IgnoreMethods options() {
    throw new UnsupportedOperationException();
  }

  public static IgnoreMethods optionsFromJson(JsonObject obj) {
    throw new UnsupportedOperationException();
  }

  @GenIgnore
  Locale doSomething();

}
