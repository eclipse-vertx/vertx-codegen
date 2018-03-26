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

import io.vertx.codegen.annotations.ProxyClose;
import io.vertx.codegen.annotations.ProxyIgnore;
import io.vertx.codegen.doc.Doc;
import io.vertx.codegen.doc.Text;
import io.vertx.codegen.overloadcheck.MethodOverloadChecker;
import io.vertx.codegen.type.*;

import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
public class ProxyModel extends ClassModel {

  public ProxyModel(ProcessingEnvironment env, MethodOverloadChecker methodOverloadChecker, Messager messager, Map<String, TypeElement> sources, Elements elementUtils, Types typeUtils, TypeElement modelElt) {
    super(env, methodOverloadChecker, messager, sources, elementUtils, typeUtils, modelElt);
  }

  @Override
  public String getKind() {
    return "proxy";
  }

  @Override
  protected void checkParamType(ExecutableElement elem, TypeMirror type, TypeInfo typeInfo, int pos, int numParams) {
    // Basic types, int, long, String etc
    // JsonObject or JsonArray
    if (typeInfo.getKind().basic || typeInfo.getKind().json) {
      return;
    }
    // We also allow enums as parameter types
    if (typeInfo.getKind() == ClassKind.ENUM) {
      return;
    }
    if (isLegalContainerParam(typeInfo)) {
      return;
    }
    // We also allow data object as parameter types if they have a 'public JsonObject toJson()' method
    if (typeInfo.getKind() == ClassKind.DATA_OBJECT) {
      if (type instanceof DeclaredType) {
        if (Helper.isJsonifiable(elementUtils, typeUtils, (TypeElement) ((DeclaredType) type).asElement())) {
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
  protected void checkReturnType(ExecutableElement elem, TypeInfo type, TypeMirror typeMirror) {

    if (elem.getModifiers().contains(Modifier.STATIC)) {
      // Ignore static methods - we won't use them anyway
      return;
    }
    if (type.isVoid()) {
      return;
    }

    throw new GenException(elem, "Proxy methods must have void or Fluent returns");
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
  protected MethodInfo createMethodInfo(Set<ClassTypeInfo> ownerTypes, String methodName, String comment, Doc doc, MethodKind kind, TypeInfo returnType,
                                        Text returnDescription,
                                        boolean isFluent, boolean isCacheReturn, List<ParamInfo> mParams,
                                        ExecutableElement methodElt, boolean isStatic, boolean isDefault, ArrayList<TypeParamInfo.Method> typeParams,
                                        TypeElement declaringElt, boolean methodDeprecated) {
    AnnotationMirror proxyIgnoreAnnotation = Helper.resolveMethodAnnotation(ProxyIgnore.class, elementUtils, typeUtils, declaringElt, methodElt);
    boolean isProxyIgnore = proxyIgnoreAnnotation != null;
    AnnotationMirror proxyCloseAnnotation = Helper.resolveMethodAnnotation(ProxyClose.class, elementUtils, typeUtils, declaringElt, methodElt);
    boolean isProxyClose = proxyCloseAnnotation != null;
    if (isProxyClose && mParams.size() > 0) {
      if (mParams.size() > 1) {
        throw new GenException(this.modelElt, "@ProxyClose methods can't have more than one parameter");
      }
      if (kind != MethodKind.FUTURE) {
        throw new GenException(this.modelElt, "@ProxyClose parameter must be Handler<AsyncResult<Void>>");
      }
      TypeInfo type = mParams.get(0).getType();
      TypeInfo arg = ((ParameterizedTypeInfo) ((ParameterizedTypeInfo) type).getArgs().get(0)).getArgs().get(0);
      if (arg.getKind() != ClassKind.VOID) {
        throw new GenException(this.modelElt, "@ProxyClose parameter must be " +
            "Handler<AsyncResult<Void>> instead of " + type);
      }
    }
    return new ProxyMethodInfo(ownerTypes, methodName, kind, returnType, returnDescription,
      isFluent, isCacheReturn, mParams, comment, doc, isStatic, isDefault, typeParams, isProxyIgnore,
      isProxyClose, methodDeprecated);
  }

  private boolean isLegalHandlerAsyncResultType(TypeInfo type) {
    if (type.getErased().getKind() == ClassKind.HANDLER) {
      TypeInfo eventType = ((ParameterizedTypeInfo) type).getArgs().get(0);
      if (eventType.getErased().getKind() == ClassKind.ASYNC_RESULT) {
        TypeInfo resultType = ((ParameterizedTypeInfo) eventType).getArgs().get(0);
        if (resultType.getKind().json || resultType.getKind().basic ||
          isLegalListSetMapResult(resultType) || resultType.getKind() == ClassKind.VOID ||
          resultType.getKind() == ClassKind.ENUM || resultType.getKind() == ClassKind.DATA_OBJECT) {
          return true;
        }
        if (resultType.getKind() == ClassKind.API) {
          ApiTypeInfo cla = (ApiTypeInfo)resultType;
          if (cla.isProxyGen()) {
            return true;
          }
        }
      }
    }
    return false;
  }

  private boolean isLegalListSetMapResult(TypeInfo type) {
    if (type instanceof ParameterizedTypeInfo) {
      if (type.getKind() == ClassKind.LIST || type.getKind() == ClassKind.SET) {
        TypeInfo elementType = ((ParameterizedTypeInfo) type).getArgs().get(0);
        if (elementType.getKind().basic || elementType.getKind().json || elementType.getKind() == ClassKind.DATA_OBJECT) {
          return true;
        }
      }
    }
    return false;
  }

  // TODO should we allow enums/Data objects in List/Set/Map params

  protected boolean isLegalContainerParam(TypeInfo type) {
    TypeInfo raw = type.getRaw();
    if (raw.getName().equals(List.class.getName()) || raw.getName().equals(Set.class.getName())) {
      TypeInfo argument = ((ParameterizedTypeInfo) type).getArgs().get(0);
      if (argument.getKind().basic || argument.getKind().json || argument.getKind() == ClassKind.DATA_OBJECT) {
        return true;
      }
    } else if (raw.getName().equals(Map.class.getName())) {
      TypeInfo argument0 = ((ParameterizedTypeInfo) type).getArgs().get(0);
      if (!argument0.getName().equals(String.class.getName())) {
        return false;
      }
      TypeInfo argument1 = ((ParameterizedTypeInfo) type).getArgs().get(1);
      if (argument1.getKind().basic || argument1.getKind().json) {
        return true;
      }
    }
    return false;
  }
}
