package io.vertx.test.codegen.testapi;

import io.vertx.codegen.annotations.Options;
import io.vertx.core.json.JsonObject;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@Options
public interface OptionsWithFactoryMethod {

  static OptionsWithFactoryMethod optionsFromJson(JsonObject json) {
    return null;
  }
}
