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

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
public class MethodInfo {

  final String name;
  final MethodKind kind;
  final TypeInfo returnType;
  final boolean fluent;
  final boolean cacheReturn;
  List<ParamInfo> params;
  final String comment;
  final boolean staticMethod;
  boolean squashed;
  List<String> typeParams;
  LinkedHashSet<TypeInfo.Class> ownerTypes;

  public MethodInfo(LinkedHashSet<TypeInfo.Class> ownerTypes, String name, MethodKind kind, TypeInfo returnType, boolean fluent,
                    boolean cacheReturn, List<ParamInfo> params, String comment, boolean staticMethod, List<String> typeParams) {
    this.kind = kind;
    this.name = name;
    this.returnType = returnType;
    this.fluent = fluent;
    this.cacheReturn = cacheReturn;
    this.comment = comment;
    this.staticMethod = staticMethod;
    addParams(params);
    this.typeParams = typeParams;
    this.ownerTypes = ownerTypes;
  }

  public String getName() {
    return name;
  }

  public MethodKind getKind() {
    return kind;
  }

  public TypeInfo getReturnType() {
    return returnType;
  }

  public Set<TypeInfo.Class> getOwnerTypes() {
    return ownerTypes;
  }

  public boolean isFluent() {
    return fluent;
  }

  public boolean isIndexGetter() {
    return kind == MethodKind.INDEX_GETTER;
  }

  public boolean isIndexSetter() {
    return kind == MethodKind.INDEX_SETTER;
  }

  public boolean isCacheReturn() {
    return cacheReturn;
  }

  public List<ParamInfo> getParams() {
    return params;
  }

  public String getComment() {
    return comment;
  }

  public boolean isSquashed() {
    return squashed;
  }

  public void setSquashed(boolean squashed) {
    this.squashed = squashed;
  }

  public boolean isStaticMethod() {
    return staticMethod;
  }

  public List<String> getTypeParams() {
    return typeParams;
  }

  public void addParams(List<ParamInfo> params) {
    if (params == null) {
      throw new NullPointerException("params");
    }
    if (this.params == null) {
      this.params = new ArrayList<>();
    } else {
      squashed = true;
    }
    for (ParamInfo param: params) {
      if (!this.params.contains(param)) {
        this.params.add(param);
      }
    }
  }

  public void collectImports(Collection<TypeInfo.Class> imports) {
    params.stream().map(ParamInfo::getType).forEach(a -> a.collectImports(imports));
  }

  public boolean hasSameSignature(MethodInfo other) {
    if (name.equals(other.name) && params.size() == other.params.size()) {
      for (int i = 0;i < params.size();i++) {
        if (!params.get(i).type.equals(other.params.get(i).type)) {
          return false;
        }
      }
      return true;
    }
    return false;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    if (typeParams.size() > 0) {
      for (int i = 0;i < typeParams.size();i++) {
        sb.append(i > 0 ? ", " : "<");
        sb.append(typeParams.get(i));
      }
      sb.append("> ");
    }
    sb.append(returnType.getName());
    sb.append(' ');
    sb.append(name);
    sb.append('(');
    if (params.size() > 0) {
      for (int i = 0;i < params.size();i++) {
        if (i > 0) {
          sb.append(", ");
        }
        sb.append(params.get(i).getType().getName()).append(" ").append(params.get(i).type.getName());
      }
    }
    sb.append(')');
    return sb.toString();
  }
}
