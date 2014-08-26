package io.vertx.test.codegen.testoptions;

import io.vertx.codegen.annotations.Options;
import io.vertx.core.json.JsonObject;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@Options
public interface AdderNormalizationRules {

  public static AdderNormalizationRules options() {
    throw new UnsupportedOperationException();
  }

  public static AdderNormalizationRules optionsFromJson(JsonObject obj) {
    throw new UnsupportedOperationException();
  }

  AdderNormalizationRules addLocator(boolean locator);
  AdderNormalizationRules addURL(boolean url);
  AdderNormalizationRules addURLLocator(boolean urlLocator);

}
