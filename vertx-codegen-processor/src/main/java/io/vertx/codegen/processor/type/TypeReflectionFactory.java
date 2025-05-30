package io.vertx.codegen.processor.type;

import io.vertx.codegen.annotations.GenIgnore;
import io.vertx.codegen.processor.Helper;
import io.vertx.codegen.processor.MapperKind;
import io.vertx.codegen.processor.ModuleInfo;
import io.vertx.codegen.processor.TypeParamInfo;
import io.vertx.codegen.annotations.DataObject;
import io.vertx.codegen.annotations.ModuleGen;
import io.vertx.codegen.annotations.VertxGen;

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
            false,
            null
          );
        } else {
          ClassKind kind = ClassKind.getKind(fqcn, classType.getAnnotation(VertxGen.class) != null);
          List<TypeParamInfo.Class> typeParams = new ArrayList<>();
          int index = 0;
          for (java.lang.reflect.TypeVariable<? extends Class<?>> var : classType.getTypeParameters()) {
            typeParams.add(new TypeParamInfo.Class(classType.getName(), index++, var.getName()));
          }
          if (kind == ClassKind.API) {
            Class<?> handlerClass;
            try {
              handlerClass = classType.getClassLoader().loadClass("io.vertx.core.Handler");
            } catch (ClassNotFoundException e) {
              throw new RuntimeException(e);
            }
            TypeVariable<? extends Class<?>> classTypeVariable = handlerClass.getTypeParameters()[0];
            Type handlerArg = Helper.resolveTypeParameter(type, classTypeVariable);
            return new ApiTypeInfo(fqcn, true, typeParams, handlerArg != null ? create(handlerArg) : null, module, false, false, null);
          } else {
            DataObjectInfo dataObject;
            boolean permitted;
            if (classType.getDeclaredAnnotation(DataObject.class) != null) {
              MapperInfo serializer = getDataObjectSerializer(classType);
              MapperInfo deserializer = getDataObjectDeserializer(classType);
              dataObject = new DataObjectInfo(true, serializer, deserializer);
              permitted = false;
            } else {
              dataObject = null;
              if (classType.getDeclaredAnnotation(GenIgnore.class) != null) {
                permitted = true;
              } else {
                permitted = false;
              }
            }
            return new ClassTypeInfo(kind, fqcn, module, false, typeParams, permitted, dataObject);
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
    } else if (type instanceof  WildcardType) {
      WildcardType wildcardType = (WildcardType) type;
      if (wildcardType.getUpperBounds().length > 0) {
        return new WildcardTypeInfo(create(wildcardType.getUpperBounds()[0]), null);
      } else if (wildcardType.getLowerBounds().length > 0) {
        return new WildcardTypeInfo(null, create(wildcardType.getLowerBounds()[0]));
      } else {
        return new WildcardTypeInfo(null, null);
      }
    } else {
      throw new IllegalArgumentException("Unsupported type " + type);
    }
  }

  private static MapperInfo getDataObjectSerializer(Class<?> type) {
    try {
      Method m = type.getMethod("toJson");
      if (Modifier.isPublic(m.getModifiers()) && m.getReturnType().getName().equals("io.vertx.core.json.JsonObject")) {
        MapperInfo serializer = new MapperInfo();
        serializer.setQualifiedName(type.getName());
        serializer.setKind(MapperKind.SELF);
        return serializer;
      }
    } catch (NoSuchMethodException ignore) {
    }
    return null;
  }

  private static MapperInfo getDataObjectDeserializer(Class<?> type) {
    if (
      !Modifier.isAbstract(type.getModifiers()) &&
        !type.isInterface()) {
      for (Constructor<?> ct : type.getConstructors()) {
        if (ct.getParameterTypes().length == 1 &&
          ct.getParameterTypes()[0].getName().equals("io.vertx.core.json.JsonObject") &&
          Modifier.isPublic(ct.getModifiers())) {
          MapperInfo deserializer = new MapperInfo();
          deserializer.setQualifiedName(type.getName());
          deserializer.setKind(MapperKind.SELF);
          return deserializer;
        }
      }
    }
    return null;
  }
}
