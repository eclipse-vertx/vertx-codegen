package io.vertx.codegen;

import io.vertx.codegen.type.TypeVariableInfo;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public abstract class TypeVariableResolution {

  private final TypeVariableInfo variable;
  private final boolean foobar;

  public boolean isFoobar() {
    return foobar;
  }

  public TypeVariableResolution(TypeVariableInfo variable, boolean foobar) {
    this.variable = variable;
    this.foobar = foobar;
  }

  public static class Class extends TypeVariableResolution {

    private final ParamInfo param;

    public Class(TypeVariableInfo variable, ParamInfo param) {
      super(variable, true);
      this.param = param;
    }

    public ParamInfo getParam() {
      return param;
    }
  }

  public static class Parameterized extends TypeVariableResolution {

    private final ParamInfo param;
    private final int index;

    public Parameterized(TypeVariableInfo variable, ParamInfo param, int index) {
      super(variable, false);
      this.param = param;
      this.index = index;
    }

    public ParamInfo getParam() {
      return param;
    }

    public int getIndex() {
      return index;
    }
  }
}
