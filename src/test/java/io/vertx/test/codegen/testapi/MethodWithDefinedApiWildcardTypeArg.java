package io.vertx.test.codegen.testapi;

import java.util.List;

import io.vertx.codegen.annotations.GenTypeParams;
import io.vertx.codegen.annotations.GenTypeParams.Param;
import io.vertx.codegen.annotations.VertxGen;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
@VertxGen
public interface MethodWithDefinedApiWildcardTypeArg {

  @GenTypeParams(@Param(name="GENERIC_INTERACE", generated=GenericInterface.class))
  <GENERIC_INTERACE extends GenericInterface<?>> void fooApi(GENERIC_INTERACE generic);
  
  @GenTypeParams(@Param(name="ANY_LIST", generated=List.class))
  <ANY_LIST extends List<?>> void fooList(ANY_LIST generic);
  
}
