package io.vertx.test.codegen.testapi;

import io.vertx.codegen.annotations.VertxGen;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
@VertxGen
public interface OverloadedMethodsInWrongTypeParameterOrder {

  <A, B, C> void foo(A a, B b, C c);

  <A, C, B> void foo(A a, B b, C c, String s);
}
