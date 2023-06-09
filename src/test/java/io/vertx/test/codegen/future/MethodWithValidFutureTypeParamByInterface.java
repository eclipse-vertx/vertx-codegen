package io.vertx.test.codegen.future;

import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.Future;
import io.vertx.test.codegen.testapi.GenericInterface;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
@VertxGen
public interface MethodWithValidFutureTypeParamByInterface {

  <T> Future<T> withType(Class<T> classType);
  <T> Future<List<T>> withListType(Class<T> classType);
  <T> Future<Set<T>> withSetType(Class<T> classType);
  <T> Future<Map<String, T>> withMapType(Class<T> classType);
  <T> Future<GenericInterface<T>> withGenericType(Class<T> classType);

}
