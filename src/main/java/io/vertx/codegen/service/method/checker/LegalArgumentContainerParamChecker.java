package io.vertx.codegen.service.method.checker;

import io.vertx.codegen.type.ClassKind;
import io.vertx.codegen.type.TypeInfo;

import javax.lang.model.element.ExecutableElement;

/**
 * @author Евгений Уткин (evgeny.utkin@mediascope.net)
 */
public class LegalArgumentContainerParamChecker implements Checker {

  private final Checker vertxGenInterfaceChecker;

  public static Checker getInstance() {
    return new LegalArgumentContainerParamChecker();
  }

  public LegalArgumentContainerParamChecker(Checker vertxGenInterfaceChecker) {
    this.vertxGenInterfaceChecker = vertxGenInterfaceChecker;
  }

  public LegalArgumentContainerParamChecker() {
    this.vertxGenInterfaceChecker = NotAllowParameterizedVertxGenInterfaceChecker.getInstance();
  }

  @Override
  public boolean check(ExecutableElement elt, TypeInfo type, boolean allowAnyJavaType) {
    ClassKind argumentKind = type.getKind();
    return argumentKind.basic
      || argumentKind.json
      || vertxGenInterfaceChecker.check(elt, type, false)
      || argumentKind == ClassKind.OBJECT
      || (allowAnyJavaType && argumentKind == ClassKind.OTHER);
  }
}
