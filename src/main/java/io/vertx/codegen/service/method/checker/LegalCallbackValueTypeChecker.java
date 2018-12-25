package io.vertx.codegen.service.method.checker;

import io.vertx.codegen.type.ClassKind;
import io.vertx.codegen.type.TypeInfo;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.util.Elements;

/**
 * @author Евгений Уткин (evgeny.utkin@mediascope.net)
 */
public class LegalCallbackValueTypeChecker implements Checker {

  private final Checker legalNonCallableReturnType;

  public static Checker getInstance(Elements elementUtils) {
    return new LegalCallbackValueTypeChecker(elementUtils);
  }

  public LegalCallbackValueTypeChecker(Checker legalNonCallableReturnType) {
    this.legalNonCallableReturnType = legalNonCallableReturnType;
  }

  public LegalCallbackValueTypeChecker(Elements elementUtils) {
    this.legalNonCallableReturnType = LegalNonCallableReturnTypeChecker.getInstance(elementUtils);
  }

  @Override
  public boolean check(ExecutableElement elt, TypeInfo type, boolean allowAnyJavaType) {
    if (type.getKind() == ClassKind.VOID) {
      return true;
    }
    return legalNonCallableReturnType.check(elt, type, allowAnyJavaType);
  }
}
