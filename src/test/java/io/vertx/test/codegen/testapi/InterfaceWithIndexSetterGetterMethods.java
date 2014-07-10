package io.vertx.test.codegen.testapi;

import io.vertx.codegen.annotations.IndexGetter;
import io.vertx.codegen.annotations.IndexSetter;
import io.vertx.codegen.annotations.VertxGen;

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
