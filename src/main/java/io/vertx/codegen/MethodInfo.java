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
import io.vertx.codegen.type.AnnotationValueInfo;
import io.vertx.codegen.type.ClassKind;
import io.vertx.codegen.type.ClassTypeInfo;
import io.vertx.codegen.type.ParameterizedTypeInfo;
import io.vertx.codegen.type.TypeInfo;
import io.vertx.codegen.type.TypeVariableInfo;

import javax.lang.model.element.Name;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
public class MethodInfo implements Comparable<MethodInfo> {

  private String name;
  private TypeInfo returnType;
  private Text returnDescription;
  private boolean fluent;
  private boolean cacheReturn;
  private String comment;
  private Doc doc;
  private boolean staticMethod;
  private boolean defaultMethod;
  private List<TypeParamInfo.Method> typeParams;
  private Set<ClassTypeInfo> ownerTypes;
  private List<ParamInfo> params;
  private boolean deprecated;
  private Text deprecatedDesc;
  private Name companion;
  private List<AnnotationValueInfo> annotations;

  public MethodInfo(Set<ClassTypeInfo> ownerTypes, String name,
                    TypeInfo returnType, Text returnDescription, boolean fluent,  boolean cacheReturn,
                    List<ParamInfo> params, String comment, Doc doc, boolean staticMethod, boolean defaultMethod,
                    List<TypeParamInfo.Method> typeParams, boolean deprecated, Text deprecatedDesc) {


    this.comment = comment;
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
    this.ownerTypes = ownerTypes;
    this.deprecated = deprecated;
    this.deprecatedDesc = deprecatedDesc;
  }

  public String getName() {
    return name;
  }

  public MethodInfo setName(String name) {
    this.name = name;
    return this;
  }

  public String getName(Case _case) {
    return _case.format(Case.CAMEL.parse(name));
  }

  public MethodKind getKind() {
    int lastParamIndex = params.size() - 1;
    if (lastParamIndex >= 0 && (returnType.isVoid() || fluent)) {
      TypeInfo lastParamType = params.get(lastParamIndex).type;
      if (lastParamType.getKind() == ClassKind.HANDLER) {
        TypeInfo typeArg = ((ParameterizedTypeInfo) lastParamType).getArgs().get(0);
        if (typeArg.getKind() == ClassKind.ASYNC_RESULT) {
          return MethodKind.FUTURE;
        } else {
          return MethodKind.HANDLER;
        }
      }
    }
    return MethodKind.OTHER;
  }

  public TypeInfo getReturnType() {
    return returnType;
  }

  public MethodInfo setReturnType(TypeInfo returnType) {
    this.returnType = returnType;
    return this;
  }

  /**
   * @return whether the method is annotated with {@link io.vertx.codegen.annotations.GenIgnore}
   */
  public boolean isContainingAnyJavaType() {
    return params.stream().map(ParamInfo::getType).anyMatch(MethodInfo::containsAnyJavaType) || containsAnyJavaType(returnType);
  }

  private static boolean containsAnyJavaType(TypeInfo type) {
    if (type instanceof ParameterizedTypeInfo) {
      return containsAnyJavaType(type.getRaw()) || ((ParameterizedTypeInfo) type).getArgs().stream().anyMatch(MethodInfo::containsAnyJavaType);
    } else {
      return type.getKind() == ClassKind.OTHER;
    }
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
    TypeArgExpression res = resolveTypeArg(typeVar);
    if (res != null && res.isClassType()) {
      return res.getParam();
    }
    return null;
  }

