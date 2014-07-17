package io.vertx.codegen;

import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVariable;
import javax.lang.model.util.Types;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
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

  public static Variable create(Types typeUtils, TypeVariable type) {
    return new Variable(type.toString());
  }

  public static Class create(Types typeUtils, DeclaredType type) {
    List<? extends TypeMirror> typeArgs = type.getTypeArguments();
    List<TypeInfo> typeArguments;
    if (typeArgs.size() > 0) {
      typeArguments = new ArrayList<>(typeArgs.size());
      for (TypeMirror typeArg : typeArgs) {
        TypeInfo typeArgDesc;
        switch (typeArg.getKind()) {
          case DECLARED:
            typeArgDesc = create(typeUtils, (DeclaredType) typeArg);
            break;
          case TYPEVAR:
            typeArgDesc = create(typeUtils, (TypeVariable) typeArg);
            break;
          default:
            throw new IllegalArgumentException("Unsupported type argument " + typeArg);
        }
        // Need to check it is an interface type
        typeArguments.add(typeArgDesc);
      }
    } else {
      typeArguments = Collections.emptyList();
    }
    return new Class(typeUtils.erasure(type).toString(), typeArguments);
  }

  public static class Variable extends TypeInfo {

    final String name;

    private Variable(String name) {
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
  }

  public static class Class extends TypeInfo {

    final String fqcn;
    final List<TypeInfo> typeArguments;

    private Class(String fqcn, List<TypeInfo> typeArguments) {
      this.fqcn = fqcn;
      this.typeArguments = typeArguments;
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
    public String toString() {
      if (typeArguments.isEmpty()) {
        return fqcn;
      } else {
        StringBuilder buf = new StringBuilder(fqcn).append('<');
        for (int i = 0;i < typeArguments.size();i++) {
          TypeInfo typeArgument = typeArguments.get(i);
          if (i > 0) {
            buf.append(',');
          }
          buf.append(typeArgument);
        }
        buf.append('>');
        return buf.toString();
      }
    }
  }

  public abstract boolean equals(Object obj);
  public abstract String toString();

}
