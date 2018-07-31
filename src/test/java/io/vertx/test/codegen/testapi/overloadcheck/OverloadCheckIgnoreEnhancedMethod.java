package io.vertx.test.codegen.testapi.overloadcheck;

import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.json.JsonObject;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
@VertxGen()
public interface OverloadCheckIgnoreEnhancedMethod<T> {

  @SuppressWarnings("codegen-allow-any-java-type")
  void meth(JsonObject arg);

  @SuppressWarnings("codegen-allow-any-java-type")
  void meth(Thread t);

}
