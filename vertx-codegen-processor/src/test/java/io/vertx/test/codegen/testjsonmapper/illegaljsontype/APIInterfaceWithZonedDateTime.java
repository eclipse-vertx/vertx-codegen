package io.vertx.test.codegen.testjsonmapper.illegaljsontype;

import io.vertx.codegen.annotations.VertxGen;

import java.time.Instant;
import java.time.ZonedDateTime;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@VertxGen
public interface APIInterfaceWithZonedDateTime {

  static ZonedDateTime deserialize(Instant s) {
    throw new UnsupportedOperationException();
  }

  static Instant serialize(ZonedDateTime zdt) {
    throw new UnsupportedOperationException();
  }

  void doSomething(ZonedDateTime dateTime);

  ZonedDateTime returnSomething();

}
