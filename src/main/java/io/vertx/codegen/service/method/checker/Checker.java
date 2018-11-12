package io.vertx.codegen.service.method.checker;

import io.vertx.codegen.type.TypeInfo;

import javax.lang.model.element.ExecutableElement;

/**
 * @author Евгений Уткин (evgeny.utkin@mediascope.net)
 */
@FunctionalInterface
public interface Checker {

  boolean check(ExecutableElement elt, TypeInfo type, boolean allowAnyJavaType);

}
