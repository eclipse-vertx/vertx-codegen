package io.vertx.test.codegen.testdataobject;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@DataObject
public class NoConverterDataObject {

  public NoConverterDataObject() {
  }

  public NoConverterDataObject(NoConverterDataObject other) {
  }

  public NoConverterDataObject(JsonObject json) {
  }
}
