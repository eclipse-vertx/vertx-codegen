package io.vertx.test.codegen.proxytestapi;

import io.vertx.codegen.annotations.ProxyClose;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
@ProxyGen
public interface ValidProxyCloseWithFuture {

  @ProxyClose
  void closeIt(Handler<AsyncResult<Void>> handler);

}
