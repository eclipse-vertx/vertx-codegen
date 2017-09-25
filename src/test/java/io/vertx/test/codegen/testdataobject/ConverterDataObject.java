package io.vertx.test.codegen.testdataobject;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@DataObject(generateConverter = true, publicConverter = false)
public class ConverterDataObject {

  public ConverterDataObject() {
  }

  public ConverterDataObject(ConverterDataObject other) {
  }

  public ConverterDataObject(JsonObject json) {
  }
}
