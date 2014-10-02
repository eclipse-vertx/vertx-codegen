package io.vertx.codegen;

/*
 * Copyright 2014 Red Hat, Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Apache License v2.0 which accompanies this distribution.
 *
 * The Eclipse Public License is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * The Apache License v2.0 is available at
 * http://www.opensource.org/licenses/apache2.0.php
 *
 * You may elect to redistribute this code under either of these licenses.
 */

import io.vertx.codegen.annotations.Options;
import io.vertx.codegen.annotations.VertxGen;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVariable;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
public class Helper {

  static final Function<Element, Stream<ExecutableElement>> FILTER_METHOD = element -> {
    if (element.getKind() == ElementKind.METHOD) {
      return Stream.of((ExecutableElement) element);
    } else {
      return Stream.empty();
    }
  };

  public static ClassKind getKind(AnnotationResolver annotations, String fqcn) {
    if (annotations.get(Options.class) != null) {
      return ClassKind.OPTIONS;
    } else if (annotations.get(VertxGen.class) != null) {
      return ClassKind.API;
    } else if (fqcn.equals(ClassModel.VERTX_HANDLER)) {
      return ClassKind.HANDLER;
    } else if (fqcn.equals(ClassModel.VERTX_ASYNC_RESULT)) {
      return ClassKind.ASYNC_RESULT;
    } else if (fqcn.equals(ClassModel.JSON_ARRAY)) {
      return ClassKind.JSON_ARRAY;
    } else if (fqcn.equals(ClassModel.JSON_OBJECT)) {
      return ClassKind.JSON_OBJECT;
    } else if (fqcn.equals(Object.class.getName())) {
      return ClassKind.OBJECT;
    } else if (fqcn.equals(String.class.getName())) {
      return ClassKind.STRING;
    } else if (fqcn.equals(List.class.getName())) {
      return ClassKind.LIST;
    } else if (fqcn.equals(Set.class.getName())) {
      return ClassKind.SET;
    } else if (fqcn.equals(Map.class.getName())) {
      return ClassKind.MAP;
    } else if (fqcn.equals(Throwable.class.getName())) {
      return ClassKind.THROWABLE;
    } else if (fqcn.equals(Void.class.getName())) {
      return ClassKind.VOID;
    } else if (fqcn.equals(Integer.class.getName()) ||
        fqcn.equals(Long.class.getName()) ||
        fqcn.equals(Boolean.class.getName()) ||
        fqcn.equals(Double.class.getName()) ||
        fqcn.equals(Float.class.getName()) ||
        fqcn.equals(Short.class.getName()) ||
        fqcn.equals(Character.class.getName()) ||
        fqcn.equals(Byte.class.getName())) {
      return ClassKind.BOXED_PRIMITIVE;
    } else {
      return ClassKind.OTHER;
    }
  }

  /**
   * Normalize a property name:<br/>
   *
   * <ul>
   *   <li>the first char will always be a lower case</li>
   *   <li>if the first char is an upper case, any following upper case char will be lower cased unless it is followed
   *   by a lower case char</li>
   * </ul>
   *
   * For instance:
   * <ul>
   *   <li>foo -> foo</li>
   *   <li>Foo -> foo</li>
   *   <li>URL -> url</li>
   *   <li>URLFactory -> urlFactory</li>
   * </ul>
   *
   * @param propertyName the property name
   * @return the normalized property name
   */
  public static String normalizePropertyName(String propertyName) {
    if (Character.isUpperCase(propertyName.charAt(0))) {
      StringBuilder buffer = new StringBuilder(propertyName);
      int index = 0;
      while (true) {
        buffer.setCharAt(index, Character.toLowerCase(buffer.charAt(index++)));
        if (index < buffer.length() && Character.isUpperCase(buffer.charAt(index))) {
          if (index + 1 < buffer.length() && Character.isLowerCase(buffer.charAt(index + 1))) {
            break;
          }
        } else {
          break;
        }
      }
      propertyName = buffer.toString();
    }
    return propertyName;
  }

  public static String decapitaliseFirstLetter(String str) {
    if (str.length() == 0) {
      return str;
    } else {
      return str.substring(0, 1).toLowerCase() + str.substring(1);
    }
  }

  public static String convertCamelCaseToUnderscores(String str) {
    return str.replaceAll("([A-Z]+)([A-Z][a-z])", "$1_$2").replaceAll("([a-z\\d])([A-Z])", "$1_$2").toLowerCase();
  }

  public static String getSimpleName(String type) {
    return type.substring(type.lastIndexOf('.') + 1);
  }

  public static String getPackageName(String type) {
    return type.substring(0, type.lastIndexOf('.'));
  }

  public static String getGenericType(String type) {
    return getGenericType(type, 0);
  }

