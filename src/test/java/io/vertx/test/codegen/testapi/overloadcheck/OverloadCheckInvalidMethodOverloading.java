package io.vertx.test.codegen.testapi.overloadcheck;

import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.json.JsonArray;

import java.util.List;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
@VertxGen(concrete = false)
public interface OverloadCheckInvalidMethodOverloading<T> {

  void meth(JsonArray arg);

  void meth(List<String> arg);

}
