package io.vertx.test.codegen.testapi;

import io.vertx.codegen.annotations.VertxGen;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
@VertxGen
public interface MethodWithValidVertxGenReturn {

  VertxGenClass1 methodWithVertxGen1Return();
  VertxGenClass2 methodWithVertxGen2Return();


}
