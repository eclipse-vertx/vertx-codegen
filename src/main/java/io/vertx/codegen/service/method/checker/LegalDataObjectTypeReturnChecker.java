package io.vertx.codegen.service.method.checker;

import io.vertx.codegen.Helper;
import io.vertx.codegen.type.ClassKind;
import io.vertx.codegen.type.TypeInfo;
import io.vertx.core.json.JsonObject;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import java.util.Optional;

/**
 * @author Евгений Уткин (evgeny.utkin@mediascope.net)
 */
public class LegalDataObjectTypeReturnChecker implements Checker {

  private final Elements elementUtils;

  public static Checker getInstance(Elements elementUtils) {
    return new LegalDataObjectTypeReturnChecker(elementUtils);
  }

  public LegalDataObjectTypeReturnChecker(Elements elementUtils) {
    this.elementUtils = elementUtils;
  }

  @Override
  public boolean check(ExecutableElement elt, TypeInfo type, boolean allowAnyJavaType) {
    if (type.getKind() == ClassKind.DATA_OBJECT) {
      TypeElement typeElt = elementUtils.getTypeElement(type.getName());
      if (typeElt != null) {
        Optional<ExecutableElement> opt = elementUtils.
          getAllMembers(typeElt).
          stream().
          flatMap(Helper.FILTER_METHOD).
          filter(m -> m.getSimpleName().toString().equals("toJson") &&
            m.getParameters().isEmpty() &&
            m.getReturnType().toString().equals(JsonObject.class.getCanonicalName())).
          findFirst();
        return opt.isPresent();
      }
    }
    return false;
  }
}
