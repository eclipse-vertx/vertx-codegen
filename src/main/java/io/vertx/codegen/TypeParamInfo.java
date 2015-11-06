package io.vertx.codegen;

import io.vertx.codegen.annotations.VertxGen;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Parameterizable;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.TypeParameterElement;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.TypeVariable;
import java.util.Collections;
import java.util.Set;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public abstract class TypeParamInfo {

  static TypeParamInfo create(java.lang.reflect.TypeVariable typeVariable) {
    GenericDeclaration decl = typeVariable.getGenericDeclaration();
    TypeVariable<?>[] typeParams = decl.getTypeParameters();
    for (int index = 0;index < typeParams.length;index++) {
      if (typeParams[index].equals(typeVariable)) {
        if (decl instanceof java.lang.Class) {
          java.lang.Class<?> classDecl = (java.lang.Class<?>) decl;
          VertxGen genAnn = classDecl.getAnnotation(VertxGen.class);
          boolean concreteType = genAnn == null || genAnn.concrete();
          return new Class(classDecl.getName(), index, typeVariable.getName(), Collections.emptySet(), concreteType);
        } else if (decl instanceof java.lang.reflect.Method) {
          java.lang.reflect.Method methodDecl = (java.lang.reflect.Method) decl;
          return new Method(methodDecl.getDeclaringClass().getName(), methodDecl.getName(), index, typeVariable.getName());
        } else {
          throw new UnsupportedOperationException();
        }
      }
    }
    throw new AssertionError();
  }

  static TypeParamInfo create(TypeParameterElement paramElt) {
    Parameterizable genericElt = (Parameterizable) paramElt.getGenericElement();
    int index = genericElt.getTypeParameters().indexOf(paramElt);
    switch (genericElt.getKind()) {
      case INTERFACE: {
        TypeElement typeElt = (TypeElement) genericElt;
        boolean concreteType = Helper.isConcreteType(typeElt);
        return new TypeParamInfo.Class(
            typeElt.getQualifiedName().toString(), index,
            paramElt.getSimpleName().toString(), Collections.emptySet(), concreteType);
      }
      case METHOD: {
        ExecutableElement methodElt = (ExecutableElement) genericElt;
        TypeElement typeElt = (TypeElement) methodElt.getEnclosingElement();
        return new TypeParamInfo.Method(
            typeElt.getQualifiedName().toString(), methodElt.getSimpleName().toString(), index,
            paramElt.getSimpleName().toString());
      }
      default:
        throw new UnsupportedOperationException(genericElt.getKind() + "");
    }
  }

  protected final int index;
  protected final String name;

  public TypeParamInfo(int index, String name) {
    this.index = index;
    this.name = name;
  }

  public static class Class extends TypeParamInfo {

    private final String typeName;
    private final Set<Variance> siteVariances;
    private final boolean concreteType;

    public Class(String typeName, int index, String name, Set<Variance> siteVariances, boolean concreteType) {
      super(index, name);
      this.typeName = typeName;
      this.siteVariances = siteVariances;
      this.concreteType = concreteType;
    }

    public boolean isConcreteType() {
      return concreteType;
    }

    public boolean isSiteCovariant() {
      return siteVariances.size() == 1 && siteVariances.contains(Variance.COVARIANT);
    }

    public boolean isSiteContravariant() {
      return siteVariances.size() == 1 && siteVariances.contains(Variance.CONTRAVARIANT);
    }

    @Override
    public boolean equals(Object obj) {
      if (obj instanceof Class) {
        TypeParamInfo.Class that = (Class) obj;
        return typeName.equals(that.typeName) && index == that.getIndex();
      }
      return false;
    }

    @Override
    public String toString() {
      return "TypeParamInfo.Class[name=" + name + ",typeName=" + typeName + "]";
    }
  }

  public static class Method extends TypeParamInfo {

    private final String typeName;
    private final String methodName;

    public Method(String typeName, String methodName, int index, String name) {
      super(index, name);

      this.typeName = typeName;
      this.methodName = methodName;
    }

    @Override
    public boolean equals(Object obj) {
      if (obj instanceof Method) {
        TypeParamInfo.Method that = (Method) obj;
        return typeName.equals(that.typeName) && methodName.equals(that.methodName) && index == that.getIndex();
      }
      return false;
    }

    @Override
    public String toString() {
      return "TypeParamInfo.Method[name=" + name + ",typeName=" + typeName + ",methodName" + methodName + "]";
    }
  }

  public int getIndex() {
    return index;
  }

  public String getName() {
    return name;
  }
}
