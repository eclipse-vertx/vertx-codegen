package io.vertx.test.codegen.nextgen2.o;

import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
@VertxGen(concrete = false)
public interface MethodWithCallbacks {

  void callbackStyle1(Handler<AsyncResult<String>> handler);

  Future<String> callbackStyle1();

  void callbackStyle2(String s, Handler<AsyncResult<String>> handler);

  Future<String> callbackStyle2(String s);

  void overridenCallbackStyle1(Handler<AsyncResult<String>> handler);

  Future<String> overridenCallbackStyle1();

  void overridenCallbackStyle2(String s, Handler<AsyncResult<String>> handler);

  Future<String> overridenCallbackStyle2(String s);

}
