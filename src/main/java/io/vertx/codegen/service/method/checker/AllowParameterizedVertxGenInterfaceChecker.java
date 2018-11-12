package io.vertx.codegen.service.method.checker;

import io.vertx.codegen.type.ApiTypeInfo;
import io.vertx.codegen.type.ClassKind;
import io.vertx.codegen.type.ParameterizedTypeInfo;
import io.vertx.codegen.type.TypeInfo;

import javax.lang.model.element.ExecutableElement;

/**
 * @author Евгений Уткин (evgeny.utkin@mediascope.net)
 */
public class AllowParameterizedVertxGenInterfaceChecker implements Checker {

  public static Checker getInstance() {
    return new AllowParameterizedVertxGenInterfaceChecker();
  }

  @Override
  public boolean check(ExecutableElement elt, TypeInfo type, boolean allowAnyJavaType) {
    if (type.getKind() == ClassKind.API) {
      if (type.isParameterized()) {
        ParameterizedTypeInfo parameterized = (ParameterizedTypeInfo) type;
        for (TypeInfo paramType : parameterized.getArgs()) {
          ClassKind kind = paramType.getKind();
          if (!(paramType instanceof ApiTypeInfo || paramType.isVariable() || kind == ClassKind.VOID
            || kind.basic || kind.json || kind == ClassKind.DATA_OBJECT || kind == ClassKind.ENUM)) {
            return false;
          }
          if (paramType.isNullable()) {
            return false;
          }
        }
        return true;
      } else {
        return true;
      }
    }
    return false;
  }
}
