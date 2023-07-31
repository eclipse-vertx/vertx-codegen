package io.vertx.test.codegen.testapi.fluent;

import io.vertx.codegen.annotations.VertxGen;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
@VertxGen
public interface FluentMethodWithIllegalParameterizedReturn extends FluentMethodWithGenericReturn<String> {

  @Override
  String foo();
}
