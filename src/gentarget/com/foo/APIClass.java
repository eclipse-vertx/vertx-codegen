package com.foo;

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

import org.vertx.java.core.AsyncResult;
import org.vertx.java.core.Handler;
import org.vertx.java.core.buffer.Buffer;
import org.vertx.java.core.gen.Fluent;
import org.vertx.java.core.gen.IndexGetter;
import org.vertx.java.core.gen.IndexSetter;
import org.vertx.java.core.gen.VertxGen;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
@VertxGen
public interface APIClass {

//  void byteMethod(byte myByte);
//  void shortMethod(short myShort);
//  void intMethod(int myInt);
//  void longMethod(long myLong);
//  void floatMethod(float myFloat);
//  void doubleMethod(double myDouble);
//  void booleanMethod(boolean myBoolean);
//  void charMethod(char myChar);
//  void stringMethod(String myString);
//  void byteArrayMethod(byte[] myBytes);
//
//  void byteObjectMethod(Byte myByte);
//  void shortObjectMethod(Short myShort);
//  void intObjectMethod(Integer myInt);
//  void longObjectMethod(Long myLong);
//  void floatObjectMethod(Float myFloat);
//  void doubleObjectMethod(Double myDouble);
//  void booleanObjectMethod(Boolean myBoolean);
//  void charObjectMethod(Character myChar);
//
//  void altogetherMethod(byte myByte, short myShort, int myInt, long myLong, float myFloat, double myDouble,
//                        boolean myBoolean, char myChar, String myString, byte[] myBytes,
//                        Byte myOByte, Short myOShort, Integer myOInt, Float myOFloat, Double myODouble,
//                        Boolean myOBoolean, Character myOChar);
//
//  byte byteReturnMethod();
//  short shortReturnMethod();
//  int intReturnMethod();
//  long longReturnMethod();
//  float floatReturnMethod();
//  double doubleReturnMethod();
//  boolean booleanReturnMethod();
//  char charReturnMethod();
//  String stringReturnMethod();
//  byte[] byteArrayReturnMethod();
//
//  @Fluent
//  APIClass fluentMethod(String foo);
//
//  @IndexGetter
//  int getAt(int pos);
//
//  @IndexSetter
//  void setAt(int pos, String foo);
//
//  @IndexSetter
//  @Fluent
//  APIClass fluentSetter(int pos, String foo);
//
//  void handlerVoidMethod(String foo, Handler<Void> handler);
//
//  @Fluent
//  APIClass fluentHandlerVoidMethod(String foo, Handler<Void> handler);
//
//  void handlerOtherAPIClass(String foo, Handler<OtherAPIClass> handler);
//
//  void handlerString(String foo, Handler<String> handler);

  String handlerint(String foo, APIClass other, Handler<AsyncResult<String>> handler);







}