  public static String getGenericType(String type, int index) {
    int pos = type.indexOf("<");
    if (pos >= 0) {
      int lastPos = type.lastIndexOf(">");
      List<String> list = Arrays.asList(type.substring(pos + 1, lastPos).split(","));
      return list.get(index);
    } else {
      return null;
    }
  }

  public static String getNonGenericType(String type) {
    int pos = type.indexOf("<");
    if (pos >= 0) {
      String nonGenericType = type.substring(0, pos);
      return nonGenericType;
    } else {
      return type;
    }
  }

  public static boolean isBasicType(String type) {
    switch (type) {
      case "void":
      case "byte":
      case "short":
      case "int":
      case "long":
      case "float":
      case "double":
      case "boolean":
      case "char":
      case "java.lang.String":
      case "java.lang.Byte":
      case "java.lang.Short":
      case "java.lang.Integer":
      case "java.lang.Long":
      case "java.lang.Float":
      case "java.lang.Double":
      case "java.lang.Boolean":
      case "java.lang.Character":
        return true;
      default:
        return false;
    }
  }

  public static boolean isVertxGenType(String type) {
    try {
      Class<?> clazz = Thread.currentThread().getContextClassLoader().loadClass(type);
      return clazz.getAnnotation(VertxGen.class) != null;
    } catch (ClassNotFoundException e) {
      return false;
    }
  }

