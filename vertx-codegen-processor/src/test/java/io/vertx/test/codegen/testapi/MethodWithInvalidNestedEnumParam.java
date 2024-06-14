package io.vertx.test.codegen.testapi;

import io.vertx.codegen.annotations.VertxGen;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
@VertxGen
public interface MethodWithInvalidNestedEnumParam {

  void methodWithEnumParam(InnerEnum weirdo);

  enum InnerEnum {

  }

}
