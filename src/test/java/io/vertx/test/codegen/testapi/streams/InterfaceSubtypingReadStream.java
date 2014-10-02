package io.vertx.test.codegen.testapi.streams;

import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.streams.ReadStream;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@VertxGen
public interface InterfaceSubtypingReadStream extends GenericInterfaceExtentingReadStream<String> {
  void bar();
}
