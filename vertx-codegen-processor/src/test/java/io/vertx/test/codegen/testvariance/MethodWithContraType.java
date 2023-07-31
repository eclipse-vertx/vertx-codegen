package io.vertx.test.codegen.testvariance;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public interface MethodWithContraType<@Analyze T> {

  void foo(T t);

}
