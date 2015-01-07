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
  final TypeInfo type;

  public ParamInfo(String name, TypeInfo type) {
    this.name = name;
    this.type = type;
  }

  public String getName() {
    return name;
  }

  public String getName(Case _case) {
    return _case.format(Case.CAMEL.parse(name));
  }

  public TypeInfo getType() {
    return type;
  }

  public boolean isOptions() {
    return type instanceof TypeInfo.Class && ((TypeInfo.Class) type).getKind() == ClassKind.OPTIONS;
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

  @Override
  public String toString() {
    return type + " " + name;
  }
}
