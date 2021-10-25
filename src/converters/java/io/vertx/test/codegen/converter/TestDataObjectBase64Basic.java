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
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;

@DataObject(generateConverter = true, base64Type = "basic")
public class TestDataObjectBase64Basic {

  private Buffer data;

  public TestDataObjectBase64Basic() {
  }

  public TestDataObjectBase64Basic(TestDataObjectBase64Basic copy) {
  }

  public TestDataObjectBase64Basic(JsonObject json) {
    TestDataObjectBase64BasicConverter.fromJson(json, this);
  }

  public Buffer getData() {
    return data;
  }

  public TestDataObjectBase64Basic setData(Buffer data) {
    this.data = data;
    return this;
  }

  public JsonObject toJson() {
    JsonObject json = new JsonObject();
    TestDataObjectBase64BasicConverter.toJson(this, json);
    return json;
  }
}
