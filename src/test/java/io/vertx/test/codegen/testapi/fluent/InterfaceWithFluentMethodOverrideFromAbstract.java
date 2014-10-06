package io.vertx.test.codegen.testapi.fluent;

import io.vertx.codegen.annotations.VertxGen;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
@VertxGen
public interface InterfaceWithFluentMethodOverrideFromAbstract extends AbstractInterfaceWithFluentMethods {

  @Override
  InterfaceWithFluentMethodOverrideFromAbstract foo(String str);

  @Override
  InterfaceWithFluentMethodOverrideFromAbstract bar(int i);

}
