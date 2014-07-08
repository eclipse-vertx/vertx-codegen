package io.vertx.test.codegen.testapi;

import io.vertx.core.gen.IndexGetter;
import io.vertx.core.gen.IndexSetter;
import io.vertx.core.gen.VertxGen;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
@VertxGen
public interface InterfaceWithIndexSetterGetterMethods {

  @IndexGetter
  byte getAt(int pos);

  @IndexSetter
  void setAt(int pos, byte b);

}
