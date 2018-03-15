package io.vertx.codegen.type;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.ExecutableElement;

import io.vertx.codegen.annotations.Nullable;

/**
 * What we need to use from type use annotations, wether it uses <i>lang model</i> api
 * or <i>lang reflect</i> api.
 *
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class TypeUse {

  static final String NULLABLE = Nullable.class.getName();
  private static final List<TypeInternalProvider> providers = TypeInternalProviders.getProviders();

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
}
