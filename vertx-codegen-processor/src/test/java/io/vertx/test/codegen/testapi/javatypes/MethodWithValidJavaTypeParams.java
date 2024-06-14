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
  void methodWithParams(Socket socket,
                        List<Socket> listSocket,
                        Set<Socket> setSocket,
                        Map<String, Socket> mapSocket);

  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  void methodWithHandlerParams(Handler<Socket> socketHandler,
                               Handler<List<Socket>> listSocketHandler,
                               Handler<Set<Socket>> setSocketHandler,
                               Handler<Map<String, Socket>> mapSocketHandler);

  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  void methodWithFunctionParams(Function<Socket, Socket> socketFunction,
                                Function<List<Socket>, List<Socket>> listSocketFunction,
                                Function<Set<Socket>, Set<Socket>> setSocketFunction,
                                Function<Map<String, Socket>, Map<String, Socket>> mapSocketFunction);

  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  void methodWithSupplierParams(Supplier<Socket> socketSupplier,
                                Supplier<List<Socket>> listSocketSupplier,
                                Supplier<Set<Socket>> setSocketSupplier,
                                Supplier<Map<String, Socket>> mapSocketSupplier);

  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  void methodWithArrayParams(byte[] byteArray,
                             boolean[] booleanArray);

  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  void methodWithParameterizedParams(Iterable<String> iterableString);

  @GenIgnore(GenIgnore.PERMITTED_TYPE)
  void methodWithWildcardType(List<?> listOfUnknown);
}
