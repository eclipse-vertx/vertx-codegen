package io.vertx.codegen.service.method.checker;

import io.vertx.codegen.type.ClassKind;
import io.vertx.codegen.type.ParameterizedTypeInfo;
import io.vertx.codegen.type.TypeInfo;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.util.Elements;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static io.vertx.codegen.util.ModelUtils.rawTypeIs;

/**
 * @author Евгений Уткин (evgeny.utkin@mediascope.net)
 */
public class LegalContainerReturnChecker implements Checker {

  private final Checker vertxGenInterfaceChecker;
  private final Checker legalDataObjectTypeReturnChecker;

  public static Checker getInstance(Elements elementsUtils) {
    return new LegalContainerReturnChecker(elementsUtils);
  }

  public LegalContainerReturnChecker(Checker vertxGenInterfaceChecker, Checker legalDataObjectTypeReturnChecker) {
    this.vertxGenInterfaceChecker = vertxGenInterfaceChecker;
    this.legalDataObjectTypeReturnChecker = legalDataObjectTypeReturnChecker;
  }



  public LegalContainerReturnChecker(Elements elementsUtils) {
    this.vertxGenInterfaceChecker = NotAllowParameterizedVertxGenInterfaceChecker.getInstance();
    this.legalDataObjectTypeReturnChecker = LegalDataObjectTypeReturnChecker.getInstance(elementsUtils);
  }

  @Override
  public boolean check(ExecutableElement elt, TypeInfo type, boolean allowAnyJavaType) {
    if (rawTypeIs(type, List.class, Set.class, Map.class)) {
      List<TypeInfo> args = ((ParameterizedTypeInfo) type).getArgs();
      if (type.getKind() == ClassKind.MAP) {
        if (args.get(0).getKind() != ClassKind.STRING) {
          return false;
        }
        TypeInfo valueType = args.get(1);
        return valueType.getKind().basic ||
          valueType.getKind().json ||
          (allowAnyJavaType && valueType.getKind() == ClassKind.OTHER);
      } else {
        TypeInfo valueType = args.get(0);
        return valueType.getKind().basic ||
          valueType.getKind().json ||
          valueType.getKind() == ClassKind.ENUM ||
          vertxGenInterfaceChecker.check(elt, valueType, allowAnyJavaType) ||
          (allowAnyJavaType && valueType.getKind() == ClassKind.OTHER) ||
          legalDataObjectTypeReturnChecker.check(elt, valueType, allowAnyJavaType);
      }
    }
    return false;
  }
}
