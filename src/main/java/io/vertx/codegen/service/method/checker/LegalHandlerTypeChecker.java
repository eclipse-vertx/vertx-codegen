package io.vertx.codegen.service.method.checker;

import io.vertx.codegen.type.ClassKind;
import io.vertx.codegen.type.ParameterizedTypeInfo;
import io.vertx.codegen.type.TypeInfo;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.util.Elements;

/**
 * @author Евгений Уткин (evgeny.utkin@mediascope.net)
 */
public class LegalHandlerTypeChecker implements Checker {

  private final Checker legalCallbackValueType;

  public static Checker getInstance(Elements elementUtils) {
    return new LegalHandlerTypeChecker(elementUtils);
  }

  public LegalHandlerTypeChecker(Checker legalCallbackValueType) {
    this.legalCallbackValueType = legalCallbackValueType;
  }

  public LegalHandlerTypeChecker(Elements elementUtils) {
    this.legalCallbackValueType = LegalCallbackValueTypeChecker.getInstance(elementUtils);
  }

  @Override
  public boolean check(ExecutableElement elt, TypeInfo type, boolean allowAnyJavaType) {
    if (type.getErased().getKind() == ClassKind.HANDLER) {
      TypeInfo eventType = ((ParameterizedTypeInfo) type).getArgs().get(0);
      return legalCallbackValueType.check(elt, eventType, allowAnyJavaType) || eventType.getKind() == ClassKind.THROWABLE;
    }
    return false;
  }
}
