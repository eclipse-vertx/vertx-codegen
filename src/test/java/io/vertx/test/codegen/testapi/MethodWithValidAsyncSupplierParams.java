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
public interface MethodWithValidAsyncSupplierParams {

  <T> void methodWithSupplierParams(Handler<Future<Byte>> byteSupplier, Handler<Future<Short>> shortSupplier, Handler<Future<Integer>> intSupplier,
                                    Handler<Future<Long>> longSupplier, Handler<Future<Float>> floatSupplier, Handler<Future<Double>> doubleSupplier,
                                    Handler<Future<Boolean>> booleanSupplier, Handler<Future<Character>> charSupplier, Handler<Future<String>> strSupplier,
                                    Handler<Future<VertxGenClass1>> gen1Supplier, Handler<Future<VertxGenClass2>> gen2Supplier,
                                    Handler<Future<String>> voidSupplier, Handler<Future<TestDataObject>> dataObjectSupplier,
                                    Handler<Future<TestEnum>> enumSupplier, Handler<Future<T>> genericSupplier, Handler<Future<GenericInterface<T>>> genericUserTypeSupplier);

  void methodWithListSupplierParams(Handler<Future<List<Byte>>> listByteSupplier, Handler<Future<List<Short>>> listShortSupplier, Handler<Future<List<Integer>>> listIntSupplier,
                                    Handler<Future<List<Long>>> listLongSupplier, Handler<Future<List<Float>>> listFloatSupplier, Handler<Future<List<Double>>> listDoubleSupplier,
                                    Handler<Future<List<Boolean>>> listBooleanSupplier, Handler<Future<List<Character>>> listCharSupplier, Handler<Future<List<String>>> listStrSupplier,
                                    Handler<Future<List<VertxGenClass1>>> listVertxGenSupplier, Handler<Future<List<JsonObject>>> listJsonObjectSupplier, Handler<Future<List<JsonArray>>> listJsonArraySupplier,
                                    Handler<Future<List<TestDataObject>>> listDataObjectSupplier, Handler<Future<List<TestEnum>>> listEnumSupplier);

  void methodWithSetSupplierParams(Handler<Future<Set<Byte>>> setByteSupplier, Handler<Future<Set<Short>>> setShortSupplier, Handler<Future<Set<Integer>>> setIntSupplier,
                                   Handler<Future<Set<Long>>> setLongSupplier, Handler<Future<Set<Float>>> setFloatSupplier, Handler<Future<Set<Double>>> setDoubleSupplier,
                                   Handler<Future<Set<Boolean>>> setBooleanSupplier, Handler<Future<Set<Character>>> setCharSupplier, Handler<Future<Set<String>>> setStrSupplier,
                                   Handler<Future<Set<VertxGenClass1>>> setVertxGenSupplier, Handler<Future<Set<JsonObject>>> setJsonObjectSupplier, Handler<Future<Set<JsonArray>>> setJsonArraySupplier,
                                   Handler<Future<Set<TestDataObject>>> setDataObjectSupplier, Handler<Future<Set<TestEnum>>> setEnumSupplier);

  void methodWithMapSupplierParams(Handler<Future<Map<String, Byte>>> mapByteSupplier, Handler<Future<Map<String, Short>>> mapShortSupplier, Handler<Future<Map<String, Integer>>> mapIntSupplier,
                                   Handler<Future<Map<String, Long>>> mapLongSupplier, Handler<Future<Map<String, Float>>> mapFloatSupplier, Handler<Future<Map<String, Double>>> mapDoubleSupplier,
                                   Handler<Future<Map<String, Boolean>>> mapBooleanSupplier, Handler<Future<Map<String, Character>>> mapCharSupplier, Handler<Future<Map<String, String>>> mapStrSupplier,
                                   Handler<Future<Map<String, JsonObject>>> mapJsonObjectSupplier, Handler<Future<Map<String, JsonArray>>> mapJsonArraySupplier);

}
