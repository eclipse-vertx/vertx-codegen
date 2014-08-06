package io.vertx.test.codegen.testapi;

import io.vertx.codegen.annotations.IndexSetter;
import io.vertx.codegen.annotations.VertxGen;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@VertxGen
public interface MethodWithNoIntIndexSetter {

  @IndexSetter
  void setAt(String pos, byte b);

}
