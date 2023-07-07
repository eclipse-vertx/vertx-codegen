package io.vertx.test.codegen.testapi.fluent;

import io.vertx.codegen.annotations.Fluent;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.test.codegen.testapi.VertxGenClass1;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
@VertxGen
public interface FluentMethodWithWrongReturnType {

  @Fluent
  VertxGenClass1 foo(String str);
}
