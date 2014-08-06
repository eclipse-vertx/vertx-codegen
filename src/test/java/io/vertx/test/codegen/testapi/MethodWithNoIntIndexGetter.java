package io.vertx.test.codegen.testapi;

import io.vertx.codegen.annotations.IndexGetter;
import io.vertx.codegen.annotations.VertxGen;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@VertxGen
public interface MethodWithNoIntIndexGetter {

  @IndexGetter
  byte getAt(String pos);

}
