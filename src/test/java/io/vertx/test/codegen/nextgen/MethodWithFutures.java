package io.vertx.test.codegen.nextgen;

import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.Future;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
@VertxGen
public interface MethodWithFutures {

  Future<String> methodWithFuture();

}
