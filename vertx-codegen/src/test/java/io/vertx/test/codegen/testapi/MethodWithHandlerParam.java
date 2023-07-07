package io.vertx.test.codegen.testapi;

import io.vertx.codegen.annotations.Fluent;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.Handler;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@VertxGen
public interface MethodWithHandlerParam {

  void foo_1(Handler<String> handler);
  void foo_2(String s, Handler<String> handler);
  void foo_3(Handler<String> handler1, Handler<String> handler2);

  @Fluent MethodWithHandlerParam foo_4(Handler<String> handler);
  @Fluent MethodWithHandlerParam foo_5(String s, Handler<String> handler);
  @Fluent MethodWithHandlerParam foo_6(Handler<String> handler1, Handler<String> handler2);

  // Unrecognized
  String foo_7(Handler<String> handler);
  void foo_8(Handler<String> handler, String s);

}
