package io.vertx.test.codegen.testdataobject;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;
import io.vertx.test.codegen.testdataobject.imported.Imported;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@DataObject
public interface ImportedSubinterface extends Imported {

  public static ImportedSubinterface dataObject() {
    throw new UnsupportedOperationException();
  }

  public static ImportedSubinterface dataObjectFromJson(JsonObject obj) {
    throw new UnsupportedOperationException();
  }

}
