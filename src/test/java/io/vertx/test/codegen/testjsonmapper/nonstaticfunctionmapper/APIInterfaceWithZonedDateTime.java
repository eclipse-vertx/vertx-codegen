package io.vertx.test.codegen.testjsonmapper.nonstaticfunctionmapper;

import io.vertx.codegen.annotations.Mapper;

import java.time.ZonedDateTime;
import java.util.function.Function;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class APIInterfaceWithZonedDateTime {

  @Mapper
  public Function<String, ZonedDateTime> deserializer = null;

  @Mapper
  public Function<ZonedDateTime, String> serializer = null;

}
