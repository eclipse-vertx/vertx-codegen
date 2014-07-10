package io.vertx.test.codegen.testapi;

import io.vertx.codegen.annotations.VertxGen;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
@VertxGen
public interface MethodWithValidBasicParams {

  void methodWithBasicParams(byte b, short s, int i, long l, float f, double d, boolean bool, char ch, String str);
}
