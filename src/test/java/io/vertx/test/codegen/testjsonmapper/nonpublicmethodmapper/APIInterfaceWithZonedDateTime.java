package io.vertx.test.codegen.testjsonmapper.nonpublicmethodmapper;

import io.vertx.codegen.annotations.Mapper;

import java.time.ZonedDateTime;
import java.util.function.Function;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class APIInterfaceWithZonedDateTime {

  @Mapper
  static ZonedDateTime deserialize(String s) {
    throw new UnsupportedOperationException();
  }

  @Mapper
  static String serialize(ZonedDateTime s) {
    throw new UnsupportedOperationException();
  }

}
