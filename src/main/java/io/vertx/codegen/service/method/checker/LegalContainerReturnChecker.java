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
  private final Checker legalArgumentContainerReturn;

  public static Checker getInstance(Elements elementsUtils) {
    return new LegalContainerReturnChecker(elementsUtils);
  }

  public LegalContainerReturnChecker(Checker vertxGenInterfaceChecker, Checker legalDataObjectTypeReturnChecker, Checker legalArgumentContainerReturn) {
    this.vertxGenInterfaceChecker = vertxGenInterfaceChecker;
    this.legalDataObjectTypeReturnChecker = legalDataObjectTypeReturnChecker;
    this.legalArgumentContainerReturn = legalArgumentContainerReturn;
  }


  public LegalContainerReturnChecker(Elements elementsUtils) {
    this.vertxGenInterfaceChecker = NotAllowParameterizedVertxGenInterfaceChecker.getInstance();
    this.legalDataObjectTypeReturnChecker = LegalDataObjectTypeReturnChecker.getInstance(elementsUtils);
    this.legalArgumentContainerReturn = LegalArgumentContainerReturnChecker.getInstance();
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
        return legalArgumentContainerReturn.check(elt, valueType, allowAnyJavaType);
      } else {
        TypeInfo valueType = args.get(0);
        return legalArgumentContainerReturn.check(elt, valueType, allowAnyJavaType) ||
          valueType.getKind() == ClassKind.ENUM ||
          vertxGenInterfaceChecker.check(elt, valueType, false) ||
          legalDataObjectTypeReturnChecker.check(elt, valueType, false);
      }
    }
    return false;
  }
}
