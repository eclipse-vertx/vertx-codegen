package io.vertx.codegen.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The annotated DataObject property is ignored by the generated converter.
 *
 * This can be used to provide custom `fromJson()`/`toJson()` implementations for
 * types not compatible with the vertx codegen rules.
 *
 * @author Richard Gomez
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ConverterIgnore {
}
