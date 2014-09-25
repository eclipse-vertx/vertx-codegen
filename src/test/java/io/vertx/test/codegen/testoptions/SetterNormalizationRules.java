package io.vertx.test.codegen.testoptions;

import io.vertx.codegen.annotations.Options;
import io.vertx.core.json.JsonObject;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@Options
public interface SetterNormalizationRules {

  SetterNormalizationRules setGroup(boolean group);
  SetterNormalizationRules setHA(boolean ha);
  SetterNormalizationRules setHAGroup(boolean haGroup);

}
