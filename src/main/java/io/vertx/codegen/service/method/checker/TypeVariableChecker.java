package io.vertx.codegen.service.method.checker;

import io.vertx.codegen.type.TypeInfo;
import io.vertx.codegen.type.TypeVariableInfo;

import javax.lang.model.element.ExecutableElement;

/**
 * @author Евгений Уткин (evgeny.utkin@mediascope.net)
 */
public class TypeVariableChecker implements Checker {

  public static Checker getInstance() {
    return new TypeVariableChecker();
  }

  @Override
  public boolean check(ExecutableElement elt, TypeInfo type, boolean allowAnyJavaType) {
    return type instanceof TypeVariableInfo;
  }
}
