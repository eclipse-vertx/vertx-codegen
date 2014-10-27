package io.vertx.test.codegen.proxytestapi;

import io.vertx.codegen.annotations.Options;
import io.vertx.core.json.JsonObject;

/**
 * @author <a href="mailto:nscavell@redhat.com">Nick Scavelli</a>
 */
@Options
public class ProxyOptions {

  public ProxyOptions() {
  }

  public ProxyOptions(JsonObject json) {
  }

  public JsonObject toJson() {
    return new JsonObject();
  }
}
