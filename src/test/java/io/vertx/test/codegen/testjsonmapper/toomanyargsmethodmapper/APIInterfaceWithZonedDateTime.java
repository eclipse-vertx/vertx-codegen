package io.vertx.test.codegen.testjsonmapper.toomanyargsmethodmapper;

import io.vertx.codegen.annotations.Mapper;
import io.vertx.codegen.annotations.VertxGen;

import java.time.ZonedDateTime;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@VertxGen
public interface APIInterfaceWithZonedDateTime {

  @Mapper
  static ZonedDateTime deserialize(String s, String t) {
    throw new UnsupportedOperationException();
  }

}
