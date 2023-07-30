package io.vertx.test.codegen.testapi;

import io.vertx.codegen.annotations.CacheReturn;
import io.vertx.codegen.annotations.VertxGen;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
@VertxGen
public interface CacheReturnMethodWithVoidReturn {

  @CacheReturn
  void foo(String str);
}
