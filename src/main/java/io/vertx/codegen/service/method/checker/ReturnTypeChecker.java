package io.vertx.codegen.service.method.checker;

import io.vertx.codegen.type.TypeInfo;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.util.Elements;
import java.util.List;

import static java.util.Arrays.asList;

/**
 * @author Евгений Уткин (evgeny.utkin@mediascope.net)
 */
public class ReturnTypeChecker implements Checker {

  private final List<Checker> delegates;

  public static Checker getInstance(Elements elementUtils) {
    return new ReturnTypeChecker(elementUtils);
  }

  public ReturnTypeChecker(List<Checker> delegates) {
    this.delegates = delegates;
  }

  public ReturnTypeChecker(Elements elementUtils) {
    this.delegates = asList(
      (elt, type, allowAnyJavaType) -> type.isVoid(),
      LegalNonCallableReturnTypeChecker.getInstance(elementUtils),
      LegalHandlerTypeChecker.getInstance(elementUtils),
      LegalHandlerAsyncResultTypeChecker.getInstance(elementUtils)
    );
  }

  @Override
  public boolean check(ExecutableElement elt, TypeInfo type, boolean allowAnyJavaType) {
    return delegates.stream().anyMatch(checker -> checker.check(elt, type, allowAnyJavaType));
  }
}
