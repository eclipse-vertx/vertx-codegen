package io.vertx.codegen;

import io.vertx.codegen.annotations.ModuleGen;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.streams.ReadStream;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVariable;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
      java.lang.Class<?> classType = (java.lang.Class<?>) type;
      if (classType.isPrimitive()) {
        return Primitive.PRIMITIVES.get(classType.getName());
      } else {
        Package pkg = classType.getPackage();
        ModuleInfo module = null;
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        Thread.currentThread().setContextClassLoader(classType.getClassLoader());
        try {
          while (pkg != null) {
            ModuleGen annotation = pkg.getAnnotation(ModuleGen.class);
            if (annotation != null) {
              module = new ModuleInfo(pkg.getName(), annotation.name(), annotation.groupPackage());
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
        if (classType.isEnum()) {
          return new Class.Enum(
              fqcn,
              classType.getDeclaredAnnotation(VertxGen.class) != null,
              Stream.of(classType.getEnumConstants()).map(Object::toString).collect(Collectors.toList()),
              module,
              false);
        } else {
          ClassKind kind = Helper.getKind(classType::getAnnotation, fqcn);
          List<TypeParamInfo.Class> typeParams = new ArrayList<>();
          int index = 0;
          for (java.lang.reflect.TypeVariable<? extends java.lang.Class<?>> var : classType.getTypeParameters()) {
            typeParams.add(new TypeParamInfo.Class(classType.getName(), index++, var.getName(), Collections.<Variance>emptySet()));
          }
          if (kind == ClassKind.API) {
            java.lang.reflect.TypeVariable<java.lang.Class<ReadStream>> classTypeVariable = ReadStream.class.getTypeParameters()[0];
            Type readStreamArg = Helper.resolveTypeParameter(type, classTypeVariable);
            return new Class.Api(fqcn, true, typeParams, readStreamArg != null ? create(readStreamArg) : null, null, null, module, false);
          } else {
            return new Class(kind, fqcn, module, false, typeParams);
          }
        }
      }
    } else if (type instanceof ParameterizedType) {
      ParameterizedType parameterizedType = (ParameterizedType) type;
      List<TypeInfo> args = Arrays.asList(parameterizedType.getActualTypeArguments()).
          stream().
          map(TypeInfo::create).
          collect(Collectors.toList());
      Type raw = parameterizedType.getRawType();
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
          return Primitive.PRIMITIVES.get(type.toString());
        case TYPEVAR:
          return create((TypeVariable) type);
        default:
          throw new IllegalArgumentException("Illegal type " + type + " of kind " + type.getKind());
      }
    }

    public TypeInfo create(DeclaredType type) {
      Element elt = type.asElement();
      PackageElement pkgElt = elementUtils.getPackageOf(elt);
      ModuleInfo module = ModuleInfo.resolve(elementUtils, pkgElt);
      String fqcn = typeUtils.erasure(type).toString();
      boolean proxyGen = elt.getAnnotation(ProxyGen.class) != null;
      if (elt.getKind() == ElementKind.ENUM) {
        ArrayList<String> values = new ArrayList<>();
        for (Element enclosedElt : elt.getEnclosedElements()) {
          if (enclosedElt.getKind() == ElementKind.ENUM_CONSTANT) {
            values.add(enclosedElt.getSimpleName().toString());
          }
        }
        boolean gen = elt.getAnnotation(VertxGen.class) != null;
        return new Class.Enum(fqcn, gen, values, module, proxyGen);
      } else {
        ClassKind kind = Helper.getKind(annotationType -> elt.getAnnotation(annotationType), fqcn);
        Class raw;
        if (kind == ClassKind.BOXED_PRIMITIVE) {
          raw = Class.PRIMITIVES.get(fqcn);
        } else {
          List<TypeParamInfo.Class> typeParams = createTypeParams(type);
          if (kind == ClassKind.API) {
            VertxGen genAnn = elt.getAnnotation(VertxGen.class);
            TypeInfo[] args = Stream.of(
                ClassModel.VERTX_READ_STREAM,
                ClassModel.VERTX_WRITE_STREAM,
                ClassModel.VERTX_HANDLER
            ).map(s -> {
              TypeElement parameterizedElt = elementUtils.getTypeElement(s);
              TypeMirror parameterizedType = parameterizedElt.asType();
              TypeMirror rawType = typeUtils.erasure(parameterizedType);
              if (typeUtils.isSubtype(type, rawType)) {
                TypeMirror resolved = Helper.resolveTypeParameter(typeUtils, type, parameterizedElt.getTypeParameters().get(0));
                return create(resolved);
              }
              return null;
            }).toArray(TypeInfo[]::new);
            raw = new Class.Api(fqcn, genAnn.concrete(), typeParams, args[0], args[1], args[2], module, proxyGen);
          } else {
            raw = new Class(kind, fqcn, module, proxyGen, typeParams);
          }
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
    }

    public Variable create(TypeVariable type) {
      TypeParameterElement elt = (TypeParameterElement) type.asElement();
      TypeParamInfo param = TypeParamInfo.create(elt);
      return new Variable(param, type.toString());
    }

    private List<TypeParamInfo.Class> createTypeParams(DeclaredType type) {
      List<TypeParamInfo.Class> typeParams = new ArrayList<>();
      TypeElement elt = (TypeElement) type.asElement();
      List<? extends TypeParameterElement> typeParamElts = elt.getTypeParameters();
      for (int index = 0;index < typeParamElts.size();index++) {
        TypeParameterElement typeParamElt = typeParamElts.get(index);
        Set<Variance> siteVariance = EnumSet.noneOf(Variance.class);
        for (Variance variance : Variance.values()) {
          if (Helper.resolveSiteVariance(typeParamElt, variance)) {
            siteVariance.add(variance);
          }
        }
        typeParams.add(new TypeParamInfo.Class(elt.getQualifiedName().toString(), index, typeParamElt.getSimpleName().toString(), siteVariance));
      }
      return typeParams;
    }
  }

  public static class Primitive extends TypeInfo {

    private static final HashMap<String, Primitive> PRIMITIVES = new HashMap<>();

    static {
      java.lang.Class<?>[] primitives = {boolean.class,byte.class,short.class,int.class,long.class,
          float.class,double.class,char.class};
      java.lang.Class<?>[] boxes = {Boolean.class,Byte.class,Short.class,Integer.class,Long.class,
          Float.class,Double.class,Character.class};
      for (int i = 0;i < primitives.length;i++) {
        java.lang.Class<?> primitive = primitives[i];
        String name = primitive.getName();
        PRIMITIVES.put(name, new Primitive(primitive.getName(), boxes[i].getName()));
      }
    }

    final String name;
    final String boxedName;

    private Primitive(String name, String boxedName) {
      this.name = name;
      this.boxedName = boxedName;
    }

    @Override
    public boolean equals(Object obj) {
      if (obj instanceof Primitive) {
        return name.equals(((Primitive) obj).name);
      }
      return false;
    }

    /**
     * @return the boxed equivalent
     */
    public TypeInfo.Class getBoxed() {
      return Class.PRIMITIVES.get(boxedName);
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
      return new Class(ClassKind.OBJECT, java.lang.Object.class.getName(), null, false, Collections.emptyList());
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

    /**
     * @param index the type argument index
     * @return a specific type argument
     */
    public TypeInfo getArg(int index) {
      return args.get(index);
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
    public String translateName(String lang) {
      StringBuilder buf = new StringBuilder(raw.translateName(lang)).append('<');
      for (int i = 0;i < args.size();i++) {
        TypeInfo typeArgument = args.get(i);
        if (i > 0) {
          buf.append(',');
        }
        buf.append(typeArgument.translateName(lang));
      }
      buf.append('>');
      return buf.toString();
    }
  }

  public static class Class extends TypeInfo {

    private static final HashMap<String, Class> PRIMITIVES = new HashMap<>();

    static {
      java.lang.Class<?>[] boxes = {Boolean.class,Byte.class,Short.class,Integer.class,Long.class,
          Float.class,Double.class,Character.class};
      for (java.lang.Class<?> boxe : boxes) {
        String name = boxe.getName();
        PRIMITIVES.put(name, new Class(ClassKind.BOXED_PRIMITIVE, name, null, false, Collections.emptyList()));
      }
    }

    final ClassKind kind;
    final String name;
    final String simpleName;
    final String packageName;
    final ModuleInfo module;
    final boolean proxyGen;
    final List<TypeParamInfo.Class> params;

    public Class(ClassKind kind, String name, ModuleInfo module, boolean proxyGen, List<TypeParamInfo.Class> params) {
      this.kind = kind;
      this.name = name;
      this.simpleName = Helper.getSimpleName(name);
      this.packageName = Helper.getPackageName(name);
      this.module = module;
      this.proxyGen = proxyGen;
      this.params = params;
    }

    public List<TypeParamInfo.Class> getParams() {
      return params;
    }

    /**
     * @return the optional module name only present for {@link io.vertx.codegen.annotations.VertxGen} annotated types.
     */
    public String getModuleName() {
      return module != null ? module.getName() : null;
    }

    /**
     * @return the optional module name only present for {@link io.vertx.codegen.annotations.VertxGen} annotated types.
     */
    public ModuleInfo getModule() {
      return module;
    }

    public ClassKind getKind() {
      return kind;
    }

    public String getPackageName() {
      return packageName;
    }

    public String getSimpleName(Case _case) {
      return _case.format(Case.CAMEL.parse(simpleName));
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
    public boolean equals(Object obj) {
      if (obj instanceof Class) {
        return name.equals(((Class) obj).name);
      }
      return false;
    }

    @Override
    public String format(boolean qualified) {
      return qualified ? name : simpleName;
    }

    public String translatePackageName(String id) {
      return module == null ? packageName : module.translateQualifiedName(packageName, id);
    }

    public String translateName(String lang) {
      return  module == null ? name : module.translateQualifiedName(name, lang);
    }

    public static class Enum extends Class {

      final List<String> values;
      final boolean gen;

      public Enum(String fqcn, boolean gen, List<String> values, ModuleInfo module, boolean proxyGen) {
        super(ClassKind.ENUM, fqcn, module, proxyGen, Collections.emptyList());

        this.gen = gen;
        this.values = values;
      }

      /**
       * @return true if the type is a generated type
       */
      public boolean isGen() {
        return gen;
      }

      /**
       * @return the enum possible values
       */
      public List<String> getValues() {
        return values;
      }
    }

    /**
     * A special subclass for {@link io.vertx.codegen.ClassKind#API} kinds.
     */
    public static class Api extends Class {

      final boolean concrete;
      final TypeInfo readStreamArg;
      final TypeInfo writeStreamArg;
      final TypeInfo handlerArg;

      public Api(
          String fqcn,
          boolean concrete,
          List<TypeParamInfo.Class> params,
          TypeInfo readStreamArg,
          TypeInfo writeStreamArg,
          TypeInfo handlerArg,
          ModuleInfo module,
          boolean proxyGen) {
        super(ClassKind.API, fqcn, module, proxyGen, params);
        this.concrete = concrete;
        this.readStreamArg = readStreamArg;
        this.writeStreamArg = writeStreamArg;
        this.handlerArg = handlerArg;
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

      public TypeInfo getHandlerArg() {
        return handlerArg;
      }

      public boolean isHandler() {
        return handlerArg != null;
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

  /**
   * Translate the current type name based on the module group package name and the specified
   * {@code lang} parameter. This has effect only for {@link io.vertx.codegen.TypeInfo.Class.Api} or
   * {@link io.vertx.codegen.TypeInfo.Parameterized} types.
   *
   * @param lang the target language, for instance {@literal groovy}
   * @return the translated name
   */
  public String translateName(String lang) {
    return getName();
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
