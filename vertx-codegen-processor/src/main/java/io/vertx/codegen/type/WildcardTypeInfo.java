package io.vertx.codegen.type;

public class WildcardTypeInfo extends TypeInfo {

  public static final WildcardTypeInfo INSTANCE = new WildcardTypeInfo();

  private WildcardTypeInfo() {
  }

  @Override
  public boolean equals(Object obj) {
    return obj == this;
  }

  @Override
  public ClassKind getKind() {
    return ClassKind.OTHER;
  }

  @Override
  String format(boolean qualified) {
    return "?";
  }
}
