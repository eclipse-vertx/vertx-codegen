package io.vertx.codegen;

import java.util.List;

/*
 * Copyright 2013 Red Hat, Inc.
 *
 * Red Hat licenses this file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 *
 */
public class MethodInfo {

  final String name;
  final String returnType;
  final boolean fluent;
  final boolean indexGetter;
  final boolean indexSetter;
  final List<MethodParam> params;

  public MethodInfo(String name, String returnType, boolean fluent, boolean indexGetter, boolean indexSetter, List<MethodParam> params) {
    this.name = name;
    this.returnType = returnType;
    this.fluent = fluent;
    this.indexGetter = indexGetter;
    this.indexSetter = indexSetter;
    this.params = params;
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

  public List<MethodParam> getParams() {
    return params;
  }
}
