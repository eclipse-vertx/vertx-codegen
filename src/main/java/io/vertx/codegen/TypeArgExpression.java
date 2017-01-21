package io.vertx.codegen;

import io.vertx.codegen.type.TypeVariableInfo;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class TypeArgExpression {

  static final int CLASS_TYPE_ARG = 0;
  static final int API_ARG = 1;

  private final int kind;
  private final ParamInfo param;
  private final TypeVariableInfo variable;
  private final int index;

  TypeArgExpression(int kind, TypeVariableInfo variable, ParamInfo param, int index) {
    this.kind = kind;
    this.variable = variable;
    this.param = param;
    this.index = index;
  }

  public ParamInfo getParam() {
    return param;
  }

  public TypeVariableInfo getVariable() {
    return variable;
  }

  public int getIndex() {
    return index;
  }

  public boolean isClassType() {
    return kind == CLASS_TYPE_ARG;
  }

  public boolean isApi() {
    return kind == API_ARG;
  }
}
