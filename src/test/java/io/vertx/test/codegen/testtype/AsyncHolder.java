package io.vertx.test.codegen.testtype;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.test.codegen.testapi.GenericInterface;

import java.util.List;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public interface AsyncHolder<ClassTypeParam> {

  Handler<AsyncResult<Void>> asyncVoid();
  Handler<AsyncResult<String>> asyncString();
  Handler<AsyncResult<List<String>>> asyncListString();
  Handler<AsyncResult<List<ApiObject>>> asyncListApi();
  Handler<AsyncResult<GenericInterface<ClassTypeParam>>> asyncParameterizedByClassTypeParam();
  <MethodTypeParam> Handler<AsyncResult<GenericInterface<MethodTypeParam>>> asyncParameterizedByMethodTypeParam();

}
