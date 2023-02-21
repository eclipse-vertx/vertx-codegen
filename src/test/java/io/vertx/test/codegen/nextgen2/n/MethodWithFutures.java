package io.vertx.test.codegen.nextgen2.n;

import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
@VertxGen
public interface MethodWithFutures extends io.vertx.test.codegen.nextgen2.o.MethodWithCallbacks {

  Future<String> futureStyle();

  @Override
  void overridenCallbackStyle1(Handler<AsyncResult<String>> handler);

  @Override
  Future<String> overridenCallbackStyle1();

  @Override
  void overridenCallbackStyle2(String s, Handler<AsyncResult<String>> handler);

  @Override
  Future<String> overridenCallbackStyle2(String s);

}
