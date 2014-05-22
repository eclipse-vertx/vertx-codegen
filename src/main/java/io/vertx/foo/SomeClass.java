package io.vertx.foo;

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
import org.vertx.java.core.gen.Fluent;
import org.vertx.java.core.gen.IndexGetter;
import org.vertx.java.core.gen.IndexSetter;
import org.vertx.java.core.gen.VertxGen;
import org.vertx.java.core.json.JsonObject;
import org.vertx.java.core.net.NetServer;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
@VertxGen
public interface SomeClass {

  /**
  Here is a comment
   */
  JsonObject someMethod(int age, double length, JsonObject obj);

  JsonObject methodWithHandler(Handler<JsonObject> handler);

  JsonObject methodWithAsyncResultHandler(Handler<AsyncResult<NetServer>> handler);

  /**
  Here is another comment
   */
  @Fluent
  SomeClass setFoo(int foo);

  @IndexGetter
  int getAt(int pos);

  @IndexSetter
  @Fluent
  SomeClass setAt(int pos, int val);
}
