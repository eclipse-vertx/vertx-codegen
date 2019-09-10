package io.vertx.test.codegen.testjsonmapper.nonstaticmethodmapper;

import io.vertx.codegen.annotations.Mapper;

import java.time.ZonedDateTime;
import java.util.function.Function;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class APIInterfaceWithZonedDateTime {

  @Mapper
  public ZonedDateTime deserialize(String s) {
    throw new UnsupportedOperationException();
  }

  @Mapper
  public String serialize(ZonedDateTime s) {
    throw new UnsupportedOperationException();
  }

}
