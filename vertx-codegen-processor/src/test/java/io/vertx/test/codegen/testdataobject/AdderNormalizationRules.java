package io.vertx.test.codegen.testdataobject;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@DataObject
public interface AdderNormalizationRules {

  public static AdderNormalizationRules dataObject() {
    throw new UnsupportedOperationException();
  }

  public static AdderNormalizationRules dataObjectFromJson(JsonObject obj) {
    throw new UnsupportedOperationException();
  }

  AdderNormalizationRules addLocator(boolean locator);
  AdderNormalizationRules addURL(boolean url);
  AdderNormalizationRules addURLLocator(boolean urlLocator);

}
