package io.vertx.test.codegen.testapi;

import io.vertx.core.gen.GenIgnore;
import io.vertx.core.gen.VertxGen;

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
