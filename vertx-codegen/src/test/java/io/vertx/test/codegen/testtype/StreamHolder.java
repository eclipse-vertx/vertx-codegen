package io.vertx.test.codegen.testtype;

import io.vertx.core.streams.ReadStream;
import io.vertx.test.codegen.testapi.streams.InterfaceExtentingReadStream;
import io.vertx.test.codegen.testapi.streams.InterfaceSubtypingReadStream;
import io.vertx.test.codegen.testapi.streams.ReadStreamWithParameterizedTypeArg;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public interface StreamHolder<ClassTypeParam> {

  ReadStream<String> readStreamOfString();
  InterfaceExtentingReadStream extendsReadStreamWithClassArg();
  InterfaceSubtypingReadStream  extendsGenericReadStreamSubTypeWithClassArg();
  ReadStreamWithParameterizedTypeArg<ClassTypeParam> genericReadStreamSubTypeWithClassTypeParamArg();

}
