package io.vertx.test.codegen.testapi;

import io.vertx.codegen.annotations.VertxGen;

import java.net.Socket;
import java.util.Set;

@VertxGen
public interface MethodWithOtherContainerType {

  Set<Socket> methodWhichTakesContainerParametrizedByOther(Set<Socket> sockets);
}
