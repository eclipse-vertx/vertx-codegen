package io.vertx.test.codegen.testapi;

import io.vertx.core.gen.VertxGen;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
@VertxGen
public interface MethodWithValidVertxGenParams {

  void methodWithVertxGenParams(String str, VertxGenClass1 myParam1, VertxGenClass2 myParam2);
}
