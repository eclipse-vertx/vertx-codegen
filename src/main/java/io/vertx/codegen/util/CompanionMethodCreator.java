package io.vertx.codegen.util;

import io.vertx.codegen.MethodInfo;
import io.vertx.codegen.doc.Text;
import io.vertx.codegen.type.ClassTypeInfo;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import java.util.Optional;
import java.util.Set;

/**
 * @author Евгений Уткин (evgeny.utkin@mediascope.net)
 */
public class CompanionMethodCreator implements MethodCreator {

  @Override
  public Optional<MethodInfo> createMethod(TypeElement modelElt, ExecutableElement modelMethod, boolean allowAnyJavaType, Set<ClassTypeInfo> collectedTypes, Text deprecatedDesc) {
    return Optional.empty();
  }
}
