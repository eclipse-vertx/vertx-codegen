package io.vertx.test.codegen.testjsonmapper.nonpublicmethodmapper;

import java.time.ZonedDateTime;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class APIInterfaceWithZonedDateTime {

  static ZonedDateTime deserialize(String s) {
    throw new UnsupportedOperationException();
  }

  static String serialize(ZonedDateTime s) {
    throw new UnsupportedOperationException();
  }

}
