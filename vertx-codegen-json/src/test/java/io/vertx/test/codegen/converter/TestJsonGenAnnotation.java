/*
 * Copyright (c) 2011-2017 Contributors to the Eclipse Foundation
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0, or the Apache License, Version 2.0
 * which is available at https://www.apache.org/licenses/LICENSE-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0 OR Apache-2.0
 */

package io.vertx.test.codegen.converter;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.codegen.json.annotations.JsonGen;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;

@DataObject
@JsonGen(publicConverter = false)
public class TestJsonGenAnnotation {

  private Buffer data;

  public TestJsonGenAnnotation() {
  }

  public TestJsonGenAnnotation(TestJsonGenAnnotation copy) {
  }

  public TestJsonGenAnnotation(JsonObject json) {
  }

  public Buffer getData() {
    return data;
  }

  public TestJsonGenAnnotation setData(Buffer data) {
    this.data = data;
    return this;
  }

  public JsonObject toJson() {
    JsonObject json = new JsonObject();
    return json;
  }
}
