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
import io.vertx.codegen.format.ScreamingSnakeCase;
import io.vertx.core.json.JsonObject;

@DataObject(generateConverter = true, jsonPropertyNameFormatter = ScreamingSnakeCase.class)
public class ScreamingSnakeFormattedDataObject {

  private String foo;
  private String fooBar;
  private String fooBarJuu;

  public ScreamingSnakeFormattedDataObject() {}

  public ScreamingSnakeFormattedDataObject(JsonObject json) {}

  public String getFoo() {
    return foo;
  }

  public ScreamingSnakeFormattedDataObject setFoo(String foo) {
    this.foo = foo;
    return this;
  }

  public String getFooBar() {
    return fooBar;
  }

  public ScreamingSnakeFormattedDataObject setFooBar(String fooBar) {
    this.fooBar = fooBar;
    return this;
  }

  public String getFooBarJuu() {
    return fooBarJuu;
  }

  public ScreamingSnakeFormattedDataObject setFooBarJuu(String fooBarJuu) {
    this.fooBarJuu = fooBarJuu;
    return this;
  }
}
