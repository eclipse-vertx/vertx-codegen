package io.vertx.test.codegen.testjsonmapper.nonpublicfunctionmapper;

import java.time.ZonedDateTime;
import java.util.function.Function;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class APIInterfaceWithZonedDateTime {

  static Function<String, ZonedDateTime> deserializer = null;

  static Function<ZonedDateTime, String> serializer = null;

}
