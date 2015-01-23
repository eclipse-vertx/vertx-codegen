package io.vertx.test.codegen.testdataobject;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@DataObject
public interface AdderWithNestedDataObject {

  public static AdderWithNestedDataObject dataObject() {
    throw new UnsupportedOperationException();
  }

  public static AdderWithNestedDataObject dataObjectFromJson(JsonObject obj) {
    throw new UnsupportedOperationException();
  }

  AdderWithNestedDataObject addNested(Empty nested);

}
