package io.vertx.test.codegen.testapi;

import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

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
  void juu(Handler<AsyncResult<GenericInterface<T>>> handler);

  @Override
  void daa(Handler<GenericInterface<T>> handler);

  @Override
  void collargol(GenericInterface<T> t);

  @Override
  void selfArg(GenericAbstractInterface<GenericInterface<T>> self);
}
