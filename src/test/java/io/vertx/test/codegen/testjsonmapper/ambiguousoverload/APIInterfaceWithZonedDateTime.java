package io.vertx.test.codegen.testjsonmapper.ambiguousoverload;

import io.vertx.codegen.annotations.VertxGen;

import java.time.ZonedDateTime;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@VertxGen
public interface APIInterfaceWithZonedDateTime {

  static ZonedDateTime deserialize(String s) {
    throw new UnsupportedOperationException();
  }

  void doSomething(ZonedDateTime dateTime);

  void doSomething(String dateTime);

}
