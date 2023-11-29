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
import io.vertx.codegen.format.SnakeCase;
import io.vertx.codegen.json.annotations.JsonGen;
import io.vertx.core.json.JsonObject;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@DataObject
@JsonGen(jsonPropertyNameFormatter = SnakeCase.class)
public class SnakeFormattedDataObject {

  private String foo;
  private String fooBar;
  private String fooBarJuu;

  public SnakeFormattedDataObject() {
  }

  public SnakeFormattedDataObject(JsonObject json) {
  }

  public String getFoo() {
    return foo;
  }

  public SnakeFormattedDataObject setFoo(String foo) {
    this.foo = foo;
    return this;
  }

  public String getFooBar() {
    return fooBar;
  }

  public SnakeFormattedDataObject setFooBar(String fooBar) {
    this.fooBar = fooBar;
    return this;
  }

  public String getFooBarJuu() {
    return fooBarJuu;
  }

  public SnakeFormattedDataObject setFooBarJuu(String fooBarJuu) {
    this.fooBarJuu = fooBarJuu;
    return this;
  }
}
