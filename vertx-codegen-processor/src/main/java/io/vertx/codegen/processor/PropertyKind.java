package io.vertx.codegen.processor;

import io.vertx.codegen.processor.type.ClassKind;

import java.util.HashMap;
import java.util.Map;

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
   * The property is a list with a setter and optionally a getter.
   */
  SET,

  /**
   * The property is a map with a setter and optionally a getter.
   */
  MAP;

  /**
   * @return true if the property kind is a list
   */
  public boolean isList() {
    return this == LIST;
  }

  /**
   * @return true if the property kind is a set
   */
  public boolean isSet() {
    return this == SET;
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
   * Useful for testing the type property kind, allows to do prop.kind == PROP_VALUE instead of prop.kind.name() == "VALUE"
   */
  public static Map<String, PropertyKind> vars() {
    HashMap<String, PropertyKind> vars = new HashMap<>();
    for (PropertyKind propKind : PropertyKind.values()) {
      vars.put("PROP_" + propKind.name(), propKind);
    }
    return vars;
  }

  static PropertyKind forType(ClassKind kind) {
    switch (kind) {
      case LIST:
        return PropertyKind.LIST;
      case SET:
        return PropertyKind.SET;
      case MAP:
        return PropertyKind.MAP;
      default:
        return PropertyKind.VALUE;
    }
  }
}
