package io.vertx.codegen.testmodel;

import io.vertx.core.Future;
import io.vertx.core.Handler;

import java.net.Socket;
import java.util.*;

import static org.junit.Assert.*;

public class AnyJavaTypeTCKImpl implements AnyJavaTypeTCK {

  @Override
  public void methodWithJavaTypeParam(Socket socket) {
    assertNotNull(socket);
  }

  @Override
  public void methodWithListOfJavaTypeParam(List<Socket> socketList) {
    for (Socket socket : socketList) {
      assertNotNull(socket);
    }
  }

  @Override
  public void methodWithSetOfJavaTypeParam(Set<Socket> socketSet) {
    for (Socket socket : socketSet) {
      assertNotNull(socket);
    }
  }

  @Override
  public void methodWithMapOfJavaTypeParam(Map<String, Socket> socketMap) {
    for (Map.Entry<String, Socket> stringSocketEntry : socketMap.entrySet()) {
      assertNotNull(stringSocketEntry.getValue());
    }
  }

  @Override
  public void methodWithGenericWithWildcard(Iterable<?> iterable) {
    iterable.forEach(o -> {
      assertNotNull(o);
    });
  }

  @Override
  public Socket methodWithJavaTypeReturn() {
    return new Socket();
  }

  @Override
  public List<Socket> methodWithListOfJavaTypeReturn() {
    Socket socket = new Socket();
    ArrayList<Socket> sockets = new ArrayList<>();
    sockets.add(socket);
    return sockets;
  }

  @Override
  public Set<Socket> methodWithSetOfJavaTypeReturn() {
    Socket socket = new Socket();
    Set<Socket> sockets = new HashSet<>();
    sockets.add(socket);
    return sockets;
  }

  @Override
  public Map<String, Socket> methodWithMapOfJavaTypeReturn() {
    Socket socket = new Socket();
    Map<String, Socket> sockets = new HashMap<>();
    sockets.put("1", socket);
    return sockets;
  }

  @Override
  public Iterable<?> methodWithGenericWithWildcardReturn() {
    return Arrays.asList("A", "B", "C");
  }

  @Override
  public void methodWithHandlerJavaTypeParam(Handler<Socket> socketHandler) {
    assertNotNull(socketHandler);
    socketHandler.handle(methodWithJavaTypeReturn());
  }

  @Override
  public void methodWithHandlerListOfJavaTypeParam(Handler<List<Socket>> socketListHandler) {
    assertNotNull(socketListHandler);
    socketListHandler.handle(methodWithListOfJavaTypeReturn());
  }

  @Override
  public void methodWithHandlerSetOfJavaTypeParam(Handler<Set<Socket>> socketSetHandler) {
    assertNotNull(socketSetHandler);
    socketSetHandler.handle(methodWithSetOfJavaTypeReturn());
  }

  @Override
  public void methodWithHandlerMapOfJavaTypeParam(Handler<Map<String, Socket>> socketMapHandler) {
    assertNotNull(socketMapHandler);
    socketMapHandler.handle(methodWithMapOfJavaTypeReturn());
  }

  @Override
  public void methodWithHandlerGenericWithWildcardParam(Handler<Iterable<?>> iterableHandler) {
    iterableHandler.handle(methodWithGenericWithWildcardReturn());
  }

  @Override
  public Future<Socket> methodWithHandlerAsyncResultJavaTypeParam() {
    return Future.succeededFuture(methodWithJavaTypeReturn());
  }

  @Override
  public Future<List<Socket>> methodWithHandlerAsyncResultListOfJavaTypeParam() {
    return Future.succeededFuture(methodWithListOfJavaTypeReturn());
  }

  @Override
  public Future<Set<Socket>> methodWithHandlerAsyncResultSetOfJavaTypeParam() {
    return Future.succeededFuture(methodWithSetOfJavaTypeReturn());
  }

  @Override
  public Future<Map<String, Socket>> methodWithHandlerAsyncResultMapOfJavaTypeParam() {
    return Future.succeededFuture(methodWithMapOfJavaTypeReturn());
  }

  @Override
  public Future<Iterable<?>> methodWithHandlerAsyncResultGenericWithWildcardParam() {
    return Future.succeededFuture(methodWithGenericWithWildcardReturn());
  }
}
