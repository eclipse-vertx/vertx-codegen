package io.vertx.test.codegen.testapi;

import io.vertx.codegen.annotations.VertxGen;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@VertxGen
public interface MethodWithValidClassTypeParams {

  <T> void withType(Class<T> classType, T handler);
  <T> void withListType(Class<T> classType, List<T> handler);
  <T> void withSetType(Class<T> classType, Set<T> handler);
  <T> void withMapType(Class<T> classType, Map<String, T> handler);
  <T> void withGenericType(Class<T> classType, GenericInterface<T> handler);

}
