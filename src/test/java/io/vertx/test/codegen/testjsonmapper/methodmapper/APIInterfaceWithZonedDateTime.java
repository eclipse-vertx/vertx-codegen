package io.vertx.test.codegen.testjsonmapper.methodmapper;

import io.vertx.codegen.annotations.Mapper;
import io.vertx.codegen.annotations.VertxGen;

import java.time.ZonedDateTime;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@VertxGen
public interface APIInterfaceWithZonedDateTime {

  @Mapper
  static ZonedDateTime deserialize(String s) {
    throw new UnsupportedOperationException();
  }

  @Mapper
  static String serialize(ZonedDateTime s) {
    throw new UnsupportedOperationException();
  }

  void doSomething(ZonedDateTime dateTime);

  ZonedDateTime returnSomething();

}
