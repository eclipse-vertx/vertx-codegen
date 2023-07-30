package io.vertx.test.codegen.testapi.nullable;

import io.vertx.codegen.annotations.Nullable;
import io.vertx.codegen.annotations.VertxGen;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@VertxGen
public interface DiamondGenericBottomFluentNullableParam<T> extends DiamondGenericSub1FluentNullableParam<T>, DiamondGenericSub2FluentNullableParam<T> {

  @Override
  DiamondGenericBottomFluentNullableParam<T> method(String param);

}
