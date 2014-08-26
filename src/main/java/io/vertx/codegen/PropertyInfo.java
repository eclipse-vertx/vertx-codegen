package io.vertx.codegen;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class PropertyInfo {

  final String name;
  final TypeInfo type;
  final boolean array;

  public PropertyInfo(String name, TypeInfo type, boolean array) {
    this.name = name;
    this.type = type;
    this.array = array;
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
