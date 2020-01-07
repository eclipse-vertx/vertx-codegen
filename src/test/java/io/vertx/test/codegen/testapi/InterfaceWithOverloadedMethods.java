package io.vertx.test.codegen.testapi;

import io.vertx.core.Handler;
import io.vertx.codegen.annotations.VertxGen;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
@VertxGen
public interface InterfaceWithOverloadedMethods {

  void foo(String str);

  void foo(String str, Handler<VertxGenClass1> handler);

  void foo(String str, long time, Handler<VertxGenClass1> handler);

  void bar(VertxGenClass2 obj1);

  void bar(String obj1);

  void juu(String str);

  void juu(String str, long time);

  <T> void juu(String str, long time, Handler<T> handler);

  // The generic type name does not matter when checking return type overloaded
  <T> GenericInterface<T> method();
  <U> GenericInterface<U> method(String s);

}
