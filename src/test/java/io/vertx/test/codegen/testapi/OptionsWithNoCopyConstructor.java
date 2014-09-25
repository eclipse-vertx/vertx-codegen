package io.vertx.test.codegen.testapi;

import io.vertx.codegen.annotations.Options;
import io.vertx.core.json.JsonObject;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@Options
public class OptionsWithNoCopyConstructor {

  public OptionsWithNoCopyConstructor() {
  }

  public OptionsWithNoCopyConstructor(JsonObject json) {
  }
}
