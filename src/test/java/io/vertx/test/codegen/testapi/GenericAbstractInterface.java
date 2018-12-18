package io.vertx.test.codegen.testapi;

import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

import java.util.List;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
@VertxGen(concrete = false)
public interface GenericAbstractInterface<T> {

  T foo();

  List<T> bar();

  void juu(Handler<AsyncResult<T>> handler);

  void daa(Handler<T> handler);

  void collargol(T t);

  void selfArg(GenericAbstractInterface<T> self);

  void inheritedSelfArg(GenericAbstractInterface<T> self);

}
