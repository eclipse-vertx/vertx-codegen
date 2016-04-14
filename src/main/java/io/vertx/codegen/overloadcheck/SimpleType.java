package io.vertx.codegen.overloadcheck;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class SimpleType {

  private static final String ALL_TYPE = "ALL";

  final String name;
  final boolean nullable;

  public SimpleType(String value, boolean nullable) {
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
    return name.equals(other.name) || isAll() || other.isAll() || (nullable && other.nullable);
  }
}