  public TypeArgExpression resolveTypeArg(TypeVariableInfo typeVar) {
    for (TypeParamInfo.Method typeParam : typeParams) {
      if (typeParam.getName().equals(typeVar.getName())) {
        for (ParamInfo param : params) {
          if (param.getType().getKind() == ClassKind.CLASS_TYPE &&
            param.getType().isParameterized()) {
            TypeInfo arg_ = ((ParameterizedTypeInfo) param.getType()).getArg(0);
            if (arg_.isVariable()) {
              TypeVariableInfo ttt = (TypeVariableInfo) arg_;
              if (ttt.getParam().equals(typeParam)) {
                return new TypeArgExpression(TypeArgExpression.CLASS_TYPE_ARG, ttt, param, 0);
              }
            }
          } else if (param.getType().getKind() == ClassKind.API && param.getType().isParameterized()) {
            ParameterizedTypeInfo type = (ParameterizedTypeInfo) param.getType();
            int index = 0;
            for (TypeInfo i : type.getArgs()) {
              if (i instanceof TypeVariableInfo) {
                TypeVariableInfo tt = (TypeVariableInfo) i;
                if (tt.getParam().equals(typeParam)) {
                  return new TypeArgExpression(TypeArgExpression.API_ARG, tt, param, index);
                }
              }
              index++;
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

  public MethodInfo setReturnDescription(Text returnDescription) {
    this.returnDescription = returnDescription;
    return this;
  }

  public Set<ClassTypeInfo> getOwnerTypes() {
    return ownerTypes;
  }

  public MethodInfo setOwnerTypes(Set<ClassTypeInfo> ownerTypes) {
    this.ownerTypes = ownerTypes;
    return this;
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

  public MethodInfo setFluent(boolean fluent) {
    this.fluent = fluent;
    return this;
  }

  public boolean isCacheReturn() {
    return cacheReturn;
  }

  public MethodInfo setCacheReturn(boolean cacheReturn) {
    this.cacheReturn = cacheReturn;
    return this;
  }

  /**
   * @return true if the method has a nullable return
   */
  public boolean isNullableReturn() {
    return returnType.isNullable();
  }

  public List<ParamInfo> getParams() {
    return params;
  }

  public MethodInfo setParams(List<ParamInfo> params) {
    this.params = params;
    return this;
  }

  public ParamInfo getParam(int index) {
    return params.get(index);
  }

  public String getComment() {
    return comment;
  }

  public MethodInfo setComment(String comment) {
    this.comment = comment;
    return this;
  }

  public Doc getDoc() {
    return doc;
  }

  public MethodInfo setDoc(Doc doc) {
    this.doc = doc;
    return this;
  }

  public boolean isStaticMethod() {
    return staticMethod;
  }

  public MethodInfo setStaticMethod(boolean staticMethod) {
    this.staticMethod = staticMethod;
    return this;
  }

  public boolean isDefaultMethod() {
    return defaultMethod;
  }

  public MethodInfo setDefaultMethod(boolean defaultMethod) {
    this.defaultMethod = defaultMethod;
    return this;
  }

  public String getCompanion() {
    return Objects.toString(companion);
  }

  public MethodInfo setCompanion(Name companionObjectName) {
    this.companion = companionObjectName;
    return this;
  }

  public boolean isCompanionMethod() {
    return companion != null;
  }

  /**
   *
   * @return {@code true} if the method has a {@code @Deprecated} annotation
   */
  public boolean isDeprecated() {
    return deprecated;
  }

  public MethodInfo setDeprecated(boolean deprecated) {
    this.deprecated = deprecated;
    return this;
  }

  /**
   * @return the description of deprecated
   */
  public Text getDeprecatedDesc() {
    return deprecatedDesc;
  }

  public MethodInfo setDeprecatedDesc(Text deprecatedDesc) {
    this.deprecatedDesc = deprecatedDesc;
    return this;
  }

  public List<TypeParamInfo.Method> getTypeParams() {
    return typeParams;
  }

  public MethodInfo setTypeParams(List<TypeParamInfo.Method> typeParams) {
    this.typeParams = typeParams;
    return this;
  }

  public List<AnnotationValueInfo> getAnnotations() {
    return annotations;
  }

  public MethodInfo setAnnotations(List<AnnotationValueInfo> annotations) {
    this.annotations = annotations;
    return this;
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

  /**
   * @return a copy of this object
   */
  public MethodInfo copy() {
    return new MethodInfo(
      new HashSet<>(ownerTypes),
      name,
      returnType,
      returnDescription,
      fluent,
      cacheReturn,
      new ArrayList<>(params),
      comment,
      doc,
      staticMethod,
      defaultMethod,
      new ArrayList<>(typeParams),
      deprecated,
      deprecatedDesc);
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
