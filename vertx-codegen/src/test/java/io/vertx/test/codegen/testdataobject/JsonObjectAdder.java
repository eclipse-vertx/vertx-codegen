package io.vertx.test.codegen.testdataobject;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@DataObject
public interface JsonObjectAdder {

  public static JsonObjectAdder dataObject() {
    throw new UnsupportedOperationException();
  }

  public static JsonObjectAdder dataObjectFromJson(JsonObject obj) {
    throw new UnsupportedOperationException();
  }

  JsonObjectAdder addJsonObject(JsonObject s);

}
