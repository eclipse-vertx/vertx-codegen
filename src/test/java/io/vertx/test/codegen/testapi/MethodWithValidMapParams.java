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

import java.util.Map;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
@VertxGen
public interface MethodWithValidMapParams {

  void methodWithMapParams(Map<String, String> mapString,
                           Map<String, Long> mapLong,
                           Map<String, JsonObject> mapJsonObject,
                           Map<String, JsonArray> mapJsonArray,
                           Map<String, VertxGenClass1> mapVertxGen,
                           Map<String, TestDataObject> mapDataObject,
                           Map<String, TestEnum> mapEnum,
                           Map<String, Object> mapObject);
}
