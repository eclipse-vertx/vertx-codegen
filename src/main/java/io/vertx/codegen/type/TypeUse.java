package io.vertx.codegen.type;

import io.vertx.codegen.Helper;
import io.vertx.codegen.annotations.Nullable;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedParameterizedType;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.List;

/**
 * What we need to use from type use annotations, wether it uses <i>lang model</i> api
 * or <i>lang reflect</i> api.
 *
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public abstract class TypeUse {

  private static final String NULLABLE = Nullable.class.getName();

  /**
   * Create a type use for reflect types.
   *
   * @param reflectTypes the annotated types
   * @return the created type use
   */
  public static TypeUse createTypeUse(AnnotatedType... reflectTypes) {
    return new TypeUse() {

      public TypeUse getArg(int index) {

        AnnotatedType arg = ((AnnotatedParameterizedType) reflectTypes[0]).getAnnotatedActualTypeArguments()[index];
        List<AnnotatedType> argAnnotatedTypes = new ArrayList<>();

        // The first one is the actual type so we add it
        argAnnotatedTypes.add(arg);

        //
        ParameterizedType pt = (ParameterizedType) reflectTypes[0].getType();
        Type[] typeArgs = pt.getActualTypeArguments();
        TypeVariable<? extends Class<?>>[] typeVars = ((Class<?>) pt.getRawType()).getTypeParameters();

        // For all other types (i.e >= 1) when it's a parameterized type, this type can only be
        // a super type of the first annotated type
        // we need to find out
        // if one of its type parameters matches the actual argument we need
        for (int i = 1;i < reflectTypes.length;i++) {
          if (reflectTypes[i].getType() instanceof ParameterizedType) {
            ParameterizedType a = (ParameterizedType) reflectTypes[i].getType();
            TypeVariable[] typeParam = ((Class<?>) a.getRawType()).getTypeParameters();
            for (int j = 0;j < typeParam.length;j++) {
              Type resolved = Helper.resolveTypeParameter(pt, typeParam[j]);
              if (resolved != null) {
                if (typeArgs[index].equals(resolved)) {
                  argAnnotatedTypes.add(((AnnotatedParameterizedType) reflectTypes[i]).getAnnotatedActualTypeArguments()[j]);
                }
              }
            }
          }
        }
        return createTypeUse(argAnnotatedTypes.toArray(new AnnotatedType[argAnnotatedTypes.size()]));
      }

      public boolean isNullable() {
        boolean nullable = false;
        for (int index = 0;index < reflectTypes.length;index++) {
          if (isNullable(index)) {
            nullable = true;
          } else {
            if (nullable) {
              throw new RuntimeException("Nullable type cannot override non nullable");
            }
          }
        }
        return nullable;
      }

      private boolean isNullable(int index) {
        for (Annotation annotation : reflectTypes[index].getAnnotations()) {
          if (annotation.annotationType().getName().equals(NULLABLE)) {
            return true;
          }
        }
        return false;
      }
    };
  }

  /**
   * Create a type use for model types.
   *
   * @param modelTypes the annotated types
   * @return the created type use
   */
  public static TypeUse createTypeUse(TypeMirror... modelTypes) {
    return new TypeUse() {
      @Override
      public TypeUse getArg(int index) {
        // Only use the current types as the other types won't have the info anyway
        return createTypeUse(((DeclaredType) modelTypes[0]).getTypeArguments().get(index));
      }

      @Override
      public boolean isNullable() {
        boolean nullable = false;
        for (int index = 0;index < modelTypes.length;index++) {
          if (isNullable(index)) {
            nullable = true;
          } else {
            if (nullable) {
              throw new RuntimeException("Nullable type cannot override non nullable");
            }
          }
        }
        return nullable;
      }

      private boolean isNullable(int index) {
        for (AnnotationMirror annotation : modelTypes[index].getAnnotationMirrors()) {
          DeclaredType annotationType = annotation.getAnnotationType();
          TypeElement annotationTypeElt = (TypeElement) annotationType.asElement();
          if (annotationTypeElt.getQualifiedName().toString().equals(NULLABLE)) {
            return true;
          }
        }
        return false;
      }
    };
  }

  /**
   * Return the type use of a type argument of the underlying type.
   *
   * @param index the argument index
   * @return the type use
   */
  public abstract TypeUse getArg(int index);

  /**
   * @return true if the type is nullable
   */
  public abstract  boolean isNullable();

}
