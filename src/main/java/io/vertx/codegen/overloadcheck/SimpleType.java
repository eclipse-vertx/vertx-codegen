package io.vertx.codegen.overloadcheck;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class SimpleType {

  // Can be
  // null
  // empty (all)
  // or a set of names
  final Set<String> name;
  final boolean nullable;

  public SimpleType(Set<String> value, boolean nullable) {
    this.name = value;
    this.nullable = nullable;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof SimpleType) {
      SimpleType that = (SimpleType) obj;
      return Objects.equals(name, that.name);
    }
    return false;
  }

  public boolean isAll() {
    return name != null && name.isEmpty();
  }

  public boolean matches(SimpleType other) {
    if (nullable && other.nullable) {
      return true;
    }
    if (name == null || other.name == null) {
      return false;
    }
    Set<String> intersection = new HashSet<>(name);
    intersection.retainAll(other.name);
    return intersection.size() > 0 || isAll() || other.isAll();
  }
}
