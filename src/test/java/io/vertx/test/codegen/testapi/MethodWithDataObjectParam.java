package io.vertx.test.codegen.testapi;

import io.vertx.codegen.annotations.GenIgnore;
import io.vertx.codegen.annotations.VertxGen;

import java.net.URI;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
@VertxGen
public interface MethodWithDataObjectParam {

  @GenIgnore
  static URI deserializeURI(String s) {
    throw new UnsupportedOperationException();
  }

  void methodWithDataObjectParam(PlainDataObject dataObject);

  void methodWithMappedDataObjectParam(URI uri);

}
