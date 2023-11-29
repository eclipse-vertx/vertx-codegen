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

import java.util.Objects;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@DataObject
public class NestedStringDataObject {

  private String value;

  public NestedStringDataObject() {
  }

  public NestedStringDataObject(NestedStringDataObject copy) {
  }

  public NestedStringDataObject(String json) {
    value = json;
  }

  public String getValue() {
    return value;
  }

  public NestedStringDataObject setValue(String value) {
    this.value = value;
    return this;
  }

  public String toJson() {
    return value;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof NestedStringDataObject) {
      NestedStringDataObject that = (NestedStringDataObject) obj;
      return Objects.equals(value, that.value);
    }
    return false;
  }
}
