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
import io.vertx.core.json.JsonObject;

@DataObject(generateConverter = true)
public class DataObjectPropertyFormattedDataObject {

  private String id;
  private String ref;

  public DataObjectPropertyFormattedDataObject() {
  }

  public DataObjectPropertyFormattedDataObject(JsonObject json) {
  }

  public String getId() {
    return id;
  }

  public DataObjectPropertyFormattedDataObject setId(String id) {
    this.id = id;
    return this;
  }

  @DataObject.Property(name = ":ref")
  public String getRef() {
    return ref;
  }

  public DataObjectPropertyFormattedDataObject setRef(String ref) {
    this.ref = ref;
    return this;
  }
}
