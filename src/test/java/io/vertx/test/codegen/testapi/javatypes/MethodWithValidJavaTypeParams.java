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

import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
@VertxGen()
@SuppressWarnings("codegen-allow-any-java-type")
public interface MethodWithValidJavaTypeParams {

  @SuppressWarnings("codegen-allow-any-java-type")
  void methodWithParams(Socket socket,
                        List<Socket> listSocket,
                        Set<Socket> setSocket,
                        Map<String, Socket> mapSocket);

  @SuppressWarnings("codegen-allow-any-java-type")
  void methodWithHandlerParams(Handler<Socket> socketHandler,
                               Handler<List<Socket>> listSocketHandler,
                               Handler<Set<Socket>> setSocketHandler,
                               Handler<Map<String, Socket>> mapSocketHandler);

  @SuppressWarnings("codegen-allow-any-java-type")
  void methodWithHandlerAsyncResultParams(Handler<AsyncResult<Socket>> socketHandler,
                                          Handler<AsyncResult<List<Socket>>> listSocketHandler,
                                          Handler<AsyncResult<Set<Socket>>> setSocketHandler,
                                          Handler<AsyncResult<Map<String, Socket>>> mapSocketHandler);

  @SuppressWarnings("codegen-allow-any-java-type")
  void methodWithFunctionParams(Function<Socket, Socket> socketFunction,
                                Function<List<Socket>, List<Socket>> listSocketFunction,
                                Function<Set<Socket>, Set<Socket>> setSocketFunction,
                                Function<Map<String, Socket>, Map<String, Socket>> mapSocketFunction);

  @SuppressWarnings("codegen-allow-any-java-type")
  void methodWithArrayParams(byte[] byteArray,
                             boolean[] booleanArray);

  @SuppressWarnings("codegen-allow-any-java-type")
  void methodWithParameterizedParams(Iterable<String> iterableString);

}
