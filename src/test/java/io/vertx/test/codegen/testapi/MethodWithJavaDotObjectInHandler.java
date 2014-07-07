package io.vertx.test.codegen.testapi;

import io.vertx.core.Handler;
import io.vertx.core.gen.VertxGen;

import java.net.Socket;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
@VertxGen
public interface MethodWithJavaDotObjectInHandler {

  void foo(Handler<Socket> handler);
}
