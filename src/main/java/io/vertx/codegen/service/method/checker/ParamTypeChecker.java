package io.vertx.codegen.service.method.checker;

import io.vertx.codegen.type.TypeInfo;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.util.Elements;
import java.util.List;

import static java.util.Arrays.asList;

/**
 * @author Евгений Уткин (evgeny.utkin@mediascope.net)
 */
public class ParamTypeChecker implements Checker {

  private final List<Checker> delegates;

  public static Checker getInstance(Elements elementUtils) {
    return new ParamTypeChecker(elementUtils);
  }

  public ParamTypeChecker(List<Checker> delegates) {
    this.delegates = delegates;
  }

  public ParamTypeChecker(Elements elementUtils) {
    this.delegates = asList(
      LegalNonCallableParamChecker.getInstance(),
      LegalClassTypeParamChecker.getInstance(),
      LegalHandlerTypeChecker.getInstance(elementUtils),
      LegalHandlerAsyncResultTypeChecker.getInstance(elementUtils),
      LegalFunctionTypeChecker.getInstance(elementUtils)
    );
  }

  @Override
  public boolean check(ExecutableElement elt, TypeInfo type, boolean allowAnyJavaType) {
    return delegates.stream().anyMatch(checker -> checker.check(elt, type, allowAnyJavaType));
  }
}
