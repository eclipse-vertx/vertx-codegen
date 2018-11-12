package io.vertx.codegen.service.method.checker;

import io.vertx.codegen.type.ClassKind;
import io.vertx.codegen.type.ParameterizedTypeInfo;
import io.vertx.codegen.type.TypeInfo;

import javax.lang.model.element.ExecutableElement;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static io.vertx.codegen.util.ModelUtils.rawTypeIs;

/**
 * @author Евгений Уткин (evgeny.utkin@mediascope.net)
 */
public class LegalContainerParamChecker implements Checker {

  private final Checker vertxGenInterfaceChecker;
  private final Checker legalDataObjectTypeParamChecker;

  public static Checker getInstance() {
    return new LegalContainerParamChecker();
  }

  public LegalContainerParamChecker(Checker vertxGenInterfaceChecker, Checker legalDataObjectTypeParamChecker) {
    this.vertxGenInterfaceChecker = vertxGenInterfaceChecker;
    this.legalDataObjectTypeParamChecker = legalDataObjectTypeParamChecker;
  }


  public LegalContainerParamChecker() {
    this.vertxGenInterfaceChecker = NotAllowParameterizedVertxGenInterfaceChecker.getInstance();
    this.legalDataObjectTypeParamChecker = LegalDataObjectTypeParamChecker.getInstance();
  }

  @Override
  public boolean check(ExecutableElement elt, TypeInfo type, boolean allowAnyJavaType) {
    // List<T> and Set<T> are also legal for params if T = basic type, json, @VertxGen, @DataObject
    // Map<K,V> is also legal for returns and params if K is a String and V is a basic type, json, or a @VertxGen interface
    if (rawTypeIs(type, List.class, Set.class, Map.class)) {
      TypeInfo argument = ((ParameterizedTypeInfo) type).getArgs().get(0);
      if (type.getKind() != ClassKind.MAP) {
        return argument.getKind().basic ||
          argument.getKind().json ||
          vertxGenInterfaceChecker.check(elt, argument, false) ||
          legalDataObjectTypeParamChecker.check(elt, argument, false) ||
          argument.getKind() == ClassKind.ENUM ||
          (allowAnyJavaType && argument.getKind() == ClassKind.OTHER);
      } else if (argument.getKind() == ClassKind.STRING) { // Only allow Map's with String's for keys
        argument = ((ParameterizedTypeInfo) type).getArgs().get(1);
        return argument.getKind().basic || argument.getKind().json || vertxGenInterfaceChecker.check(elt, argument, false) || (allowAnyJavaType && argument.getKind() == ClassKind.OTHER);
      }
    }
    return false;
  }
}
