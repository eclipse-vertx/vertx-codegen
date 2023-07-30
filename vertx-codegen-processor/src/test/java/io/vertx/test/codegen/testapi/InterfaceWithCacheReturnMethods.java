package io.vertx.test.codegen.testapi;

import io.vertx.codegen.annotations.CacheReturn;
import io.vertx.codegen.annotations.VertxGen;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
@VertxGen
public interface InterfaceWithCacheReturnMethods {

  @CacheReturn
  String foo(String str);

  @CacheReturn
  VertxGenClass1 bar(int i);

}
