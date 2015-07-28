package io.vertx.test.codegen.testdataobject;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@DataObject(inheritConverter = false)
public class InheritedConverterDataObject {

  public InheritedConverterDataObject() {
  }

  public InheritedConverterDataObject(InheritedConverterDataObject other) {
  }

  public InheritedConverterDataObject(JsonObject json) {
  }
}
