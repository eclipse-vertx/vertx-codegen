package io.vertx.codegen.processor;

import io.vertx.codegen.processor.type.*;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Validates codegen types.
 */
class TypeValidator {

  static boolean isAnyJavaType(MethodInfo method) {
    for (ParamInfo param : method.getParams()) {
      if (isAnyJavaType(param.getType())) {
        return true;
      }
    }
    return isAnyJavaType(method.getReturnType());
  }

  static boolean isAnyJavaType(TypeInfo type) {
    switch (type.getKind()) {
      case API:
      case BOXED_PRIMITIVE:
      case ENUM:
      case PRIMITIVE:
      case JSON_OBJECT:
      case JSON_ARRAY:
      case VOID:
      case OBJECT:
      case CLASS_TYPE:
      case STRING:
      case THROWABLE:
        return false;
      case MAP:
        ParameterizedTypeInfo mapType = (ParameterizedTypeInfo) type;
        return isAnyJavaType(mapType.getArg(1));
      case LIST:
        ParameterizedTypeInfo listType = (ParameterizedTypeInfo) type;
        return isAnyJavaType(listType.getArg(0));
      case SET:
        ParameterizedTypeInfo setType = (ParameterizedTypeInfo) type;
        return isAnyJavaType(setType.getArg(0));
      case FUTURE:
        ParameterizedTypeInfo futureType = (ParameterizedTypeInfo) type;
        return isAnyJavaType(futureType.getArg(0));
      case SUPPLIER:
        ParameterizedTypeInfo supplierType = (ParameterizedTypeInfo) type;
        return isAnyJavaType(supplierType.getArg(0));
      case HANDLER:
        ParameterizedTypeInfo handlerType = (ParameterizedTypeInfo) type;
        return isAnyJavaType(handlerType.getArg(0));
      case FUNCTION:
        ParameterizedTypeInfo functionType = (ParameterizedTypeInfo) type;
        return isAnyJavaType(functionType.getArg(0)) || isAnyJavaType(functionType.getArg(1));
      case OTHER:
        if (type instanceof ParameterizedTypeInfo) {
          return isAnyJavaType(type.getRaw());
        } else {
          ClassTypeInfo otherType = (ClassTypeInfo) type;
          return otherType.isPermitted();
        }
      default:
        throw new UnsupportedOperationException("" + type.getKind());
    }
  }

  static void validateParamType(ExecutableElement elem, TypeInfo typeInfo) {
    if (isValidNonCallableType(elem, typeInfo, true, false, true)) {
      return;
    }
    if (isValidClassTypeParam(elem, typeInfo)) {
      return;
    }
    if (isValidHandlerType(elem, typeInfo)) {
      return;
    }
    if (isValidFunctionType(elem, typeInfo)) {
      return;
    }
    if (isValidSupplierType(elem, typeInfo)) {
      return;
    }
    throw new GenException(elem, "type " + typeInfo + " is not legal for use for a parameter in code generation");
  }

  static void validateReturnType(ExecutableElement elem, TypeInfo type) {
    if (type.isVoid()) {
      return;
    }
    if (isValidNonCallableType(elem, type, false, true, true)) {
      return;
    }
    if (isValidHandlerType(elem, type)) {
      return;
    }
    throw new GenException(elem, "type " + type + " is not legal for use for a return type in code generation");
  }

  static void validateConstantType(Types typeUtils, VariableElement elem, TypeInfo type, TypeMirror typeMirror) {
    if (isValidNonCallableType(elem, type, false, true, true)) {
      return;
    }
    // Workaround for Kotlin companion objects.
    // https://github.com/vert-x3/vertx-lang-kotlin/issues/93
    if (isValidKotlinCompanionObject(typeUtils, elem)) {
      return;
    }
    throw new GenException(elem, "type " + type + " is not legal for use for a constant type in code generation");
  }

  private static boolean isValidNonCallableType(Element elem, TypeInfo type, boolean isParam, boolean isReturn, boolean allowParameterized) {
    if (type.isDataObjectHolder()) {
      return true;
    }
    if (type.getKind() == ClassKind.VOID) {
      return true;
    }
    if (type.getKind().basic) {
      return true;
    }
    if (type.getKind().json) {
      return true;
    }
    if (isValidEnum(type)) {
      return true;
    }
    if (type.getKind() == ClassKind.THROWABLE) {
      return true;
    }
    if (type.isVariable()) {
      return true;
    }
    if (type.getKind() == ClassKind.OBJECT) {
      return true;
    }
    if (isValidFutureType(elem, type)) {
      return true;
    }
    if (isValidVertxGenInterface(elem, type, allowParameterized)) {
      return true;
    }
    if (isValidOtherType(type)) {
      return true;
    }
    if (allowParameterized && isValidContainer(elem, type)) {
      return true;
    }
    return false;
  }

