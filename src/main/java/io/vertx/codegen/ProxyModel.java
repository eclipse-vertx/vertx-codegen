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

package io.vertx.codegen;

import io.vertx.codegen.annotations.ProxyIgnore;

import javax.annotation.processing.Messager;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
public class ProxyModel extends ClassModel {

  public ProxyModel(Messager messager, Map<String, TypeElement> sources, Elements elementUtils, Types typeUtils, TypeElement modelElt) {
    super(messager, sources, elementUtils, typeUtils, modelElt);
  }

  @Override
  public String getKind() {
    return "proxy";
  }

  @Override
  protected void checkParamType(Element elem, TypeMirror type, TypeInfo typeInfo, int pos, int numParams) {
    // Basic types, int, long, String etc
    // JsonObject or JsonArray
    if (typeInfo.getKind().basic || typeInfo.getKind().json) {
      return;
    }
    // We also allow enums as parameter types
    if (typeInfo.getKind() == ClassKind.ENUM) {
      return;
    }
    // We also allow options as parameter types if they have a 'public JsonObject toJson()' method
    if (typeInfo.getKind() == ClassKind.OPTIONS) {
      if (type instanceof DeclaredType) {
        List<TypeInfo> list = ((DeclaredType) type).asElement().getEnclosedElements().stream()
          .filter(e -> e.getKind() == ElementKind.METHOD)
          .map(e -> (ExecutableElement) e)
          .filter(e -> e.getParameters().size() == 0 && e.getSimpleName().toString().equals("toJson"))
          .map(e -> typeFactory.create(e.getReturnType()))
          .filter(ti -> ti.getKind() == ClassKind.JSON_OBJECT)
          .collect(Collectors.toList());

        if (list.size() == 1) { // we have our toJson method
          return;
        }

        throw new GenException(elem, "type " + typeInfo + " does not have a valid 'public JsonObject toJson()' method.");
      }
    }
    if (isLegalHandlerAsyncResultType(typeInfo)) {
      if (pos != numParams - 1) {
        throw new GenException(elem, "Handler<AsyncResult<T>> must be the last parameter if present in a proxied method");
      }
      return;
    }
    if (elem.getModifiers().contains(Modifier.STATIC)) {
      // Ignore static methods - we won't use them anyway
      return;
    }
    throw new GenException(elem, "type " + typeInfo + " is not legal for use for a parameter in proxy");
  }

  @Override
  protected void checkReturnType(Element elem, TypeInfo type) {

    if (elem.getModifiers().contains(Modifier.STATIC)) {
      // Ignore static methods - we won't use them anyway
      return;
    }
    if (type instanceof TypeInfo.Void) {
      return;
    }
    throw new GenException(elem, "Proxy methods must have void or fluent returns");
  }

  @Override
  protected void checkMethod(MethodInfo methodInfo) {
    // We don't allow overloaded methods in proxies
    List<MethodInfo> methodsByName = methodMap.get(methodInfo.getName());
    if (methodsByName != null) {
      throw new GenException(this.modelElt, "Overloaded methods are not allowed in ProxyGen interfaces " + methodInfo.name);
    }
  }

  @Override
  protected MethodInfo createMethodInfo(TypeInfo.Class ownerType, String methodName, MethodKind kind, TypeInfo returnType,
                                        boolean isFluent, boolean isCacheReturn, List<ParamInfo> mParams,
                                        ExecutableElement methodElt, boolean isStatic, ArrayList<TypeParamInfo.Method> typeParams,
                                        TypeElement declaringElt) {
    AnnotationMirror proxyIgnoreAnnotation = Helper.resolveMethodAnnotation(ProxyIgnore.class, elementUtils, typeUtils, declaringElt, methodElt);
    boolean isProxyIgnore = proxyIgnoreAnnotation != null;
    return new ProxyMethodInfo(Collections.singleton(ownerType), methodName, kind, returnType,
      isFluent, isCacheReturn, mParams, elementUtils.getDocComment(methodElt), isStatic, typeParams, isProxyIgnore);
  }

  private boolean isLegalHandlerAsyncResultType(TypeInfo type) {
    if (type.getErased().getKind() == ClassKind.HANDLER) {
      TypeInfo eventType = ((TypeInfo.Parameterized) type).getArgs().get(0);
      if (eventType.getErased().getKind() == ClassKind.ASYNC_RESULT) {
        TypeInfo resultType = ((TypeInfo.Parameterized) eventType).getArgs().get(0);
        if (resultType.getKind().json || resultType.getKind().basic ||
          isLegalListOrSet(resultType) || resultType.getKind() == ClassKind.VOID ||
          resultType.getKind() == ClassKind.ENUM) {
          return true;
        }
      }
    }
    return false;
  }

  private boolean isLegalListOrSet(TypeInfo type) {
    if (type instanceof TypeInfo.Parameterized) {
      TypeInfo raw = type.getRaw();
      if (raw.getName().equals(List.class.getName()) || raw.getName().equals(Set.class.getName())) {
        TypeInfo elementType = ((TypeInfo.Parameterized) type).getArgs().get(0);
        if (elementType.getKind().basic || elementType.getKind().json || elementType.getKind() == ClassKind.ENUM) {
          return true;
        }
      }
    }
    return false;
  }
}
