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
@JsonGen(base64Type = "base64url")
public class TestDataObjectBase64URL {

  private Buffer data;

  public TestDataObjectBase64URL() {
  }

  public TestDataObjectBase64URL(TestDataObjectBase64URL copy) {
  }

  public TestDataObjectBase64URL(JsonObject json) {
    TestDataObjectBase64URLConverter.fromJson(json, this);
  }

  public Buffer getData() {
    return data;
  }

  public TestDataObjectBase64URL setData(Buffer data) {
    this.data = data;
    return this;
  }

  public JsonObject toJson() {
    JsonObject json = new JsonObject();
    TestDataObjectBase64URLConverter.toJson(this, json);
    return json;
  }
}
