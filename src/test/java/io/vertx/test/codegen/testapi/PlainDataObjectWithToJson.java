package io.vertx.test.codegen.testapi;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@DataObject
public class PlainDataObjectWithToJson {

  public PlainDataObjectWithToJson(JsonObject json) {
  }

  public JsonObject toJson() {
    throw new UnsupportedOperationException();
  }
}
