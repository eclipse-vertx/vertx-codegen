package io.vertx.test.codegen.testvariance;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public interface MethodWithVarAsContraParamInContraPos<@Analyze T> {

  void foo(MethodWithContraType<T> arg);

}
