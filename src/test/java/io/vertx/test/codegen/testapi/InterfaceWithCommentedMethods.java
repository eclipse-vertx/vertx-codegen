package io.vertx.test.codegen.testapi;

import io.vertx.core.gen.VertxGen;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
@VertxGen
public interface InterfaceWithCommentedMethods {

  /**
   * Comment 1 line 1
   * Comment 1 line 2
   */
  void foo(String str);

  /**
   * Comment 2 line 1
   * Comment 2 line 2
   */
  void bar(String str);


}
