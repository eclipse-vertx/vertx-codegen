package io.vertx.test.codegen.testapi.overloadcheck;

import io.vertx.codegen.annotations.GenIgnore;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.json.JsonObject;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
@VertxGen()
public interface OverloadCheckIgnoreEnhancedMethod<T> {

  void meth(JsonObject arg);

  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  void meth(Thread t);

}
