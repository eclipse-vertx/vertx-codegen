package io.vertx.codegen.service.method.checker;

import io.vertx.codegen.type.ClassKind;
import io.vertx.codegen.type.TypeInfo;

import javax.lang.model.element.ExecutableElement;

/**
 * @author Евгений Уткин (evgeny.utkin@mediascope.net)
 */
public class LegalArgumentContainerReturnChecker implements Checker {

  public static Checker getInstance() {
    return new LegalArgumentContainerReturnChecker();
  }

  @Override
  public boolean check(ExecutableElement elt, TypeInfo type, boolean allowAnyJavaType) {
    ClassKind argumentKind = type.getKind();
    return argumentKind.basic
      || argumentKind.json
      || argumentKind == ClassKind.OBJECT
      || (allowAnyJavaType && argumentKind == ClassKind.OTHER);
  }
}
