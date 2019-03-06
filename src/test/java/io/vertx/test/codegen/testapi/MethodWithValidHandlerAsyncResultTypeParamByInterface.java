package io.vertx.test.codegen.testapi;

import io.vertx.codegen.annotations.VertxGen;
import io.vertx.codegen.testmodel.TestEnum;
import io.vertx.codegen.testmodel.TestGenEnum;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
@VertxGen
public interface MethodWithValidHandlerAsyncResultTypeParamByInterface {

  <T> void withType(Class<T> classType, Handler<AsyncResult<T>> handler);
  <T> void withListType(Class<T> classType, Handler<AsyncResult<List<T>>> handler);
  <T> void withSetType(Class<T> classType, Handler<AsyncResult<Set<T>>> handler);
  <T> void withMapType(Class<T> classType, Handler<AsyncResult<Map<String, T>>> handler);
  <T> void withGenericType(Class<T> classType, Handler<AsyncResult<GenericInterface<T>>> handler);

}
