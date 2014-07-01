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
  List<ParamInfo> params;
  final String comment;
  boolean squashed;

  public MethodInfo(String name, String returnType, boolean fluent, boolean indexGetter, boolean indexSetter,
                    List<ParamInfo> params, String comment) {
    this.name = name;
    this.returnType = returnType;
    this.fluent = fluent;
    this.indexGetter = indexGetter;
    this.indexSetter = indexSetter;
    this.comment = comment;
    addParams(params);
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

  public void addParams(List<ParamInfo> params) {
    if (params == null) {
      throw new NullPointerException("params");
    }
    boolean isAppendBuffer = name.equals("appendBuffer");

    //if (isAppendBuffer)

    //System.out.println("Method is " + this.getName());
    if (this.params == null) {
      this.params = new ArrayList<>();
    } else {
      squashed = true;
    }
    int mandatoryNum = Math.min(this.params.size(), params.size());
    if (isAppendBuffer) {
      System.out.println("Adding " + params.size() + " to existing " + this.params.size());
      System.out.println("madatory num is " + mandatoryNum);
    }

    for (ParamInfo param: params) {
      if (!this.params.contains(param)) {
        this.params.add(param);
      }
    }
    int pos = 0;
    for (ParamInfo param: this.params) {
      param.setMandatory(pos < mandatoryNum);
      pos++;
    }
    if (isAppendBuffer) {
      System.out.println("Now has " + this.params.size() + " params");
    }
  }
}
