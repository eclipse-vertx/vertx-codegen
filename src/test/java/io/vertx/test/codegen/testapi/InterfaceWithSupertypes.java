package io.vertx.test.codegen.testapi;

import io.vertx.core.gen.VertxGen;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
@VertxGen
public interface InterfaceWithSupertypes extends VertxGenClass1, VertxGenClass2 {

  void quux(String str);

}
