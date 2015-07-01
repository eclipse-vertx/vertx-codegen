package io.vertx.test.codegen.testapi;

import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

import java.util.List;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@VertxGen
public interface InterfaceWithGenericMethodOverride extends GenericAbstractInterface<String> {

  @Override
  String foo();

  @Override
  List<String> bar();

  @Override
  void juu(Handler<AsyncResult<String>> handler);

  @Override
  void daa(Handler<String> handler);

  @Override
  void collargol(String t);
}
