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

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
public class MethodInfo {

  final String name;
  final String returnType;
  final boolean fluent;
  final boolean indexGetter;
  final boolean indexSetter;
  final boolean cacheReturn;
  List<ParamInfo> params;
  final String comment;
  final boolean staticMethod;
  boolean squashed;
  List<String> typeParams;

  public MethodInfo(String name, String returnType, boolean fluent, boolean indexGetter, boolean indexSetter,
                    boolean cacheReturn, List<ParamInfo> params, String comment, boolean staticMethod, List<String> typeParams) {
    this.name = name;
    this.returnType = returnType;
    this.fluent = fluent;
    this.indexGetter = indexGetter;
    this.indexSetter = indexSetter;
    this.cacheReturn = cacheReturn;
    this.comment = comment;
    this.staticMethod = staticMethod;
    addParams(params);
    this.typeParams = typeParams;
  }

  public String getName() {
    return name;
  }

  public String getReturnType() {
    return returnType;
  }

  public boolean isFluent() {
    return fluent;
  }

  public boolean isIndexGetter() {
    return indexGetter;
  }

  public boolean isIndexSetter() {
    return indexSetter;
  }

  public boolean isCacheReturn() {
    return cacheReturn;
  }

  public List<ParamInfo> getParams() {
    return params;
  }

  public String getComment() {
    return comment;
  }

  public boolean isSquashed() {
    return squashed;
  }

  public void setSquashed(boolean squashed) {
    this.squashed = squashed;
  }

  public boolean isStaticMethod() {
    return staticMethod;
  }

  public List<String> getTypeParams() {
    return typeParams;
  }

  public void addParams(List<ParamInfo> params) {
    if (params == null) {
      throw new NullPointerException("params");
    }
    if (this.params == null) {
      this.params = new ArrayList<>();
    } else {
      squashed = true;
    }
    for (ParamInfo param: params) {
      if (!this.params.contains(param)) {
        this.params.add(param);
      }
    }
  }


}
