package io.vertx.codegen;

/*
 * Copyright 2014 Red Hat, Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Apache License v2.0 which accompanies this distribution.
 *
 * The Eclipse Public License is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * The Apache License v2.0 is available at
 * http://www.opensource.org/licenses/apache2.0.php
 *
 * You may elect to redistribute this code under either of these licenses.
 */

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
public class ParamInfo {
  final String name;
  final String type;
  boolean options;

  public ParamInfo(String name, String type, boolean options) {
    this.name = name;
    this.type = type;
    this.options = options;
  }

  public String getName() {
    return name;
  }

  public String getType() {
    return type;
  }


  @Override
  public boolean equals(Object other) {
    if (!(other instanceof  ParamInfo)) {
      return false;
    }
    ParamInfo pother = (ParamInfo)other;
    return this.name.equals(pother.name) && this.type.equals(pother.type);
  }

  @Override
  public int hashCode() {
    int result = name.hashCode();
    result = 31 * result + type.hashCode();
    return result;
  }

  public boolean isOptions() {
    return options;
  }

  public void setOptions(boolean options) {
    this.options = options;
  }
}
