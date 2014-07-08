package io.vertx.test.codegen.testapi;

import io.vertx.core.gen.VertxGen;

import java.util.List;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
@VertxGen
public interface MethodWithListParam {

  void foo(List<String> list);
}
