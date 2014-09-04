package io.vertx.test.codegen.testvariance;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public interface MethodWithInvType<@Analyze T> {

  T foo(T t);

}
