package io.vertx.codegen;

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
public class MethodParam {
  final String name;
  final String type;
  final boolean handlerParam;
  final boolean asyncResultHandlerParam;
  final String genericHandlerType;

  public MethodParam(String name, String type, boolean handlerParam, boolean asyncResultHandlerParam, String genericHandlerType) {
    this.name = name;
    this.type = type;
    this.asyncResultHandlerParam = asyncResultHandlerParam;
    this.handlerParam = handlerParam;
    this.genericHandlerType = genericHandlerType;
  }

  public String getName() {
    return name;
  }

  public String getType() {
    return type;
  }

  public boolean isHandlerParam() {
    return handlerParam;
  }

  public boolean isAsyncResultHandlerParam() {
    return asyncResultHandlerParam;
  }

  public String getGenericHandlerType() {
    return genericHandlerType;
  }

}
