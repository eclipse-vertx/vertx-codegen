package io.vertx.codegen;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class PropertyInfo {

  final boolean declared;
  final String name;
  final TypeInfo type;
  final boolean array;

  public PropertyInfo(boolean declared, String name, TypeInfo type, boolean array) {
    this.declared = declared;
    this.name = name;
    this.type = type;
    this.array = array;
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

  public boolean isArray() {
    return array;
  }
}
