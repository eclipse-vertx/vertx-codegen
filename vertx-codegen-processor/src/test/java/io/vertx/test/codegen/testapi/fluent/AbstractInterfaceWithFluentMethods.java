package io.vertx.test.codegen.testapi.fluent;

import io.vertx.codegen.annotations.Fluent;
import io.vertx.codegen.annotations.VertxGen;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
@VertxGen(concrete = false)
public interface AbstractInterfaceWithFluentMethods {

  @Fluent
  AbstractInterfaceWithFluentMethods foo(String str);

  @Fluent
  AbstractInterfaceWithFluentMethods bar(int i);

}
