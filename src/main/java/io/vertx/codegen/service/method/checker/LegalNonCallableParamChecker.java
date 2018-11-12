package io.vertx.codegen.service.method.checker;

import io.vertx.codegen.type.ClassKind;
import io.vertx.codegen.type.TypeInfo;

import javax.lang.model.element.ExecutableElement;
import java.util.List;

import static java.util.Arrays.asList;

/**
 * @author Евгений Уткин (evgeny.utkin@mediascope.net)
 */
public class LegalNonCallableParamChecker implements Checker {

  private final List<Checker> delegates;

  public static Checker getInstance() {
    return new LegalNonCallableParamChecker();
  }

  public LegalNonCallableParamChecker(List<Checker> delegates) {
    this.delegates = delegates;
  }

  public LegalNonCallableParamChecker() {
    this.delegates = asList(
      (elt, type, allowAnyJavaType) -> type.getKind().basic,
      (elt, type, allowAnyJavaType) -> type.getKind().json,
      LegalDataObjectTypeParamChecker.getInstance(),
      LegalEnumChecker.getInstance(),
      (elt, type, allowAnyJavaType) -> type.getKind() == ClassKind.THROWABLE,
      TypeVariableChecker.getInstance(),
      (elt, type, allowAnyJavaType) -> type.getKind() == ClassKind.OBJECT,
      AllowParameterizedVertxGenInterfaceChecker.getInstance(),
      (elt, type, allowAnyJavaType) -> allowAnyJavaType && type.getKind() == ClassKind.OTHER,
      LegalContainerParamChecker.getInstance()
    );
  }

  @Override
  public boolean check(ExecutableElement elt, TypeInfo type, boolean allowAnyJavaType) {
    return delegates.stream().anyMatch(checker -> checker.check(elt, type, allowAnyJavaType));
  }
}
