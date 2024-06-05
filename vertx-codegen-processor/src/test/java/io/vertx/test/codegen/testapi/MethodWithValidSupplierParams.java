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
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
@VertxGen
public interface MethodWithValidSupplierParams {

  <T> void methodWithSupplierParams(Supplier<Byte> byteSupplier, Supplier<Short> shortSupplier, Supplier<Integer> intSupplier,
                                    Supplier<Long> longSupplier, Supplier<Float> floatSupplier, Supplier<Double> doubleSupplier,
                                    Supplier<Boolean> booleanSupplier, Supplier<Character> charSupplier, Supplier<String> strSupplier,
                                    Supplier<VertxGenClass1> gen1Supplier, Supplier<VertxGenClass2> gen2Supplier,
                                    Supplier<Void> voidSupplier, Supplier<Throwable> throwableSupplier, Supplier<TestDataObject> dataObjectSupplier,
                                    Supplier<TestEnum> enumSupplier, Supplier<Object> objectSupplier, Supplier<T> genericSupplier, Supplier<GenericInterface<T>> genericUserTypeSupplier);

  void methodWithListSupplierParams(Supplier<List<Byte>> listByteSupplier, Supplier<List<Short>> listShortSupplier, Supplier<List<Integer>> listIntSupplier,
                                   Supplier<List<Long>> listLongSupplier, Supplier<List<Float>> listFloatSupplier, Supplier<List<Double>> listDoubleSupplier,
                                   Supplier<List<Boolean>> listBooleanSupplier, Supplier<List<Character>> listCharSupplier, Supplier<List<String>> listStrSupplier,
                                   Supplier<List<VertxGenClass1>> listVertxGenSupplier, Supplier<List<JsonObject>> listJsonObjectSupplier, Supplier<List<JsonArray>> listJsonArraySupplier,
                                   Supplier<List<TestDataObject>> listDataObjectSupplier, Supplier<List<TestEnum>> listEnumSupplier);

  void methodWithSetSupplierParams(Supplier<Set<Byte>> setByteSupplier, Supplier<Set<Short>> setShortSupplier, Supplier<Set<Integer>> setIntSupplier,
                                  Supplier<Set<Long>> setLongSupplier, Supplier<Set<Float>> setFloatSupplier, Supplier<Set<Double>> setDoubleSupplier,
                                  Supplier<Set<Boolean>> setBooleanSupplier, Supplier<Set<Character>> setCharSupplier, Supplier<Set<String>> setStrSupplier,
                                  Supplier<Set<VertxGenClass1>> setVertxGenSupplier, Supplier<Set<JsonObject>> setJsonObjectSupplier, Supplier<Set<JsonArray>> setJsonArraySupplier,
                                  Supplier<Set<TestDataObject>> setDataObjectSupplier, Supplier<Set<TestEnum>> setEnumSupplier);

  void methodWithMapSupplierParams(Supplier<Map<String,Byte>> mapByteSupplier, Supplier<Map<String,Short>> mapShortSupplier, Supplier<Map<String,Integer>> mapIntSupplier,
                                  Supplier<Map<String,Long>> mapLongSupplier, Supplier<Map<String,Float>> mapFloatSupplier, Supplier<Map<String,Double>> mapDoubleSupplier,
                                  Supplier<Map<String,Boolean>> mapBooleanSupplier, Supplier<Map<String,Character>> mapCharSupplier, Supplier<Map<String,String>> mapStrSupplier,
                                  Supplier<Map<String,JsonObject>> mapJsonObjectSupplier, Supplier<Map<String,JsonArray>> mapJsonArraySupplier);

}
