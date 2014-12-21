package io.vertx.test.codegen.testapi;

import io.vertx.codegen.annotations.Options;
import io.vertx.core.json.JsonObject;

/**
 * @author <a href="http://www.campudus.com">Joern Bernhardt</a>
 */
@Options
public class OptionsWithNoToJsonMethod {

  public OptionsWithNoToJsonMethod() {
  }

  public OptionsWithNoToJsonMethod(OptionsWithNoToJsonMethod other) {
  }

  public OptionsWithNoToJsonMethod(JsonObject json) {
  }

}
