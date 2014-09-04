package io.vertx.test.codegen.testvariance;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public interface MethodWithVarAsCoParamAsCoParamInCoPos<@Analyze T> {

  MethodWithCoType<MethodWithCoType<T>> foo();

}
