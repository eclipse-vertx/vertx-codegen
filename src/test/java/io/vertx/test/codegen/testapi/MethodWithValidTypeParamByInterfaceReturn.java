package io.vertx.test.codegen.testapi;

import io.vertx.codegen.annotations.VertxGen;
import io.vertx.codegen.testmodel.TestEnum;
import io.vertx.codegen.testmodel.TestGenEnum;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
@VertxGen
public interface MethodWithValidTypeParamByInterfaceReturn {

  GenericInterface<Byte> withByte();
  GenericInterface<Short> withShort();
  GenericInterface<Integer> withInteger();
  GenericInterface<Long> withLong();
  GenericInterface<Float> withFloat();
  GenericInterface<Double> withDouble();
  GenericInterface<Boolean> withBoolean();
  GenericInterface<Character> withCharacter();
  GenericInterface<String> withString();
  GenericInterface<JsonObject> withJsonObject();
  GenericInterface<JsonArray> withJsonArray();
  GenericInterface<ReadOnlyJsonObjectDataObject> withDataObject();
  GenericInterface<TestEnum> withEnum();
  GenericInterface<TestGenEnum> withGenEnum();
  GenericInterface<VertxGenClass1> withUserType();
  GenericInterface<Void> withVoid();
  GenericInterface<Object> withObject();
  GenericInterface<Throwable> withThrowable();
  <T> GenericInterface<T> withTypeVariable();
  <T> GenericInterface<T> withClassType(Class<T> classType);
  GenericInterface<List<Object>> withListOfObjects();
  GenericInterface<Set<Object>> withSetOfObjects();
  GenericInterface<Map<String, Object>> withMapOfObjects();

}
