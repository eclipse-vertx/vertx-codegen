package io.vertx.test.codegen.testapi;

import io.vertx.codegen.annotations.GenIgnore;
import io.vertx.codegen.annotations.VertxGen;

import java.net.URI;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
@VertxGen
public interface MethodWithValidDataObjectParam {

  @GenIgnore
  static URI deserializeURI(String s) {
    throw new UnsupportedOperationException();
  }

  void methodWithJsonObjectDataObjectParam(WriteOnlyJsonObjectDataObject dataObject);
  void methodWithStringDataObjectParam(WriteOnlyStringDataObject dataObject);

  void methodWithMappedDataObjectParam(URI uri);

}
