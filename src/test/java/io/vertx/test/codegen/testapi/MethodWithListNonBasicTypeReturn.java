package io.vertx.test.codegen.testapi;

import io.vertx.core.gen.VertxGen;

import java.net.Socket;
import java.util.List;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
@VertxGen
public interface MethodWithListNonBasicTypeReturn {

  List<Socket> foo(String param);
}
