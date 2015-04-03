package io.vertx.test.codegen.proxytestapi;

import io.vertx.codegen.annotations.ProxyClose;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
@ProxyGen
public interface InvalidClose2 {

  @ProxyClose
  void closeIt(String s);
}
