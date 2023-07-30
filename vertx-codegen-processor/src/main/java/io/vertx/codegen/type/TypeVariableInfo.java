package io.vertx.codegen.type;

import io.vertx.codegen.TypeParamInfo;

import java.util.Collections;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class TypeVariableInfo extends TypeInfo {

  final String name;
  final boolean nullable;
  final TypeParamInfo param;

  public TypeVariableInfo(TypeParamInfo param, boolean nullable, String name) {
    this.param = param;
    this.nullable = nullable;
    this.name = name;
  }

  public TypeParamInfo getParam() {
    return param;
  }

  public boolean isClassParam() {
    return param.isClass();
  }

  public boolean isMethodParam() {
    return param.isMethod();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof io.vertx.codegen.type.TypeVariableInfo) {
      io.vertx.codegen.type.TypeVariableInfo that = (io.vertx.codegen.type.TypeVariableInfo) obj;
      return param.equals(that.param);
    } else {
      return false;
    }
  }

  @Override
  public boolean isNullable() {
    return nullable;
  }

  @Override
  public TypeInfo getErased() {
    return new ClassTypeInfo(ClassKind.OBJECT, Object.class.getName(), null, false, Collections.emptyList(), null);
  }

  @Override
  public String toString() {
    return name;
  }

  @Override
  public String format(boolean qualified) {
    return name;
  }

  @Override
  public ClassKind getKind() {
    return ClassKind.OBJECT;
  }

  @Override
  public boolean isVariable() {
    return true;
  }
}
