package io.vertx.test.codegen.future;

import io.vertx.codegen.annotations.GenIgnore;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.codegen.testmodel.TestDataObject;
import io.vertx.codegen.testmodel.TestEnum;
import io.vertx.core.Future;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.test.codegen.testapi.GenericInterface;
import io.vertx.test.codegen.testapi.VertxGenClass1;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
@VertxGen
public interface MethodWithValidFutureReturns {

  Future<Byte> methodWithByte();
  Future<Short> methodWithShort();
  Future<Integer> methodWithInteger();
  Future<Long> methodWithLong();
  Future<Float> methodWithFloat();
  Future<Double> methodWithDouble();
  Future<Boolean> methodWithBoolean();
  Future<Character> methodWithCharacter();
  Future<String> methodWithString();
  Future<VertxGenClass1> methodWithVertxGen();
  Future<TestDataObject> methodWithDataObject();
  Future<TestEnum> methodWithEnum();
  Future<Object> methodWithAny();
  Future<JsonObject> methodWithJsonObject();
  Future<JsonArray> methodWithJsonArray();
//  @GenIgnore(GenIgnore.PERMITTED_TYPE)
//  Future<Thread> methodWithPermitted();

  Future<List<Byte>> methodWithListOfByte();
  Future<List<Short>> methodWithListOfShort();
  Future<List<Integer>> methodWithListOfInteger();
  Future<List<Long>> methodWithListOfLong();
  Future<List<Float>> methodWithListOfFloat();
  Future<List<Double>> methodWithListOfDouble();
  Future<List<Boolean>> methodWithListOfBoolean();
  Future<List<Character>> methodWithListOfCharacter();
  Future<List<String>> methodWithListOfString();
  Future<List<VertxGenClass1>> methodWithListOfVertxGen();
  Future<List<TestDataObject>> methodWithListOfDataObject();
  Future<List<TestEnum>> methodWithListOfEnum();
  Future<List<Object>> methodWithListOfAny();
  Future<List<JsonObject>> methodWithListOfJsonObject();
  Future<List<JsonArray>> methodWithListOfJsonArray();
//  @GenIgnore(GenIgnore.PERMITTED_TYPE)
//  Future<List<Thread>> methodWithListOfPermitted();

  Future<Set<Byte>> methodWithSetOfByte();
  Future<Set<Short>> methodWithSetOfShort();
  Future<Set<Integer>> methodWithSetOfInteger();
  Future<Set<Long>> methodWithSetOfLong();
  Future<Set<Float>> methodWithSetOfFloat();
  Future<Set<Double>> methodWithSetOfDouble();
  Future<Set<Boolean>> methodWithSetOfBoolean();
  Future<Set<Character>> methodWithSetOfCharacter();
  Future<Set<String>> methodWithSetOfString();
  Future<Set<VertxGenClass1>> methodWithSetOfVertxGen();
  Future<Set<TestDataObject>> methodWithSetOfDataObject();
  Future<Set<TestEnum>> methodWithSetOfEnum();
  Future<Set<Object>> methodWithSetOfAny();
  Future<Set<JsonObject>> methodWithSetOfJsonObject();
  Future<Set<JsonArray>> methodWithSetOfJsonArray();
//  @GenIgnore(GenIgnore.PERMITTED_TYPE)
//  Future<Set<Thread>> methodWithSetOfPermitted();

  Future<Map<String, Byte>> methodWithMapOfByte();
  Future<Map<String, Short>> methodWithMapOfShort();
  Future<Map<String, Integer>> methodWithMapOfInteger();
  Future<Map<String, Long>> methodWithMapOfLong();
  Future<Map<String, Float>> methodWithMapOfFloat();
  Future<Map<String, Double>> methodWithMapOfDouble();
  Future<Map<String, Boolean>> methodWithMapOfBoolean();
  Future<Map<String, Character>> methodWithMapOfCharacter();
  Future<Map<String, String>> methodWithMapOfString();
  Future<Map<String, VertxGenClass1>> methodWithMapOfVertxGen();
  Future<Map<String, TestDataObject>> methodWithMapOfDataObject();
  Future<Map<String, TestEnum>> methodWithMapOfEnum();
  Future<Map<String, Object>> methodWithMapOfAny();
  Future<Map<String, JsonObject>> methodWithMapOfJsonObject();
  Future<Map<String, JsonArray>> methodWithMapOfJsonArray();
//  @GenIgnore(GenIgnore.PERMITTED_TYPE)
//  Future<Map<String, Thread>> methodWithMapOfPermitted();

  Future<GenericInterface<Byte>> methodWithGenericByte();
  Future<GenericInterface<Short>> methodWithGenericShort();
  Future<GenericInterface<Integer>> methodWithGenericInteger();
  Future<GenericInterface<Long>> methodWithGenericLong();
  Future<GenericInterface<Float>> methodWithGenericFloat();
  Future<GenericInterface<Double>> methodWithGenericDouble();
  Future<GenericInterface<Boolean>> methodWithGenericBoolean();
  Future<GenericInterface<Character>> methodWithGenericCharacter();
  Future<GenericInterface<String>> methodWithGenericString();
  Future<GenericInterface<VertxGenClass1>> methodWithGenericVertxGen();
  Future<GenericInterface<TestDataObject>> methodWithGenericDataObject();
  Future<GenericInterface<TestEnum>> methodWithGenericEnum();
  Future<GenericInterface<Object>> methodWithGenericAny();
  Future<GenericInterface<JsonObject>> methodWithGenericJsonObject();
  Future<GenericInterface<JsonArray>> methodWithGenericJsonArray();
//  @GenIgnore(GenIgnore.PERMITTED_TYPE)
//  Future<GenericInterface<Thread>> methodWithGenericPermitted();

}
