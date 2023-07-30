/*
 * Copyright 2014 Red Hat, Inc.
 *
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  and Apache License v2.0 which accompanies this distribution.
 *
 *  The Eclipse Public License is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 *
 *  The Apache License v2.0 is available at
 *  http://www.opensource.org/licenses/apache2.0.php
 *
 *  You may elect to redistribute this code under either of these licenses.
 */

package io.vertx.test.codegen.testapi;

import io.vertx.codegen.annotations.GenIgnore;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.codegen.testmodel.TestDataObject;
import io.vertx.codegen.testmodel.TestEnum;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
@VertxGen
public interface MethodWithValidFunctionParams {

  <T> void methodWithFunctionParams(Function<Byte, Byte> byteFunction, Function<Short, Short> shortFunction, Function<Integer, Integer> intFunction,
                               Function<Long, Long> longFunction, Function<Float, Float> floatFunction, Function<Double, Double> doubleFunction,
                               Function<Boolean, Boolean> booleanFunction, Function<Character, Character> charFunction, Function<String, String> strFunction,
                               Function<VertxGenClass1, VertxGenClass1> gen1Function, Function<VertxGenClass2, VertxGenClass2> gen2Function,
                               Function<Void, Void> voidFunction, Function<Throwable, Throwable> throwableFunction, Function<TestDataObject, TestDataObject> dataObjectFunction,
                               Function<TestEnum, TestEnum> enumFunction, Function<Object, Object> objectFunction, Function<T, T> genericFunction, Function<GenericInterface<T>, GenericInterface<T>> genericUserTypeFunction);

  void methodWithListFunctionParams(Function<List<Byte>, List<Byte>> listByteFunction, Function<List<Short>, List<Short>> listShortFunction, Function<List<Integer>, List<Integer>> listIntFunction,
                                   Function<List<Long>, List<Long>> listLongFunction, Function<List<Float>, List<Float>> listFloatFunction, Function<List<Double>, List<Double>> listDoubleFunction,
                                   Function<List<Boolean>, List<Boolean>> listBooleanFunction, Function<List<Character>, List<Character>> listCharFunction, Function<List<String>, List<String>> listStrFunction,
                                   Function<List<VertxGenClass1>, List<VertxGenClass1>> listVertxGenFunction, Function<List<JsonObject>, List<JsonObject>> listJsonObjectFunction, Function<List<JsonArray>, List<JsonArray>> listJsonArrayFunction,
                                   Function<List<TestDataObject>, List<TestDataObject>> listDataObjectFunction, Function<List<TestEnum>, List<TestEnum>> listEnumFunction);

  void methodWithSetFunctionParams(Function<Set<Byte>, Set<Byte>> setByteFunction, Function<Set<Short>, Set<Short>> setShortFunction, Function<Set<Integer>, Set<Integer>> setIntFunction,
                                  Function<Set<Long>, Set<Long>> setLongFunction, Function<Set<Float>, Set<Float>> setFloatFunction, Function<Set<Double>, Set<Double>> setDoubleFunction,
                                  Function<Set<Boolean>, Set<Boolean>> setBooleanFunction, Function<Set<Character>, Set<Character>> setCharFunction, Function<Set<String>, Set<String>> setStrFunction,
                                  Function<Set<VertxGenClass1>, Set<VertxGenClass1>> setVertxGenFunction, Function<Set<JsonObject>, Set<JsonObject>> setJsonObjectFunction, Function<Set<JsonArray>, Set<JsonArray>> setJsonArrayFunction,
                                  Function<Set<TestDataObject>, Set<TestDataObject>> setDataObjectFunction, Function<Set<TestEnum>, Set<TestEnum>> setEnumFunction);

  void methodWithMapFunctionParams(Function<Map<String,Byte>, Map<String,Byte>> mapByteFunction, Function<Map<String,Short>, Map<String,Short>> mapShortFunction, Function<Map<String,Integer>, Map<String,Integer>> mapIntFunction,
                                  Function<Map<String,Long>, Map<String,Long>> mapLongFunction, Function<Map<String,Float>, Map<String,Float>> mapFloatFunction, Function<Map<String,Double>, Map<String,Double>> mapDoubleFunction,
                                  Function<Map<String,Boolean>, Map<String,Boolean>> mapBooleanFunction, Function<Map<String,Character>, Map<String,Character>> mapCharFunction, Function<Map<String,String>, Map<String,String>> mapStrFunction,
                                  Function<Map<String,JsonObject>, Map<String,JsonObject>> mapJsonObjectFunction, Function<Map<String,JsonArray>, Map<String,JsonArray>> mapJsonArrayFunction);

}
