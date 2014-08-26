package io.vertx.test.codegen.testoptions;

import io.vertx.codegen.annotations.Options;
import io.vertx.core.json.JsonObject;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@Options
public interface AdderWithNestedOptions {

  public static AdderWithNestedOptions options() {
    throw new UnsupportedOperationException();
  }

  public static AdderWithNestedOptions optionsFromJson(JsonObject obj) {
    throw new UnsupportedOperationException();
  }

  AdderWithNestedOptions addNested(Empty nested);

}
