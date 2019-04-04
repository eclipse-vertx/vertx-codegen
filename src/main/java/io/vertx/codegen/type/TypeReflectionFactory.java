package io.vertx.codegen.type;

import io.vertx.codegen.Helper;
import io.vertx.codegen.ModuleInfo;
import io.vertx.codegen.TypeParamInfo;
import io.vertx.codegen.annotations.DataObject;
import io.vertx.codegen.annotations.ModuleGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.json.JsonObject;
import io.vertx.core.streams.ReadStream;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Type info factory based on <i>java.lang.reflect</i> and {@link Type types}
 *
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class TypeReflectionFactory {

  public static TypeInfo create(Type type) {
    if (type == void.class) {
      return VoidTypeInfo.INSTANCE;
    } else if (type instanceof Class) {
      String fqcn = type.getTypeName();
      Class<?> classType = (Class<?>) type;
      if (classType.isPrimitive()) {
        return PrimitiveTypeInfo.PRIMITIVES.get(classType.getName());
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
          return new EnumTypeInfo(
            fqcn,
            classType.getDeclaredAnnotation(VertxGen.class) != null,
            Stream.of(classType.getEnumConstants()).map(Object::toString).collect(Collectors.toList()),
            module,
            false
          );
        } else {
          ClassKind kind = ClassKind.getKind(fqcn, classType.getAnnotation(DataObject.class) != null, classType.getAnnotation(VertxGen.class) != null);
          List<TypeParamInfo.Class> typeParams = new ArrayList<>();
          int index = 0;
          for (java.lang.reflect.TypeVariable<? extends Class<?>> var : classType.getTypeParameters()) {
            typeParams.add(new TypeParamInfo.Class(classType.getName(), index++, var.getName()));
          }
          if (kind == ClassKind.API) {
            java.lang.reflect.TypeVariable<Class<ReadStream>> classTypeVariable = ReadStream.class.getTypeParameters()[0];
            Type readStreamArg = Helper.resolveTypeParameter(type, classTypeVariable);
            return new ApiTypeInfo(fqcn, true, typeParams, readStreamArg != null ? create(readStreamArg) : null, null, null, module, false, false);
          } else if (kind == ClassKind.DATA_OBJECT) {
            boolean encodable = isDataObjectAnnotatedEncodable(classType);
            boolean decodable = isDataObjectAnnotatedDecodable(classType);
            return new DataObjectTypeInfo(
              fqcn,
              module,
              false,
              typeParams,
              (encodable) ? classType.getSimpleName() + "Converter" : null,
              null,
              (encodable) ? pkg.getName() : null,
              (decodable) ? classType.getSimpleName() + "Converter" : null,
              null,
              (decodable) ? pkg.getName() : null,
              create(JsonObject.class)
            );
          } else {
            return new ClassTypeInfo(kind, fqcn, module, false, typeParams);
          }
        }
      }
    } else if (type instanceof ParameterizedType) {
      ParameterizedType parameterizedType = (ParameterizedType) type;
      List<TypeInfo> args = Arrays.asList(parameterizedType.getActualTypeArguments()).
        stream().
        map(TypeReflectionFactory::create).
        collect(Collectors.toList());
      Type raw = parameterizedType.getRawType();
      return new ParameterizedTypeInfo((ClassTypeInfo) create(raw), false, args);
    } else if (type instanceof java.lang.reflect.TypeVariable) {
      java.lang.reflect.TypeVariable typeVar = (java.lang.reflect.TypeVariable) type;
      TypeParamInfo param = TypeParamInfo.create(typeVar);
      return new TypeVariableInfo(param, false, ((java.lang.reflect.TypeVariable) type).getName());
    } else {
      throw new IllegalArgumentException("Unsupported type " + type);
    }
  }

  private static boolean isDataObjectAnnotatedEncodable(Class<?> type) {
    try {
      Method m = type.getMethod("toJson");
      return
        type.getAnnotation(DataObject.class).generateConverter() ||
          (Modifier.isPublic(m.getModifiers()) && m.getReturnType().equals(JsonObject.class));
    } catch (NoSuchMethodException e) {
      return false;
    }
  }

  private static boolean isDataObjectAnnotatedDecodable(Class<?> type) {
    boolean isConcreteAndHasJsonConstructor = false;
    try {
      isConcreteAndHasJsonConstructor =
        !Modifier.isAbstract(type.getModifiers()) &&
        !type.isInterface() &&
        Modifier.isPublic(type.getConstructor(JsonObject.class).getModifiers());
    } catch (NoSuchMethodException e) { }
    boolean isNotConcreteAndHasDecodeStaticMethod = false;
    try {
      Method m = type.getMethod("decode", JsonObject.class);
      isNotConcreteAndHasDecodeStaticMethod =
        (type.isInterface() || Modifier.isAbstract(type.getModifiers())) &&
          Modifier.isStatic(m.getModifiers()) &&
          Modifier.isPublic(m.getModifiers()) &&
          m.getReturnType().equals(type);
    } catch (NoSuchMethodException e) { }
    boolean hasEmptyConstructorAndGenerateConverterAndConcrete = false;
    try {
      hasEmptyConstructorAndGenerateConverterAndConcrete =
        type.getAnnotation(DataObject.class).generateConverter() &&
        type.getConstructor() != null &&
        !type.isInterface() && !Modifier.isAbstract(type.getModifiers());
    } catch (NoSuchMethodException e) { }
    return isConcreteAndHasJsonConstructor || isNotConcreteAndHasDecodeStaticMethod || hasEmptyConstructorAndGenerateConverterAndConcrete;
  }
}
