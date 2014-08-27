package io.vertx.codegen.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Options {

  /**
   * @return true when the annotated interface is considered to be implemented by a class in the generated
   * code base, when an interface is not concrete it is an abstract interface. An abstract interface cannot
   * extend a concrete interface, a concrete interface can extend at most two concrete interfaces.
   */
  boolean concrete() default true;

}
