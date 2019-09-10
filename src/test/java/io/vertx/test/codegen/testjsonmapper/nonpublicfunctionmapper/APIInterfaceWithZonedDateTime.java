package io.vertx.test.codegen.testjsonmapper.nonpublicfunctionmapper;

import io.vertx.codegen.annotations.Mapper;

import java.time.ZonedDateTime;
import java.util.function.Function;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class APIInterfaceWithZonedDateTime {

  @Mapper
  static Function<String, ZonedDateTime> deserializer = null;

  @Mapper
  static Function<ZonedDateTime, String> serializer = null;

}
