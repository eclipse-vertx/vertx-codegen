package io.vertx.test.codegen.testapi;

import io.vertx.codegen.annotations.GenIgnore;
import io.vertx.codegen.annotations.VertxGen;

import java.net.URI;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
@VertxGen
public interface MethodWithValidDataObjectReturn {

  @GenIgnore
  static String serializeURI(URI uri) {
    throw new UnsupportedOperationException();
  }

  PlainDataObjectWithToJson methodWithDataObjectReturn();
  AbstractDataObjectWithToJson methodWithAbstractDataObjectReturn();
  InterfaceDataObjectWithToJson methodWithInterfaceDataObjectReturn();
  URI methodWithMappedDataObjectReturn();


}
