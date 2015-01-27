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

import io.vertx.codegen.annotations.DataObject;
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
import javax.lang.model.type.ExecutableType;
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
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
    if (annotations.get(DataObject.class) != null) {
      return ClassKind.DATA_OBJECT;
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
    int index = type.lastIndexOf('.');
    if (index >= 0) {
      return type.substring(0, index);
    } else {
      return "";
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


  /**
   * Return the type of a type parameter element of a given type element when that type parameter
   * element is parameterized by a sub type, directly or indirectly. When the type parameter cannot
   * be resolve, null is returned.
   *
   * @param typeUtils the type utils
   * @param subType the sub type for which the type parameter is parameterized
   * @param typeParam the type parameter to resolve
   * @return the type parameterizing the type parameter
   */
  public static TypeMirror resolveTypeParameter(Types typeUtils, DeclaredType subType, TypeParameterElement typeParam) {
    TypeMirror erased = typeUtils.erasure(typeParam.getGenericElement().asType());
    TypeMirror erasedSubType = typeUtils.erasure(subType);
    if (typeUtils.isSameType(erased, erasedSubType)) {
      return typeParam.asType();
    } else if (typeUtils.isSubtype(erasedSubType, erased)) {
      for (TypeMirror superType : typeUtils.directSupertypes(subType)) {
        TypeMirror resolved = resolveTypeParameter(typeUtils, (DeclaredType) superType, typeParam);
        if (resolved != null) {
          if (resolved.getKind() == TypeKind.TYPEVAR) {
            return typeUtils.asMemberOf(subType, ((TypeVariable) resolved).asElement());
          } else {
            return resolved;
          }
        }
      }
    }
    return null;
  }

  private static final Pattern SIGNATURE_PATTERN = Pattern.compile("#(\\p{javaJavaIdentifierStart}(?:\\p{javaJavaIdentifierPart})*)(?:\\((.*)\\))?$");
  public static final Pattern LINK_REFERENCE_PATTERN = Pattern.compile(
          "(?:(?:\\p{javaJavaIdentifierStart}\\p{javaJavaIdentifierPart}*\\.)*" + "\\p{javaJavaIdentifierStart}\\p{javaJavaIdentifierPart}*)?" +
          "(?:" + SIGNATURE_PATTERN.pattern() + ")?");

  /**
   * Resolves a documentation signature, null can be returned if no element can be resolved.
   *
   * @param elementUtils the element utils
   * @param typeUtils the type utils
   * @param declaringElt the declaring element, may be null
   * @param signature the signature to resolve
   * @return the resolved element
   */
  public static Element resolveSignature(
      Elements elementUtils,
      Types typeUtils,
      TypeElement declaringElt,
      String signature) {
    Matcher signatureMatcher = SIGNATURE_PATTERN.matcher(signature);
    if (signatureMatcher.find()) {
      String memberName = signatureMatcher.group(1);
      String typeName = signature.substring(0, signatureMatcher.start());
      TypeElement typeElt = resolveTypeElement(elementUtils, declaringElt, typeName);
      if (typeElt != null) {
        Predicate<? super Element> memberMatcher;
        if (signatureMatcher.group(2) != null) {
          String t = signatureMatcher.group(2).trim();
          Predicate<ExecutableElement> parametersMatcher;
          if (t.length() == 0) {
            parametersMatcher = exeElt -> exeElt.getParameters().isEmpty();
          } else {
            parametersMatcher = parametersMatcher(typeUtils, t.split("\\s*,\\s*"));
          }
          memberMatcher = elt -> matchesConstructor(elt, memberName, parametersMatcher) || matchesMethod(elt, memberName, parametersMatcher);
        } else {
          memberMatcher = elt -> matchesConstructor(elt, memberName, exeElt -> true) ||
              matchesMethod(elt, memberName, exeElt -> true) ||
              matchesField(elt, memberName);
        }
        // The order of kinds is important
        for (ElementKind kind : Arrays.asList(ElementKind.FIELD, ElementKind.CONSTRUCTOR, ElementKind.METHOD)) {
          for (Element memberElt : elementUtils.getAllMembers(typeElt)) {
            if(memberElt.getKind() == kind && memberMatcher.test(memberElt)) {
               return memberElt;
            }
          }
        }
      }
      return null;
    } else {
      return resolveTypeElement(elementUtils, declaringElt, signature);
    }
  }

  private static TypeElement resolveTypeElement(Elements elementUtils, TypeElement declaringElt, String typeName) {
    if (typeName.isEmpty()) {
      return declaringElt;
    } else {
      if (typeName.lastIndexOf('.') == -1) {
        String packageName = elementUtils.getPackageOf(declaringElt).getQualifiedName().toString();
        typeName = packageName + '.' + typeName;
      }
      return elementUtils.getTypeElement(typeName);
    }
  }

  private static boolean matchesConstructor(Element elt, String memberName, Predicate<ExecutableElement> parametersMatcher) {
    if (elt.getKind() == ElementKind.CONSTRUCTOR) {
      ExecutableElement constructorElt = (ExecutableElement) elt;
      TypeElement typeElt = (TypeElement) constructorElt.getEnclosingElement();
      return typeElt.getSimpleName().toString().equals(memberName) && parametersMatcher.test(constructorElt);
    }
    return false;
  }

  private static boolean matchesMethod(Element elt, String memberName, Predicate<ExecutableElement> parametersMatcher) {
    if (elt.getKind() == ElementKind.METHOD) {
      ExecutableElement methodElt = (ExecutableElement) elt;
      return methodElt.getSimpleName().toString().equals(memberName) && parametersMatcher.test(methodElt);
    }
    return false;
  }

  private static boolean matchesField(Element elt, String memberName) {
    return elt.getKind() == ElementKind.FIELD && elt.getSimpleName().toString().equals(memberName);
  }

  /**
   * Return a matcher for parameters, given the parameter type signature of an executable element. The parameter signature
   * is a list of parameter types formatted as a signature, i.e all types are raw, or primitive, or arrays. Unqualified
   * types are resolved against the import of the specified {@code compilationUnitTree} argument.
   *
   * @param parameterSignature the parameter type names
   * @return the matcher
   */
  private static Predicate<ExecutableElement> parametersMatcher(Types typeUtils, String[] parameterSignature) {
    return exeElt -> {
      if (exeElt.getParameters().size() == parameterSignature.length) {
        TypeMirror tm2 = exeElt.asType();
        ExecutableType tm3 = (ExecutableType) typeUtils.erasure(tm2);
        for (int j = 0; j < parameterSignature.length; j++) {
          String t1 = tm3.getParameterTypes().get(j).toString();
          String t2 = parameterSignature[j];
          if (t2.indexOf('.') == -1) {
            t1 = t1.substring(t1.lastIndexOf('.') + 1);
          }
          if (!t1.equals(t2)) {
            return false;
          }
        }
        return true;
      } else {
        return false;
      }
    };
  }

  /**
   * Return the element type of the specified element.
   *
   * @param elt the element
   * @return the element type or null if none exists
   */
  public static TypeElement getElementTypeOf(Element elt) {
    if (elt.getKind() == ElementKind.CLASS || elt.getKind() == ElementKind.INTERFACE) {
      return (TypeElement) elt;
    }
    Element enclosingElt = elt.getEnclosingElement();
    if (enclosingElt != null) {
      return getElementTypeOf(enclosingElt);
    }
    return null;
  }
}
