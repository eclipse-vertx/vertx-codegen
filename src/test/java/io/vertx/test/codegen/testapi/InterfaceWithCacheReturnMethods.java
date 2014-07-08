package io.vertx.test.codegen.testapi;

import io.vertx.core.gen.CacheReturn;
import io.vertx.core.gen.VertxGen;

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
