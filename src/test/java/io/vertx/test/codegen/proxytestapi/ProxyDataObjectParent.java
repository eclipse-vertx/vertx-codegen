package io.vertx.test.codegen.proxytestapi;

import io.vertx.core.json.JsonObject;

/**
 * Created by Erwin on 25/03/16.
 */
public abstract class ProxyDataObjectParent {

    public JsonObject toJson() {
        return new JsonObject();
    }
}
