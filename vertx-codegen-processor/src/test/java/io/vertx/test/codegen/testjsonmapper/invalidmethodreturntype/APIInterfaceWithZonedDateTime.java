package io.vertx.test.codegen.testjsonmapper.invalidmethodreturntype;

import io.vertx.codegen.annotations.VertxGen;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Locale;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@VertxGen
public interface APIInterfaceWithZonedDateTime {

  static Locale deserialize(String s) {
    throw new UnsupportedOperationException();
  }

}
