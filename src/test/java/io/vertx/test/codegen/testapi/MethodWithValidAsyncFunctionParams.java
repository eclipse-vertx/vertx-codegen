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

import io.vertx.codegen.annotations.VertxGen;
import io.vertx.codegen.testmodel.TestDataObject;
import io.vertx.codegen.testmodel.TestEnum;
import io.vertx.core.Future;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
@VertxGen
public interface MethodWithValidAsyncFunctionParams {

  <T> void methodWithFunctionParams(Function<Byte, Future<Byte>> byteFunction, Function<Short, Future<Short>> shortFunction, Function<Integer, Future<Integer>> intFunction,
                                    Function<Long, Future<Long>> longFunction, Function<Float, Future<Float>> floatFunction, Function<Double, Future<Double>> doubleFunction,
                                    Function<Boolean, Future<Boolean>> booleanFunction, Function<Character, Future<Character>> charFunction, Function<String, Future<String>> strFunction,
                                    Function<VertxGenClass1, Future<VertxGenClass1>> gen1Function, Function<VertxGenClass2, Future<VertxGenClass2>> gen2Function,
                                    Function<Void, Future<String>> voidFunction, Function<Throwable, Future<Throwable>> throwableFunction, Function<TestDataObject, Future<TestDataObject>> dataObjectFunction,
                                    Function<TestEnum, Future<TestEnum>> enumFunction, Function<Object, Future<Object>> objectFunction, Function<T, Future<T>> genericFunction, Function<GenericInterface<T>, Future<GenericInterface<T>>> genericUserTypeFunction);

  void methodWithListFunctionParams(Function<List<Byte>, Future<List<Byte>>> listByteFunction, Function<List<Short>, Future<List<Short>>> listShortFunction, Function<List<Integer>, Future<List<Integer>>> listIntFunction,
                                    Function<List<Long>, Future<List<Long>>> listLongFunction, Function<List<Float>, Future<List<Float>>> listFloatFunction, Function<List<Double>, Future<List<Double>>> listDoubleFunction,
                                    Function<List<Boolean>, Future<List<Boolean>>> listBooleanFunction, Function<List<Character>, Future<List<Character>>> listCharFunction, Function<List<String>, Future<List<String>>> listStrFunction,
                                    Function<List<VertxGenClass1>, Future<List<VertxGenClass1>>> listVertxGenFunction, Function<List<JsonObject>, Future<List<JsonObject>>> listJsonObjectFunction, Function<List<JsonArray>, Future<List<JsonArray>>> listJsonArrayFunction,
                                    Function<List<TestDataObject>, Future<List<TestDataObject>>> listDataObjectFunction, Function<List<TestEnum>, Future<List<TestEnum>>> listEnumFunction);

  void methodWithSetFunctionParams(Function<Set<Byte>, Future<Set<Byte>>> setByteFunction, Function<Set<Short>, Future<Set<Short>>> setShortFunction, Function<Set<Integer>, Future<Set<Integer>>> setIntFunction,
                                   Function<Set<Long>, Future<Set<Long>>> setLongFunction, Function<Set<Float>, Future<Set<Float>>> setFloatFunction, Function<Set<Double>, Future<Set<Double>>> setDoubleFunction,
                                   Function<Set<Boolean>, Future<Set<Boolean>>> setBooleanFunction, Function<Set<Character>, Future<Set<Character>>> setCharFunction, Function<Set<String>, Future<Set<String>>> setStrFunction,
                                   Function<Set<VertxGenClass1>, Future<Set<VertxGenClass1>>> setVertxGenFunction, Function<Set<JsonObject>, Future<Set<JsonObject>>> setJsonObjectFunction, Function<Set<JsonArray>, Future<Set<JsonArray>>> setJsonArrayFunction,
                                   Function<Set<TestDataObject>, Future<Set<TestDataObject>>> setDataObjectFunction, Function<Set<TestEnum>, Future<Set<TestEnum>>> setEnumFunction);

  void methodWithMapFunctionParams(Function<Map<String, Future<Byte>>, Map<String, Byte>> mapByteFunction, Function<Map<String, Future<Short>>, Map<String, Short>> mapShortFunction, Function<Map<String, Future<Integer>>, Map<String, Integer>> mapIntFunction,
                                   Function<Map<String, Future<Long>>, Map<String, Long>> mapLongFunction, Function<Map<String, Future<Float>>, Map<String, Float>> mapFloatFunction, Function<Map<String, Future<Double>>, Map<String, Double>> mapDoubleFunction,
                                   Function<Map<String, Future<Boolean>>, Map<String, Boolean>> mapBooleanFunction, Function<Map<String, Future<Character>>, Map<String, Character>> mapCharFunction, Function<Map<String, Future<String>>, Map<String, String>> mapStrFunction,
                                   Function<Map<String, Future<JsonObject>>, Map<String, JsonObject>> mapJsonObjectFunction, Function<Map<String, Future<JsonArray>>, Map<String, JsonArray>> mapJsonArrayFunction);

}
