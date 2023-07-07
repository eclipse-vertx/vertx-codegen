package io.vertx.test.codegen.doc;

import io.vertx.codegen.annotations.VertxGen;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@VertxGen
public interface LinkToMethodInSameType {

  /**{@link #method(String,int)}{@link LinkToMethodInSameType#method(String,int)}{@link LinkToMethodInSameType#method(String,int)}{@link #method(String, int)}*/
  void m();

  void method(String s, int i);

}
