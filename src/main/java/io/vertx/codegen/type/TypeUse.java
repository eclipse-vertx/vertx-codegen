package io.vertx.codegen.type;

import io.vertx.codegen.Helper;
import io.vertx.codegen.annotations.Nullable;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedParameterizedType;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

/**
 * What we need to use from type use annotations, whether it uses <i>lang model</i> api
 * or <i>lang reflect</i> api.
 *
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class TypeUse {

  static final String NULLABLE = Nullable.class.getName();
  private static final List<TypeInternalProvider> providers = new ArrayList<>();

  static {
    try {
      TypeUse.class.getClassLoader().loadClass("java.lang.invoke.VarHandle");
      // compiling a codegen project with Java 9 - Trees are not available but type use via mirror should work fine
    } catch (Throwable ignore1) {
      // compiling with Java 8, it has incomplete implementation of type use API
      try {
        // Add via reflection so the codegen project can be compiled with Java 9 also
        String fqn = TypeUse.class.getPackage().getName() + ".TreeTypeInternal";
        Class<?> clazz = TypeUse.class.getClassLoader().loadClass(fqn);
        Field getter = clazz.getField("PROVIDER");
        TypeInternalProvider provider = (TypeInternalProvider) getter.get(null);
        providers.add(provider);
      } catch (Throwable ignore2) {
        // Codegen was compiled with Java 9, no TreeTypeInternal
      }
    }
    providers.add(new TypeInternalProvider() {
      private Method getMethod(ProcessingEnvironment env, ExecutableElement methodElt) {
        Method methodRef = Helper.getReflectMethod(Thread.currentThread().getContextClassLoader(), methodElt);
        if (methodRef == null) {
          methodRef = Helper.getReflectMethod(env, methodElt);
        }
        return methodRef;
      }
      public TypeUse.TypeInternal forParam(ProcessingEnvironment env, ExecutableElement methodElt, int paramIndex) {
        Method methodRef = getMethod(env, methodElt);
        if (methodRef == null) {
          return null;
        }
        AnnotatedType annotated = methodRef.getAnnotatedParameterTypes()[paramIndex];
        return new ReflectType(annotated);
      }
      public TypeUse.TypeInternal forReturn(ProcessingEnvironment env, ExecutableElement methodElt) {
        Method methodRef = getMethod(env, methodElt);
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
    String rawName();
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
   * @param rawName the name of the raw type for which we want to get the argument
   * @param index the argument index
   * @return the type use
   */
  public TypeUse getArg(String rawName, int index) {
    List<TypeInternal> abc = new ArrayList<>();
    for (TypeInternal type : types) {
      if (!rawName.equals(type.rawName())) {
        break;
      }
      abc.add(type.getArgAt(index));
    }
    return new TypeUse(abc.toArray(new TypeInternal[abc.size()]));
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

  private static class ReflectType implements TypeInternal {

    private final AnnotatedType annotatedType;
    private final boolean nullable;

    private ReflectType(AnnotatedType annotated) {
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

    public boolean isNullable() {
      return nullable;
    }

    public TypeInternal getArgAt(int index) {
      AnnotatedParameterizedType annotatedParameterizedType = (AnnotatedParameterizedType) annotatedType;
      return new ReflectType(annotatedParameterizedType.getAnnotatedActualTypeArguments()[index]);
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

    @Override
    public String rawName() {
      if (mirror.getKind() == TypeKind.DECLARED) {
        return ((TypeElement)((DeclaredType)mirror).asElement()).getQualifiedName().toString();
      } else {
        return null;
      }
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
      return new MirrorTypeInternal(args.get(index));
    }
  }
}
