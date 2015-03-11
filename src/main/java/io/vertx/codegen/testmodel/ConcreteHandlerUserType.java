package io.vertx.codegen.testmodel;

import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.Handler;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@VertxGen
public interface ConcreteHandlerUserType extends Handler<RefedInterface1> {

  static ConcreteHandlerUserType createConcrete(Handler<RefedInterface1> handler) {
    return handler::handle;
  }

  static AbstractHandlerUserType createAbstract(Handler<RefedInterface1> handler) {
    return handler::handle;
  }
}
