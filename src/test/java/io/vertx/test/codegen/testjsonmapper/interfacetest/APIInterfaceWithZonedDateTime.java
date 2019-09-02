package io.vertx.test.codegen.testjsonmapper.interfacetest;

import io.vertx.codegen.annotations.VertxGen;

import java.time.ZonedDateTime;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@VertxGen
public interface APIInterfaceWithZonedDateTime {

  void doSomething(ZonedDateTime dateTime);

}