  public static String indentString(String str, String indent) {
    StringBuilder sb = new StringBuilder(indent);
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      sb.append(ch);
      if (ch == '\n' && i != str.length() - 1) {
        sb.append(indent);
      }
    }
    return sb.toString();
  }

  public static String ind(int spaces) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < spaces; i++) {
      sb.append(' ');
    }
    return sb.toString();
  }

  public static String getJavadocTag(String comment, String tagName) {
    int pos = comment.indexOf(tagName);
    int endPos = comment.indexOf("\n", pos);
    String tag = comment.substring(pos + tagName.length() + 1, endPos);
    return tag;
  }

  public static String removeTags(String comment) {
    // we remove everything from the first tag to the end of the comment -
    // tags MUST be at the end of the comment
    int pos = comment.indexOf('@');
    if (pos == -1) {
      return comment;
    }
    if (pos > 0) {
      String beforePos = comment.substring(0, pos);
      int prevReturn = beforePos.lastIndexOf('\n');
      if (prevReturn != -1) {
        pos = prevReturn;
      } else {
        pos = 0;
      }
    }
    return comment.substring(0, pos);
  }

  public static boolean resolveSiteVariance(TypeParameterElement typeParam, Variance variance) {

    return resolveVariance(Collections.emptyMap(), typeParam, variance);
  }


  private static boolean resolveVariance(
      Map<TypeParameterElement, Variance> wantMap,
      TypeParameterElement typeParam,
      Variance variance) {

    //
    if (wantMap.containsKey(typeParam)) {
      return wantMap.get(typeParam) == variance;
    } else {
      wantMap = new HashMap<>(wantMap);
      wantMap.put(typeParam, variance);
    }

    TypeVariable typeVar = (TypeVariable) typeParam.asType();
    TypeElement genericElt = (TypeElement) typeParam.getGenericElement();

    //
    for (TypeMirror superInterface : genericElt.getInterfaces()) {
      if (superInterface.getKind() == TypeKind.DECLARED) {
        Boolean checked = resolveSiteVariance(wantMap, typeVar, Variance.COVARIANT, superInterface, variance);
        if (checked != null && !checked) {
          return checked;
        }
      }
    }

    //
    for (Element elt : genericElt.getEnclosedElements()) {
      switch (elt.getKind()) {
        case METHOD:
          ExecutableElement methodElt = (ExecutableElement) elt;
          if (!methodElt.getModifiers().contains(Modifier.STATIC)) {

            // Return type check
            TypeMirror returnType = methodElt.getReturnType();
            Boolean checked = resolveSiteVariance(wantMap, typeVar, Variance.COVARIANT, returnType, variance);
            if (checked != null && !checked) {
              return checked;
            }

            // Parameter type check
            for (VariableElement paramElt : methodElt.getParameters()) {
              TypeMirror paramType = paramElt.asType();
              checked = resolveSiteVariance(wantMap, typeVar, Variance.CONTRAVARIANT, paramType, variance);
              if (checked != null && !checked) {
                return checked;
              }
            }
          }
          break;
        default:
          // throw new UnsupportedOperationException("" + elt + " with kind " + elt.getKind());
      }
    }
    return true;
  }

  private static Boolean resolveSiteVariance(
      Map<TypeParameterElement, Variance> wantMap,
      TypeVariable typeParam,
      Variance position,
      TypeMirror type,
      Variance variance) {

    if (type.getKind() == TypeKind.TYPEVAR) {
      if (typeParam.equals(type)) {
        switch (position) {
          case COVARIANT:
            return variance == Variance.COVARIANT;
          case CONTRAVARIANT:
            return variance == Variance.CONTRAVARIANT;
          default:
            throw new AssertionError();
        }
      }
    } else if (type.getKind() == TypeKind.DECLARED) {
      DeclaredType declaredType = (DeclaredType) type;
      TypeElement typeElt = (TypeElement) declaredType.asElement();
      List<? extends TypeParameterElement> typeParams = typeElt.getTypeParameters();
      List<? extends TypeMirror> typeArgs = declaredType.getTypeArguments();
      for (int i = 0;i < typeArgs.size();i++) {
        TypeParameterElement abc = typeParams.get(i);
        switch (position) {
          case COVARIANT:
            if (resolveVariance(wantMap, abc, Variance.COVARIANT)) {
              Boolean checked = resolveSiteVariance(wantMap, typeParam, Variance.COVARIANT, typeArgs.get(i), variance);
              if (checked != null && !checked) {
                return false;
              }
            }
            if (resolveVariance(wantMap, abc, Variance.CONTRAVARIANT)) {
              Boolean checked = resolveSiteVariance(wantMap, typeParam, Variance.CONTRAVARIANT, typeArgs.get(i), variance);
              if (checked != null && !checked) {
                return false;
              }
            }
            break;
          case CONTRAVARIANT:
            if (resolveVariance(wantMap, abc, Variance.COVARIANT)) {
              Boolean checked = resolveSiteVariance(wantMap, typeParam, Variance.CONTRAVARIANT, typeArgs.get(i), variance);
              if (checked != null && !checked) {
                return false;
              }
            }
            if (resolveVariance(wantMap, abc, Variance.CONTRAVARIANT)) {
              Boolean checked = resolveSiteVariance(wantMap, typeParam, Variance.COVARIANT, typeArgs.get(i), variance);
              if (checked != null && !checked) {
                return false;
              }
            }
            break;
          default:
            throw new AssertionError();
        }
      }
    }
    return true;
  }

  /**
   * Resolve a method annotation, this method scan the specified method, if the annotation is not found
   * it will also scan the methods this method overrides and return the annotation when it is found.
   *
   * @param annotationType the annotation type,
   * @param elementUtils element utils
   * @param typeUtils type utils
   * @param declaring the element declaring the method
   * @param method the method to start the resolution from
   * @return the annotation if resolved otherwise null
   */
  public static AnnotationMirror resolveMethodAnnotation(
      Class<? extends Annotation> annotationType, Elements elementUtils, Types typeUtils,
      TypeElement declaring, ExecutableElement method) {
    return resolveMethodAnnotation(
        (DeclaredType) elementUtils.getTypeElement(annotationType.getName()).asType(),
        elementUtils, typeUtils, declaring, method);
  }

  /**
   * Resolve a method annotation, this method scan the specified method, if the annotation is not found
   * it will also scan the methods this method overrides and return the annotation when it is found.
   *
   * @param annotationType the annotation type,
   * @param elementUtils element utils
   * @param typeUtils type utils
   * @param declaring the element declaring the method
   * @param method the method to start the resolution from
   * @return the annotation if resolved otherwise null
   */
  public static AnnotationMirror resolveMethodAnnotation(
      DeclaredType annotationType, Elements elementUtils, Types typeUtils,
      TypeElement declaring, ExecutableElement method) {
    Optional<? extends AnnotationMirror> annotation = method.getAnnotationMirrors().stream().filter(mirror -> typeUtils.isSameType(mirror.getAnnotationType(), annotationType)).findFirst();
    if (annotation.isPresent()) {
      return annotation.get();
    } else {
      return isFluent(annotationType, elementUtils, typeUtils, declaring, method, method.getEnclosingElement().asType());
    }
  }

  private static AnnotationMirror isFluent(DeclaredType annotationType, Elements elementUtils,
                                                    Types typeUtils, TypeElement declaring, ExecutableElement method, TypeMirror type) {
    for (TypeMirror directSuperType : typeUtils.directSupertypes(type)) {
      Element directSuperTypeElt = typeUtils.asElement(directSuperType);
      if (directSuperTypeElt instanceof TypeElement) {
        List<ExecutableElement> methods = ((TypeElement) directSuperTypeElt).getEnclosedElements().stream().
            filter(member -> member.getKind() == ElementKind.METHOD).map(member -> (ExecutableElement) member).
            collect(Collectors.toList());
        for (ExecutableElement m : methods) {
          if (elementUtils.overrides(method, m, declaring)) {
            AnnotationMirror annotation = resolveMethodAnnotation(annotationType, elementUtils, typeUtils, (TypeElement) directSuperTypeElt, m);
            if (annotation != null) {
              return annotation;
            }
          }
        }
        AnnotationMirror annotation = isFluent(annotationType, elementUtils, typeUtils, declaring, method, directSuperType);
        if (annotation != null) {
          return annotation;
        }
      }
    }
    return null;
  }
}
