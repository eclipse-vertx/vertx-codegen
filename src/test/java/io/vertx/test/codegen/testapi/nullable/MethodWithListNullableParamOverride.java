package io.vertx.test.codegen.testapi.nullable;

import io.vertx.codegen.annotations.VertxGen;

import java.util.List;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@VertxGen
public interface MethodWithListNullableParamOverride extends MethodWithListNullableParam {

  @Override
  void method(List<String> param);

}
