package io.vertx.test.codegen.testdataobject;

import io.vertx.codegen.annotations.VertxGen;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@VertxGen
public interface ApiObject {
   String getFoo();
  void setFoo(String foo);

}
