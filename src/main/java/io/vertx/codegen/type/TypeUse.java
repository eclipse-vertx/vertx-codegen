package io.vertx.codegen.type;

import io.vertx.codegen.Helper;
import io.vertx.codegen.annotations.Nullable;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedParameterizedType;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * What we need to use from type use annotations, wether it uses <i>lang model</i> api
 * or <i>lang reflect</i> api.
 *
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class TypeUse {

  static final String NULLABLE = Nullable.class.getName();
  private static final List<TypeInternalProvider> providers = new ArrayList<>();

  static {
    try {
      // Java 8 compiler has incomplete implementation of type use API
      providers.add(TreeTypeInternal.PROVIDER);
    } catch (Throwable ignore) {
      // Java 9 compiler - Trees are not available but type use via mirror should work fine
    }
    providers.add(new TypeInternalProvider() {
      public TypeUse.TypeInternal forParam(ProcessingEnvironment env, ExecutableElement methodElt, int paramIndex) {
        Method methodRef = Helper.getReflectMethod(methodElt);
        if (methodRef == null) {
          return null;
        }
        AnnotatedType annotated = methodRef.getAnnotatedParameterTypes()[paramIndex];
        return new ReflectType(annotated);
      }
      public TypeUse.TypeInternal forReturn(ProcessingEnvironment env, ExecutableElement methodElt) {
        Method methodRef = Helper.getReflectMethod(methodElt);
        if (methodRef == null) {
          return null;
        }
        AnnotatedType annotated = methodRef.getAnnotatedReturnType();
        return new ReflectType(annotated);
      }
    });
    providers.add(new TypeInternalProvider() {
      @Override
      public TypeInternal forParam(ProcessingEnvironment env, ExecutableElement methodElt, int index) {
        return new MirrorTypeInternal(methodElt.getParameters().get(index).asType());
      }
      @Override
      public TypeInternal forReturn(ProcessingEnvironment env, ExecutableElement methodElt) {
        return new MirrorTypeInternal(methodElt.getReturnType());
      }
    });
  }

  interface TypeInternal {
    boolean isNullable();
    TypeInternal getArgAt(int index);
  }

  interface TypeInternalProvider {
    TypeInternal forParam(ProcessingEnvironment env, ExecutableElement methodElt, int index);
    TypeInternal forReturn(ProcessingEnvironment env, ExecutableElement methodElt);
  }


  public static TypeUse createParamTypeUse(ProcessingEnvironment env, ExecutableElement[] methods, int index) {
    TypeInternal[] internals = new TypeInternal[methods.length];
    for (int i = 0;i < methods.length;i++) {
      for (TypeInternalProvider provider : providers) {
        internals[i] = provider.forParam(env, methods[i], index);
        if (internals[i] != null) {
          break;
        }
      }
    }
    return new TypeUse(internals);
  }

  public static TypeUse createReturnTypeUse(ProcessingEnvironment env, ExecutableElement... methods) {
    TypeInternal[] internals = new TypeInternal[methods.length];
    for (int i = 0;i < methods.length;i++) {
      for (TypeInternalProvider provider : providers) {
        internals[i] = provider.forReturn(env, methods[i]);
        if (internals[i] != null) {
          break;
        }
      }
    }
    return new TypeUse(internals);
  }

  private final TypeInternal[] types;

  private TypeUse(TypeInternal[] types) {
    this.types = types;
  }

  /**
   * Return the type use of a type argument of the underlying type.
   *
   * @param index the argument index
   * @return the type use
   */
  public TypeUse getArg(int index) {
    TypeInternal[] abc = new TypeInternal[types.length];
    for (int i = 0;i < types.length;i++) {
      abc[i] = types[i].getArgAt(index);
    }
    return new TypeUse(abc);
  }

  /**
   * @return true if the type is nullable
   */
  public boolean isNullable() {
    boolean nullable = false;
    for (TypeInternal type : types) {
      if (type.isNullable()) {
        nullable = true;
      } else {
        if (nullable) {
          throw new RuntimeException("Nullable type cannot override non nullable");
        }
      }
    }
    return nullable;
  }

  static TypeInternal NULL_TYPE_INTERNAL = new TypeInternal() {
    @Override
    public boolean isNullable() {
      return false;
    }
    @Override
    public TypeInternal getArgAt(int index) {
      return NULL_TYPE_INTERNAL;
    }
  };

  private static class ReflectType implements TypeInternal {

    private final AnnotatedType annotatedType;
    private final boolean nullable;

    private ReflectType(AnnotatedType annotated) {
      this.annotatedType = annotated;
      this.nullable = isNullable(annotated);
    }

    public boolean isNullable() {
      return nullable;
    }

    public TypeInternal getArgAt(int index) {
      if (annotatedType instanceof AnnotatedParameterizedType) {
        AnnotatedParameterizedType annotatedParameterizedType = (AnnotatedParameterizedType) annotatedType;
        return new ReflectType(annotatedParameterizedType.getAnnotatedActualTypeArguments()[index]);
      } else {
        return NULL_TYPE_INTERNAL;
      }
    }

    private static boolean isNullable(AnnotatedType type) {
      for (Annotation annotation : type.getAnnotations()) {
        if (annotation.annotationType().getName().equals(NULLABLE)) {
          return true;
        }
      }
      return false;
    }
  }

  private static class MirrorTypeInternal implements TypeInternal {

    final TypeMirror mirror;

    private MirrorTypeInternal(TypeMirror mirror) {
      this.mirror = mirror;
    }

    public boolean isNullable() {
      for (AnnotationMirror annotation : mirror.getAnnotationMirrors()) {
        DeclaredType annotationType = annotation.getAnnotationType();
        TypeElement annotationTypeElt = (TypeElement) annotationType.asElement();
        if (annotationTypeElt.getQualifiedName().toString().equals(NULLABLE)) {
          return true;
        }
      }
      return false;
    }

    public TypeInternal getArgAt(int index) {
      List<? extends TypeMirror> args = ((DeclaredType) mirror).getTypeArguments();
      if (index < args.size()) {
        return new MirrorTypeInternal(args.get(index));
      } else {
        return NULL_TYPE_INTERNAL;
      }
    }
  }
}
