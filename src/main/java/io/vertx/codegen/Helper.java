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
import io.vertx.codegen.annotations.GenIgnore;
import io.vertx.codegen.type.ClassKind;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.*;
import javax.lang.model.type.*;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
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

  public static final Function<Element, Stream<ExecutableElement>> FILTER_METHOD = element -> {
    if (element.getKind() == ElementKind.METHOD) {
      return Stream.of((ExecutableElement) element);
    } else {
      return Stream.empty();
    }
  };

  public static final Function<Element, Stream<VariableElement>> FILTER_FIELD = element -> {
    if (element.getKind() == ElementKind.FIELD) {
      return Stream.of((VariableElement) element);
    } else {
      return Stream.empty();
    }
  };

  static <T> Function<Object, Stream<T>> instanceOf(Class<T> type) {
    return o -> {
      if (type.isInstance(o)) {
        return Stream.of(type.cast(o));
      } else {
        return Stream.empty();
      }
    };
  }

  static <T> Function<Object, Stream<T>> cast(Class<T> type) {
    return o -> Stream.of(type.cast(o));
  }

  static final Function<Element, Stream<ExecutableElement>> CAST = element -> {
    if (element.getKind() == ElementKind.METHOD) {
      return Stream.of((ExecutableElement) element);
    } else {
      return Stream.empty();
    }
  };

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
   * be resolved, null is returned.
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
      return typeUtils.asMemberOf(subType, ((TypeVariable) typeParam.asType()).asElement());
    } else if (typeUtils.isSubtype(erasedSubType, erased)) {
      for (TypeMirror superType : typeUtils.directSupertypes(subType)) {
        TypeMirror resolved = resolveTypeParameter(typeUtils, (DeclaredType) superType, typeParam);
        if (resolved != null) {
          return resolved;
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
   * @param type the sub type for which the type parameter is parameterized
   * @param typeParam the type parameter to resolve
   * @return the type parameterizing the type parameter
   */
  public static <T> Type resolveTypeParameter(Type type, java.lang.reflect.TypeVariable<java.lang.Class<T>> typeParam) {
    if (type instanceof Class<?>) {
      Class<?> classType = (Class<?>) type;
      if (Stream.of(classType.getTypeParameters()).filter(tp -> tp.equals(typeParam)).findFirst().isPresent()) {
        return typeParam;
      }
      List<Type> superTypes = new ArrayList<>();
      if (classType.getGenericSuperclass() != null) {
        superTypes.add(classType.getGenericSuperclass());
      }
      Collections.addAll(superTypes, classType.getGenericInterfaces());
      for (Type superType : superTypes) {
        Type resolved = resolveTypeParameter(superType, typeParam);
        if (resolved != null) {
          return resolved;
        }
      }
    } else if (type instanceof ParameterizedType) {
      ParameterizedType parameterizedType = (ParameterizedType) type;
      Type rawType = parameterizedType.getRawType();
      Type resolvedType = resolveTypeParameter(rawType, typeParam);
      if (resolvedType instanceof java.lang.reflect.TypeVariable<?>) {
        GenericDeclaration owner = ((java.lang.reflect.TypeVariable) resolvedType).getGenericDeclaration();
        if (owner.equals(rawType)) {
          java.lang.reflect.TypeVariable<?>[] typeParams = owner.getTypeParameters();
          for (int i = 0;i < typeParams.length;i++) {
            if (typeParams[i].equals(resolvedType)) {
              return parameterizedType.getActualTypeArguments()[i];
            }
          }
        }
      }
    } else {
      throw new UnsupportedOperationException("Todo " + type.getTypeName() + " " + type.getClass().getName());
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
    TypeElement resolvedElt;
    if (typeName.isEmpty()) {
      resolvedElt = declaringElt;
    } else {
      if (typeName.lastIndexOf('.') == -1) {
        resolvedElt = elementUtils.getTypeElement("java.lang." +typeName);
        if (resolvedElt == null) {
          String packageName = elementUtils.getPackageOf(declaringElt).getQualifiedName().toString();
          resolvedElt = elementUtils.getTypeElement(packageName + '.' + typeName);
        }
      } else {
        resolvedElt = elementUtils.getTypeElement(typeName);
      }
    }
    return resolvedElt;
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
    ElementKind kind = elt.getKind();
    if (kind == ElementKind.CLASS || kind == ElementKind.INTERFACE || kind == ElementKind.ENUM) {
      return (TypeElement) elt;
    }
    Element enclosingElt = elt.getEnclosingElement();
    if (enclosingElt != null) {
      return getElementTypeOf(enclosingElt);
    }
    return null;
  }

  private static final Pattern WHITESPACE_CLUSTER_PATTERN = Pattern.compile("\\s+");

  /**
   * Trim and normalize the whitespaces in a string: any cluster of more than one whitespace char
   * is replaced by a space char, then the string is trimmed.
   *
   * @param s the string to normalize
   * @return the normalized string
   */
  public static String normalizeWhitespaces(String s) {
    Matcher matcher = WHITESPACE_CLUSTER_PATTERN.matcher(s);
    return matcher.replaceAll(" ").trim();
  }

  /**
   * Resolve the set of all the ancestors declared types of a given type element.
   *
   * @param typeElt the type element to resolve
   * @return the set of ancestors
   */
  public static Set<DeclaredType> resolveAncestorTypes(TypeElement typeElt, boolean withSuper, boolean withInterfaces) {
    Set<DeclaredType> ancestors = new LinkedHashSet<>();
    resolveAncestorTypes(typeElt, ancestors, withSuper, withInterfaces);
    return ancestors;
  }

  private static void resolveAncestorTypes(TypeElement typeElt, Set<DeclaredType> ancestors, boolean withSuper, boolean withInterfaces) {
    List<TypeMirror> superTypes = new ArrayList<>();
    if (withSuper && typeElt.getSuperclass() != null) {
      superTypes.add(typeElt.getSuperclass());
    }
    if (withInterfaces) {
      superTypes.addAll(typeElt.getInterfaces());
    }
    for (TypeMirror superType : superTypes) {
      if (superType.getKind() == TypeKind.DECLARED) {
        DeclaredType superDeclaredType = (DeclaredType) superType;
        if (!ancestors.contains(superDeclaredType)) {
          ancestors.add(superDeclaredType);
          resolveAncestorTypes((TypeElement) superDeclaredType.asElement(), ancestors, withSuper, withInterfaces);
        }
      }
    }
  }

  static void checkUnderModule(Model model, String annotation) {
    if (model.getModule() == null) {
      throw new GenException(model.getElement(), "Declaration annotated with " + annotation + " must be under a package annotated" +
          "with @ModuleGen. Check that the package '" + model.getFqn() +
          "' or a parent package contains a 'package-info.java' using the @ModuleGen annotation");
    }
  }

  static void ensureParentDir(File f) {
    if (!f.getParentFile().exists()) {
      f.getParentFile().mkdirs();
    }
  }

  /**
   * Compute the string representation of a type mirror.
   *
   * @param mirror the type mirror
   * @return the string representation
   */
  static String toString(TypeMirror mirror) {
    StringBuilder buffer = new StringBuilder();
    toString(mirror, buffer);
    return buffer.toString();
  }

  /**
   * Compute the string representation of a type mirror.
   *
   * @param mirror the type mirror
   * @param buffer the buffer appended with the string representation
   */
  static void toString(TypeMirror mirror, StringBuilder buffer) {
    switch (mirror.getKind()) {
      case DECLARED: {
        DeclaredType dt = (DeclaredType) mirror;
        TypeElement elt = (TypeElement) dt.asElement();
        buffer.append(elt.getQualifiedName().toString());
        List<? extends TypeMirror> args = dt.getTypeArguments();
        if (args.size() > 0) {
          buffer.append("<");
          for (int i = 0;i < args.size();i++) {
            if (i > 0) {
              buffer.append(",");
            }
            toString(args.get(i), buffer);
          }
          buffer.append(">");
        }
        break;
      }
      case WILDCARD: {
        javax.lang.model.type.WildcardType wt = (javax.lang.model.type.WildcardType) mirror;
        buffer.append("?");
        if (wt.getSuperBound() != null) {
          buffer.append(" super ");
          toString(wt.getSuperBound(), buffer);
        } else if (wt.getExtendsBound() != null) {
          buffer.append(" extends ");
          toString(wt.getExtendsBound(), buffer);
        }
        break;
      }
      case TYPEVAR: {
        javax.lang.model.type.TypeVariable tv = (TypeVariable) mirror;
        TypeParameterElement elt = (TypeParameterElement) tv.asElement();
        buffer.append(elt.getSimpleName().toString());
        if (tv.getUpperBound() != null && !tv.getUpperBound().toString().equals("java.lang.Object")) {
          buffer.append(" extends ");
          toString(tv.getUpperBound(), buffer);
        } else if (tv.getLowerBound() != null && tv.getLowerBound().getKind() != TypeKind.NULL) {
          buffer.append(" super ");
          toString(tv.getUpperBound(), buffer);
        }
        break;
      }
      case BYTE:
      case SHORT:
      case INT:
      case LONG:
      case FLOAT:
      case DOUBLE:
      case CHAR:
      case BOOLEAN: {
        PrimitiveType pm = (PrimitiveType) mirror;
        buffer.append(pm.getKind().name().toLowerCase());
        break;
      }
      case ARRAY: {
        ArrayType at = (ArrayType) mirror;
        toString(at.getComponentType(), buffer);
        buffer.append("[]");
        break;
      }
      default:
        throw new UnsupportedOperationException("todo " + mirror + " " + mirror.getKind());
    }
  }

  /**
   * Like {@link #getReflectMethod(ClassLoader, ExecutableElement)} but using the processing environment context.
   */
  public static Method getReflectMethod(ProcessingEnvironment env, ExecutableElement modelMethod) {
    ClassLoader loader = CodeGen.loaderMap.get(env);
    if (loader != null) {
      return getReflectMethod(loader, modelMethod);
    }
    return null;
  }

  /**
   * Returns a {@link Method } corresponding to the {@literal methodElt} parameter. Obviously this work
   * only when the corresponding method is available on the classpath using java lang reflection.
   *
   * @param modelMethod the model method element
   * @return the method or null if not found
   */
  public static Method getReflectMethod(ClassLoader loader, ExecutableElement modelMethod) {
    TypeElement typeElt = (TypeElement) modelMethod.getEnclosingElement();
    Method method = null;
    try {
      Class<?> clazz = loader.loadClass(typeElt.getQualifiedName().toString());
      StringBuilder sb = new StringBuilder(modelMethod.getSimpleName());
      sb.append("(");
      List<? extends VariableElement> params = modelMethod.getParameters();
      for (int i = 0;i < params.size();i++) {
        if (i > 0) {
          sb.append(",");
        }
        VariableElement param = params.get(i);
        toString(param.asType(), sb);
      }
      sb.append(")");
      String s = sb.toString();
      for (Method m : clazz.getMethods()) {
        String sign = m.toGenericString();
        int pos = sign.indexOf('(');
        pos = sign.lastIndexOf('.', pos) + 1;
        sign = sign.substring(pos);
        sign = sign.replace(", ", ","); // Remove space between arguments
        if (sign.equals(s)) {
          // Test this case
          if (method != null) {
            if (method.getReturnType().isAssignableFrom(m.getReturnType())) {
              method = m;
            }
          } else {
            method = m;
          }
        }
      }
    } catch (ClassNotFoundException e) {
    }
    return method;
  }

  public static boolean isDataObjectAnnotatedEncodable(Elements elementUtils, TypeElement dataObjectElt) {
    return
      dataObjectElt.getAnnotation(DataObject.class).generateConverter() ||
      elementUtils.getAllMembers(dataObjectElt)
      .stream()
      .flatMap(Helper.FILTER_METHOD)
      .anyMatch(exeElt ->
        exeElt.getSimpleName().toString().equals("toJson") &&
          exeElt.getReturnType().toString().equals("io.vertx.core.json.JsonObject")
      );
  }

  public static boolean isConcreteClass(TypeElement element) {
    return element.getKind() == ElementKind.CLASS && !element.getModifiers().contains(Modifier.ABSTRACT);
  }

  public static boolean isAbstractClassOrInterface(TypeElement element) {
    return element.getKind().isInterface() ||
      (element.getKind() == ElementKind.CLASS && element.getModifiers().contains(Modifier.ABSTRACT));
  }

  public static boolean isDataObjectAnnotatedDecodable(Elements elementUtils, Types typeUtils, TypeElement dataObjectElt) {
    boolean isConcreteAndHasJsonConstructor =
      isConcreteClass(dataObjectElt) &&
      elementUtils
        .getAllMembers(dataObjectElt)
        .stream()
        .filter(e -> e.getKind() == ElementKind.CONSTRUCTOR)
        .map(e -> (ExecutableElement)e)
        .anyMatch(constructor ->
          constructor.getParameters().size() == 1 &&
            constructor.getModifiers().contains(Modifier.PUBLIC) &&
            constructor.getParameters().get(0).asType().toString().equals("io.vertx.core.json.JsonObject")
        );
    boolean isNotConcreteAndHasStaticDecodeMethod =
      isAbstractClassOrInterface(dataObjectElt) &&
        elementUtils
          .getAllMembers(dataObjectElt)
          .stream()
          .filter(e -> e.getKind() == ElementKind.METHOD)
          .map(e -> (ExecutableElement)e)
          .anyMatch(methodElt ->
            methodElt.getSimpleName().contentEquals("decode") &&
            methodElt.getModifiers().containsAll(Arrays.asList(Modifier.STATIC, Modifier.PUBLIC)) &&
            methodElt.getParameters().size() == 1 &&
            methodElt.getParameters().get(0).asType().toString().equals("io.vertx.core.json.JsonObject") &&
            typeUtils.isSameType(methodElt.getReturnType(), dataObjectElt.asType())
          );
    boolean hasGenerateConverterAndEmptyConstructorAndConcrete = dataObjectElt.getAnnotation(DataObject.class).generateConverter() &&
      elementUtils
        .getAllMembers(dataObjectElt)
        .stream()
        .filter(e -> e.getKind() == ElementKind.CONSTRUCTOR)
        .map(e -> (ExecutableElement)e)
        .anyMatch(constructor ->
          constructor.getParameters().size() == 0 &&
            constructor.getModifiers().contains(Modifier.PUBLIC)
        ) && dataObjectElt.getKind() == ElementKind.CLASS && !dataObjectElt.getModifiers().contains(Modifier.ABSTRACT)
      ;
    return isConcreteAndHasJsonConstructor || isNotConcreteAndHasStaticDecodeMethod || hasGenerateConverterAndEmptyConstructorAndConcrete;
  }

  /**
   * @param elt the element to check
   * @return Return {@code true} if the {@code elt} is annotated with {@code @GenIgnore}.
   */
  public static boolean isGenIgnore(Element elt) {
    return elt.getAnnotation(GenIgnore.class) != null;
  }
}
