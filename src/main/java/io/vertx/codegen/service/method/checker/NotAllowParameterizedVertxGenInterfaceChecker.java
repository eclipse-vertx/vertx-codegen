package io.vertx.codegen.service.method.checker;

import io.vertx.codegen.type.ClassKind;
import io.vertx.codegen.type.TypeInfo;

import javax.lang.model.element.ExecutableElement;

/**
 * @author Евгений Уткин (evgeny.utkin@mediascope.net)
 */
public class NotAllowParameterizedVertxGenInterfaceChecker implements Checker {

  public static Checker getInstance() {
    return new NotAllowParameterizedVertxGenInterfaceChecker();
  }

  @Override
  public boolean check(ExecutableElement elt, TypeInfo type, boolean allowAnyJavaType) {
    if (type.getKind() == ClassKind.API) {
      return !type.isParameterized();
    }
    return false;
  }
}