  /**
   * @return check whether {@code constantElt} type is an inner type of the same class of the constant declaration
   */
  private static boolean isValidKotlinCompanionObject(Types typeUtils, VariableElement constantElt) {
    TypeMirror contantType = constantElt.asType();
    Element constantTypeContainerElt = typeUtils.asElement(contantType).getEnclosingElement();
    if (constantTypeContainerElt.getKind() == ElementKind.INTERFACE) {
      TypeMirror constantTypeContainerType = constantTypeContainerElt.asType();
      TypeMirror constantContainerType = constantElt.getEnclosingElement().asType();
      return typeUtils.isSameType(constantTypeContainerType, constantContainerType);
    }
    return false;
  }

  private static boolean isValidEnum(TypeInfo info) {
    return info.getKind() == ClassKind.ENUM;
  }

  private static boolean isValidClassTypeParam(ExecutableElement elem, TypeInfo type) {
    if (type.getKind() == ClassKind.CLASS_TYPE && type.isParameterized()) {
      ParameterizedTypeInfo parameterized = (ParameterizedTypeInfo) type;
      TypeInfo arg = parameterized.getArg(0);
      if (arg.isVariable()) {
        TypeVariableInfo variable = (TypeVariableInfo) arg;
        for (TypeParameterElement typeParamElt : elem.getTypeParameters()) {
          if (typeParamElt.getSimpleName().toString().equals(variable.getName())) {
            return true;
          }
        }
      }
    }
    return false;
  }

  private static boolean isValidContainer(Element elem, TypeInfo type) {
    TypeInfo argument = null;
    if (rawTypeIs(type, List.class, Set.class, Map.class)) {
      ParameterizedTypeInfo parameterizedType = (ParameterizedTypeInfo) type;
      if (type.getKind() != ClassKind.MAP) {
        argument = parameterizedType.getArgs().get(0);
      } else if (parameterizedType.getArgs().get(0).getKind() == ClassKind.STRING) { // Only allow Map's with String's for keys
        argument= parameterizedType.getArgs().get(1);
      }
    }
    return argument != null && isValidContainerComponent(elem, argument);
  }

  private static boolean isValidContainerComponent(Element elem, TypeInfo arg) {
    return isValidNonCallableType(elem, arg, true, true, false);
  }

  private static boolean isValidVertxGenTypeArgument(Element elem, TypeInfo arg) {
    return isValidNonCallableType(elem, arg, false, false, true);
  }

  private static boolean isValidOtherType(TypeInfo type) {
    if (type instanceof ClassTypeInfo) {
      return type.getKind() == ClassKind.OTHER && ((ClassTypeInfo)type).isPermitted();
    } else if (type instanceof ParameterizedTypeInfo) {
      return isValidOtherType(type.getRaw());
    } else {
      return false;
    }
  }

  private static boolean isValidVertxGenInterface(Element elem, TypeInfo type, boolean allowParameterized) {
    if (type.getKind() == ClassKind.API) {
      if (type.isParameterized()) {
        ParameterizedTypeInfo parameterized = (ParameterizedTypeInfo) type;
        return allowParameterized &&
          parameterized
            .getArgs()
            .stream()
            .noneMatch(arg -> !isValidVertxGenTypeArgument(elem, arg) || arg.isNullable());
      } else {
        return true;
      }
    }
    return false;
  }

  private static boolean isValidFutureType(Element elem, TypeInfo type) {
    if (type.getKind() == ClassKind.FUTURE) {
      ParameterizedTypeInfo parameterized = (ParameterizedTypeInfo) type;
      return parameterized
          .getArgs()
          .stream()
          .allMatch(arg -> isValidCallbackValueType(elem, arg));
    }
    return false;
  }

  private static boolean isValidFunctionType(Element elem, TypeInfo typeInfo) {
    if (typeInfo.getErased().getKind() == ClassKind.FUNCTION) {
      TypeInfo paramType = ((ParameterizedTypeInfo) typeInfo).getArgs().get(0);
      if (isValidCallbackValueType(elem, paramType)) {
        TypeInfo returnType = ((ParameterizedTypeInfo) typeInfo).getArgs().get(1);
        return isValidNonCallableType(elem, returnType, true, false, true);
      }
    }
    return false;
  }

  private static boolean isValidSupplierType(Element elem, TypeInfo typeInfo) {
    if (typeInfo.getErased().getKind() == ClassKind.SUPPLIER) {
      TypeInfo returnType = ((ParameterizedTypeInfo) typeInfo).getArgs().get(0);
      return isValidNonCallableType(elem, returnType, true, false, true);
    }
    return false;
  }

  private static boolean isValidHandlerType(Element elem, TypeInfo type) {
    if (type.getErased().getKind() == ClassKind.HANDLER) {
      TypeInfo eventType = ((ParameterizedTypeInfo) type).getArgs().get(0);
      return isValidCallbackValueType(elem, eventType);
    }
    return false;
  }

  private static boolean isValidCallbackValueType(Element elem, TypeInfo type) {
    return isValidNonCallableType(elem, type, false, true, true);
  }

  private static boolean rawTypeIs(TypeInfo type, Class<?>... classes) {
    if (type instanceof ParameterizedTypeInfo) {
      String rawClassName = type.getRaw().getName();
      for (Class<?> c : classes) {
        if (rawClassName.equals(c.getName())) {
          return true;
        }
      }
    }
    return false;
  }
}
