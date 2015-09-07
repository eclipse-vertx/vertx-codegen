package io.vertx.codegen;

/**
 * The kind of property.
 *
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public enum PropertyKind {

  /**
   * The property is a single value setter and optionally a getter.
   */
  VALUE,

  /**
   * The property is a list with a setter and optionally a getter.
   */
  LIST,

  /**
   * The property is a list with an adder and optionally a getter.
   */
  LIST_ADD,

  /**
   * The property is a map with a setter and optionally a getter.
   */
  MAP;

  /**
   * @return true if the property kind is a list
   */
  public boolean isList() {
    return this == LIST || this == LIST_ADD;
  }

  /**
   * @return true if the property kind is a map
   */
  public boolean isMap() {
    return this == MAP;
  }

  /**
   * @return true if the property kind is a single value
   */
  public boolean isValue() {
    return this == VALUE;
  }

  /**
   * @return true if the property kind is list adder
   */
  public boolean isAdder() {
    return this == LIST_ADD;
  }
}
