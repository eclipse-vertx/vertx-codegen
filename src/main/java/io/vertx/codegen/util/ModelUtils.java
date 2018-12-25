package io.vertx.codegen.util;

import io.vertx.codegen.type.ParameterizedTypeInfo;
import io.vertx.codegen.type.TypeInfo;

import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;

/**
 * @author Евгений Уткин (evgeny.utkin@mediascope.net)
 */
public abstract class ModelUtils {

  private ModelUtils() {
  }

  public static boolean isObjectBound(TypeMirror bound) {
    return bound.getKind() == TypeKind.DECLARED && bound.toString().equals(Object.class.getName());
  }

  public static boolean rawTypeIs(TypeInfo type, Class<?>... classes) {
    if (type instanceof ParameterizedTypeInfo) {
      String rawClassName = type.getRaw().getName();
      for (Class<?> c : classes) {
        if (rawClassName.equals(c.getName())) {
          return true;
        }
      }
    }

    return false;
  }
}
