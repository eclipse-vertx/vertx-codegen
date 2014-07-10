package io.vertx.test.codegen.testapi;

import io.vertx.codegen.annotations.VertxGen;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
@VertxGen
public interface MethodWithValidBasicBoxedParams {

  void methodWithBasicParams(Byte b, Short s, Integer i, Long l, Float f, Double d, Boolean bool, Character ch, String str);
}
