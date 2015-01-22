package io.vertx.codegen;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class PropertyInfo {

  final boolean declared;
  final String name;
  final TypeInfo type;
  final String methodName;
  final String getter;
  final boolean array;
  final boolean adder;

  public PropertyInfo(boolean declared, String name, TypeInfo type, String methodName, boolean array, boolean adder, String getter) {
    this.declared = declared;
    this.name = name;
    this.type = type;
    this.methodName = methodName;
    this.array = array;
    this.adder = adder;
    this.getter = getter;
  }

  public boolean isDeclared() {
    return declared;
  }

  public String getName() {
    return name;
  }

  public TypeInfo getType() {
    return type;
  }

  public String getMethodName() {
    return methodName;
  }

  public String getGetter() {
    return getter;
  }

  public boolean isArray() {
    return array;
  }

  public boolean isAdder() {
    return adder;
  }
}
