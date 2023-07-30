package io.vertx.test.codegen.testapi.fluent;

import io.vertx.codegen.annotations.VertxGen;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
@VertxGen
public interface InterfaceWithFluentMethodOverrideFromConcrete extends ConcreteInterfaceWithFluentMethods {

  @Override
  InterfaceWithFluentMethodOverrideFromConcrete foo(String str);

  @Override
  InterfaceWithFluentMethodOverrideFromConcrete bar(int i);

}
