package io.vertx.test.codegen.proxytestapi;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

/**
 * @author <a href="mailto:nscavell@redhat.com">Nick Scavelli</a>
 */
@DataObject
public class ProxyDataObject {

  public ProxyDataObject() {
  }

  public ProxyDataObject(JsonObject json) {
  }

  public JsonObject toJson() {
    return new JsonObject();
  }
}
