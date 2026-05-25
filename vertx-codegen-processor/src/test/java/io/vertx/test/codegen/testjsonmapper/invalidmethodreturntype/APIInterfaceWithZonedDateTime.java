package io.vertx.test.codegen.testjsonmapper.invalidmethodreturntype;

import io.vertx.codegen.annotations.VertxGen;

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
