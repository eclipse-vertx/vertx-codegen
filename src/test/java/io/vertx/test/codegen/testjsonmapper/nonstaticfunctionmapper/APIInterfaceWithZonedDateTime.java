package io.vertx.test.codegen.testjsonmapper.nonstaticfunctionmapper;

import java.time.ZonedDateTime;
import java.util.function.Function;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class APIInterfaceWithZonedDateTime {

  public Function<String, ZonedDateTime> deserializer = null;

  public Function<ZonedDateTime, String> serializer = null;

}
