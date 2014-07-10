package io.vertx.test.codegen.testapi;

import io.vertx.core.Handler;
import io.vertx.codegen.annotations.VertxGen;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
@VertxGen
public interface InterfaceWithOverloadedMethods {

  void foo(String str);

  void foo(String str, long time);

  void foo(String str, long time, Handler<VertxGenClass1> handler);

  void bar(VertxGenClass2 obj1);

  void bar(VertxGenClass2 obj1, String str);

}
