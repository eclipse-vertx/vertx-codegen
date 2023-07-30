package io.vertx.test.codegen.testdataobject;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * @author <a href="http://escoffier.me">Clement Escoffier</a>
 */
@Target({ElementType.CONSTRUCTOR, ElementType.FIELD, ElementType.TYPE})
public @interface SomeAnnotation {
}
