package io.vertx.test.codegen.testapi;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

/**
 * @author <a href="http://www.campudus.com">Joern Bernhardt</a>
 */
@DataObject
public class DataObjectWithNoToJsonMethod {

  public DataObjectWithNoToJsonMethod() {
  }

  public DataObjectWithNoToJsonMethod(DataObjectWithNoToJsonMethod other) {
  }

  public DataObjectWithNoToJsonMethod(JsonObject json) {
  }

}
