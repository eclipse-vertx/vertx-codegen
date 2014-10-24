package io.vertx.test.codegen.proxytestapi;

import io.vertx.codegen.annotations.ProxyGen;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
@ProxyGen
public interface InvalidOverloaded {

  void someMethod(String str);

  void someMethod(String str, int i);
}
