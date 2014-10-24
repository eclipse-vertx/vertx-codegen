package io.vertx.test.codegen.proxytestapi;

import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.core.json.JsonObject;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
@ProxyGen
public interface InvalidReturn3 {

  JsonObject someMethod();
}
