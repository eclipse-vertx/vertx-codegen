package io.vertx.test.codegen.testapi;

import io.vertx.codegen.annotations.VertxGen;

/**
 * Interface comment line 1
 * Interface comment line 2
 * Interface comment line 3
 *
 * @author <a href="http://tfox.org">Tim Fox</a>
 * @version 12.2
 * @see io.vertx.codegen.testmodel.TestInterface
 */
@VertxGen
public interface InterfaceWithComments {

  /**
   * Comment 1 line 1
   * Comment 1 line 2
   * @param str the_string
   * @return the_return_value
   */
  String foo(String str);

  /**
   * Comment 2 line 1
   * Comment 2 line 2
   */
  void bar(String str);

  /**
   * Comment 3 line 1
   * Comment 3 line 2
   */
  String someField = "value-does-not-matter";

}
