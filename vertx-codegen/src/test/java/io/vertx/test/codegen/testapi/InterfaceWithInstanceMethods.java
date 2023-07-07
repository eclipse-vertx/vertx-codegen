package io.vertx.test.codegen.testapi;

import io.vertx.codegen.annotations.VertxGen;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
@VertxGen
public interface InterfaceWithInstanceMethods {

  VertxGenClass1 foo(String str);

  VertxGenClass2 bar(int i);

}
