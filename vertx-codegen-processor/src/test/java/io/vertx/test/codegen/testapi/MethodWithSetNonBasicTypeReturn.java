package io.vertx.test.codegen.testapi;

import io.vertx.codegen.annotations.VertxGen;

import java.net.Socket;
import java.util.Set;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
@VertxGen
public interface MethodWithSetNonBasicTypeReturn {

  Set<Socket> foo(String param);
}
