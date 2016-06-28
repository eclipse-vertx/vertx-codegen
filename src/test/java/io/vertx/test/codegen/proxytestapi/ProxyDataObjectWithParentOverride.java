package io.vertx.test.codegen.proxytestapi;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

/**
 * Created by Erwin on 25/03/16.
 */
@DataObject
public class ProxyDataObjectWithParentOverride extends ProxyDataObjectParent {

    public ProxyDataObjectWithParentOverride() {
    }

    public ProxyDataObjectWithParentOverride(JsonObject json) {
    }

    @Override
    public JsonObject toJson() {
        return super.toJson();
    }
}
