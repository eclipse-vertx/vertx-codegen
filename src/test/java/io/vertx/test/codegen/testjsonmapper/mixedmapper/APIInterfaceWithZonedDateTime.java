package io.vertx.test.codegen.testjsonmapper.mixedmapper;

import io.vertx.codegen.annotations.GenIgnore;
import io.vertx.codegen.annotations.VertxGen;

import java.time.ZonedDateTime;
import java.util.function.Function;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@VertxGen
public interface APIInterfaceWithZonedDateTime {

  @GenIgnore
  Function<String, ZonedDateTime> deserializer = null;

  @GenIgnore
  static String serialize(ZonedDateTime s) {
    throw new UnsupportedOperationException();
  }

  void doSomething(ZonedDateTime dateTime);

  ZonedDateTime returnSomething();

}
