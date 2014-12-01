package io.vertx.codegen;

import io.vertx.codegen.annotations.GenModule;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVariable;
import javax.lang.model.type.WildcardType;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Describes a java type.
 *
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public abstract class TypeInfo {

  public static TypeInfo create(Type type) {
    if (type == void.class) {
      return Void.INSTANCE;
    } else if (type instanceof java.lang.Class) {
      String fqcn = type.getTypeName();
      java.lang.Class classType = (java.lang.Class) type;
      if (classType.isPrimitive()) {
        return new Primitive(classType.getName());
      } else {
        Package pkg = classType.getPackage();
        ModuleInfo module = null;
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        Thread.currentThread().setContextClassLoader(classType.getClassLoader());
        try {
          while (pkg != null) {
            GenModule annotation = pkg.getAnnotation(GenModule.class);
            if (annotation != null) {
              module = new ModuleInfo(pkg.getName(), annotation.name());
              break;
            } else {
              int pos = pkg.getName().lastIndexOf('.');
              if (pos == -1) {
                break;
              } else {
                pkg = Package.getPackage(pkg.getName().substring(0, pos));
              }
            }
          }
        } finally {
          Thread.currentThread().setContextClassLoader(loader);
        }
        ClassKind kind = Helper.getKind(classType::getAnnotation, fqcn);
        if (kind == ClassKind.API) {
          return new Class.Api(fqcn, true, null, null, module, false);
        } else {
          return new Class(kind, fqcn, module, false);
        }
      }
    } else if (type instanceof ParameterizedType) {
      ParameterizedType parameterizedType = (ParameterizedType) type;
      List<TypeInfo> args = Arrays.asList(parameterizedType.getActualTypeArguments()).
          stream().
          map(TypeInfo::create).
          collect(Collectors.toList());
      java.lang.Class raw = (java.lang.Class) parameterizedType.getRawType();
      return new Parameterized((Class) create(raw), args);
    } else if (type instanceof java.lang.reflect.TypeVariable) {
      java.lang.reflect.TypeVariable typeVar = (java.lang.reflect.TypeVariable) type;
      TypeParamInfo param = TypeParamInfo.create(typeVar);
      return new Variable(param, ((java.lang.reflect.TypeVariable)type).getName());
    } else {
      throw new IllegalArgumentException("Unsupported type " + type);
    }
  }

  public static class Factory {

    final Elements elementUtils;
    final Types typeUtils;

    public Factory(Elements elementUtils, Types typeUtils) {
      this.elementUtils = elementUtils;
      this.typeUtils = typeUtils;
    }

    public TypeInfo create(TypeMirror type) {
      switch (type.getKind()) {
        case VOID:
          return Void.INSTANCE;
        case ERROR:
        case DECLARED:
          return create((DeclaredType) type);
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
          return create((TypeVariable) type);
        case WILDCARD:
          return create((WildcardType) type);
        default:
          throw new IllegalArgumentException("Illegal type " + type + " of kind " + type.getKind());
      }
    }

    public Wildcard create(WildcardType type) {
      if (type.getExtendsBound() != null) {
        throw new IllegalArgumentException("Wildcard type cannot have an upper bound");
      }
      if (type.getSuperBound() != null) {
        throw new IllegalArgumentException("Wildcard type cannot have a lower bound");
      }
      return new Wildcard();
    }

    public TypeInfo create(DeclaredType type) {
      ModuleInfo module = null;
      Element elt = type.asElement();
      PackageElement pkgElt = elementUtils.getPackageOf(elt);
      while (pkgElt != null) {
        GenModule annotation = pkgElt.getAnnotation(GenModule.class);
        if (annotation != null) {
          module = new ModuleInfo(pkgElt.getQualifiedName().toString(), annotation.name());
          break;
        }
        String pkgQN = pkgElt.getQualifiedName().toString();
        int pos = pkgQN.lastIndexOf('.');
        if (pos == -1) {
          break;
        } else {
          pkgElt = elementUtils.getPackageElement(pkgQN.substring(0, pos));
        }
      }
      String fqcn = typeUtils.erasure(type).toString();
      ClassKind kind;
      if (elt.getKind() == ElementKind.ENUM) {
        kind = ClassKind.ENUM;
      } else {
        kind = Helper.getKind(annotationType -> elt.getAnnotation(annotationType), fqcn);
      }
      Class raw;
      boolean proxyGen = elt.getAnnotation(ProxyGen.class) != null;
      if (kind == ClassKind.API) {
        VertxGen genAnn = elt.getAnnotation(VertxGen.class);
        TypeElement readStreamElt = elementUtils.getTypeElement(ClassModel.VERTX_READ_STREAM);
        TypeMirror readStreamType = readStreamElt.asType();
        TypeElement writeStreamElt = elementUtils.getTypeElement(ClassModel.VERTX_WRITE_STREAM);
        TypeMirror writeStreamType = writeStreamElt.asType();
        TypeMirror readStreamRawType = typeUtils.erasure(readStreamType);
        TypeMirror writeStreamRawType = typeUtils.erasure(writeStreamType);
        TypeInfo readStreamArg = null;
        if (typeUtils.isSubtype(type, readStreamRawType)) {
          TypeMirror resolved = Helper.resolveTypeParameter(typeUtils, type, readStreamElt.getTypeParameters().get(0));
          readStreamArg = create(resolved);
        }
        TypeInfo writeStreamArg = null;
        if (typeUtils.isSubtype(type, writeStreamRawType)) {
          TypeMirror resolved = Helper.resolveTypeParameter(typeUtils, type, writeStreamElt.getTypeParameters().get(0));
          writeStreamArg = create(resolved);
        }
        raw = new Class.Api(fqcn, genAnn.concrete(), readStreamArg, writeStreamArg, module, proxyGen);
      } else {
        raw = new Class(kind, fqcn, module, proxyGen);
      }
      List<? extends TypeMirror> typeArgs = type.getTypeArguments();
      if (typeArgs.size() > 0) {
        List<TypeInfo> typeArguments;
        typeArguments = new ArrayList<>(typeArgs.size());
        for (TypeMirror typeArg : typeArgs) {
          TypeInfo typeArgDesc = create(typeArg);
          // Need to check it is an interface type
          typeArguments.add(typeArgDesc);
        }
        return new Parameterized(raw, typeArguments);
      } else {
        return raw;
      }
    }

    public Variable create(TypeVariable type) {
      TypeParameterElement elt = (TypeParameterElement) type.asElement();
      TypeParamInfo param = TypeParamInfo.create(elt);
      return new Variable(param, type.toString());
    }
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
    public String format(boolean qualified) {
      return "?";
    }

    @Override
    public ClassKind getKind() {
      return ClassKind.OBJECT;
    }
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
    public ClassKind getKind() {
      return ClassKind.PRIMITIVE;
    }

    @Override
    public String format(boolean qualified) {
      return name;
    }
  }

  public static class Variable extends TypeInfo {

    final String name;
    final TypeParamInfo param;

    public Variable(TypeParamInfo param, String name) {
      this.param = param;
      this.name = name;
    }

    public TypeParamInfo getParam() {
      return param;
    }

    @Override
    public boolean equals(Object obj) {
      if (obj instanceof Variable) {
        Variable that = (Variable) obj;
        return param.equals(that.param);
      } else {
        return false;
      }
    }

    @Override
    public TypeInfo getErased() {
      return new Class(ClassKind.OBJECT, java.lang.Object.class.getName(), null, false);
    }

    @Override
    public String toString() {
      return name;
    }

    @Override
    public String format(boolean qualified) {
      return name;
    }

    @Override
    public ClassKind getKind() {
      return ClassKind.OBJECT;
    }

  }

  public static class Parameterized extends TypeInfo {

    final Class raw;
    final List<TypeInfo> args;

    public Parameterized(Class raw, List<TypeInfo> args) {
      this.raw = raw;
      this.args = args;
    }

    @Override
    public TypeInfo getErased() {
      return new Parameterized(raw, args.stream().map(TypeInfo::getErased).collect(Collectors.toList()));
    }

    public Class getRaw() {
      return raw;
    }

    /**
     * @return the type arguments
     */
    public List<TypeInfo> getArgs() {
      return args;
    }

    @Override
    public ClassKind getKind() {
      return raw.getKind();
    }

    @Override
    public void collectImports(Collection<TypeInfo.Class> imports) {
      raw.collectImports(imports);
      args.stream().forEach(a -> a.collectImports(imports));
    }

    @Override
    public boolean equals(Object obj) {
      if (obj instanceof Parameterized) {
        Parameterized that = (Parameterized) obj;
        return raw.equals(that.raw) && args.equals(that.args);
      }
      return false;
    }

    @Override
    public String format(boolean qualified) {
      StringBuilder buf = new StringBuilder(raw.format(qualified)).append('<');
      for (int i = 0;i < args.size();i++) {
        TypeInfo typeArgument = args.get(i);
        if (i > 0) {
          buf.append(',');
        }
        buf.append(typeArgument.format(qualified));
      }
      buf.append('>');
      return buf.toString();
    }

    @Override
    public TypeInfo renamePackage(String oldPackageName, String newPackageName) {
      return new Parameterized(
          raw.renamePackage(oldPackageName, newPackageName),
          args.stream().map(typeArgument -> typeArgument.renamePackage(oldPackageName, newPackageName)).
              collect(Collectors.toList()));
    }
  }

  public static class Class extends TypeInfo {

    final ClassKind kind;
    final String fqcn;
    final String simpleName;
    final String packageName;
    final ModuleInfo module;
    final boolean proxyGen;

    public Class(ClassKind kind, String fqcn, ModuleInfo module, boolean proxyGen) {
      this.kind = kind;
      this.fqcn = fqcn;
      this.simpleName = Helper.getSimpleName(fqcn);
      this.packageName = Helper.getPackageName(fqcn);
      this.module = module;
      this.proxyGen = proxyGen;
    }

    /**
     * @return the optional module name only present for {@link io.vertx.codegen.annotations.VertxGen} annotated types.
     */
    public String getModuleName() {
      return module != null ? module.getName() : null;
    }

    public ClassKind getKind() {
      return kind;
    }

    public String getPackageName() {
      return packageName;
    }

    public boolean isProxyGen() {
      return proxyGen;
    }

    @Override
    public Class getRaw() {
      return this;
    }

    @Override
    public void collectImports(Collection<TypeInfo.Class> imports) {
      imports.add(this);
    }

    @Override
    public TypeInfo.Class renamePackage(String oldPackageName, String newPackageName) {
      return packageName.startsWith(oldPackageName) ?
          new Class(kind, newPackageName + fqcn.substring(oldPackageName.length()), null, proxyGen) :
          this;
    }

    @Override
    public boolean equals(Object obj) {
      if (obj instanceof Class) {
        return fqcn.equals(((Class) obj).fqcn);
      }
      return false;
    }

    @Override
    public String format(boolean qualified) {
      return qualified ? fqcn : simpleName;
    }

    /**
     * A special subclass for {@link io.vertx.codegen.ClassKind#API} kinds.
     */
    public static class Api extends Class {

      final boolean concrete;
      final TypeInfo readStreamArg;
      final TypeInfo writeStreamArg;

      public Api(String fqcn, boolean concrete, TypeInfo readStreamArg, TypeInfo writeStreamArg, ModuleInfo module,
                 boolean proxyGen) {
        super(ClassKind.API, fqcn, module, proxyGen);

        this.concrete = concrete;
        this.readStreamArg = readStreamArg;
        this.writeStreamArg = writeStreamArg;

      }

      @Override
      public Class renamePackage(String oldPackageName, String newPackageName) {
        return packageName.startsWith(oldPackageName) ?
            new Api(newPackageName + fqcn.substring(oldPackageName.length()), concrete, readStreamArg, writeStreamArg, module, proxyGen) :
            this;
      }

      public boolean isConcrete() {
        return concrete;
      }

      public boolean isAbstract() {
        return !concrete;
      }

      public TypeInfo getReadStreamArg() {
        return readStreamArg;
      }

      public boolean isReadStream() {
        return readStreamArg != null;
      }

      public TypeInfo getWriteStreamArg() {
        return writeStreamArg;
      }

      public boolean isWriteStream() {
        return writeStreamArg != null;
      }
    }
  }

  public static class Void extends TypeInfo {
    public static TypeInfo INSTANCE = new Void() {};
    private Void() {}
    @Override
    public boolean equals(Object obj) {
      return obj instanceof Void;
    }
    @Override
    public String format(boolean qualified) {
      return "void";
    }
  }

  public abstract boolean equals(Object obj);

  public int hashCode() {
    return toString().hashCode();
  }

  /**
   * Collect the import fqcn needed by this type.
   *
   * @param imports the imports
   */
  public void collectImports(Collection<TypeInfo.Class> imports) {
  }

  /**
   * @return the erased type of this type
   */
  public TypeInfo getErased() {
    return this;
  }

  /**
   * @return the corresponding raw type or null
   */
  public TypeInfo.Class getRaw() {
    return null;
  }

  /**
   * @return the class kind this type resolves to
   */
  public ClassKind getKind() {
    return ClassKind.OTHER;
  }

  /**
   * @return the declaration suitable for source code represented using qualified names, for instance
   * <code>io.vertx.core.Handler&lt;io.vertx.core.buffer.Buffer&gt;</code>
   */
  public String getName() {
    return format(true);
  }

  public TypeInfo renamePackage(String oldPackageName, String newPackageName) {
    return this;
  }

  /**
   * @return the declaration suitable for source code represented using unqualified names, for instance
   * <code>Handler&lt;Buffer&gt;</code>
   */
  public String getSimpleName() { return format(false); }

  /**
   * @return the @{link #getName} value of this type
   */
  public String toString() {
    return getName();
  }

  /**
   * Renders the type name.
   *
   * @param qualified true when class fqcn should be used, otherwise simple names will be used
   * @return the representation of the type
   */
  abstract String format(boolean qualified);


}
