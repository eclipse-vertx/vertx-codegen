package io.vertx.test.codegen.testapi;

import io.vertx.codegen.annotations.VertxGen;
import io.vertx.codegen.testmodel.TestEnum;
import io.vertx.codegen.testmodel.TestGenEnum;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
@VertxGen
public interface MethodWithValidHandlerTypeParamByInterface {

  void withByte(Handler<GenericInterface<Byte>> handler);
  void withShort(Handler<GenericInterface<Short>> handler);
  void withInteger(Handler<GenericInterface<Integer>> handler);
  void withLong(Handler<GenericInterface<Long>> handler);
  void withFloat(Handler<GenericInterface<Float>> handler);
  void withDouble(Handler<GenericInterface<Double>>handler);
  void withBoolean(Handler<GenericInterface<Boolean>> handler);
  void withCharacter(Handler<GenericInterface<Character>> handler);
  void withString(Handler<GenericInterface<String>> handler);
  void withJsonObject(Handler<GenericInterface<JsonObject>> handler);
  void withJsonArray(Handler<GenericInterface<JsonArray>> handler);
  void withDataObject(Handler<GenericInterface<ReadOnlyJsonObjectDataObject>> handler);
  void withEnum(Handler<GenericInterface<TestEnum>> handler);
  void withGenEnum(Handler<GenericInterface<TestGenEnum>> handler);
  void withUserType(Handler<GenericInterface<VertxGenClass1>> handler);
  <T> void withClassType(Class<T> classType, Handler<GenericInterface<T>> handler);

}
