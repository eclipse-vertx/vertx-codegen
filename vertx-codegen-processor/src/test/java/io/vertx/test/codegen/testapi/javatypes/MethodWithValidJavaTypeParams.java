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

package io.vertx.test.codegen.testapi.javatypes;

import io.vertx.codegen.annotations.GenIgnore;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
@VertxGen()
@GenIgnore(GenIgnore.PERMITTED_TYPE)
public interface MethodWithValidJavaTypeParams {

  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  void methodWithParams0(Socket socket,
                        List<Socket> listSocket,
                        Set<Socket> setSocket,
                        Map<String, Socket> mapSocket);

  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  void methodWithHandlerParams0(Handler<Socket> socketHandler,
                               Handler<List<Socket>> listSocketHandler,
                               Handler<Set<Socket>> setSocketHandler,
                               Handler<Map<String, Socket>> mapSocketHandler);

  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  void methodWithFunctionParams0(Function<Socket, Socket> socketFunction,
                                Function<List<Socket>, List<Socket>> listSocketFunction,
                                Function<Set<Socket>, Set<Socket>> setSocketFunction,
                                Function<Map<String, Socket>, Map<String, Socket>> mapSocketFunction);

  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  void methodWithSupplierParams0(Supplier<Socket> socketSupplier,
                                Supplier<List<Socket>> listSocketSupplier,
                                Supplier<Set<Socket>> setSocketSupplier,
                                Supplier<Map<String, Socket>> mapSocketSupplier);

  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  void methodWithBilto0(List<? extends Socket> listSocket,
                        Set<? extends Socket> setSocket,
                        Map<? extends String, ? extends Socket> mapSocket);

  void methodWithParams1(PermittedType socket,
                        List<PermittedType> listSocket,
                        Set<PermittedType> setSocket,
                        Map<String, PermittedType> mapSocket);

  void methodWithHandlerParams1(Handler<PermittedType> socketHandler,
                               Handler<List<PermittedType>> listSocketHandler,
                               Handler<Set<PermittedType>> setSocketHandler,
                               Handler<Map<String, PermittedType>> mapSocketHandler);

  void methodWithFunctionParams1(Function<PermittedType, PermittedType> socketFunction,
                                Function<List<PermittedType>, List<PermittedType>> listSocketFunction,
                                Function<Set<PermittedType>, Set<PermittedType>> setSocketFunction,
                                Function<Map<String, PermittedType>, Map<String, PermittedType>> mapSocketFunction);

  void methodWithSupplierParams1(Supplier<PermittedType> socketSupplier,
                                Supplier<List<PermittedType>> listSocketSupplier,
                                Supplier<Set<PermittedType>> setSocketSupplier,
                                Supplier<Map<String, PermittedType>> mapSocketSupplier);

  void methodWithParams2(PermittedType socket,
                         List<PermittedType> listSocket,
                         Set<PermittedType> setSocket,
                         Map<String, PermittedType> mapSocket);

  void methodWithHandlerParams2(Handler<ParameterizedPermittedType<String>> socketHandler,
                                Handler<List<ParameterizedPermittedType<String>>> listSocketHandler,
                                Handler<Set<ParameterizedPermittedType<String>>> setSocketHandler,
                                Handler<Map<String, ParameterizedPermittedType<String>>> mapSocketHandler);

  void methodWithFunctionParams2(Function<ParameterizedPermittedType<String>, ParameterizedPermittedType<String>> socketFunction,
                                 Function<List<ParameterizedPermittedType<String>>, List<ParameterizedPermittedType<String>>> listSocketFunction,
                                 Function<Set<ParameterizedPermittedType<String>>, Set<ParameterizedPermittedType<String>>> setSocketFunction,
                                 Function<Map<String, ParameterizedPermittedType<String>>, Map<String, ParameterizedPermittedType<String>>> mapSocketFunction);

  void methodWithSupplierParams2(Supplier<ParameterizedPermittedType<String>> socketSupplier,
                                 Supplier<List<ParameterizedPermittedType<String>>> listSocketSupplier,
                                 Supplier<Set<ParameterizedPermittedType<String>>> setSocketSupplier,
                                 Supplier<Map<String, ParameterizedPermittedType<String>>> mapSocketSupplier);

  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  void methodWithArrayParams(byte[] byteArray,
                             boolean[] booleanArray);

  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  void methodWithParameterizedParams(Iterable<String> iterableString);

}
