package io.vertx.codegen.service.method.checker;

import io.vertx.codegen.type.ClassKind;
import io.vertx.codegen.type.ParameterizedTypeInfo;
import io.vertx.codegen.type.TypeInfo;
import io.vertx.codegen.type.TypeVariableInfo;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeParameterElement;

/**
 * @author Евгений Уткин (evgeny.utkin@mediascope.net)
 */
public class LegalClassTypeParamChecker implements Checker {

  public static Checker getInstance() {
    return new LegalClassTypeParamChecker();
  }

  @Override
  public boolean check(ExecutableElement elt, TypeInfo type, boolean allowAnyJavaType) {
    if (type.getKind() == ClassKind.CLASS_TYPE && type.isParameterized()) {
      ParameterizedTypeInfo parameterized = (ParameterizedTypeInfo) type;
      TypeInfo arg = parameterized.getArg(0);
      if (arg.isVariable()) {
        TypeVariableInfo variable = (TypeVariableInfo) arg;
        for (TypeParameterElement typeParamElt : elt.getTypeParameters()) {
          if (typeParamElt.getSimpleName().toString().equals(variable.getName())) {
            return true;
          }
        }
      }
    }
    return false;
  }
}
