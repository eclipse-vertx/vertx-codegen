package io.vertx.test.codegen.testjsonmapper.interfacewithoverloads;

import io.vertx.codegen.annotations.GenIgnore;
import io.vertx.codegen.annotations.VertxGen;

import java.time.ZonedDateTime;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@VertxGen
public interface APIInterfaceWithZonedDateTime {

  @GenIgnore
  static ZonedDateTime deserialize(String s) {
    throw new UnsupportedOperationException();
  }

  void doSomething(ZonedDateTime dateTime);

  void doSomething(long dateTime);

}
