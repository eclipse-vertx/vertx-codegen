package io.vertx.codegen.overloadcheck;

import java.util.HashSet;
import java.util.Set;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class SimpleType {

  private static final Set<String> ALL_TYPE = new HashSet<>();

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
      return name.equals(that.name);
    }
    return false;
  }

  public boolean isAll() {
    return name.equals(ALL_TYPE);
  }

  public boolean matches(SimpleType other) {
    Set<String> intersection = new HashSet<>(name);
    intersection.retainAll(other.name);
    return intersection.size() > 0 || isAll() || other.isAll() || (nullable && other.nullable);
  }
}
