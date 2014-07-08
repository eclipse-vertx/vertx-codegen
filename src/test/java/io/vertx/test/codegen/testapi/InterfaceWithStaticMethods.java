package io.vertx.test.codegen.testapi;

import io.vertx.core.gen.VertxGen;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
@VertxGen
public interface InterfaceWithStaticMethods {

  static VertxGenClass1 foo(String str) {
    return null;
  }

  static VertxGenClass2 bar(int i) {
    return null;
  }

}
