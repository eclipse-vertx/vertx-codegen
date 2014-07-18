package io.vertx.codegen;

import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVariable;
import javax.lang.model.type.WildcardType;
import javax.lang.model.util.Types;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Describes a java type.
 *
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public abstract class TypeInfo {

  public static TypeInfo create(Type type) {
    if (type instanceof java.lang.Class) {
      return new Class(type.getTypeName(), Collections.emptyList());
    } else if (type instanceof ParameterizedType) {
      ParameterizedType parameterizedType = (ParameterizedType) type;
      List<TypeInfo> args = Arrays.asList(parameterizedType.getActualTypeArguments()).
          stream().
          map(TypeInfo::create).
          collect(Collectors.toList());
      return new Class(parameterizedType.getRawType().getTypeName(), args);
    } else if (type instanceof java.lang.reflect.TypeVariable) {
      return new Variable(((java.lang.reflect.TypeVariable)type).getName());
    } else {
      throw new IllegalArgumentException("Unsupported type " + type);
    }
  }

  public static TypeInfo create(Types typeUtils, TypeMirror type) {
    switch (type.getKind()) {
      case DECLARED:
        return create(typeUtils, (DeclaredType) type);
      case DOUBLE:
      case LONG:
      case FLOAT:
      case CHAR:
      case BYTE:
      case SHORT:
      case BOOLEAN:
      case INT:
        return new Primitive(type.toString());
      case TYPEVAR:
        return create(typeUtils, (TypeVariable) type);
      case WILDCARD:
        return create(typeUtils, (WildcardType) type);
      default:
        throw new IllegalArgumentException("Illegal type " + type + " of kind " + type.getKind());
    }
  }

  public static Wildcard create(Types typeUtils, WildcardType type) {
    if (type.getExtendsBound() != null) {
      throw new IllegalArgumentException("Wildcard type cannot have an upper bound");
    }
    if (type.getSuperBound() != null) {
      throw new IllegalArgumentException("Wildcard type cannot have a lower bound");
    }
    return new Wildcard();
  }

  /**
   * Simple wildcard without bound support.
   */
  public static class Wildcard extends TypeInfo {

    @Override
    public boolean equals(Object obj) {
      return obj instanceof Wildcard;
    }

    @Override
    public String toString(boolean qualified) {
      return "?";
    }
  }

  public static Variable create(Types typeUtils, TypeVariable type) {
    return new Variable(type.toString());
  }

  public static Class create(Types typeUtils, DeclaredType type) {
    List<? extends TypeMirror> typeArgs = type.getTypeArguments();
    List<TypeInfo> typeArguments;
    if (typeArgs.size() > 0) {
      typeArguments = new ArrayList<>(typeArgs.size());
      for (TypeMirror typeArg : typeArgs) {
        TypeInfo typeArgDesc = create(typeUtils, typeArg);
        // Need to check it is an interface type
        typeArguments.add(typeArgDesc);
      }
    } else {
      typeArguments = Collections.emptyList();
    }
    return new Class(typeUtils.erasure(type).toString(), typeArguments);
  }

  public static class Primitive extends TypeInfo {

    final String name;

    public Primitive(String name) {
      this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
      if (obj instanceof Primitive) {
        return name.equals(((Primitive) obj).name);
      }
      return false;
    }

    @Override
    public String toString(boolean qualified) {
      return name;
    }
  }

  public static class Variable extends TypeInfo {

    final String name;

    public Variable(String name) {
      this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
      if (obj instanceof Variable) {
        Variable that = (Variable) obj;
        return name.equals(that.name);
      } else {
        return false;
      }
    }

    @Override
    public String toString() {
      return name;
    }

    @Override
    public String toString(boolean qualified) {
      return name;
    }
  }

  public static class Class extends TypeInfo {

    final String fqcn;
    final String simpleName;
    final List<TypeInfo> typeArguments;

    public Class(String fqcn, List<TypeInfo> typeArguments) {
      this.fqcn = fqcn;
      this.simpleName = Helper.getSimpleName(fqcn);
      this.typeArguments = typeArguments;
    }

    public String getSimpleName() {
      return simpleName;
    }

    @Override
    public void collectImports(Collection<String> imports) {
      imports.add(fqcn);
      typeArguments.stream().forEach(a -> a.collectImports(imports));
    }

    @Override
    public boolean equals(Object obj) {
      if (obj instanceof Class) {
        Class that = (Class) obj;
        return fqcn.equals(that.fqcn) && typeArguments.equals(that.typeArguments);
      } else {
        return false;
      }
    }

    @Override
    public String toString(boolean qualified) {
      if (typeArguments.isEmpty()) {
        return qualified ? fqcn : simpleName;
      } else {
        StringBuilder buf = new StringBuilder(qualified ? fqcn : simpleName).append('<');
        for (int i = 0;i < typeArguments.size();i++) {
          TypeInfo typeArgument = typeArguments.get(i);
          if (i > 0) {
            buf.append(',');
          }
          buf.append(typeArgument.toString(qualified));
        }
        buf.append('>');
        return buf.toString();
      }
    }
  }

  public abstract boolean equals(Object obj);

  /**
   * Collect the import fqcn needed by this type.
   *
   * @param imports the imports
   */
  public void collectImports(Collection<String> imports) {
  }

  /**
   * Renders the type name using fqcn.
   *
   * @return the representation of this type
   */
  public String toString() {
    return toString(true);
  }

  /**
   * Renders the type name.
   *
   * @param qualified true when class fqcn should be used, otherwise simple names will be used
   * @return the representation of the type
   */
  public abstract String toString(boolean qualified);

}
