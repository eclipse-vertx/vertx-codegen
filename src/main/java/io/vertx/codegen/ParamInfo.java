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
  //final boolean handlerParam;
  //final boolean asyncResultHandlerParam;
  //final String genericHandlerType;

  public ParamInfo(String name, String type) { //boolean handlerParam, boolean asyncResultHandlerParam, String genericHandlerType) {
    this.name = name;
    this.type = type;
    //this.asyncResultHandlerParam = asyncResultHandlerParam;
    //this.handlerParam = handlerParam;
    //this.genericHandlerType = genericHandlerType;
  }

  public String getName() {
    return name;
  }

  public String getType() {
    return type;
  }

//  public boolean isHandlerParam() {
//    return handlerParam;
//  }
//
//  public boolean isAsyncResultHandlerParam() {
//    return asyncResultHandlerParam;
//  }
//
//  public String getGenericHandlerType() {
//    return genericHandlerType;
//  }

}
