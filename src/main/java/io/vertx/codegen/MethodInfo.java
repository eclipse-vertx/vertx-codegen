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

import io.vertx.codegen.doc.Doc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
public class MethodInfo implements Comparable<MethodInfo> {

  final String name;
  final MethodKind kind;
  final TypeInfo returnType;
  final String returnDescription;
  final boolean fluent;
  final boolean cacheReturn;
  final String comment;
  final Doc doc;
  final boolean staticMethod;
  List<TypeParamInfo.Method> typeParams;
  LinkedHashSet<TypeInfo.Class> ownerTypes;
  List<ParamInfo> params;

  public MethodInfo(Set<TypeInfo.Class> ownerTypes, String name, MethodKind kind,
                    TypeInfo returnType, String returnDescription, boolean fluent,  boolean cacheReturn,
                    List<ParamInfo> params, String comment, Doc doc, boolean staticMethod,
                    List<TypeParamInfo.Method> typeParams) {


    this.comment = comment;
    this.kind = kind;
    this.name = name;
    this.returnType = returnType;
    this.returnDescription = returnDescription;
    this.fluent = fluent;
    this.cacheReturn = cacheReturn;
    this.doc = doc;
    this.staticMethod = staticMethod;
    this.params = params;
    this.typeParams = typeParams;
    this.ownerTypes = new LinkedHashSet<>(ownerTypes);
  }

  /**
   * @return the associated property name when this method is a getter otherwise null is returned
   */
  public String getPropertyName() {
    if (kind == MethodKind.GETTER) {
      if (name.startsWith("is")) {
        return Helper.normalizePropertyName(name.substring(2));
      } else {
        return Helper.normalizePropertyName(name.substring(3));
      }
    } else {
      return null;
    }
  }

  public String getName() {
    return name;
  }

  public String getName(Case _case) {
    return _case.format(Case.CAMEL.parse(name));
  }

  public MethodKind getKind() {
    return kind;
  }

  public TypeInfo getReturnType() {
    return returnType;
  }

  public String getReturnDescription() {
    return returnDescription;
  }

  public Set<TypeInfo.Class> getOwnerTypes() {
    return ownerTypes;
  }

  /**
   * Returns the method signature, the returned object is freely modifiable.
   *
   * @return the method signature
   */
  public Signature getSignature() {
    return new Signature(name, new ArrayList<>(params));
  }

  /**
   * Return true if the provided type is the sole owner of this method, i.e this method
   * is only declared by the provided type.
   *
   * @param owner the tested type
   * @return true when this method is owned by the <code>owner</code> argument
   */
  public boolean isOwnedBy(TypeInfo.Class owner) {
    return ownerTypes.contains(owner) && ownerTypes.size() == 1;
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

  public Doc getDoc() {
    return doc;
  }

  public boolean isStaticMethod() {
    return staticMethod;
  }

  public List<TypeParamInfo.Method> getTypeParams() {
    return typeParams;
  }

  public void mergeTypeParams(List<TypeParamInfo.Method> mergedTypeParams) throws IllegalArgumentException {
    int l = Math.min(typeParams.size(), mergedTypeParams.size());
    if (typeParams.subList(0, l).equals(mergedTypeParams.subList(0, l))) {
      if (mergedTypeParams.size() > typeParams.size()) {
        typeParams.addAll(mergedTypeParams.subList(typeParams.size(), mergedTypeParams.size()));
      }
    } else {
      throw new IllegalArgumentException("Merged type params " + mergedTypeParams + " don't match the existing ones " + typeParams);
    }
  }

  public void collectImports(Collection<TypeInfo.Class> imports) {
    params.stream().map(ParamInfo::getType).forEach(a -> a.collectImports(imports));
  }

  @Override
  public int compareTo(MethodInfo o) {
    int cmp = name.compareTo(o.name);
    if (cmp != 0) {
      return cmp;
    }
    Iterator<ParamInfo> i1 = params.iterator();
    Iterator<ParamInfo> i2 = o.params.iterator();
    while (i1.hasNext() && i2.hasNext()) {
      ParamInfo p1 = i1.next();
      ParamInfo p2 = i2.next();
      cmp = p1.getType().getRaw().getName().compareTo(p2.getType().getRaw().getName());
      if (cmp != 0) {
        return cmp;
      }
    }
    if (i1.hasNext()) {
      if (!i2.hasNext()) {
        return 1;
      }
    } else if (!i2.hasNext()) {
      return -1;
    }
    return 0;
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
