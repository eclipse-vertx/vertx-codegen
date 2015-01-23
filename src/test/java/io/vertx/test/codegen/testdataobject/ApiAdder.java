package io.vertx.test.codegen.testdataobject;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@DataObject
public interface ApiAdder {

  public static ApiAdder dataObject() {
    throw new UnsupportedOperationException();
  }

  public static ApiAdder dataObjectFromJson(JsonObject obj) {
    throw new UnsupportedOperationException();
  }

  ApiAdder addApiObject(ApiObject s);

}
