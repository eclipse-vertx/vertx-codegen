package io.vertx.test.codegen.testdataobject;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@DataObject
public interface BasicGetters {

  public static BasicGetters dataObject() {
    throw new UnsupportedOperationException();
  }

  public static BasicGetters dataObjectFromJson(JsonObject obj) {
    throw new UnsupportedOperationException();
  }

  String getString();
  Integer getBoxedInteger();
  int getPrimitiveInteger();
  Boolean isBoxedBoolean();
  boolean isPrimitiveBoolean();
  Long getBoxedLong();
  long getPrimitiveLong();

}
