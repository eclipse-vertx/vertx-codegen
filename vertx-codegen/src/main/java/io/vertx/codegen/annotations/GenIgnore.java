package io.vertx.codegen.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * The annotated element is ignored by the code generator, this can be used to provide Java exclusive methods that are
 * not compatible with the vertx codegen rules.
 *
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface GenIgnore {

  String PERMITTED_TYPE = "permitted-type";

  String[] value() default {};

}
