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

package io.vertx.codegen.overloadcheck;

import io.vertx.codegen.type.ClassKind;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
public class SimpleParam {
  public final String name;
  public final ClassKind classKind;
  public final boolean nullable;
  public final String typeName;

  public SimpleParam(String name, ClassKind classKind, String typeName) {
    this.name = name;
    this.classKind = classKind;
    this.nullable = false;
    this.typeName = typeName;
  }

  public SimpleParam(String name, ClassKind classKind, boolean nullable, String typeName) {
    this.name = name;
    this.classKind = classKind;
    this.nullable = nullable;
    this.typeName = typeName;
  }
}
