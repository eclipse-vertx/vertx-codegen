package io.vertx.test.codegen.testapi.nullable;

import io.vertx.codegen.annotations.Nullable;
import io.vertx.codegen.annotations.VertxGen;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@VertxGen(concrete = false)
public interface DiamondGenericSub2FluentNullableParam<T> extends DiamondTopFluentNullableParam {

  @Override
  DiamondGenericSub2FluentNullableParam<T> method( String param);

}
