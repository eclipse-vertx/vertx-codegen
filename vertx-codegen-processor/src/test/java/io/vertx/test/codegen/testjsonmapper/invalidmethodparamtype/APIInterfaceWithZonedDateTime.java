package io.vertx.test.codegen.testjsonmapper.invalidmethodparamtype;

import io.vertx.codegen.annotations.VertxGen;

import java.util.Locale;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@VertxGen
public interface APIInterfaceWithZonedDateTime {

  static String serialize(Locale s) {
    throw new UnsupportedOperationException();
  }

}
