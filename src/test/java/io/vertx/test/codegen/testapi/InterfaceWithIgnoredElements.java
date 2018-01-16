package io.vertx.test.codegen.testapi;

import io.vertx.codegen.annotations.GenIgnore;
import io.vertx.codegen.annotations.VertxGen;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
@VertxGen
public interface InterfaceWithIgnoredElements {

  void foo(String str);

  void bar(int i);

  @GenIgnore
  void quux(long l);

  @GenIgnore
  interface NestedInterface {}

  @GenIgnore
  class NestedClass {}

  int IMPLICITELY_IGNORED_CONSTANT = 0;

}
