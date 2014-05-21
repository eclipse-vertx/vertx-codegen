package io.vertx.foo;

import org.vertx.java.core.AsyncResult;
import org.vertx.java.core.Handler;
import org.vertx.java.core.gen.Fluent;
import org.vertx.java.core.gen.IndexGetter;
import org.vertx.java.core.gen.IndexSetter;
import org.vertx.java.core.gen.VertxGen;
import org.vertx.java.core.json.JsonObject;
import org.vertx.java.core.net.NetServer;

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
