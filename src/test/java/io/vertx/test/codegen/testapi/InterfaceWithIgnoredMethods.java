package io.vertx.test.codegen.testapi;

import io.vertx.codegen.annotations.GenIgnore;
import io.vertx.codegen.annotations.VertxGen;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
@VertxGen
public interface InterfaceWithIgnoredMethods {

  void foo(String str);

  void bar(int i);

  @GenIgnore
  void quux(long l);
}
