package io.vertx.test.codegen.testtype;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public interface JsonHolder {

  JsonObject jsonObject();
  JsonArray jsonArray();

}
