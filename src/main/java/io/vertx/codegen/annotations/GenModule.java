package io.vertx.codegen.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Declares a <i>Codegen</i> module.
 *
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PACKAGE)
public @interface GenModule {

  /**
   * @return the module name
   */
  String name();

}
