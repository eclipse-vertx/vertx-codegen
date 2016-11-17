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
import io.vertx.codegen.doc.Text;
import io.vertx.codegen.type.ClassKind;
import io.vertx.codegen.type.ClassTypeInfo;
import io.vertx.codegen.type.ParameterizedTypeInfo;
import io.vertx.codegen.type.TypeInfo;
import io.vertx.codegen.type.TypeVariableInfo;

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
  final Text returnDescription;
  final boolean fluent;
  final boolean cacheReturn;
  final String comment;
  final Doc doc;
  final boolean staticMethod;
  final boolean defaultMethod;
  List<TypeParamInfo.Method> typeParams;
  LinkedHashSet<ClassTypeInfo> ownerTypes;
  List<ParamInfo> params;

  public MethodInfo(Set<ClassTypeInfo> ownerTypes, String name, MethodKind kind,
                    TypeInfo returnType, Text returnDescription, boolean fluent,  boolean cacheReturn,
                    List<ParamInfo> params, String comment, Doc doc, boolean staticMethod, boolean defaultMethod,
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
    this.defaultMethod = defaultMethod;
    this.params = params;
    this.typeParams = typeParams;
    this.ownerTypes = new LinkedHashSet<>(ownerTypes);
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

  /**
   * Resolve the method parameter that is of kind {@link ClassKind#CLASS_TYPE} and that matches the specified type variable e.g:<br/>
   * <br/>
   * {@code <U> Map.Entry<String, U> getEntry(String s, Class<U> type);}
   * <br/>
   * <br/>
   * returns for {@code <U>} the second method parameter.
   *
   * @param typeVar the type variable to check
   * @return the matching method parameter or null
   */
  public ParamInfo resolveClassTypeParam(TypeVariableInfo typeVar) {
    for (TypeParamInfo.Method typeParam : typeParams) {
      if (typeParam.getName().equals(typeVar.getName())) {
        for (ParamInfo param : params) {
          if (param.getType().getKind() == ClassKind.CLASS_TYPE &&
              param.getType().isParameterized()) {
            TypeInfo arg_ = ((ParameterizedTypeInfo) param.getType()).getArg(0);
            if (arg_.isVariable() && typeVar.getName().equals(arg_.getName())) {
              return param;
            }
          }
        }
        return null;
      }
    }
    return null;
  }

  public Text getReturnDescription() {
    return returnDescription;
  }

  public Set<ClassTypeInfo> getOwnerTypes() {
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
  public boolean isOwnedBy(ClassTypeInfo owner) {
    return ownerTypes.contains(owner) && ownerTypes.size() == 1;
  }

  public boolean isFluent() {
    return fluent;
  }

  public boolean isCacheReturn() {
    return cacheReturn;
  }

  /**
   * @return true if the method has a nullable return
   */
  public boolean isNullableReturn() {
    return returnType instanceof TypeVariableInfo || returnType.isNullable();
  }

  public List<ParamInfo> getParams() {
    return params;
  }

  public ParamInfo getParam(int index) {
    return params.get(index);
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

  public boolean isDefaultMethod() {
    return defaultMethod;
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

  public void collectImports(Collection<ClassTypeInfo> imports) {
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
        sb.append(typeParams.get(i).getName());
      }
      sb.append("> ");
    }
    sb.append(returnType.getName());
    sb.append(' ');
    sb.append(getSignature().toString());
    return sb.toString();
  }
}
