package io.vertx.test.codegen.testdataobject;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@DataObject
public interface PropertyGetters {

  static PropertyGetters dataObject() {
    throw new UnsupportedOperationException();
  }

  static PropertyGetters dataObjectFromJson(JsonObject obj) {
    throw new UnsupportedOperationException();
  }

  String getString();
  Integer getBoxedInteger();
  int getPrimitiveInteger();
  Boolean isBoxedBoolean();
  boolean isPrimitiveBoolean();
  Long getBoxedLong();
  long getPrimitiveLong();

  ApiObject getApiObject();
  EmptyDataObject getDataObject();
  ToJsonDataObject getToJsonDataObject();

  JsonObject getJsonObject();
  JsonArray getJsonArray();

  Enumerated getEnumerated();
}
