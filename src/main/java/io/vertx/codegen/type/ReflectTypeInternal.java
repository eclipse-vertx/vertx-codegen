package io.vertx.codegen.type;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedParameterizedType;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.ExecutableElement;

import io.vertx.codegen.Helper;
import io.vertx.codegen.type.TypeUse.TypeInternal;

class ReflectTypeInternal implements TypeInternal {

  public static final TypeUse.TypeInternalProvider PROVIDER = new TypeUse.TypeInternalProvider() {
    private Method getMethod(ProcessingEnvironment env, ExecutableElement methodElt) {
      Method methodRef = Helper.getReflectMethod(Thread.currentThread().getContextClassLoader(), methodElt);
      if (methodRef == null) {
        methodRef = Helper.getReflectMethod(env, methodElt);
      }
      return methodRef;
    }

    @Override
    public TypeUse.TypeInternal forParam(ProcessingEnvironment env, ExecutableElement methodElt, int paramIndex) {
      Method methodRef = getMethod(env, methodElt);
      if (methodRef == null) {
        return null;
      }
      AnnotatedType annotated = methodRef.getAnnotatedParameterTypes()[paramIndex];
      return new ReflectTypeInternal(annotated);
    }

    @Override
    public TypeUse.TypeInternal forReturn(ProcessingEnvironment env, ExecutableElement methodElt) {
      Method methodRef = getMethod(env, methodElt);
      if (methodRef == null) {
        return null;
      }
      AnnotatedType annotated = methodRef.getAnnotatedReturnType();
      return new ReflectTypeInternal(annotated);
    }
  };

  private final AnnotatedType annotatedType;
  private final boolean nullable;

  private ReflectTypeInternal(AnnotatedType annotated) {
    this.annotatedType = annotated;
    this.nullable = isNullable(annotated);
  }

  @Override
  public String rawName() {
    if (annotatedType instanceof AnnotatedParameterizedType) {
      return ((ParameterizedType)(annotatedType.getType())).getRawType().getTypeName();
    } else {
      return null;
    }
  }

  @Override
  public boolean isNullable() {
    return nullable;
  }

  @Override
  public TypeInternal getArgAt(int index) {
    AnnotatedParameterizedType annotatedParameterizedType = (AnnotatedParameterizedType) annotatedType;
    return new ReflectTypeInternal(annotatedParameterizedType.getAnnotatedActualTypeArguments()[index]);
  }

  private static boolean isNullable(AnnotatedType type) {
    for (Annotation annotation : type.getAnnotations()) {
      if (annotation.annotationType().getName().equals(TypeUse.NULLABLE)) {
        return true;
      }
    }
    return false;
  }
}
