package io.vertx.codegen.service.method.checker;

import io.vertx.codegen.type.ClassKind;
import io.vertx.codegen.type.TypeInfo;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.util.Elements;
import java.util.List;

import static java.util.Arrays.asList;

/**
 * @author Евгений Уткин (evgeny.utkin@mediascope.net)
 */
public class LegalNonCallableReturnTypeChecker implements Checker {

  private final List<Checker> delegates;

  public static Checker getInstance(Elements elementUtils) {
    return new LegalNonCallableReturnTypeChecker(elementUtils);
  }

  public LegalNonCallableReturnTypeChecker(List<Checker> delegates) {
    this.delegates = delegates;
  }

  public LegalNonCallableReturnTypeChecker(Elements elementUtils) {
    this.delegates = asList(
      (elt, type, allowAnyJavaType) -> type.getKind().basic,
      (elt, type, allowAnyJavaType) -> type.getKind().json,
      LegalDataObjectTypeReturnChecker.getInstance(elementUtils),
      LegalEnumChecker.getInstance(),
      (elt, type, allowAnyJavaType) -> type.getKind() == ClassKind.THROWABLE,
      TypeVariableChecker.getInstance(),
      (elt, type, allowAnyJavaType) -> type.getKind() == ClassKind.OBJECT,
      AllowParameterizedVertxGenInterfaceChecker.getInstance(),
      (elt, type, allowAnyJavaType) -> allowAnyJavaType && type.getKind() == ClassKind.OTHER,
      LegalContainerReturnChecker.getInstance(elementUtils)
    );
  }

  @Override
  public boolean check(ExecutableElement elt, TypeInfo type, boolean allowAnyJavaType) {
    return delegates.stream().anyMatch(checker -> checker.check(elt, type, allowAnyJavaType));
  }
}
