package io.vertx.test.codegen.testapi;

import io.vertx.codegen.annotations.VertxGen;
import io.vertx.codegen.testmodel.TestEnum;
import io.vertx.codegen.testmodel.TestGenEnum;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
@VertxGen
public interface MethodWithValidHandlerAsyncResultTypeParamByInterface {

  void withByte(Handler<AsyncResult<GenericInterface<Byte>>> handler);
  void withShort(Handler<AsyncResult<GenericInterface<Short>>> handler);
  void withInteger(Handler<AsyncResult<GenericInterface<Integer>>> handler);
  void withLong(Handler<AsyncResult<GenericInterface<Long>>> handler);
  void withFloat(Handler<AsyncResult<GenericInterface<Float>>> handler);
  void withDouble(Handler<AsyncResult<GenericInterface<Double>>> handler);
  void withBoolean(Handler<AsyncResult<GenericInterface<Boolean>>> handler);
  void withCharacter(Handler<AsyncResult<GenericInterface<Character>>> handler);
  void withString(Handler<AsyncResult<GenericInterface<String>>> handler);
  void withJsonObject(Handler<AsyncResult<GenericInterface<JsonObject>>> handler);
  void withJsonArray(Handler<AsyncResult<GenericInterface<JsonArray>>> handler);
  void withDataObject(Handler<AsyncResult<GenericInterface<PlainDataObjectWithToJson>>> handler);
  void withEnum(Handler<AsyncResult<GenericInterface<TestEnum>>> handler);
  void withGenEnum(Handler<AsyncResult<GenericInterface<TestGenEnum>>> handler);
  void withUserType(Handler<AsyncResult<GenericInterface<VertxGenClass1>>> handler);
//  void withThrowableType(Handler<AsyncResult<GenericInterface<Throwable>>> handler);
//  void withVoidType(Handler<AsyncResult<GenericInterface<Void>>> handler);
//  void withObjectType(Handler<AsyncResult<GenericInterface<Object>>> handler);
//  <T> void withGenericType(Handler<AsyncResult<GenericInterface<T>>> handler);
  <T> void withClassType(Class<T> classType, Handler<AsyncResult<GenericInterface<T>>> handler);

}
