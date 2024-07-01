package io.vertx.test.codegen.testapi;

import io.vertx.codegen.annotations.GenIgnore;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.Handler;

import java.util.Locale;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
@VertxGen
public interface InterfaceParameterizedByOtherType extends GenericInterface<Locale> {

  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  @Override
  Locale methodWithClassTypeParam(Locale locale, Handler<Locale> handler);

  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  @Override
  <R> GenericInterface<R> someGenericMethod(R r, Handler<R> handler);
}
