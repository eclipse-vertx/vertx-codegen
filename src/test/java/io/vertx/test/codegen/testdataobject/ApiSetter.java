package io.vertx.test.codegen.testdataobject;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@DataObject
public interface ApiSetter {

  public static ApiSetter dataObject() {
    throw new UnsupportedOperationException();
  }

  public static ApiSetter dataObjectFromJson(JsonObject obj) {
    throw new UnsupportedOperationException();
  }

  ApiSetter setApiObject(ApiObject s);

}
