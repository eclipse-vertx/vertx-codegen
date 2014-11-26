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

import java.util.List;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
public class SimpleMethod {
  public final String name;
  public final List<SimpleParam> params;

  public SimpleMethod(String name, List<SimpleParam> params) {
    this.name = name;
    this.params = params;
  }

  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append(name).append("(");
    int count = 0;
    for (SimpleParam param: params) {
      builder.append(param.typeName).append(" ").append(param.name);
      if (++count != params.size()) {
        builder.append(", ");
      }
    }
    builder.append(")");
    return builder.toString();
  }
}
