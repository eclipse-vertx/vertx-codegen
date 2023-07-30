package io.vertx.test.codegen.testapi.fluent;

import io.vertx.codegen.annotations.Fluent;
import io.vertx.codegen.annotations.VertxGen;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
@VertxGen()
public interface ConcreteInterfaceWithFluentMethods {

  @Fluent
  ConcreteInterfaceWithFluentMethods foo(String str);

  @Fluent
  ConcreteInterfaceWithFluentMethods bar(int i);

}
