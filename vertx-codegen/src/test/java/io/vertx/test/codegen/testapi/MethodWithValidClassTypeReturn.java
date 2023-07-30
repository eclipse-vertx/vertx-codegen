package io.vertx.test.codegen.testapi;

import io.vertx.codegen.annotations.VertxGen;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@VertxGen
public interface MethodWithValidClassTypeReturn {

  <T> T withType(Class<T> classType);
  <T> List<T> withListType(Class<T> classType);
  <T> Set<T> withSetType(Class<T> classType);
  <T> Map<String, T> withMapType(Class<T> classType);
  <T> GenericInterface<T> withGenericType(Class<T> classType);

}
