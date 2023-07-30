package io.vertx.test.codegen.testapi.streams;

import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.streams.ReadStream;
import io.vertx.core.streams.WriteStream;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@VertxGen
public interface GenericInterfaceExtentingReadStreamAndWriteStream<U> extends ReadStream<U>, WriteStream<U> {
  void foo();
}
