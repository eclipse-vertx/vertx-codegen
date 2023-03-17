package io.vertx.test.codegen.future;

import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.test.codegen.future.GenericAbstractInterface;

import java.util.List;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@VertxGen
public interface InterfaceParameterizedByClassArgMethodOverride extends GenericAbstractInterface<String> {

  @Override
  String foo();

  @Override
  List<String> bar();

  @Override
  Future<String> juu();

  @Override
  void daa(Handler<String> handler);

  @Override
  void collargol(String t);

  @Override
  void selfArg(GenericAbstractInterface<String> self);
}
