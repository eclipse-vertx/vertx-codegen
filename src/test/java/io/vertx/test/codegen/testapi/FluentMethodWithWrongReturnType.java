package io.vertx.test.codegen.testapi;

import io.vertx.codegen.annotations.Fluent;
import io.vertx.codegen.annotations.VertxGen;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
@VertxGen
public interface FluentMethodWithWrongReturnType {

  @Fluent
  VertxGenClass1 foo(String str);
}
