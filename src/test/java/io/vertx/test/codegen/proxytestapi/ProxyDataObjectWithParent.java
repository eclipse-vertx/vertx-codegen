package io.vertx.test.codegen.proxytestapi;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

/**
 * Created by Erwin on 25/03/16.
 */
@DataObject
public class ProxyDataObjectWithParent extends ProxyDataObjectParent {

    public ProxyDataObjectWithParent() {
    }

    public ProxyDataObjectWithParent(JsonObject json) {
    }


}
