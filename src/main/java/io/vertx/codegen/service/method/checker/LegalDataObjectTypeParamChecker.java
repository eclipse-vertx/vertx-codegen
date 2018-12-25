package io.vertx.codegen.service.method.checker;

import io.vertx.codegen.type.ClassKind;
import io.vertx.codegen.type.DataObjectTypeInfo;
import io.vertx.codegen.type.TypeInfo;

import javax.lang.model.element.ExecutableElement;

/**
 * @author Евгений Уткин (evgeny.utkin@mediascope.net)
 */
public class LegalDataObjectTypeParamChecker implements Checker {

  public static Checker getInstance() {
    return new LegalDataObjectTypeParamChecker();
  }

  @Override
  public boolean check(ExecutableElement elt, TypeInfo type, boolean allowAnyJavaType) {
    if (type.getKind() == ClassKind.DATA_OBJECT) {
      DataObjectTypeInfo classType = (DataObjectTypeInfo) type;
      return !classType.isAbstract();
    }
    return false;
  }
}
