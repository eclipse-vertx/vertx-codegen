package io.vertx.test.codegen.future;

import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.test.codegen.future.GenericAbstractInterface;
import io.vertx.test.codegen.testapi.GenericInterface;

import java.util.List;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@VertxGen
public interface InterfaceParameterizedByParameterizedTypeArgMethodOverride<T> extends GenericAbstractInterface<GenericInterface<T>> {

  @Override
  GenericInterface<T> foo();

  @Override
  List<GenericInterface<T>> bar();

  @Override
  Future<GenericInterface<T>> juu();

  @Override
  void daa(Handler<GenericInterface<T>> handler);

  @Override
  void collargol(GenericInterface<T> t);

  @Override
  void selfArg(GenericAbstractInterface<GenericInterface<T>> self);
}
