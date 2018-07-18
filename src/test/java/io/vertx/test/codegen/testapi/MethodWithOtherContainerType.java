package io.vertx.test.codegen.testapi;

import io.vertx.codegen.annotations.VertxGen;

import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.Set;

@VertxGen
public interface MethodWithOtherContainerType {

  Set<Socket> methodWhichReturnSetWithOther(Set<Socket> socketSet, List<Socket> socketList, Map<String, Socket> socketMap);

  List<Socket> methodWhichReturnListWithOther(Set<Socket> socketSet, List<Socket> socketList, Map<String, Socket> socketMap);

  Map<String, Socket> methodWhichReturnMapWithOther(Set<Socket> socketSet, List<Socket> socketList, Map<String, Socket> socketMap);
}
