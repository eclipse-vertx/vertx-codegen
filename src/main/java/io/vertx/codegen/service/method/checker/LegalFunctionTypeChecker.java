package io.vertx.codegen.service.method.checker;

import io.vertx.codegen.type.ClassKind;
import io.vertx.codegen.type.ParameterizedTypeInfo;
import io.vertx.codegen.type.TypeInfo;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.util.Elements;

/**
 * @author Евгений Уткин (evgeny.utkin@mediascope.net)
 */
public class LegalFunctionTypeChecker implements Checker {

  private final Checker legalCallbackValueType;
  private final Checker legalNonCallableParam;

  public static Checker getInstance(Elements elementUtils) {
    return new LegalFunctionTypeChecker(elementUtils);
  }

  public LegalFunctionTypeChecker(Checker legalCallbackValueType, Checker legalNonCallableParam) {
    this.legalCallbackValueType = legalCallbackValueType;
    this.legalNonCallableParam = legalNonCallableParam;
  }

  public LegalFunctionTypeChecker(Elements elementUtils) {
    this.legalCallbackValueType = LegalCallbackValueTypeChecker.getInstance(elementUtils);
    this.legalNonCallableParam = LegalNonCallableParamChecker.getInstance();
  }

  @Override
  public boolean check(ExecutableElement elt, TypeInfo type, boolean allowAnyJavaType) {
    if (type.getErased().getKind() == ClassKind.FUNCTION) {
      TypeInfo paramType = ((ParameterizedTypeInfo) type).getArgs().get(0);
      if (legalCallbackValueType.check(elt, paramType, allowAnyJavaType) || paramType.getKind() == ClassKind.THROWABLE) {
        TypeInfo returnType = ((ParameterizedTypeInfo) type).getArgs().get(1);
        return legalNonCallableParam.check(elt, returnType, allowAnyJavaType);
      }
    }
    return false;
  }
}
